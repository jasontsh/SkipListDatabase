import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import com.google.gson.Gson;

/*
 * To initiate this class, the command is "INSERT INTO USER (username,password) VALUES (u,p)"
 * Save path: user_idnumber
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
				endlock = endStream.getChannel().lock();
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
			FileLock firstlock = null;
			FileInputStream firstStream = null;
			while(firstlock == null){
				try {
					firstStream = new FileInputStream("user_start.txt");
					firstlock = endStream.getChannel().lock();
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
				lastlock = endStream.getChannel().lock();
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
		savePath = "user_" + (lastid+1);
		ListIterator<String> lastIterator = lastUser.getNextList().listIterator();
		while(lastIterator.nextIndex() < prevList.size() && lastIterator.hasNext()){
			lastIterator.next();
			lastIterator.set(savePath);
			prevList.add(endlist.get(0));
		}
//		now we need to loop through all the prevs.
		ListIterator<String> endIterator = endlist.listIterator(prevList.size());
		
		for(int i = prevList.size(); i < nextList.size(); i++){
			//to be continued here
			if(endlist.size() == i){
				endlist.add(savePath);
			}
			if(endlist.get(i).equals(savePath)){
				continue;
			}
		}
		for(int i = 0; i < prevList.size(); i++){
			end.getPrevList().add(savePath);
		}
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
		return 0;
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
