/**
 *  This class handles all the queries that are passed in dblp query engine
 *  It has three contructors
 *
 *  SearchResults(String,boolean) - for query1
 *  SearchResults(int)            - for query2
 *  SearchResults(String[],int)   - for query3
 *
 *  Query1 - Search using title / author
 *  Query2 - Find no. of authors with greater than a given no of publications
 *  Query3 - Given a year predict the no of publications in the next year for 5 authors
 *
 *  It also implements Callable interface so it can be used with a future object to run its
 *  highly time taking process while the caller thread can keep on working on something more useful
 */


import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SearchResults extends Searcher
{
    private final String[] pubs = { "article","inproceedings","proceedings","book","incollection","phdthesis","mastersthesis" };
    private final int queryType;
    private ArrayList<Record> matches;
    private String toSearch;
    private Boolean byAuthor;

    private HashMap<Long,Long> noOfPubs;
    private ArrayList<String> chosenAuthors;
    private Set<String> allAuthorNames;
    private int k;

    private String[] authorNames;
    private int tillYear; private ArrayList<Integer> prediction;
    private ArrayList<HashMap<Integer,Integer>> yearToPubs;

    SearchResults(String toSearch, boolean byAuthor, MyActionListener painter) {
        queryType=1;
        setBusy(false);
        this.painter = painter;
        this.toSearch = toSearch;
        this.byAuthor = byAuthor;
    }

    SearchResults(int k, MyActionListener painter) {
        queryType=2;
        setBusy(false);
        allAuthorNames = EntityResolver.allAuthors();
        this.painter = painter;
        this.k = k;
    }

    SearchResults(String[] authorNames,int tillYear, MyActionListener painter) {
        queryType=3;
        setBusy(false);
        yearToPubs = new ArrayList<HashMap<Integer,Integer>>();
        this.painter = painter;
        this.authorNames = authorNames;
        this.tillYear = tillYear+1;
    }

    protected ArrayList<Record> getMatches(){ return matches; }
    protected String getToSearch(){ return toSearch; }
    protected int getQueryType(){ return queryType; }
    protected MyActionListener getPainter(){ return painter; }

    @Override
    public void run()
    {
        if(getBusy()){ painter.error("Still busy"); return; }

        setBusy(true);
        switch(getQueryType())
        {
            case 1 : query1(); painter.printQuery1(matches); break;
            case 2 : query2(); break;
            case 3 : query3(); break;
        }
        setBusy(false);
    }

    private boolean isPub(String s)
    {
        for(String i:pubs) if(i.equals(s)) return true;
        return false;
    }

    @Override
    protected void query1()
    {
        boolean recordStart=false,authorStart=false,titleStart=false,yearStart=false,pagesStart=false,volumeStart=false,journalStart=false,booktitleStart=false,urlStart=false;
        matches = new ArrayList<>(); Record current = null;
        ArrayList<String> authors = null; String currentAuthor = "";
        try
        {
            XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(new FileReader("dblp.xml"));
            while(eventReader.hasNext())
            {
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType())
                {
                    case XMLStreamConstants.START_ELEMENT:

                        String openTag = event.asStartElement().getName().getLocalPart();
                        if(recordStart)
                        {
                            switch (openTag)
                            {
                                case "author": authorStart = true; currentAuthor=""; break;
                                case "title": titleStart = true; break;
                                case "year": yearStart = true; break;
                                case "pages": pagesStart = true; break;
                                case "volume": volumeStart = true; break;
                                case "journal": journalStart = true; break;
                                case "booktitle": booktitleStart = true; break;
                                case "url": urlStart = true; break;
                            }
                        }
                        else if(isPub(openTag))
                        { current = new Record(); authors = new ArrayList<>(); recordStart = true; }
                        break;

                    case XMLStreamConstants.CHARACTERS:

                        String line = event.asCharacters().toString();
                        if(authorStart) currentAuthor+=line;
                        else if(titleStart)
                        {
                            if(current.getTitle()!=null) current.setTitle(current.getTitle()+line);
                            else current.setTitle(line);
                        }
                        else if(pagesStart) current.setPages(line);
                        else if(volumeStart) current.setVolume(line);
                        else if(journalStart) current.setJournal(line);
                        else if(booktitleStart) current.setBooktitle(line);
                        else if(urlStart) current.setUrl(line);
                        else if(yearStart) current.setYear(Integer.parseInt(line));
                        break;

                    case  XMLStreamConstants.END_ELEMENT:

                        String endTag = event.asEndElement().getName().getLocalPart();
                        if(recordStart)
                        {
                            if(endTag.equals("author")){ authors.add(currentAuthor); authorStart = false; }
                            else if(endTag.equals("title")) titleStart = false;
                            else if(endTag.equals("year")) yearStart = false;
                            else if(endTag.equals("pages")) pagesStart = false;
                            else if(endTag.equals("volume")) volumeStart = false;
                            else if(endTag.equals("journal")) journalStart = false;
                            else if(endTag.equals("booktitle")) booktitleStart = false;
                            else if(endTag.equals("url")) urlStart = false;
                            else if(isPub(endTag))
                            {
                                recordStart = false;
                                if(byAuthor)
                                {
                                    for(String t:authors)
                                    if(EntityResolver.isSame(t,toSearch))
                                    {
                                        current.setAuthors(authors);
                                        matches.add(current);
                                        break;
                                    }
                                }
                                else if(current.getTitle()!=null && current.getTitle().contains(toSearch))
                                {
                                    current.setAuthors(authors);
                                    matches.add(current);
                                }
                            }
                        }
                        break;
                } eventReader.close();
            }
        }
        catch(FileNotFoundException | XMLStreamException e){ System.out.println(e); }
    }

    @Override
    protected void query2()
    {
        if(noOfPubs==null)
        {
            noOfPubs = new HashMap<Long,Long>();
            try
            {
                String currentAuthor=""; boolean recordStart = false,authorStart = false;
                XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(new FileReader("dblp.xml"));
                while(eventReader.hasNext())
                {
                    XMLEvent event = eventReader.nextEvent();
                    switch(event.getEventType())
                    {
                        case XMLStreamConstants.START_ELEMENT:

                            String openTag = event.asStartElement().getName().getLocalPart();
                            if(recordStart && openTag.equals("author")) authorStart = true;
                            else if(isPub(openTag)) recordStart = true; break;

                        case XMLStreamConstants.CHARACTERS:

                            String line = event.asCharacters().toString();
                            if(authorStart) currentAuthor+=line;
                            break;

                        case  XMLStreamConstants.END_ELEMENT:

                            String endTag = event.asEndElement().getName().getLocalPart();
                            if(recordStart)
                            {
                                if(isPub(endTag)) recordStart = false;
                                else if(endTag.equals("author"))
                                {
                                    authorStart = false;
                                    long val = EntityResolver.valueOf(currentAuthor);
                                    if(noOfPubs.containsKey(val)) noOfPubs.replace(val,noOfPubs.get(val)+1);
                                    else noOfPubs.put(val,(long)1);
                                    currentAuthor = "";
                                }
                            } break;
                    }
                }
                eventReader.close();
            }
            catch(FileNotFoundException | XMLStreamException e){ System.out.println(e); }
        }

        chosenAuthors = new ArrayList<>();
        for(String i:allAuthorNames)
        {
            Long te = noOfPubs.get(EntityResolver.valueOf(i));
            if(te!=null && te > k)
                chosenAuthors.add(i);
        }

        painter.printQuery2(chosenAuthors);
    }

    @Override
    protected void query3()
    {
        for(int i=0;i<5;i++)
        {
            HashMap<Integer,Integer> currentMap = new HashMap<Integer,Integer>();
            Integer year=null; Long currentAuthor = EntityResolver.valueOf(authorNames[i]);
            if(authorNames[i].isEmpty()) continue;

            try
            {
                boolean recordStart = false,authorStart = false,yearStart = false,authorMatched = false,yearMatched = false;
                XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(new FileReader("dblp.xml"));
                while(eventReader.hasNext())
                {
                    XMLEvent event = eventReader.nextEvent();
                    switch(event.getEventType())
                    {
                        case XMLStreamConstants.START_ELEMENT:

                            String openTag = event.asStartElement().getName().getLocalPart();
                            if(recordStart && openTag.equals("author")) authorStart = true;
                            else if(recordStart && openTag.equals("year")) yearStart = true;
                            else if(isPub(openTag)){ authorMatched = false; recordStart = true; } break;

                        case XMLStreamConstants.CHARACTERS:

                            String line = event.asCharacters().toString();
                            if(authorStart && EntityResolver.valueOf(line)==currentAuthor) authorMatched = true;
                            else if(yearStart && Integer.parseInt(line)<tillYear){ year=Integer.parseInt(line); yearMatched = true; } break;

                        case  XMLStreamConstants.END_ELEMENT:

                            String endTag = event.asEndElement().getName().getLocalPart();
                            if(recordStart)
                            {
                                if(isPub(endTag))
                                {
                                    if(yearMatched && authorMatched)
                                    {
                                        if(currentMap.containsKey(year)) currentMap.replace(year,currentMap.get(year)+1);
                                        else currentMap.put(year,1);
                                    }
                                    recordStart = false;
                                }
                                else if(endTag.equals("author")) authorStart = false;
                                else if(endTag.equals("year")) yearStart = false;
                            }   break;
                    }
                }
                eventReader.close();
            }
            catch(FileNotFoundException | XMLStreamException e){ System.out.println(e); }
            yearToPubs.add(currentMap);
        }

        System.out.println("Actual data from dblp file: ");
        for(HashMap<Integer,Integer> i:yearToPubs)
            System.out.println(i);

        System.out.println("Prediction for "+tillYear+" year "+prediction);
        prediction = new Classifier(yearToPubs,tillYear,0.4).getResult();

        painter.printQuery3(prediction);
    }
}