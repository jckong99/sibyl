package sibyl;

import java.io.IOException;
import org.jsoup.nodes.Document;
import org.w3c.dom.Element;
import org.jsoup.Jsoup;

public class Wiki extends Retriever 
{
	private Document doc;
	
	public Wiki(String word) 
	{
		String url = word.toLowerCase();
		url = url.substring(0, 1).toUpperCase() + url.substring(1);
		url.replaceAll(" ", "_");
		try 
		{
			doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + url).get();
		}
		catch(IOException e)
		{
			System.out.println("No available wiki page for " + url);
		}
	}
	
	public String get()
	{
		Elements e = doc.select(".mw-parser-output *");
		ret = "";
		for(Element i : e)
		{
			if(i.className().contains("infobox"))
			{
				break;
			}
			if(i.tag().equals("p"))
			{
				ret += i.text() + "\n";
			}
		}
	}
}
