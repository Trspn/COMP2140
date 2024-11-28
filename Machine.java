import java.io.Serializable;

public class Machine implements Serializable{
    private static final long serialVersionUID = 1L;
    private int machineID;
    private String type;
    private boolean status;

    public Machine(int machineID, String type, boolean status){
        this.machineID = machineID;
        this.type = type;
        this.status = status;
    }

    public int getMachineID() {
        return machineID;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return status;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }

    public void markUnavailable() {
        this.status = false;
        System.out.println("Machine " + machineID + " is now unavailable.");
    }

    public void markAvailable() {
        this.status = true;
        System.out.println("Machine " + machineID + " is now available.");
    }

    public void reportIssue() {
        this.status = false; // Report machine as out of service
        System.out.println("Machine " + machineID + " has an issue.");
    }

    public String toString() {
        return type + " " + machineID;
    }
}