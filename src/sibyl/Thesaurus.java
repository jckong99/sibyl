package sibyl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Thesaurus extends Retriever
{
	public Thesaurus(String word)
	{
		this.word = word;
		type = 2;
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
		int counter;
		
		if(valid)
		{
			ret += "From thesaurus.com:\n";
			Element section = doc.selectFirst(".css-1lc0dpe.et6tpn80");
			Elements moreRelevant = section.select(".css-15n8j60");
			Elements lessRelevant = section.select(".css-z20i5j");
			
			ret += "\tMore relevant:";
			counter = 1;
			for(Element e : moreRelevant)
			{
				ret += (counter == 1 ? "\n\t\t" : "    ") + e.text();
				counter++;
				if(counter > 3)
					counter = 1;
			}
			
			ret += "\n\tLess relevant:";
			counter = 1;
			for(Element e : lessRelevant)
			{
				ret += (counter == 1 ? "\n\t\t" : "    ") + e.text();
				counter++;
				if(counter > 3)
					counter = 1;
			}
		}
		
		return ret;
	}
}
