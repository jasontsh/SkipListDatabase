import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;

/*
 * To initiate this class, the command is "INSERT INTO USER (username,password) VALUES (u,p)"
 * Save path: user_idnumber.txt
 */
public class User extends DatabaseObject {

	private String savePath;
	private String username;
	private int id_number;
	private String password;
	private String news_path;
	private List<String> nextList, prevList;
	
	
	public User(HashMap<String, String> map) {
		username = map.get("username");
		password = map.get("password");
		nextList = new LinkedList<String>();
		prevList = new LinkedList<String>();
	}

	@Override
	public void firstTimeSave() throws IOException {
		//need to find path, next and prev and id, as with changing other files
		Gson gson = new Gson();
		//we also need to find how many layers this user has:
		Random random = new Random();
		if(!prevList.isEmpty() || !nextList.isEmpty()){
			//the list should be empty, or else it shouldn't be in first time save.
			throw new IllegalStateException();
		}
		//add null as a buffer
		prevList.add("user_start.txt");
		nextList.add("user_end.txt");
		while(random.nextBoolean()){
			prevList.add("user_start.txt");
			nextList.add("user_end.txt");
		}
		//lock end file:
		FileLock endlock = null;
		FileInputStream endStream = null;
		while(endlock == null){
			try {
				endStream = new FileInputStream("user_end.txt");
				endlock = endStream.getChannel().tryLock(0L, Long.MAX_VALUE, true);
			} catch(OverlappingFileLockException e){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		//now actually read:
		InputStreamReader endReader = new InputStreamReader(endStream);
		EndObject end = gson.fromJson(endReader, EndObject.class);
		//we need to find the savePath.
		List<String> endlist = end.getPrevList();
		if(endlist.isEmpty()){
			//this is the case where this is the first object.
			savePath = "user_1.txt";
			id_number = 1;
			FileLock firstlock = null;
			FileInputStream firstStream = null;
			while(firstlock == null){
				try {
					firstStream = new FileInputStream("user_start.txt");
					firstlock = firstStream.getChannel().tryLock(0L, Long.MAX_VALUE, true);
				} catch(OverlappingFileLockException e){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			InputStreamReader firstReader = new InputStreamReader(firstStream);
			StartObject first = gson.fromJson(firstReader, StartObject.class);
			for(int i = 0; i < prevList.size(); i++){
				first.getNextList().add(savePath);
				end.getPrevList().add(savePath);
			}
			firstlock.release();
			first.save();
			endlock.release();
			end.save();
			return;
		}
		
		FileLock lastlock = null;
		FileInputStream lastStream = null;
		while(lastlock == null){
			try {
				lastStream = new FileInputStream(endlist.get(0));
				lastlock = lastStream.getChannel().tryLock(0L, Long.MAX_VALUE, true);
			} catch(OverlappingFileLockException e){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		//read the last file:
		prevList = new LinkedList<String>();
		InputStreamReader lastReader = new InputStreamReader(lastStream);
		User lastUser = gson.fromJson(lastReader, User.class);
		int lastid = lastUser.getId();			
		savePath = "user_" + (lastid+1) + ".txt";
		id_number = lastid+1;
		ListIterator<String> lastIterator = lastUser.getNextList().listIterator();
		while(lastIterator.nextIndex() < nextList.size() && lastIterator.hasNext()){
			lastIterator.next();
			lastIterator.set(savePath);
			prevList.add(endlist.get(0));
		}
//		now we need to loop through all the prevs.
		ListIterator<String> endIterator = endlist.listIterator(prevList.size());
		Set<FileLock> fileLocks = new HashSet<FileLock>();
		Set<User> users = new HashSet<User>();
		while(endIterator.hasNext() && endIterator.nextIndex() < nextList.size()){
			//if the file is locked already and edited
			
			if(endIterator.hasPrevious()){
				String prev = endIterator.previous();
				endIterator.next();
				String next = endIterator.next();
				if(prev.equals(next)){
					continue;	
				}else{
					endIterator.previous();
				}
			}
			String nextp = endIterator.next();
			FileLock nextl = null;
			FileInputStream nexts = null;
			while(nextl == null){
				try {
					nexts = new FileInputStream(nextp);
					nextl = nexts.getChannel().tryLock(0L, Long.MAX_VALUE, true);
				} catch(OverlappingFileLockException e){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			InputStreamReader nextr = new InputStreamReader(nexts);
			User next = gson.fromJson(nextr, User.class);
			ListIterator<String> nextIt = next.getNextList().listIterator(prevList.size()-1);
			while(nextIt.hasNext() && nextIt.next().equals("user_end.txt")){
				nextIt.set(savePath);
				prevList.add(next.getSavePath());
			}
			users.add(next);
			fileLocks.add(nextl);
		}
		//we need to ALSO CHANGE THE USER_START!
		FileLock startl = null;
		FileInputStream starts = null;
		while(startl == null){
			try {
				starts = new FileInputStream("user_start.txt");
				startl = starts.getChannel().tryLock(0L, Long.MAX_VALUE, true);
			} catch(OverlappingFileLockException e){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		InputStreamReader startr = new InputStreamReader(starts);
		StartObject start = gson.fromJson(startr, StartObject.class);
		while(start.getNextList().size() < nextList.size()){
			start.addLayer(this);
		}
		//also need to make sure that prev and next has the same length
		while(prevList.size() < nextList.size()){
			prevList.add("user_start.txt");
		}
		//here we change all the paths of end to savePath
		endIterator = endlist.listIterator();
		while(endIterator.nextIndex() < nextList.size()){
			if(endIterator.hasNext()){
				endIterator.next();
				endIterator.set(savePath);
			}else{
				endIterator.add(savePath);
			}
		}
		//now release and save everything (but releases end at the last)
		for(FileLock fl: fileLocks){
			fl.release();
		}
		for(User u: users){
			u.save();
		}
		startl.release();
		start.save();
		lastlock.release();
		lastUser.save();
		endlock.release();
		end.save();
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
		if(other instanceof TerminalObjects){
			return 0 - other.compareTo(this, fieldName);
		}
		return id_number - ((User)other).getId();
	}


	@Override
	public String getClassName() {
		return "USER";
	}

	@Override
	public void setClassName(String name) {
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

	public int getId(){
		return id_number;
	}
}
