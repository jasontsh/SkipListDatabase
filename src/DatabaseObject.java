import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;


public abstract class DatabaseObject {
	
	public void save(){
		if(getSavePath() == null){
			firstTimeSave();
		}
		try {
			FileWriter writer = new FileWriter(getSavePath());
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

	public abstract int compareTo(DatabaseObject other);

}
