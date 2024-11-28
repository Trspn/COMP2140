public class Appointment {
    
    private Session session;
    private Customer customer;

    public Appointment(Customer customer, Session session) {
        this.customer = customer;
        this.session = session;

    }
    public Customer getCustomer(){
        return customer;
    }

    public void createAppointment() {
        // Implementation for creating appointment
        session.markTaken();
        System.out.println("Appointment created for session ID: " + session.getSessionID());
    }

    public void cancelAppointment() {
        // Implementation for canceling appointment
        session.cancelSession();
        System.out.println("Appointment canceled for session ID: " + session.getSessionID());
    }

    public void rescheduleAppointment(Session newSession) {
        // Implementation for rescheduling appointment
        this.session = newSession;
        System.out.println("Appointment rescheduled to session ID: " + newSession.getSessionID());
    }
}