package frontend;

import org.zkoss.*;
import org.zkoss.bind.annotation.Command;

/**
 * @author Gama Ogi Prayogo
 * @created January 2012, last modified May 2012
 */
public class LoginVM {

    private String username = "Enter your login name", password;

    @Command
    public void doLogin() {

        doLogin(username, password);
    }

    private void doLogin(String username, String password) {
	System.out.println("Doing login");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}