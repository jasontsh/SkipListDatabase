import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

import com.google.gson.Gson;



/**
 * 
 * @author markliammurphy
 *
 */
public class DOFromer extends DatabaseFromer {
	String classname;
	String key;
	Set<DatabaseObject> obj;
	String num;
	String dir;
	
	@Override
	public DatabaseFromer setQuery(String[] query) {
		
		classname = query[5];
		key = query[1];
		num = query[3];
		dir = query[2];
		
		return this;
	}
	
	@Override
	public void reset() {
		classname = null;
		key = null;
		obj = null;
		num = null;
		dir = null;
	}
	
	public Set<DatabaseObject> select() {
		if (obj != null) return obj;
		try {
			switch(classname.toUpperCase()) {
			case "USER": return selectUsers(Integer.parseInt(key),
					Integer.parseInt(num), dir, new HashSet<DatabaseObject>());
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Set<DatabaseObject> selectUsers(int id, 
			int range, String direction, Set<DatabaseObject> userSet) throws FileNotFoundException {
		
		userSet.add(selectUser(id));
		
		switch(direction.toUpperCase()) {
		case "UP":
			if (range != 0) {selectUsers(id+1, range-1, direction, userSet);}
			else {return userSet;}
			break;
		case "DOWN":
			if (range !=0) {selectUsers(id-1, range-1, direction, userSet);}
			else {return userSet;}
			break;
		}

		obj = userSet;
		return userSet;
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
		return nextUser;
	}
}
