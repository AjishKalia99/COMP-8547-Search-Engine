package features;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainNameExtractor {
	public static String Extract(String val,boolean exclude)
	{
		// Extract Domain Name from Link using Regex Matcher and basic String Matching
		Pattern pattern = Pattern.compile("^(?:https?:\\/\\/)?(?:[^@\\/\\n]+@)?(?:www\\.)?([^:\\/?\\n]+) ");
		Matcher matcher = pattern.matcher(val);
		if (matcher.find())
		{
		    val=matcher.group(1);
		}
		if(exclude)
		{
			int d = val.indexOf("&");
			val = val.substring(0, d);
		}
		int dot = val.indexOf("/");
		int slash = val.indexOf("/", dot+1);
		dot = val.indexOf("/",slash+1);
		val = val.substring(slash+1,dot);
		return val;
	}
}
