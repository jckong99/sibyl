package sibyl;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.Arrays;

public class Main 
{
	public static void main(String[] args) 
	{
		boolean help = false, invalidFlag = false;
		
		if(args.length == 0)
		{
			System.out.println("Invalid; must have word as argument.");
		}
		else
		{
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
					System.out.println("        -f --f\t\tPerforms the sybil replacement on the file and places that in an output file.");
					System.out.println("        -? --?\t\tDisplays this help page.");
					
					help = true;
					
					break;
				}
			}
			
			if(!help)
			{
				String word = null;
				ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(args));
				LinkedHashSet<Retriever> retrievers = new LinkedHashSet<Retriever>();
				String inputfilename = null, outputfilename = null;
				
				for(int i = 0; i < argsList.size(); i++)
				{
					if(argsList.get(i).equals("-f") || argsList.get(i).equals("--f"))
					{
						if(i + 3 < args.length && args[i+1].charAt(0) != '-' && args[i+2].charAt(0) != '-')
						{
							inputfilename = argsList.remove(i + 1);
							outputfilename = argsList.remove(i + 2);
						}
						else
							System.out.println("Invalid; -f must have two filenames as arguments.");
						
						break;
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
				}
				else
				{
					for(int i = 0; i < argsList.size(); i++)
					{
						if(argsList.get(i).equals("-d") || argsList.get(i).equals("--d"))
						{
							retrievers.add(new Dictionary(word));
						}
						else if(argsList.get(i).equals("-th") || argsList.get(i).equals("--th"))
						{
							retrievers.add(new Thesaurus(word));
						}
						else if(argsList.get(i).equals("-w") || argsList.get(i).equals("--w"))
						{
							retrievers.add(new Wiki(word));
						}
						else if(argsList.get(i).equals("-sw") || argsList.get(i).equals("--sw"))
						{
							retrievers.add(new SimpleWiki(word));
						}
						else
						{
							System.out.println("Invalid; unrecognized flag: \"" + argsList.get(i) + "\"");
							invalidFlag = true;
						}
					}
					
					if(inputfilename == null || outputfilename == null)
					{
						if(!invalidFlag)
						{
							if(retrievers.size() == 0)
								System.out.println((new Dictionary(word)).get());
							else
								for(Retriever r : retrievers)
									System.out.println(r.get());
						}
					}
					else
					{
						File file = new File(inputfilename); 
						Scanner sc = null;
						String text = "";
						try {
							sc = new Scanner(file);
							while (sc.hasNextLine())
								text += sc.nextLine()+"\n";
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
			}
		}
	}
}
