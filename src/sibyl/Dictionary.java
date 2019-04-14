package sibyl;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Dictionary extends Retriever
{	
	public Dictionary(String word)
	{
		this.word = word;
		type = 1;
		try
		{
			doc = Jsoup.connect("https://www.dictionary.com/browse/" + word.toLowerCase()).get();
			valid = true;
		}
		catch(IOException e)
		{
			System.out.println("No available dictionary entry for \"" + word + "\".");
			valid = false;
		}
	}
	
	public String get()
	{
		String ret = "";
		Elements sections, definitions;
		int value = 1;
		
		if(valid)
		{
			ret += "From dictionary.com:";
			sections = doc.select(".css-hw0b7s.e1hk9ate0");
			
			for(int sectionIndex = 0; sectionIndex < sections.size(); sectionIndex++)
			{	
				if(sections.get(sectionIndex).select("[value=" + value + "]").size() == 0)
					break;
				
				ret += "\n    Part of Speech: " + sections.get(sectionIndex).select(".luna-pos").text().split(",")[0];
				
				for(int i = 0; i < (definitions = sections.get(sectionIndex).select("[value]")).size(); i++)
				{
					ret += "\n        " + value + ". " + definitions.get(i).select("[value=" + value + "]").text();
					value++;
				}
			}
		}
		
		return ret;
	}
}
