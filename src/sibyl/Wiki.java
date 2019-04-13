package sibyl;

import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements
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
		Elements e = doc.select("*");
		String ret = "";
		boolean start = false;
		for(Element i : e)
		{
			if(start || i.className().contains("infobox"))
			{
				start = true;
				if(i.className().contains("toc"))
				{
					break;
				}
				if(i.tagName().equals("p"))
				{
					ret += i.text() + "\n";
				}
			}
		}
		return ret;
	}
}
