import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

/**
 * 
 * Abstract class for DatabaseObjects
 * Subclasses: TerminalObject.java, User.java
 *
 */

public abstract class DatabaseObject {
	
	public void save(){
		try {
			if(getSavePath() == null){
				firstTimeSave();
			}
			FileWriter writer = new FileWriter(getSavePath());
			writer.write((new Gson()).toJson(this));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * This function is called when getSavePath returns null
	 * @throws IOException 
	 */
	public abstract void firstTimeSave() throws IOException;
	
	public abstract String getSavePath();
	
	public abstract void setSavePath(String path);

	public abstract int compareTo(DatabaseObject other, String fieldName);
	
	/*
	 * The list contains the first prev as the 0 layer. This is to get the previous 
	 * objects from different layer.
	 */
	public abstract List<String> getPrevList();
	
	public abstract List<String> getNextList();
	
	public abstract void setPrevList(List<String> list);
	
	public abstract void setNextList(List<String> list);
	
	public abstract String getClassName();
	
	public abstract void setClassName(String name);
	
	/**
	 * This function set the lists to be of the same size, with start and end terminal objects.
	 */
	public void setRandomLayers(){
		if(!(getNextList().isEmpty() && getPrevList().isEmpty())){
			throw new IllegalStateException();
		}
		Random random = new Random();
		String classname = getClassName();
		String start = classname + "_start.txt";
		String end = classname + "_end.txt";
		List<String> nextList = getNextList();
		List<String> prevList = getPrevList();
		prevList.add(start);
		nextList.add(end);
		while(random.nextBoolean()){
			prevList.add(start);
			nextList.add(end);
		}
	}
	
}
