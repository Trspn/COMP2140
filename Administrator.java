public class Administrator extends User {

    public Administrator(int userID, String name, String email, String password) {
        super(userID, name, email, password);
    }

    public void updateMachineStatus(Machine machine, boolean status) {
        // Implementation for updating machine status
        machine.setStatus(status);
        System.out.println("Machine" + machine.getMachineID() + "status updated.");
    }

    public void sendManualNotification(Notification notification) {
        notification.send(); // Send notification to recipient
        System.out.println("Notification sent to: " + notification.getRecipient().getUserName());
    }

    public void reportMachineIssue(Machine machine) {
        machine.setStatus(false); // Mark machine as out of service
        System.out.println("Machine " + machine.getMachineID() + " reported as faulty.");
    }
}