package core;

public class User {
    private String username;
    private String password;
    private String fullname;
    private String gmail;
    private boolean adminPerm;

    public User(String username, String password) {

    }
    public User(String username, String password, String fullname, String gmail, boolean adminPerm) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.gmail = gmail;
        this.adminPerm = adminPerm;
    }

    public boolean isAdminPerm() {
        return adminPerm;
    }

    public void setAdminPerm(boolean adminPerm) {
        this.adminPerm = adminPerm;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
