import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;


public abstract class DatabaseObject {
	
	public void save(){
		try {
			if(getSavePath() == null){
				firstTimeSave();
			}
			FileWriter writer = new FileWriter(getSavePath());
			writer.write((new Gson()).toJson(this));
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
	
}
