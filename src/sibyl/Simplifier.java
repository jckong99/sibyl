package sibyl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Simplifier 
{
	protected String input_text;
	protected ArrayList<String> common_words;
	//private String common_words_filename = "bin/google-10000-english-usa_sorted.txt";
	private String common_words_filename = "bin/google-5000-english-usa_sorted.txt";
	//private String common_words_filename = "bin/common_words_sorted.txt";
	
	public Simplifier(String input_text)
	{
		this.input_text = input_text;
		this.common_words = new ArrayList<String>();
		File file = new File(common_words_filename); 
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		while (sc.hasNextLine()) 
		{
			common_words.add(sc.nextLine());
		}
	}
	
	public String process()
	{
		boolean complete = false;
		StringBuffer out = new StringBuffer();
		String word, replacement;
		Pattern p = Pattern.compile("[^a-zA-Z0-9][a-zA-Z]+[^a-zA-Z0-9]");
		Matcher m = p.matcher(" " + input_text + " ");
		int previousIndex = 0;
		
		while(m.find(previousIndex) && !complete)
		{
			int startIndex = m.start();
			int endIndex = m.end() - 2;
			word = input_text.substring(startIndex, endIndex);
			
			if(!common_words.contains(word.toLowerCase()))
			{
				replacement = getReplacement(word);
				out.append(input_text.substring(previousIndex, startIndex) + replacement);
			}
			else
			{
				out.append(input_text.substring(previousIndex, startIndex) + word);
			}
			previousIndex = endIndex;
		}
		out.append(input_text.substring(previousIndex));
		
		return out.toString();
	}
	
	private String getReplacement(String word)
	{
		Thesaurus synonyms = new Thesaurus(word);
		for(String w : synonyms.getList())
		{
			if(common_words.contains(w))
			{
				return w;
			}
		}
		return word;
	}
}
