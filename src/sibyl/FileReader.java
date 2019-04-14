package sibyl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileReader 
{
	private String in_filename, out_filename;
	private Retriever r;
	public FileReader(String in_filename, String out_filename, Retriever r)
	{
		this.in_filename = in_filename;
		this.out_filename = out_filename;
		this.r = r;
	}
	public void process()
	{
		String filetext = "";
		File file = new File(in_filename); 
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
		
		int wordloc = filetext.indexOf(r.getWord());
		filetext = filetext.substring(0, wordloc + r.getWord().length()) + " ("+r.get()+") "+filetext.substring(wordloc + r.getWord().length());
		
	    try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(out_filename));
			writer.write(filetext);
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	}
}
