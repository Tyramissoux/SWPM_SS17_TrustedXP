package frontend;
//https://www.zkoss.org/wiki/Small_Talks/2012/February/ZK_And_JAX_WS
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import demo.User;
import demo.UserCredentialManager;
import demo.UserDatenbank;
import frontend.helper.OldUploadItem;

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
            execution.sendRedirect("upload.zul");
        }
        usernameTxtbox.setFocus(true);
    }
/**
 * On click of the login button, doLogin method will execute
 * @param event
 */
    
    public List<User> getItems() {
		if (items == null) {
			@SuppressWarnings("unused")
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
    
    public void onClick$loginButton(Event event) {
        doLogin();
    }

    /**
     * Method that will initiate the login action
     * Input values are taken from front end and passed to the manager for processing
     */
    private void doLogin() {
        UserCredentialManager mgmt = UserCredentialManager.getIntance(Sessions.getCurrent());
        //add checks to ensure that no empty data is passed to the backend for processing
        mgmt.login(usernameTxtbox.getValue().trim(), passwordTxtbox.getValue().trim());
        if (mgmt.isAuthenticated()) {
            execution.sendRedirect("upload.zul");
        } else {
            mesgLbl.setValue("The UserName or Password provided is invalid.");
        }
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