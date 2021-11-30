package main;
import scraper.Scraper;
//import sorting.Sort;
import features.FrequencyAnalysis;
import features.MergeSort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import features.FrequencyAnalysis;
import features.CountKeywords;
import features.DomainNameExtractor;

public class Main {
	public static String search(String query) throws IOException
	{
		// Fetch Data from Google
		Document res=Scraper.get_google_Data(query);
	    Elements links = res.select("a[href]");
	    String[] domain_list=new String[21];
	    String[] temp_list=new String[21];
	    ArrayList result_list = new ArrayList<TreeMap>();
	    TreeMap<String,ArrayList> final_data = new TreeMap<String,ArrayList>();
	    int i =0;
		for (Element link : links) {
			String temp = link.attr("href");
			if(temp.startsWith("/url?q=")){
				if(i==21)
				{
					break;
				}
				temp=temp.substring(7);
				// Use Regex to obtain Domain Name
				String domain=DomainNameExtractor.Extract(temp,true);
				domain_list[i]=domain;
				temp_list[i]=temp;
				i++;
			}
		}
		// Sort the Results in Alphabetical Order using Merge Sort
		MergeSort msort = new MergeSort();
		msort.mergeSort(temp_list);
		for(String temp : temp_list) {
	       if(temp != null && temp.length() > 0) {
	    	   int d = temp.indexOf("&");
	    	   temp = temp.substring(0, d);
	    	   TreeMap<String,String> data = new TreeMap<String,String>();
	    	   // Count Keyword Occurences
	    	   data=CountKeywords.count(temp,query);
	    	   result_list.add(data);
	       }
	    }
		System.out.println(domain_list);
		final_data.put("results",result_list);
		//Remove Null Values from Array
		List<String> list = new ArrayList<String>();
	    for(String s : domain_list) {
	       if(s != null && s.length() > 0) {
	          list.add(s);
	       }
	    }
	    domain_list = list.toArray(new String[list.size()]);
	    // Remove Duplicates from Array
	    int end = domain_list.length;
	    Set<String> set = new HashSet<String>();
	    for(int j = 0; j < end; j++){
	      set.add(domain_list[j]);
	    }
	    ArrayList analysis_list = new ArrayList<TreeMap>();
	    // Run Frequency Analysis on the Domains Returned
	    analysis_list.add(FrequencyAnalysis.Analyze(set,res.html()));
	    final_data.put("analysis",analysis_list);
		String json=new Gson().toJson(final_data);
		// Return Data to GUI in Json Format
	    return json;
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Enter Search Query");
	    String search_query = myObj.nextLine();  // Read user input
	    System.out.println("Fetching Data...");
	    Document res=Scraper.get_google_Data(search_query);
	    Elements links = res.select("a[href]");
	    String[] domain_list=new String[links.size()];
	    int i =0;
		for (Element link : links) {
			String temp = link.attr("href");
			if(temp.startsWith("/url?q=")){
				if(i==21)
				{
					break;
				}
				temp=temp.substring(7);
				// Use Regex to obtain Domain Name
				String domain=DomainNameExtractor.Extract(temp,true);
				domain_list[i]=domain;
				int occurences=FrequencyAnalysis.get_frequency(res.html(), domain);
				int d = temp.indexOf("&");
				temp = temp.substring(0, d);
				TreeMap<String,String> data = new TreeMap<String,String>();
				data=CountKeywords.count(temp,search_query);
				System.out.println(data.get("Link"));
				System.out.println(data.get("Blob"));
				System.out.println(data.get("occurences"));
			    System.out.println("-------------------------------------------------");
				i++;
			}
		}
//		Remove Null Values from Array
		List<String> list = new ArrayList<String>();
	    for(String s : domain_list) {
	       if(s != null && s.length() > 0) {
	          list.add(s);
	       }
	    }
	    domain_list = list.toArray(new String[list.size()]);
	    // Remove Duplicates from Array
	    int end = domain_list.length;
	    Set<String> set = new HashSet<String>();
	    for(int j = 0; j < end; j++){
	      set.add(domain_list[j]);
	    }
	    System.out.println("-------------------------------------------------");
	    System.out.println("Frequency Analysis of Domains in the Search Results");
	    System.out.println("-------------------------------------------------");
	    FrequencyAnalysis.Analyze(set,res.html());
	}

}
