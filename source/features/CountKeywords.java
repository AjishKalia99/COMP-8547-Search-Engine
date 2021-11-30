package features;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import lib.textprocessing.*;

public class CountKeywords {
	static String link;
	Integer occurence;
	static String Blob;
	public static TreeMap<String, String> count(String temp,String pattern) throws IOException
	{
		TreeMap<String,String> data = new TreeMap<String,String>();
		// Scrape Data from the URL of the given result
		Document doc = Jsoup.connect(temp)
				.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
				.ignoreHttpErrors(true).timeout(10000).ignoreContentType(true).get();
		link=temp;
		// Convert HTML to Text
		String data_Res=doc.text();
		// Extract Domain Names from the Link
		String domain=DomainNameExtractor.Extract(temp,false);
		// Fetch first 200 characters from the Results
		if(data_Res.length()>200)
		{
			Blob=doc.text().substring(0,200)+"...";
		}
		else
		{
			Blob=doc.text().substring(0,doc.text().length());
		}
		// Find the number of Occurences in the Text using Boyer Moore Algorithm.
		String Pattern=pattern;
		BoyerMoore boyermoore1 = new BoyerMoore(Pattern);
		int count=0;
		while(true)
		{
			if(boyermoore1.search(data_Res)>=data_Res.length())
			{
				break;
			}
			else
			{
				int offset=boyermoore1.search(data_Res);
				if(offset+pattern.length()>=data_Res.length())
				{
					count++;
					break;
				}
				else
				{
					data_Res=data_Res.substring(offset+pattern.length());
				}
				count++;
			}
		}
		// Put the Data in a TreeMap and Return
		data.put("Blob",Blob);
		data.put("Link",link);
		data.put("Domain", domain);
		data.put("occurences",Integer.toString(count));
		return data;
	}
}
