
public abstract class TerminalObjects extends DatabaseObject {
	public abstract void addLayer(DatabaseObject next);
	
	
	
	public void firstTimeSave(){
		if(getSavePath() == null){
			throw new IllegalStateException();
		}
	}
}
