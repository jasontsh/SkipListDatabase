
public class DOSelecter extends DatabaseSelecter {
	String classname;
	String key;
	DatabaseObject obj;

	@Override
	public DatabaseSelecter setQuery(String[] query) {
		return null;
	}

	@Override
	public void reset() {
		classname = null;
		obj = null;
	}

	@Override
	public DatabaseObject select() {
		if(obj != null){
			return obj;
		}
		switch(classname.toUpperCase()){
		case "USER": return selectUser(Integer.parseInt(key));
		}
		return null;
	}
	
	public DatabaseObject selectUser(int id){
		return null;
	}

}
