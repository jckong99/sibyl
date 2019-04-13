package sibyl;

import org.jsoup.nodes.Document;

public abstract class Retriever 
{
	protected Document doc;
	protected boolean valid;
	
	public abstract String get();
}
