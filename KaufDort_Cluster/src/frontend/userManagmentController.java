package frontend;
//https://www.zkoss.org/wiki/Small_Talks/2012/February/ZK_And_JAX_WS
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Listitem;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

@SuppressWarnings("rawtypes")
public class userManagmentController extends GenericForwardComposer {

    private static final long serialVersionUID = 5730426085235846339L;
    private Label mesgLbl;
    private List<User> items;
    protected Window userManager;
    protected Textbox usernameTxtbox;
    protected Textbox passwordTxtbox;
    protected Button resetButton;
    protected Button newUserButton;
    private Listitem _selected;

    /**
     * Standard doAfterCompose with added authentication check before proceed
     * @param Component comp
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if (UserCredentialManager.getIntance(session).isAuthenticated()) {
           // execution.sendRedirect("upload.zul");
        }
        usernameTxtbox.setFocus(true);
    }
/**
 * On click of the login button, doLogin method will execute
 * @param event
 */
    
    public List<User> getItems() {
    	System.out.println("test liste");
		if (items == null) {
		
			UserDatenbank tmp= new UserDatenbank();
				try {
        		
                FileInputStream fileIn = new FileInputStream("Daten.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                tmp = (UserDatenbank) in.readObject();
                in.close();
                fileIn.close();
             }catch(IOException i) {
                i.printStackTrace();
                
             }catch(ClassNotFoundException c) {
                System.out.println("Datenbank wurde nicht gefunden");
                c.printStackTrace();
             }
				items=tmp.getUsers();
		}
		
		return items;
	}
    
    public void setSelected(Listitem selected) {
		_selected = selected;
	}

	public Listitem getSelected() {
		return _selected;
	}
	@Command
    public void newUserButton(Event event) {
        addUser();
    }
    
    private String createServerPath(String name) {
		String webAppPath = Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath("/");
		webAppPath += "Files" + File.separator;
		System.out.println(webAppPath);
		return webAppPath + name;

	}

    /**
     * Method that will initiate the login action
     * Input values are taken from front end and passed to the manager for processing
     */
    private void addUser() {
    	UserDatenbank tmp= new UserDatenbank();
    	try {
    		
    		File file= new File(createServerPath("Daten.ser"));
    		//System.getProperty("user.dir");
            if(file.createNewFile()){
            	FileOutputStream fileOut= new FileOutputStream(file);
            	ObjectOutputStream out = new ObjectOutputStream(fileOut);
            	out.writeObject(tmp);
            	out.close();
            	fileOut.close();
            	System.out.printf("Datenbank wurde neu erstellt");
            }
         }catch(IOException i) {
            i.printStackTrace();
         }
    	
    	try {
    		
            FileInputStream fileIn = new FileInputStream(createServerPath("Daten.ser"));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            tmp = (UserDatenbank) in.readObject();
            in.close();
            fileIn.close();
         }catch(IOException i) {
            i.printStackTrace();
            return;
         }catch(ClassNotFoundException c) {
            System.out.println("Datenbank wurde nicht gefunden");
            c.printStackTrace();
            return;
         
    	
    }
    	
    	for(User a : tmp.users){
    		if (usernameTxtbox.getValue().trim().equals(a.getName())) {
    			mesgLbl.setValue("The UserName or Password provided is already existing.");
    			return;
    		}
    	}
    	PermissionModel user = new PermissionModel("1","User");
		ArrayList<PermissionModel> Premissions = new ArrayList<PermissionModel>();
		Premissions.add(user);
		
		User newUser = new User(usernameTxtbox.getValue().trim(),passwordTxtbox.getValue().trim(),Premissions);
		tmp.users.add(newUser);
		try {
    		File file= new File(createServerPath("Daten.ser"));
    		//System.getProperty("user.dir");
            	file.createNewFile();
            	FileOutputStream fileOut= new FileOutputStream(file);
            	ObjectOutputStream out = new ObjectOutputStream(fileOut);
            	out.writeObject(tmp);
            	out.close();
            	fileOut.close();
            	System.out.printf("User erfolgreich angelegt");
            
         }catch(IOException i) {
            i.printStackTrace();
         }
		mesgLbl.setValue("User successful created");
    }
/**
 * Just clears the screen 
 * @param event
 */
    @Command
    public void resetButton(Event event) {
        this.usernameTxtbox.setValue("");
        this.passwordTxtbox.setValue("");
        this.usernameTxtbox.focus(); // set the focus on UserName

    }
}