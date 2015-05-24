import java.util.HashMap;
import java.util.StringTokenizer;


public class DOBuilder extends DatabaseObjectBuilder {

	private String query;
	private DatabaseObject object;
	public DOBuilder(){
		
	}
	@Override
	public DatabaseObjectBuilder setQuery(String query) {
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
		StringTokenizer tokenizer = new StringTokenizer(query, "&");
		HashMap<String, String> map = new HashMap<String, String>();
		while(tokenizer.hasMoreTokens()){
			String token = tokenizer.nextToken();
			if(!token.contains("=")){
				throw new IllegalArgumentException();
			}
			map.put(token.substring(0, token.indexOf("=")), token.substring(token.indexOf("=")+1));
		}
		if(!map.containsKey("class")){
			throw new IllegalArgumentException();
		}
		switch(map.get("class")){
			//insert real class names here and finish this switch
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
