import java.util.*;

/**
 * This class is used for predicting number of publication for five author
 * using the data for previous year
 */

public class Classifier
{
    private double alpha;
    private Double oldValue;
    private ArrayList<ArrayList<Integer>> dataSet;

    Classifier(ArrayList<HashMap<Integer,Integer>> yearToPubs,int tillYear,double alpha)
    {
        this.alpha = alpha;
        dataSet = new ArrayList<ArrayList<Integer>>();
        for(HashMap<Integer,Integer> i:yearToPubs)
        {
            ArrayList<Integer> curData = new ArrayList<Integer>();

            for(int j=1936; j<tillYear; j++)
                if(i.containsKey(j)) curData.add(i.get(j));
                else curData.add(0);

            dataSet.add(curData);
        }
    }

    private void average(double value)
    {
        if (oldValue == null)  oldValue = value;

        double newValue = oldValue + alpha * (value - oldValue);
        oldValue = newValue;
    }

    public ArrayList<Integer> getResult()
    {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(ArrayList<Integer> i:dataSet)
        {
            oldValue = null;
            for(Integer j:i) average(j);
            result.add(oldValue.intValue());
        }
        return result;
    }
}