import java.util.Set;

/**
 * Abstract class for fromer
 * Subclasses: DOFromer
 * 
 * @author markliammurphy
 *
 */


public abstract class DatabaseFromer {
	public abstract DatabaseFromer setQuery(String[] query);
	
	public abstract void reset();
	
	public abstract Set<DatabaseObject> select();
}
