import java.io.Serializable;
import java.time.LocalTime;
public class Session implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int nextSessionID = 1;
    private int sessionID;
    private Machine machine;
    private int startTime;
    private int endTime;
    private boolean status;

    public Session(Machine machine, int startTime, int endTime) {
        this.sessionID = nextSessionID++;
        this.machine = machine;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = true;
    }

    public String getStartTime() {
        return formatTime(startTime);
    }

    public String getEndTime() {
        return formatTime(endTime);
    }

    public int getSessionID() {
        return sessionID;
    }

    private String formatTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }

    public Machine getMachine() {
        return machine;
    }

    public boolean getStatus() {
        return status;
    }

    public void markTaken(){
        status = false;
        machine.markUnavailable();
    }

    public void markFree(){
        this.status = true;
        machine.markAvailable();
    }

    public void extendSession(int additionalTime) {
        // Implementation to extend session
        this.endTime += additionalTime;
        System.out.println("Session extended by " + additionalTime + " minutes.");
    }

    public void cancelSession() {
        // Implementation to cancel session
        this.status = true;
        System.out.println("Session ID " + sessionID + " has been canceled.");
    }

    public String toString() { 
        return "Session{" + "sessionID=" + sessionID + ", machine=" + machine + ", startTime='" + getStartTime() + '\'' + ", endTime='" + getEndTime() + '\'' + ", status=" + status + '}';
    }
}