/**
 * Abstract class with subclasses StartObject.java and EndObject.java
 *
 */


public abstract class TerminalObjects extends DatabaseObject {

	public abstract void addLayer(DatabaseObject next);
	
	@Override
	public void firstTimeSave(){
		if(getSavePath() == null){
			throw new IllegalStateException();
		}
	}
}
