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
			while(eventReader.hasNext())
			{
				XMLEvent event = eventReader.nextEvent();
				switch(event.getEventType())
				{
					case XMLStreamConstants.START_ELEMENT:

						String openTag = event.asStartElement().getName().getLocalPart();
						if(set.add(openTag)) System.out.println(openTag); break;
					/*	
					case XMLStreamConstants.CHARACTERS:

						Characters characters = event.asCharacters();
						break;

					case  XMLStreamConstants.END_ELEMENT:

						EndElement endElement = event.asEndElement();
						break;
					*/
				}
			}
		}
		
		catch(FileNotFoundException e){ System.out.println(e); }
		catch(XMLStreamException e){ System.out.println(e); }
	}
}
