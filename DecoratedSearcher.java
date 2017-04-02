import java.util.ArrayList;

public class DecoratedSearcher extends Searcher
{
    protected Searcher wrappedSearcher;
    public DecoratedSearcher (Searcher wrappedSearcher)
    {
        this.wrappedSearcher = wrappedSearcher;
    }

    @Override
    protected void query1()
    {
        wrappedSearcher.query1();
        System.out.println("In Decorated searcher");
        // extra functionality added here
    }

    @Override
    protected void query2() { wrappedSearcher.query2(); }

    @Override
    protected void query3() { wrappedSearcher.query3(); }

    protected ArrayList<Record> getMatches(){ return wrappedSearcher.getMatches(); }
    protected String getToSearch(){ return wrappedSearcher.getToSearch(); }
    protected Boolean getBusy(){ return wrappedSearcher.getBusy(); }
    protected int getQueryType(){ return wrappedSearcher.getQueryType(); }
    protected MyActionListener getPainter(){ return wrappedSearcher.getPainter(); }

    @Override
    public void run()
    {
        if(getBusy()){ painter.error("Still busy"); return; }
        setBusy(true);

        if(getQueryType()==1)
        {
            query1(); getPainter().printQuery1(getMatches());
        }
        else wrappedSearcher.run();

        setBusy(false);
    }

}