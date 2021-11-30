package features;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import lib.hashTables.*;

public class FrequencyAnalysis {
	public static int get_frequency(String string,String find)
	{
		return string.replace(find, find + "x").length() - string.length();
	}
	public static TreeMap<String, String[]> Analyze(Set<String> set,String html)
	{
		// Create a new HashTable
		TreeMap<Integer,String[]> HashTable = new TreeMap<Integer,String[]>();
		// Get the number of Times the URL has occured
		for(String value:set)
		{
			int freq=get_frequency(html,value);
			if(HashTable.get(freq)==null)
			{
				String[] arr= {value};
				HashTable.put(freq,arr);
			}
			else
			{
				String[] arr=HashTable.get(freq);
				List<String> arrlist
	            = new ArrayList<String>(
	                Arrays.asList(arr));
				arrlist.add(value);
				arr = arrlist.toArray(arr);
				HashTable.put(freq, arr);
			}
		}
		// Store the occurences in a HashTable
		TreeMap<String,String[]> final_data = new TreeMap<String,String[]>();
		Integer most_key=HashTable.lastKey();
		String[] most_value=HashTable.get(most_key);
		String[] most_keys= {most_key+""};
		//Returned the Most Occuring Domains
		final_data.put("most_domain", most_value);
		final_data.put("most_times", most_keys);
		Integer least_key=HashTable.firstKey();
		String[] least_value=HashTable.get(least_key);
		String[] least_keys= {least_key+""};
		//Returned the Least Occuring Domains
		final_data.put("least_domain", least_value);
		final_data.put("least_times", least_keys);
		return final_data;
	}
}
