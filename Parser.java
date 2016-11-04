import java.io.*;
import java.util.*;
import javax.xml.stream.*;
import javax.xml.stream.events.*;

public class Parser
{
	public static void main(String args[])
	{
		HashSet<String> set=new HashSet<>();
		HashMap<String,Boolean> map=new HashMap<>();
		try
		{
			XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(new FileReader("dblp.xml"));
			
		}
		
		catch(FileNotFoundException e){ System.out.println(e); }
		catch(XMLStreamException e){ System.out.println(e); }
	}
}