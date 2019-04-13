package sibyl;

import org.jsoup.nodes.Document;

public abstract class Retriever 
{
	protected Document doc;
	protected boolean valid;
	protected String word;
	protected int type;
	
	public abstract String get();
	public String getWord()
	{
		return word;
	}
	
	@Override
	public boolean equals(Object o)
	{
		return (((Retriever)(o)).word).equals(word) && (((Retriever)(o)).type) == type;
	}
	
	@Override
	public int hashCode()
	{
		return word.hashCode()*10 + type;
	}
}
