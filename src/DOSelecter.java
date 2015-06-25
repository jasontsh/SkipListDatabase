
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.ListIterator;

import com.google.gson.Gson;

public class DOSelecter extends DatabaseSelecter {
	String classname;
	String key;
	DatabaseObject obj;

	@Override
	public DatabaseSelecter setQuery(String[] query) {

		classname = query[3];
		key = query[1];
		return this;
	}

	@Override
	public void reset() {
		classname = null;
		obj = null;
	}

	@Override
	public DatabaseObject select() {
		if(obj != null){
			return obj;
		}
		try{
			switch(classname.toUpperCase()){
			case "USER": return selectUser(Integer.parseInt(key));
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public DatabaseObject selectUser(int id) throws FileNotFoundException{
		Gson gson = new Gson();
		StartObject start = gson.fromJson(new FileReader("user_start.txt"), StartObject.class);
		List<String> nexts = start.getNextList();
		ListIterator<String> nextsIt = nexts.listIterator(nexts.size());
		int buffid = Integer.MAX_VALUE;
		User nextUser = null;
		while (nextsIt.hasPrevious() && id <= buffid){
			String next = nextsIt.previous();
			if(next.equals("user_end.txt")){
				buffid = Integer.MAX_VALUE;
				continue;
			}
			nextUser = gson.fromJson(new FileReader(next), User.class);
			buffid = nextUser.getId();
		}
		if(nextUser == null){
			return null;
		}
		while(nextUser.getId() < id){
			nextsIt = nextUser.getNextList().listIterator(nextUser.getNextList().size());
			buffid = Integer.MAX_VALUE;
			while (nextsIt.hasPrevious() && id <= buffid){
				String next = nextsIt.previous();
				if(next.equals("user_end.txt")){
					buffid = Integer.MAX_VALUE;
					continue;
				}
				nextUser = gson.fromJson(new FileReader(next), User.class);
				buffid = nextUser.getId();
			}		
		}
		if(nextUser.getId() > id){
			return null;
		}
		obj = nextUser;
		return nextUser;
	}

}
