package sibyl;

import org.jsoup.nodes.Document;

public abstract class Retriever 
{
	protected Document doc;
	protected boolean valid;
	protected String word;
	
	public abstract String get();
	public String getWord()
	{
		return word;
	}
}
