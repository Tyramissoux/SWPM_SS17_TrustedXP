package frontend;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PermissionModel implements Serializable, Cloneable {

    private String permissionId;
    private String persmission;

    public PermissionModel(String permissionId, String persmission) {
        this.permissionId = permissionId;
        this.persmission = persmission;

    }

    public PermissionModel() {
    }

   

    public String getPersmission() {
        return persmission;
    }

    public void setPersmission(String persmission) {
        this.persmission = persmission;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "PermissionModel {"
                + "\npermissionId: " + permissionId
                + "\npersmission: " + persmission
                + "\n}";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

