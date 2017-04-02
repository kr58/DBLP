/**
 *  This is a decorater class for searcher class which extra functionality of sorting by year
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortByYear extends DecoratedSearcher
{
    public SortByYear(Searcher searcher)
    {
        super(searcher);
    }

    @Override
    public void query1()
    {
        wrappedSearcher.query1();
        System.out.println("In Sort by Year..");

        ArrayList<Record> matches = wrappedSearcher.getMatches();
        Collections.sort(matches, new Comparator<Record>()
        {
            @Override
            public int compare(Record o1,Record o2)
            {
                if(o1.getYear() == o2.getYear()) return 0;
                else if(o1.getYear() > o2.getYear()) return -1;
                return 1;
            }
        });
    }
}