package demo;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.List;

public class User {

    private long id;
    private String name;
    private String password;
    private String role;
    private String localhost;
    private String remoteHost;
    private String remoteAddy;
//adding some cred info so we can get important bits set for secrets
    String accessKey;
    private List<Permissions> permissionList = new ArrayList<Permissions>();

    public User() {
    }



    public User(String name, String password, List<Permissions> permissionList) {
        this.name = name;
        this.password = password;
        this.permissionList = permissionList;
    }

    public long getId() {
        return id;
    }

    public String getLocalhost() {
        return localhost;
    }

    public void setLocalhost(String localhost) {
        this.localhost = localhost;
    }

    public String getRemoteAddy() {
        return remoteAddy;
    }

    public void setRemoteAddy(String remoteAddy) {
        this.remoteAddy = remoteAddy;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Permissions> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permissions> permissionList) {
        this.permissionList = permissionList;
    }


   
}
