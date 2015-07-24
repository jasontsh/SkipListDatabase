import java.util.LinkedList;
import java.util.List;

/**
 * DatabaseObject that points to the last user in each list layer
 *
 */
public class EndObject extends TerminalObjects {

	private String className;
	private String savePath;
	private List<String> list;
	
	public EndObject(String className){
		savePath = className + "_end.txt";
		this.className = className;
		list = new LinkedList<String>();
	}
	
	@Override
	public String getSavePath() {
		return savePath;
	}

	@Override
	public void setSavePath(String path) {
		savePath = path;
	}

	@Override
	public int compareTo(DatabaseObject other, String fieldName) {
		if(other instanceof EndObject){
			return 0;
		}
		return 1;
	}

	@Override
	public void addLayer(DatabaseObject next) {
		list.add(next.getSavePath());
	}

	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public void setClassName(String name) {
		className = name;
	}

	@Override
	public List<String> getPrevList() {
		return list;
	}

	@Override
	public List<String> getNextList() {
		//should never get next list from an end object
		throw new UnsupportedOperationException();		
	}

	@Override
	public void setPrevList(List<String> list) {
		this.list = list;
	}

	@Override
	public void setNextList(List<String> list) {
		//should never call next list from an end object
		throw new UnsupportedOperationException();
	}


}
