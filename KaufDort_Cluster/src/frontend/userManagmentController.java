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
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


@SuppressWarnings("rawtypes")
public class userManagmentController extends GenericForwardComposer {

    private static final long serialVersionUID = 5730426085235846339L;
    private Label mesgLbl;
    private List<User> items;
    protected Window userManager;
    protected Textbox usernameTxtbox;
    protected Textbox passwordTxtbox;
    protected Button resetButton;
    protected Button loginButton;

    
    public userManagmentController() {
		UserCredentialManager mgmt = UserCredentialManager.getIntance(Sessions
				.getCurrent());
		if (!mgmt.isAuthenticated()) {
			Executions.sendRedirect("login.zul");
		}
	}
    
    /**
     * Standard doAfterCompose with added authentication check before proceed
     * @param Component comp
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        
    }
/**
 * On click of the login button, doLogin method will execute
 * @param event
 */
    
    public void onClick$btnHome(Event event) {

		Executions.sendRedirect("upload.zul");
	}
    
    public void onClick$btnLogOut(Event event) {

    	UserCredentialManager mgmt = UserCredentialManager.getIntance(Sessions
				.getCurrent());
		mgmt.logOff();
		Executions.sendRedirect("login.zul");
	}
    
    public void onClick$aendernButton(Event event) {
        doaendern();
    }

    /**
     * Method that will initiate the login action
     * Input values are taken from front end and passed to the manager for processing
     */
    private String createServerPath(String name) {
		String webAppPath = Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath("/");
		webAppPath += "Files" + File.separator;
		System.out.println(webAppPath);
		return webAppPath + name;

	}
    private void doaendern() {
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
    	
    	/*
    	 * Schnittstelle für Multiuser und User verwaltung vorhanden.
    	 */
    	
		
		User newUser = new User(tmp.users.get(0).getName(),passwordTxtbox.getValue().trim(),tmp.users.get(0).getPermissionList());
		tmp.users.clear();
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
		mesgLbl.setValue("Passwort geändert");
		Executions.sendRedirect("upload.zul");
    }
/**
 * Just clears the screen 
 * @param event
 */
    public void onClick$resetButton(Event event) {
        this.usernameTxtbox.setValue("");
        this.passwordTxtbox.setValue("");
        this.usernameTxtbox.focus(); // set the focus on UserName

    }
}