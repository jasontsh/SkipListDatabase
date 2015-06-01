import java.util.LinkedList;
import java.util.List;


public class EndObject extends TerminalObjects {

	private String prev;
	private String className;
	private String savePath;
	private List<String> list;
	
	public EndObject(String className){
		savePath = className + "_end";
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
	public List<String> getList() {
		return list;
	}

	@Override
	public void setList(List<String> list) {
		this.list = list;
	}

	@Override
	public String getNext() {
		return null;
	}

	@Override
	public String getPrev() {
		return prev;
	}

	@Override
	public void setNext(String next) {
	}

	@Override
	public void setPrev(String prev) {
		if(list.isEmpty()){
			list.add(prev);
		}
		this.prev = prev;
	}

}
