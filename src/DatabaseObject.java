import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;


public abstract class DatabaseObject {
	
	public void save(){
		if(getSavePath() == null){
			firstTimeSave();
		}
		try {
			FileWriter writer = new FileWriter(getSavePath()+".txt");
			writer.write((new Gson()).toJson(this));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This function finds the first time save path and set it
	 */
	public abstract void firstTimeSave();
	
	public abstract String getSavePath();
	
	public abstract void setSavePath(String path);

	public abstract int compareTo(DatabaseObject other, String fieldName);
	
	public abstract List<String> getList();

	public abstract void setList(List<String> list);
	
	public abstract String getClassName();
	
	public abstract void setClassName(String name);
	
	public abstract String getNext();
	
	public abstract String getPrev();
	
	public abstract void setNext(String next);
	
	public abstract void setPrev(String prev);
}
