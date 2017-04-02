/**
 *  This is a decorater class for searcher class which extra functionality of filtering the data between two years
 */

import java.util.ArrayList;

public class BetweenYears extends DecoratedSearcher
{
    private int from,to;

    public BetweenYears(Searcher searcher, int from, int to)
    {
        super(searcher);
        this.from = from;
        this.to = to;
    }

    public void query1()
    {
        wrappedSearcher.query1();
        System.out.println("In Between Years..");

        ArrayList<Record> matches = wrappedSearcher.getMatches();
        for(int i=0; i<matches.size(); i++)
        {
            if(matches.get(i).getYear()<from || matches.get(i).getYear()>to)
            { matches.remove(i); i--; }
        }
    }
}