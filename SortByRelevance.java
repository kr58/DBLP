/**
 *  This is a decorater class for searcher class which extra functionality of sorting by relevance
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortByRelevance extends DecoratedSearcher
{

    public SortByRelevance(Searcher searcher)
    {
        super(searcher);
    }

    @Override
    public void query1()
    {
        wrappedSearcher.query1();
        System.out.println("In Sort by relevance..");

        ArrayList<Record> matches = wrappedSearcher.getMatches();
        String toSearch = wrappedSearcher.getToSearch();

        Collections.sort(matches, new Comparator<Record>()
        {
            @Override
            public int compare(Record o1,Record o2)
            {
                int lastIndex=0,count1=0,count2=0;
                while(lastIndex != -1)
                {
                    lastIndex = o1.getTitle().indexOf(toSearch,lastIndex);
                    if(lastIndex != -1)
                    {
                        count1++;
                        lastIndex+=toSearch.length();
                    }
                }

                lastIndex=0;
                while(lastIndex != -1)
                {
                    lastIndex = o2.getTitle().indexOf(toSearch,lastIndex);
                    if(lastIndex != -1)
                    {
                        count2++;
                        lastIndex+=toSearch.length();
                    }
                }

                return Integer.compare(count1,count2);
            }
        });
    }
}