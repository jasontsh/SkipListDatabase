

public class Main {

	//Constants:
	public static final String QUERY_ADD = "INSERT";
	public static final String QUERY_SEARCH = "SELECT";
	/**
	 * If query from, given the path of the object from and the direction and interval, 
	 * return that k entries (not including itself)
	 */
	public static final String QUERY_FROM = "FROM";
	
	public static final String QUERY_INIT = "INIT";
	
	
	
	public static void main(String[] args) {
		String keyword = args[0];
		switch (keyword.toUpperCase()){
		case QUERY_ADD: add(args); break;
		case QUERY_SEARCH: break;
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
	
	public static void add(String[] query){
		DatabaseObjectBuilder builder = new DOBuilder();
		builder.setQuery(query).build().save();
	}

}
