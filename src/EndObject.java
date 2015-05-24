
public class EndObject extends TerminalObjects {

	@Override
	public void firstTimeSave() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSavePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSavePath(String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(DatabaseObject other) {
		if(other instanceof EndObject){
			return 0;
		}
		return 1;
	}

}
