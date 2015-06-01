import java.util.LinkedList;
import java.util.List;


public class StartObject extends TerminalObjects {

	private String className;
	private String savePath;
	private List<String> list;
	private String next;
	
	public StartObject(String className){
		savePath = className + "_start";
		this.className = className;
		list = new LinkedList<String>();
	}
	
	
	@Override
	public List<String> getList(){
		return list;
	}
	
	@Override
	public void setList(List<String> list){
		this.list = list;
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
	public String getNext() {
		return next;
	}

	@Override
	public String getPrev() {
		return null;
	}


	@Override
	public void setNext(String next) {
		if(list.isEmpty()){
			list.add(next);
		}
		this.next = next;
	}


	@Override
	public void setPrev(String prev) {
	}
	
}
