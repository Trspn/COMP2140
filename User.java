import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private int userID;
    private String name;
    private String email;
    private String password;
    private ArrayList<Notification> notifications = new ArrayList<>();

    public User(int userID, String name, String email, String password) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return name;
    }

    public String getUserEmail() {
        return email;
    }

    public String getUserPassword() {
        return password;
    }

    public void login() {
        // Implement user login
        System.out.println("User " + name + " logged in.");
    }

    public void logout() {
        // Implementation for  user logout
        System.out.println("User " + name + " logged out.");
    }

    public void receiveNotification(Notification notification) {
        notifications.add(notification);
    }
    
    public String toString() {
        return "User{" +
               "userID=" + userID +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
