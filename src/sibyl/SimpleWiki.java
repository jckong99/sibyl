package sibyl;

import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

public class SimpleWiki extends Retriever 
{
	private Document doc;
	
	public SimpleWiki(String word) 
	{
		String url = word.toLowerCase();
		url = url.substring(0, 1).toUpperCase() + url.substring(1);
		url.replaceAll(" ", "_");
		try 
		{
			doc = Jsoup.connect("https://simple.wikipedia.org/wiki/"+url).get();
		}
		catch(IOException e)
		{
			System.out.println("No available wiki page for \"" + url + "\".");
		}
	}
	
	public String get()
	{
		Elements e = doc.select("*");
		String ret = "";
		for(Element i : e)
		{
			if(i.className().contains("infobox"))
			{
				ret = "";
			}
			if(i.className().contains("toc"))
			{
				break;
			}
			if(i.tagName().equals("p"))
			{
				ret += i.text() + "\n";
			}
		}
		return ret;
	}
}
