package demo;


import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/**
 *
 */
public class UserCredentialManager {

    private static final String KEY_USER_MODEL = UserCredentialManager.class.getName() + "_MODEL";
    private User user;

    private UserCredentialManager() {
    }

    public static UserCredentialManager getIntance() {
        return getIntance(Sessions.getCurrent());
    }

    public static UserCredentialManager getIntance(Session zkSession) {
        synchronized (zkSession) {
            //setting the current user model on entry
            UserCredentialManager userModel = (UserCredentialManager) zkSession.getAttribute(KEY_USER_MODEL);
            if (userModel == null) {
                zkSession.setAttribute(KEY_USER_MODEL, userModel = new UserCredentialManager());
            }
            return userModel;
        }
    }

    /**
     * Login Method,executed on click of Login button.
     * input is mashalled to xml and web service call is made
     * <br>Output of webservice call is unmarshalled and User model is set with permissions
     * and associated information, if required, derived from the xml output</br>
     * @param String username
     * @param String password
     */
    public synchronized void login(String username, String password) {
        try {
            //make the xml for the service
           /* ServiceRequest srq = new ServiceRequest(new ServiceRequest.RequestData(username, password));
            String xmlInput = XmlHandler.constructGenericServiceResultXml(ServiceRequest.class, srq);
            //call the web service that will handle the authentication and authorization
            String xmlOutput = verifyAndGetPermissions(xmlInput);
            //get the result from the call
            ServiceResult sres = (ServiceResult) XmlHandler.unMarshallObjectFromXML(ServiceResult.class, xmlOutput);
            if (sres.getServiceStatus().getStatusCode().equalsIgnoreCase("100")) {
                User tempUser = new User(username, password, sres.getRequestResult().getPermissions());
                tempUser.setRemoteAddy(Executions.getCurrent().getSession().getRemoteAddr());
                tempUser.setRemoteHost(Executions.getCurrent().getSession().getRemoteHost());
                user = tempUser;
                //setting userinfo as a global attrib
                Executions.getCurrent().getSession().setAttribute("userInfo", user);
            } else {
                //error occured
                user = null;
            }*/
        	
        	if (username.equals("admin") && password.equals("admin")) {
                User tmpUser = new User(username, password);
                user= tmpUser;
             } else {
                 //error occured
                 user = null;
             }

        } catch (Exception ex) {
            Logger.getLogger(UserCredentialManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Simple log off and session clean up for user access
     */
    public synchronized void logOff() {
        //for clearing the models and for the clearing the session variables
        Executions.getCurrent().getSession().removeAttribute("userInfo");
        Executions.getCurrent().getSession().invalidate();
        this.user = null;
    }

    public synchronized User getUser() {
        return user;
    }

    public synchronized boolean isAuthenticated() {
        return user != null;
    }
/**
 * This is the webservice call
 * created after the import of wsdl
 * @param xmlInput
 * @return
 */
   /* private static String verifyAndGetPermissions(java.lang.String xmlInput) {
        com.ei.webui.service.ZKSmallTalk service = new com.ei.webui.service.ZKSmallTalk();
        com.ei.webui.service.SmallTalkSample port = service.getSmallTalkSamplePort();
        return port.verifyAndGetPermissions(xmlInput);
    }*/
}