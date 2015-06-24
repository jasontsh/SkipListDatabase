import com.google.gson.Gson;

import com.google.gson.Gson;

import com.google.gson.Gson;

import com.google.gson.Gson;

import com.google.gson.Gson;

import com.google.gson.Gson;

import com.google.gson.Gson;



public class Main {

	//Constants:
	public static final String QUERY_ADD = "INSERT";
	//Insert example: INSERT into User (c1,c2,c3) VALUES (v1,v2,v3); -> c1-v1, c2-v2, c3-v3
	public static final String QUERY_SEARCH = "SELECT";
	/**
	 * If query from, given the path of the object from and the direction and interval, 
	 * return that k entries (not including itself)
	 */
	public static final String QUERY_FROM = "FROM";
	
	public static final String QUERY_INIT = "INIT";
	//example: init user
	
	
	
	public static void main(String[] args) {
		String keyword = args[0];
		switch (keyword.toUpperCase()){
		case QUERY_ADD: add(args); break;
		case QUERY_SEARCH: select(args); break;
		case QUERY_FROM: break;
		case QUERY_INIT: init(args); break;
		}
	}
	
	public static void init(String[] query){
		String classname = query[1];
		StartObject start = new StartObject(classname);
		EndObject end = new EndObject(classname);
		start.save();
		end.save();
	}
	
	/**
	 * This method adds the object by parsing it with query
	 * @param query
	 */
	public static void add(String[] query){
		DatabaseObjectBuilder builder = new DOBuilder();
		builder.setQuery(query).build().save();
	}

	public static void select(String[] query){
		DatabaseSelecter selecter = new DOSelecter();
		DatabaseObject obj = selecter.setQuery(query).select();
		System.out.println((new Gson()).toJson(obj));
	}
}
