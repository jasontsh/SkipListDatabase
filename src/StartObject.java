import java.util.LinkedList;
import java.util.List;

/**
 * DatabaseObject that points to the first user in each list layer
 * 
 */
public class StartObject extends TerminalObjects {

	private String className;
	private String savePath;
	private List<String> list;
	
	//Constructor
	public StartObject(String className){
		savePath = className + "_start.txt";
		this.className = className;
		list = new LinkedList<String>();
	}
	
	@Override
	public String getSavePath() {
		return savePath;
	}

	@Override
	public void setSavePath(String path) {
		// TODO Auto-generated method stub
		savePath = path;
	}

	@Override
	public int compareTo(DatabaseObject other, String fieldName) {
		if(other instanceof StartObject){
			return 0;
		}
		return -1;
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
		//should never call prev list from an end object
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> getNextList() {
		return list;
	}

	@Override
	public void setPrevList(List<String> list) {
		//should never get prev list from start object
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNextList(List<String> list) {
		this.list = list;
	}
}
