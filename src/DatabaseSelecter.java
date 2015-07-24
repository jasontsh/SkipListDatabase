/**
 * 
 * Abstract class for selecter
 * Subclasses: DOSelecter
 *
 */
public abstract class DatabaseSelecter {
	public abstract DatabaseSelecter setQuery(String[] query);
	
	public abstract void reset();
	
	public abstract DatabaseObject select();
}
