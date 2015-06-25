import com.google.gson.Gson;
<<<<<<< HEAD
=======

>>>>>>> origin/master



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
	//from user_2.txt up 5 --> user_3, user_4, user_5, user_6, user_7
	public static final String QUERY_INIT = "INIT";
	//example: init user
	
	
	/**
	 * main function to match query
	 * @param args
	 */
	public static void main(String[] args) {
		String keyword = args[0];
		switch (keyword.toUpperCase()){
		case QUERY_ADD: add(args); break;
		case QUERY_SEARCH: select(args); break;
		case QUERY_FROM: break;
		case QUERY_INIT: init(args); break;
		}
	}
	
	/**
	 * This method initializes the database with the class name given
	 * @param query
	 */
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

<<<<<<< HEAD
	/**
	 * This method prints the Json for the requested object. If there
	 * are no objects, it prints the empty string
	 * @param query
	 */
=======
>>>>>>> origin/master
	public static void select(String[] query){
		DatabaseSelecter selecter = new DOSelecter();
		DatabaseObject obj = selecter.setQuery(query).select();
		if(obj == null){
<<<<<<< HEAD
			System.out.println("");
=======
			System.out.println("{}");
>>>>>>> origin/master
		}else{
			System.out.println((new Gson()).toJson(obj));
		}
	}
}
