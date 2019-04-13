package sibyl;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main 
{
	public static void main(String[] args) 
	{
		System.out.println("Welcome to Sybil! May I take your order?");
		String line = "";
		boolean go = true;
		List<String> line_split = new ArrayList<String>();
		Scanner in = new Scanner(System.in);
		while(go)
		{
			System.out.print("> ");
			line = in.nextLine();
			line_split = Arrays.asList(line.split(" "));
			if(line_split.get(0).equals("w")) 
			{
				Wiki word = new Wiki(line_split.get(1));
				System.out.println(word.get());
			}
			else if(line_split.get(0).equals("sw")) 
			{
				SimpleWiki word = new SimpleWiki(line_split.get(1));
				System.out.println(word.get());
			}
			else if(line_split.get(0).equals("th")) 
			{
				Thesaurus word = new Thesaurus(line_split.get(1));
				System.out.println(word.get());
			}
			else if(line_split.get(0).equals("d")) 
			{
				Dictionary word = new Dictionary(line_split.get(1));
				System.out.println(word.get());
			}
			else if(line_split.get(0).equals("help"))
			{
				System.out.println("Commands: w - wikipedia, sw - simple wikipedia, d - dictionary, th - thesaurus"); 
			}
			else if(line_split.get(0).equals("quit"))
			{
				go = false;
			}
			else
			{
				System.out.println("Invalid command.");
			}
		}
	}
}
