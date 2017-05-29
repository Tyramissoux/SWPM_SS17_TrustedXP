package frontend;
//https://www.zkoss.org/wiki/Small_Talks/2012/February/ZK_And_JAX_WS
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


@SuppressWarnings("rawtypes")
public class LoginController extends GenericForwardComposer {

    private static final long serialVersionUID = 5730426085235846339L;
    private Label mesgLbl;
    protected Window loginwin;
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