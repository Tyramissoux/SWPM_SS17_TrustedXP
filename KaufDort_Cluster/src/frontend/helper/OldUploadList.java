package frontend.helper;

import java.io.Serializable;
import java.util.ArrayList;

public class OldUploadList implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//public List<OldUploadItem> database = new ArrayList<OldUploadItem>();
	public ArrayList<OldUploadItem> database = new ArrayList<OldUploadItem>();
	
	public OldUploadList(){
		
	}
	public ArrayList<OldUploadItem> getDatabase() {
		return database;
	}

	public void setDatabase(ArrayList<OldUploadItem> database) {
		this.database = database;
	}
	
	public void setData(OldUploadItem data) {
		database.add(data);
	}
	
}
