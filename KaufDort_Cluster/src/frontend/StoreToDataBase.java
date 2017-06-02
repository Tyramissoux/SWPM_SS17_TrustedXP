package frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


import org.zkoss.zk.ui.Executions;

import frontend.helper.OldUploadItem;
import frontend.helper.OldUploadList;

public class StoreToDataBase implements Serializable{
	
	public OldUploadList daten= new OldUploadList();
	
	public StoreToDataBase(OldUploadItem old){
		
		
		loadDatabase();
		daten.database.add(old);
		saveDatabase();
		//cleanDatabase();
		
	}
	public StoreToDataBase(){
		loadDatabase();
		
		
	}
    	
    private void loadDatabase(){

    	
    	
    	try {
    		File file= new File(createServerPath("Database.ser"));
    		//System.getProperty("user.dir");
            if(file.createNewFile()){
            	FileOutputStream fileOut= new FileOutputStream(file);
            	ObjectOutputStream out = new ObjectOutputStream(fileOut);
            	out.writeObject(daten);
            	out.close();
            	fileOut.close();
            	System.out.printf("Neue datenbank wurde erstellt");
            }
         }catch(IOException i) {
            i.printStackTrace();
         }
    	try {
    		
            	FileInputStream fileIn = new FileInputStream(createServerPath("Database.ser"));
            	ObjectInputStream in = new ObjectInputStream(fileIn);
            	daten = (OldUploadList) in.readObject();
            	in.close();
            	fileIn.close();
         	}
    		catch(IOException i) {
    			i.printStackTrace();
    			return;
    		}	
    		catch(ClassNotFoundException c) {
    			System.out.println("Datenbank wurde nicht gefunden");
    			c.printStackTrace();
    			return;
    		}
    	System.out.println("Datenbank wurde geladen");
    	
    	
    }
    	
    public OldUploadList getDaten() {
		return daten;
	}
	public void setDaten(OldUploadList daten) {
		this.daten = daten;
	}
	private void saveDatabase(){
    	try {
    		File file= new File(createServerPath("Database.ser"));
    		//System.getProperty("user.dir");
    		file.createNewFile();
            	FileOutputStream fileOut= new FileOutputStream(file);
            	ObjectOutputStream out = new ObjectOutputStream(fileOut);
            	out.writeObject(daten);
            	out.close();
            	fileOut.close();
            	System.out.printf("datenbank wurde gespeichert");
            
         }catch(IOException i) {
            i.printStackTrace();
         }
		
    }
    		
    
    	
    private void cleanDatabase(){
    	ArrayList<OldUploadItem> database =daten.getDatabase();
    	if(database.size()>=5){
    	database.remove(0);
    	daten.setDatabase(database);
    	saveDatabase();
    	}
    	
    		
    }
    
    private String createServerPath(String name) {
		String webAppPath = Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath("/");
		webAppPath += "Files" + File.separator;
		System.out.println(webAppPath);
		return webAppPath + name;

	}
	
	
}
