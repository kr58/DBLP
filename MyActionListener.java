/**
 * This class integrate Gui class with main processing Searcher class
 * This is the default Action Listner for all the GUI components
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.*;

public class MyActionListener implements ActionListener{
    private Gui gui;
    private Runnable search;
    private static final ExecutorService threadpool = Executors.newFixedThreadPool(3);
    MyActionListener(Gui gui) {this.gui=gui;}
    public void actionPerformed(ActionEvent e)
    {
        String s=e.getActionCommand();
        if(s.equals("Search")) {
            if(gui.data_check()==1) return;
            gui.clear_list();
            //try{Thread.sleep(10);}catch (Exception ex){}
            if(gui.get_query()==1)
            {
                if(gui.select()==1) return;
                System.out.print("animation done");
                System.out.println(gui.get_authorandtag());
                System.out.println(gui.get_yearsincebetween());
                System.out.println(gui.get_sort());

                System.out.println(gui.get_Authortag());

                /*if(gui.get_sort()==1) result=new SortByYear(search);
                else if(gui.get_sort()==2) result=new SortByRelevance(search);

                gui.set_querylist(result.getMatches(gui.get_Authortag(),true));
                //if(gui.get_authorandtag()==1) System.out.println(result.getMatches(gui.get_Authortag(),true).size());
                //else System.out.println(result.getMatches(gui.get_Authortag(),false).size());
                //gui.set_querylist(result.getMatches("24",true));*/

                factorypattern();
                gui.animation();
                Thread th1=new Thread(search);
                th1.start();
            }
            else if(gui.get_query()==2)
            {
                //System.out.println("Start resolving");
                System.out.println(gui.publication());
                gui.animation();

                factorypattern();

                Thread th2=new Thread(search);
                th2.start();

            }
            else if(gui.get_query()==3)
            {
                gui.animation();

                factorypattern();

                Thread th3=new Thread(search);
                th3.start();
            }

        }
        else if(s.equals("Reset")) {gui.getContentPane().removeAll();gui.repaint();gui.start();}
        else if(s.equals("Next")) {gui.next();}
        else if(s.equals("Queries"))
        {
            String str=gui.queryStarting();
            if(str.equals("Query - 1")) gui.query1();
            else if(str.equals("Query - 2")) gui.query2();
            else if(str.equals("Query - 3")) gui.query3();
            gui.setVisible(true);
        }
        else if(s.equals("Years"))
        {
            String str=gui.startinquery1();
            if(str.equals("Since Year")) gui.Yearquery(1);
            else if(str.equals("Between Year")) gui.Yearquery(2);
            else if(str.equals("None")) gui.Yearquery(3);
            else {gui.setVisible(true);}
        }
        else if(s.equals("Previous")) gui.previous();
    }
    private void factorypattern()
    {
        if(gui.get_query()==1 && gui.get_sort()==1 && gui.get_authorandtag()==1 && gui.get_yearsincebetween()==3)
        {
            search=new SortByYear(new SearchResults(gui.get_Authortag(),true,this));
        }
        else if(gui.get_query()==1 && gui.get_sort()==1 && gui.get_authorandtag()==2 && gui.get_yearsincebetween()==3)
        {
            search=new SortByYear(new SearchResults(gui.get_Authortag(),false,this));
        }
        else if(gui.get_query()==1 && gui.get_sort()==2 && gui.get_authorandtag()==1 && gui.get_yearsincebetween()==3)
        {
            search=new SortByRelevance(new SearchResults(gui.get_Authortag(),true,this));
        }
        else if(gui.get_query()==1 && gui.get_sort()==2 && gui.get_authorandtag()==2 && gui.get_yearsincebetween()==3)
        {
            search=new SortByRelevance(new SearchResults(gui.get_Authortag(),false,this));
        }
        else if(gui.get_query()==1 && gui.get_sort()==1 && gui.get_authorandtag()==1 && gui.get_yearsincebetween()==1) {
            search = new SortByYear(new SinceYear(new SearchResults(gui.get_Authortag(),true,this),gui.get_Since()));
        }
        else if(gui.get_query()==1 && gui.get_sort()==1 && gui.get_authorandtag()==1 && gui.get_yearsincebetween()==2){
            search = new SortByYear(new BetweenYears(new SearchResults(gui.get_Authortag(),true,this),gui.get_Since(),gui.get_end()));
        }
        else if(gui.get_query()==1 && gui.get_sort()==1 && gui.get_authorandtag()==2 && gui.get_yearsincebetween()==1){
            search = new SortByYear(new SinceYear(new SearchResults(gui.get_Authortag(),false,this),gui.get_Since()));
        }
        else if(gui.get_query()==1 && gui.get_sort()==1 && gui.get_authorandtag()==2 && gui.get_yearsincebetween()==2){
            search = new SortByYear(new BetweenYears(new SearchResults(gui.get_Authortag(),false,this),gui.get_Since(),gui.get_end()));
        }
        else if(gui.get_query()==1 && gui.get_sort()==2 && gui.get_authorandtag()==1 && gui.get_yearsincebetween()==1){
            search = new SortByRelevance(new SinceYear(new SearchResults(gui.get_Authortag(),true,this),gui.get_Since()));
        }
        else if(gui.get_query()==1 && gui.get_sort()==2 && gui.get_authorandtag()==1 && gui.get_yearsincebetween()==2){
            search = new SortByRelevance(new BetweenYears(new SearchResults(gui.get_Authortag(),true,this),gui.get_Since(),gui.get_end()));
        }
        else if(gui.get_query()==1 && gui.get_sort()==2 && gui.get_authorandtag()==2 && gui.get_yearsincebetween()==1){
            search = new SortByRelevance(new SinceYear(new SearchResults(gui.get_Authortag(),false,this),gui.get_Since()));
        }
        else if(gui.get_query()==1 && gui.get_sort()==2 && gui.get_authorandtag()==2 && gui.get_yearsincebetween()==2){
            search = new SortByRelevance(new BetweenYears(new SearchResults(gui.get_Authortag(),false,this),gui.get_Since(),gui.get_end()));
        }
        else if(gui.get_query()==2)
        {
            search=new SearchResults(gui.publication(),this);
        }
        else if(gui.get_query()==3)
        {
            String[] txt=gui.get_query3();
            search=new SearchResults(new String[]{txt[1],txt[2],txt[3],txt[4],txt[5]},Integer.parseInt(txt[0]),this);
        }
    }
    public void printQuery1(ArrayList<Record> a)
    {
        gui.set_querylist(a);
        gui.displayquery();
        gui.removeanimation();
    }
    public void printQuery2(ArrayList<String> a)
    {
        gui.set_list(a);
        gui.display();
        gui.removeanimation();
    }
    public void printQuery3(ArrayList<Integer> a)
    {
        gui.setQuery3(a);
        gui.display_query3();
        gui.removeanimation();
    }
    public void error(String s)
    {
        new Error_Message(s);
    }
}