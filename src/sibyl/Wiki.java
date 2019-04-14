package sibyl;

import java.io.IOException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

public class Wiki extends Retriever 
{
	public Wiki(String word) 
	{
		String url = word.toLowerCase();
		url = url.substring(0, 1).toUpperCase() + url.substring(1);
		url.replaceAll(" ", "_");
		this.word = word;
		type = 3;
		try 
		{
			doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + url).get();
			valid = true;
		}
		catch(IOException e)
		{
			System.out.println("No available wiki page for " + url);
			valid = false;
		}
	}
	
	public String get()
	{
		String ret = "";
		
		if(valid)
		{
			Elements e = doc.select("*");
			
			for(Element i : e)
			{
				if(i.text().contains("v t e") || i.className().contains("thumb"))
					ret = "";
				if(i.className().contains("toc") || i.tagName().equals("h2"))
					break;
				if(i.tagName().equals("p"))
					ret += "\t" + i.text() + "\n";
			}
		}
		
		return "From wikipedia.org:\n" + ret;
	}
}
