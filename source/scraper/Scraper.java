package scraper;
import java.io.IOException;

import org.jsoup.*
;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
	public static Document get_google_Data(String Query) throws IOException
	{
		// Make Google Request According to the Search Query.
		Document doc = Jsoup.connect("http://google.com/search?q="+Query)
				.timeout(50000).ignoreHttpErrors(true)
				.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
				.get();
		return doc;
	}
	public static void main(String[] args) throws IOException
	{
		get_google_Data("test");
	}
}
