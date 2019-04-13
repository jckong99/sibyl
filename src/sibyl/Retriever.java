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
	
	@Override
	public boolean equals(Object o)
	{
		return (((Retriever)(o)).word).equals(word);
	}
	
	@Override
	public int hashCode()
	{
		return word.hashCode();
	}
}
