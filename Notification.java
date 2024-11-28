public class Notification {
    private int notificationID;
    private User recipient;
    private String message;
    private int sentTime;

    public Notification(int notificationID, User recipient, String message, int sentTime) {
        this.notificationID = notificationID;
        this.recipient = recipient;
        this.message = message;
        this.sentTime = sentTime;
    }

    public User getRecipient() {
        return recipient;
    }

    public void send() {
        recipient.receiveNotification(this);
        System.out.println("Sending notification to " + recipient.getUserName() + ": " + message);
    }

}