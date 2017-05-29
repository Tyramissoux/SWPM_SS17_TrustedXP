package demo;

import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;


public class UserDatenbank implements Serializable{

	ArrayList<User> users = new ArrayList<User>();

	public UserDatenbank() {
		PermissionModel admin = new PermissionModel("0","Admin");
		ArrayList<PermissionModel> Premissions = new ArrayList<PermissionModel>();
		Premissions.add(admin);
		
		User standart = new User("admin","admin",Premissions);
		users.add(standart);
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	
	
	
	
}
