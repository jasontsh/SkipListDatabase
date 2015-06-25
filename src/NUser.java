import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class NUser extends DatabaseObject {
	private String savePath;
	private String username;
	private String userSavePath;
	private List<String> prevList, nextList;
	
	public NUser(HashMap<String, String> map) {
		username = map.get("username");
		userSavePath = map.get("usersavepath");
		nextList = new LinkedList<String>();
		prevList = new LinkedList<String>();
	}
	
	@Override
	public void firstTimeSave() throws IOException {
		
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
		return 0;
	}

	@Override
	public List<String> getPrevList() {
		return prevList;
	}

	@Override
	public List<String> getNextList() {
		return nextList;
	}

	@Override
	public void setPrevList(List<String> list) {
		prevList = list;
	}

	@Override
	public void setNextList(List<String> list) {
		nextList = list;
	}

	@Override
	public String getClassName() {
		return "NUSER";
	}

	@Override
	public void setClassName(String name) {
	}

}
