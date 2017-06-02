package frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import frontend.helper.OldUploadItem;

public class StoreToDataBase {
	
	public List<OldUploadItem> database;
	
	public StoreToDataBase(OldUploadItem old){
		loadDatabase();
		cleanDatabase();
		saveDatabase();
		
	}
	public StoreToDataBase(){
		loadDatabase();
		
		
	}
    	
    private void loadDatabase(){

    	List<OldUploadItem> tmp= new ArrayList();
    	
    	try {
    		
            	FileInputStream fileIn = new FileInputStream("File/Database.ser");
            	ObjectInputStream in = new ObjectInputStream(fileIn);
            	tmp = (List) in.readObject();
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
    	database=tmp;
    	
    }
    	
    private void saveDatabase(){
    	try {
    		File file= new File(createServerPath("Database.ser"));
    		//System.getProperty("user.dir");
            if(file.createNewFile()){
            	FileOutputStream fileOut= new FileOutputStream(file);
            	ObjectOutputStream out = new ObjectOutputStream(fileOut);
            	out.writeObject(database);
            	out.close();
            	fileOut.close();
            	System.out.printf("Neue datenbank wurde erstellt");
            }
         }catch(IOException i) {
            i.printStackTrace();
         }
		
    }
    		
    
    	
    private void cleanDatabase(){
    	if(database.size()==5){
    	database.remove(0);
    	}
    	saveDatabase();
    		
    }
    
    private String createServerPath(String name) {
		String webAppPath = Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath("/");
		webAppPath += "Files" + File.separator;
		System.out.println(webAppPath);
		return webAppPath + name;

	}
	
	
}
