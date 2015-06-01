import java.util.HashMap;
import java.util.StringTokenizer;


public class DOBuilder extends DatabaseObjectBuilder {

	private String[] query;
	private DatabaseObject object;
	public DOBuilder(){
		
	}
	@Override
	public DatabaseObjectBuilder setQuery(String[] query) {
		this.query = query;
		return this;
	}

	@Override
	public DatabaseObject build() {
		if(object != null){
			return object;
		}
		if(query == null){
			throw new IllegalStateException();
		}
		String classname = query[2];
		String params = query[3];
		String paramValues = query[5];
		HashMap<String, String> map = new HashMap<>();
		StringBuilder paramNames = new StringBuilder();
		paramNames.append(params).deleteCharAt(0).deleteCharAt(paramNames.length()-1);
		params = paramNames.toString();
		StringBuilder paramVal = new StringBuilder();
		paramVal.append(paramValues).deleteCharAt(0).deleteCharAt(paramVal.length()-1);
		paramValues = paramVal.toString();
		StringTokenizer tokenizer = new StringTokenizer(params, ",");
		StringTokenizer tokenizer2 = new StringTokenizer(paramValues, ",");
		while(tokenizer.hasMoreTokens()){
			map.put(tokenizer.nextToken(), tokenizer2.nextToken());
		}
		switch(classname.toUpperCase()){
		case "USER":
		case "USERS": break;
		}
		return null;
		
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		object = null;
		query = null;
	}

}
