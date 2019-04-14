package sibyl;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Main 
{
	public static void main(String[] args) 
	{
		String word;
		if(args.length == 0 || (word = args[args.length - 1]).charAt(0) == '-')
		{
			System.out.println("Invalid; must have word as argument.");
		}
		else
		{
			LinkedHashSet<Retriever> retrievers = new LinkedHashSet<Retriever>();
			String inputfilename = null, outputfilename = null;
			for(int i = 0; i < args.length - 1; i++)
			{
				if(args[i].equals("-?") || args[i].equals("--?"))
				{
					System.out.println("sibyl: sibyl [-d] [-th] [-w] [-sw] [-f] <inputfilename> <outputfilename> word");
					System.out.println("\tSearches the web for and displays information on word queries.");
					System.out.println("\n\tBy default, the syntax \"sibyl word\" retrieves word's definition from dictionary.com.");
					System.out.println("\n\tOptions:");
					System.out.println("\t\t-d --d word\t\tRetrieves definition from dictionary.com.");
					System.out.println("\t\t-th --th word\t\tRetrieves synonyms from thesaurus.com.");
					System.out.println("\t\t-w --w word\t\tRetrieves synopsis from wikipedia.org.");
					System.out.println("\t\t-sw --sw word\t\tRetrieves synopsis from simple.wikipedia.org.");
					System.out.println("\t\t-? --?\t\tDisplays this help page.");
					System.out.println("\t\t-f --f <inputfilename> <outputfilename> \t\tPerforms the sybil replacement on the file and places that in an output file.");
				}
				else if(args[i].equals("-w") || args[i].equals("--w"))
				{
					if(i + 1 < args.length)
						retrievers.add(new Wiki(word));
					else
						System.out.println("Invalid; must have word as argument.");
				}
				else if(args[i].equals("-sw") || args[i].equals("--sw"))
				{
					if(i + 1 < args.length)
						retrievers.add(new SimpleWiki(word));
					else
						System.out.println("Invalid; must have word as argument.");
				}
				else if(args[i].equals("-th") || args[i].equals("--th"))
				{
					if(i + 1 < args.length)
						retrievers.add(new Thesaurus(word));
					else
						System.out.println("Invalid; must have word as argument.");
				}
				else if(args[i].equals("-d") || args[i].equals("--d") || args[i].charAt(0) != '-')
				{
					if(i + 1 < args.length)
						retrievers.add(new Dictionary(word));
					else
						System.out.println("Invalid; must have word as argument.");
				}
				else if(args[i].equals("-f") || args[i].equals("--f"))
				{
					if(i + 3 < args.length && args[i+1].charAt(0) != '-' && args[i+2].charAt(0) != '-')
					{
						inputfilename = args[i+1];
						outputfilename = args[i+2];
						i+=2;
					}
					else
						System.out.println("Invalid; must have filename as argument.");
				}
			}
			
			if(inputfilename == null || outputfilename == null)
			{
				Retriever[] output = new Retriever[retrievers.size()];
				int i = retrievers.size() - 1;
				for(Retriever r : retrievers)
				{
					output[i] = r;
					i--;
				}
				for(Retriever r : retrievers)
					System.out.println(r.get());
			}
			else
			{
				File file = new File(inputfilename); 
				Scanner sc = null;
				String text = "";
				try {
					sc = new Scanner(file);
					while (sc.hasNextLine()) 
					{
						      text += sc.nextLine()+"\n";
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				
				 try {
						BufferedWriter writer = new BufferedWriter(new FileWriter(outputfilename));
						writer.write(text);
					    writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				for(Retriever r : retrievers)
					new FileReader(outputfilename, outputfilename, r).process();
			}
		}
		
		/*if(line_split.size() <= 2)
		{
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
		else
		{
			if(line_split.contains("-f"))
			{
				String filename = line_split.get(line_split.indexOf("-f") + 1);
				String filetext = "";
				File file = new File(filename); 
				Scanner sc = null;
				try {
					sc = new Scanner(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				while (sc.hasNextLine()) 
				{
					      filetext += sc.nextLine()+"\n";
				}
			}
		}*/
	}
}
