/**
 *  This class does the work of assigning an id for every author in dblp record
 *  This helps to run other queries while keeping in mind the different names of the same author
 */

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class EntityResolver
{
    private EntityResolver(){} // ensures that objects cannot be made
    private static HashMap<String,Long> resolver;

    public static boolean isSame(String author1, String author2)
    {
        createDatabase();
        return resolver.get(author1).equals(resolver.get(author2));
    }

    public static long valueOf(String name)
    {
        createDatabase();

        if(resolver.get(name)!=null)
            return resolver.get(name);

        return Integer.MIN_VALUE;
    }

    public static Set<String> allAuthors()
    {
        return resolver.keySet();
    }

    private static void createDatabase()
    {
        if(resolver!=null) return;
        resolver = new HashMap<String,Long>();
        String currentAuthor = "";

        try
        {
            boolean wwwStart = false,authorStart = false; long i=0;
            XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(new FileReader("dblp.xml"));
            while(eventReader.hasNext())
            {
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType())
                {
                    case XMLStreamConstants.START_ELEMENT:

                        String openTag = event.asStartElement().getName().getLocalPart();
                        if(wwwStart && openTag.equals("author")){ currentAuthor=""; authorStart = true; }
                        else if(openTag.equals("www")) wwwStart = true;
                        break;

                    case XMLStreamConstants.CHARACTERS:

                        String line = event.asCharacters().toString();
                        if(authorStart) currentAuthor+=line;
                        break;

                    case  XMLStreamConstants.END_ELEMENT:

                        String endTag = event.asEndElement().getName().getLocalPart();
                        if(wwwStart)
                        {
                            if(endTag.equals("www"))
                            {
                                i++; wwwStart = false;
                            }
                            else if(endTag.equals("author")){ resolver.put(currentAuthor,i); authorStart = false; }
                        }
                        break;
                }
            }
            eventReader.close();
        }
        catch(FileNotFoundException | XMLStreamException e){ System.out.println(e); }

        try
        {
            boolean authorStart = false; long i=-1;
            XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(new FileReader("dblp.xml"));
            while(eventReader.hasNext())
            {
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType())
                {
                    case XMLStreamConstants.START_ELEMENT:

                        String openTag = event.asStartElement().getName().getLocalPart();
                        if(openTag.equals("author")){ currentAuthor=""; authorStart = true; }
                        break;

                    case XMLStreamConstants.CHARACTERS:

                        String line = event.asCharacters().toString();
                        if(authorStart) currentAuthor+=line;
                        break;

                    case  XMLStreamConstants.END_ELEMENT:

                        String endTag = event.asEndElement().getName().getLocalPart();
                        if(endTag.equals("author")){
                            if(!resolver.containsKey(currentAuthor)) resolver.put(currentAuthor,i--);
                            authorStart = false;
                        }
                        break;
                }
            }
            eventReader.close();
        }
        catch(FileNotFoundException | XMLStreamException e){ System.out.println(e); }
        System.out.println("resolver database generated");
    }
}