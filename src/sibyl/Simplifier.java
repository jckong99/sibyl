package sibyl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Simplifier 
{
	protected String input_text;
	protected ArrayList<String> common_words;
	private String common_words_filename;
	
	public Simplifier(String input_text)
	{
		this.input_text = input_text;
		this.common_words = new ArrayList<String>();
		File file = new File(common_words_filename); 
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		while (sc.hasNextLine()) 
		{
			common_words.add(sc.nextLine());
		}
	}
	
	public String process()
	{
		String[] split_list = input_text.split("\\s+");
		boolean complete = false;
		String out = "";
		while(!complete)
		{
			for(String word : split_list)
			{
				if(!common_words.contains(word.toLowerCase()))
				{
					out += replace(word) + " ";
				}
				else
					out += word + " ";
			}
		}
		return out;
	}
	
	private String replace(String word)
	{
		Thesaurus synonyms = new Thesaurus(word);
		for(String w : synonyms.getList())
		{
			if(common_words.contains(w))
			{
				return w;
			}
		}
		return word +"(unreplaced)";
	}
}
