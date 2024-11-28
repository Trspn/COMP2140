import java.util.ArrayList;
public class Customer extends User {
    private ArrayList<Session> bookedSessions;
    public Customer(int userID, String name, String email, String password) {
        super(userID, name, email, password);
        this.bookedSessions = new ArrayList<>();
    }

    public void bookSession(Session session) {
        if (session.getMachine().isAvailable()) {
            session.markTaken(); // Mark machine as in-use
            bookedSessions.add(session);
            System.out.println("Session booked for machine " + session.getMachine().getMachineID());
        } else {
            System.out.println("Machine is currently unavailable.");
        }
    }

    public void cancelSession(Session session) {
        session.markFree();; // Mark machine as available
        bookedSessions.remove(session);
        System.out.println("Session canceled for machine " + session.getMachine().getMachineID());
    }

    public void rescheduleSession(Session oldSession, Session newSession) {
        cancelSession(oldSession); // the old machine would be made available 
        bookSession(newSession); // Reserve the new machine
        System.out.println("Session rescheduled to machine " + newSession.getMachine().getMachineID());
    }
}
