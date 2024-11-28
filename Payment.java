public class Payment {
    private int paymentID;
    private float amount;
    private String status;
    private Customer customer;
    private Session session;

    public Payment(int paymentID, float amount, String status, Customer customer, Session session) {
        this.paymentID = paymentID;
        this.amount = amount;
        this.status = status;
        this.customer = customer;
        this.session = session;
    }

    public void processPayment() {
        // Implementation for processing payment
    }

    public void refund() {
        // Implementation for refund
    }

    public void generateReceipt() {
        // Implementation for generating receipt
    }
}
