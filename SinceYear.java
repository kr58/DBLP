/**
 *  This is a decorater class for searcher class which extra functionality of filtering results by a given year
 */

import java.util.ArrayList;

public class SinceYear extends DecoratedSearcher
{
    private int since;
    public SinceYear(Searcher searcher,int since)
    {
        super(searcher);
        this.since = since;
    }

    @Override
    public void query1()
    {
        wrappedSearcher.query1();
        System.out.println("In Since Year..");

        ArrayList<Record> matches = wrappedSearcher.getMatches();

        for(int i=0; i<matches.size(); i++)
        {
            if(matches.get(i).getYear()<since)
            { matches.remove(i); i--; }
        }


    }
}