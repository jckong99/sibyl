package sibyl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Thesaurus extends Retriever
{
	private Document doc;
	
	public Thesaurus(String word)
	{
		try
		{
			doc = Jsoup.connect("https://www.thesaurus.com/browse/" + word.toLowerCase()).get();
		}
		catch(IOException e)
		{
			System.out.println("No available thesaurus entry for \"" + word + "\".");
		}
	}
	
	public String get()
	{
		Elements e = doc.select(".css-1lc0dpe .et6tpn80");
		String ret = "";
		for(Element i : e)
		{
			ret += i.text();
		}
		return ret;
	}
}
