package demo;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import frontend.UserCredentialManager;

/**
 * initialization class.
 */
public class WorkbenchInit implements Initiator {

    @SuppressWarnings("unchecked")
    public void doInit(Page page, Map arg) throws Exception {
        if (!UserCredentialManager.getIntance().isAuthenticated()) {
            Executions.getCurrent().sendRedirect("login.zul");
        }
    }

    public boolean doCatch(Throwable parsingError) throws Exception {
        return false;
    }

    public void doAfterCompose(Page page) throws Exception {
    }

    public void doFinally() throws Exception {
    }

}

