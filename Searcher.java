/**
 *  This class defines how a searcher will look like
 *  It is useful in further implementing different types of searcher
 */

import java.util.ArrayList;

public abstract class Searcher implements Runnable
{
    private Boolean isBusy;
    protected MyActionListener painter;

    protected abstract ArrayList<Record> getMatches();
    protected abstract String getToSearch();
    protected abstract void query1();
    protected abstract void query2();
    protected abstract void query3();
    protected abstract int getQueryType();
    protected abstract MyActionListener getPainter();

    protected synchronized void setBusy(boolean b){ isBusy = b; }
    protected synchronized Boolean getBusy(){ return isBusy; }
}