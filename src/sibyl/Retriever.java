package sibyl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public abstract class Retriever 
{
	private Document doc;
	
	public abstract String get();
}
