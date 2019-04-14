package sibyl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.Arrays;

public class Main 
{
	public static void main(String[] args) 
	{
		String word = null;
		ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(args));
		LinkedHashSet<Retriever> retrievers = new LinkedHashSet<Retriever>();
		String inputfilename = null, outputfilename = null;
		boolean invalidFlag = false;
		
		if(args.length == 0)
		{
			System.out.println("Invalid; must have word as argument.");
			System.exit(0);
		}

		for(int i = 0; i < args.length; i++)
		{
			if(args[i].equals("-?") || args[i].equals("--?"))
			{
				System.out.println("sibyl: sibyl [-d] [-th] [-w] [-sw] [-f inputfilename outputfilename] word");
				System.out.println("    Searches the web for and displays information on word queries.");
				System.out.println("\n    By default, the syntax \"sibyl word\" retrieves word's definition from dictionary.com.");
				System.out.println("\n    Options:");
				System.out.println("        -d --d\t\tRetrieves definition from dictionary.com.");
				System.out.println("        -th --th\tRetrieves synonyms from thesaurus.com.");
				System.out.println("        -w --w\t\tRetrieves synopsis from wikipedia.org.");
				System.out.println("        -sw --sw\tRetrieves synopsis from simple.wikipedia.org.");
				System.out.println("        -f --f\t\tPerforms the sibyl replacement on the file and places that in an output file.");
				System.out.println("        -s --s\t\tPerforms a complete sibyl replacement of all words not among the 1000 most common.");
				System.out.println("        -? --?\t\tDisplays this help page.");
				
				System.exit(0);
			}
		}
		
		for(int i = 0; i < argsList.size(); i++)
		{
			if(argsList.get(i).equals("-s") || argsList.get(i).equals("--s"))
			{
				if(i + 3 < argsList.size() && argsList.get(i+1).charAt(0) != '-' && argsList.get(i+2).charAt(0) != '-')
				{
					inputfilename = argsList.remove(i + 1);
					outputfilename = argsList.remove(i + 2);
				}
				else
				{
					System.out.println("Invalid; -s must have two filenames as arguments.");
					System.exit(1);
				}
				
				break;
			}
		}
		for(int i = 0; i < argsList.size(); i++)
		{
			if(argsList.get(i).equals("-f") || argsList.get(i).equals("--f"))
			{
				if(inputfilename == null && outputfilename == null)
				{
					if(i + 3 < argsList.size() && argsList.get(i+1).charAt(0) != '-' && argsList.get(i+2).charAt(0) != '-')
					{
						inputfilename = argsList.remove(i + 1);
						outputfilename = argsList.remove(i + 2);
					}
					else
					{
						System.out.println("Invalid; -f must have two filenames as arguments.");
						System.exit(1);
					}
					
					break;
				}
				else
				{
					System.out.println("Invalid; -f and -s cannot be used simultaneously.");
					System.exit(1);
				}
			}
		}
		for(int i = 0; i < argsList.size(); i++)
		{
			if(argsList.get(i).charAt(0) != '-')
			{
				word = argsList.remove(i);
				break;
			}
		}
		if(word == null)
		{
			System.out.println("Invalid; must have word as argument.");
			System.exit(1);
		}
		else
		{
			for(int i = 0; i < argsList.size(); i++)
			{
				if(argsList.get(i).equals("-d") || argsList.get(i).equals("--d"))
					retrievers.add(new Dictionary(word));
				else if(argsList.get(i).equals("-th") || argsList.get(i).equals("--th"))
					retrievers.add(new Thesaurus(word));
				else if(argsList.get(i).equals("-w") || argsList.get(i).equals("--w"))
					retrievers.add(new Wiki(word));
				else if(argsList.get(i).equals("-sw") || argsList.get(i).equals("--sw"))
					retrievers.add(new SimpleWiki(word));
				else
				{
					System.out.println("Invalid; unrecognized flag: \"" + argsList.get(i) + "\"");
					invalidFlag = true;
				}
			}
			if(invalidFlag)
				System.exit(1);
			
			if(inputfilename == null || outputfilename == null)
			{
				if(retrievers.size() == 0)
					System.out.println((new Dictionary(word)).get());
				else
					for(Retriever r : retrievers)
						System.out.println(r.get());
			}
			else
			{
				File file = new File(inputfilename); 
				Scanner sc = null;
				String text = "";
				try
				{
					sc = new Scanner(file);
					while (sc.hasNextLine())
						text += sc.nextLine()+"\n";
				}
				catch (FileNotFoundException e)
				{
					System.out.println("Invalid; " + inputfilename + " cannot be opened.");
					System.exit(1);
				} 
				
				try
				{
					BufferedWriter writer = new BufferedWriter(new FileWriter(outputfilename));
					writer.write(text);
				    writer.close();
				}
				catch (IOException e)
				{
					System.out.println("Invalid; " + outputfilename + " cannot be opened.");
					System.exit(1);
				}
				for(Retriever r : retrievers)
					new FileReader(outputfilename, outputfilename, r).process();
			}
		}
	}
}
