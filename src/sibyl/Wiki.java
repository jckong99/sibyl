package sibyl;

import java.io.IOException;
import org.jsoup.nodes.Document;
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
		return doc.select("p").first().text();
	}
}
