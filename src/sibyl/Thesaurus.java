package sibyl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Thesaurus extends Retriever
{
	public Thesaurus(String word)
	{
		try
		{
			doc = Jsoup.connect("https://www.thesaurus.com/browse/" + word.toLowerCase()).get();
			valid = true;
		}
		catch(IOException e)
		{
			System.out.println("No available thesaurus entry for \"" + word + "\".");
			valid = false;
		}
	}
	
	public String get()
	{
		String ret = "";
		
		if(valid)
		{
			Element e = doc.select(".css-1lc0dpe.et6tpn80").first();
			ret = i.text();
		}
		
		return ret;
	}
}
