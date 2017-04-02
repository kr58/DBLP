/**
 *  This is the main class which kick starts the dblp query engine
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.FutureTask;

public class Main
{
    public static void main(String args[])
    {
        EntityResolver.valueOf("Sanjeev Saxena"); // dummy call to generate database
        EntityResolver.valueOf("Vinayak");
        new Gui("dblp query engine").start();

        //String[] authors = {"","","","",""};

        //try { System.out.println((ArrayList<Integer>) new SearchResults(authors,2014).call());}
        //catch (Exception e) { e.printStackTrace(); }
    }
}