
public abstract class DatabaseObjectBuilder {

	public abstract DatabaseObjectBuilder setQuery(String[] query);
	
	public abstract DatabaseObject build();
	
	public abstract void reset();
}
