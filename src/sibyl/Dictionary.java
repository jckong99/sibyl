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
		Elements partsOfSpeech, definitions;
		int index = 0;
		int value = 1;
		
		partsOfSpeech = doc.select(".luna-pos");
		/*for(Element e : partsOfSpeech)
			ret += e.text() + "\n";*/
		
		do
		{
			definitions = doc.select("[value=" + value + "]");
			
			for(; index < definitions.size(); index++)
			{
				
			}
			
			value++;
		}
		while(definitions.size() > 0);
		
		/*elements = doc.select(".one-click-content.css-98tqe9.elq3nklv4");
		elements = doc.select("[value=" + value + "]");
		for(Element e : elements)
			ret += e.text() + "\n";*/
		
		return ret;
	}
}
