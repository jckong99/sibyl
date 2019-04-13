package sibyl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Dictionary extends Retriever
{
	private Document doc;
	
	public static void main(String[] args)
	{
		Dictionary dict = new Dictionary("fox");
		System.out.println("Text:\n" + dict.get());
	}
	
	public Dictionary(String word)
	{
		try
		{
			doc = Jsoup.connect("https://www.dictionary.com/browse/" + word.toLowerCase()).get();
		}
		catch(IOException e)
		{
			System.out.println("No available dictionary entry for \"" + word + "\".");
		}
	}
	
	public String get()
	{
		String ret = "";
		Elements elements;
		
		elements = doc.select(".luna-pos");
		for(Element e : elements)
			ret += e.text() + "\n";
		
		//elements = doc.select(".one-click-content.css-98tqe9.elq3nklv4");
		elements = doc.select(".default-content");
		for(Element e : elements)
			ret += e.text() + "\n";
		
		return ret;
	}
}
