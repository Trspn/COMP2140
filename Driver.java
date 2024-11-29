import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate; 
import java.time.LocalDateTime; 
import java.time.LocalTime;

public class Driver {
    private ArrayList<Customer> users;
    private ArrayList<Session> sessions = new ArrayList<>();
    private ArrayList<Machine> machines = new ArrayList<>();
    private ArrayList<Administrator> administrators = new ArrayList<>();

    private static final String USERS_FILE = "users.dat";
    private static final String SESSIONS_FILE = "sessions.dat";
    private static final String MACHINES_FILE = "machines.dat";

    Administrator a1 = new Administrator(620161521, "Troy Spooner","troyspooner25@gmail.com","ts123");
    Administrator a2 = new Administrator( 620161521, "Aalyia Cato","aalyiacatowork@gmail.com","ac123");
    



    public Driver() {
        this.users = new ArrayList<>();  // Initialize the users ArrayList
        loadSessions();
        loadData();
        
    }

    public void loadSessions(){
        administrators.add(a1);
        administrators.add(a2);
        for (int i = 0; i < 8; i++) {
            String type = (i < 4) ? "Washer" : "Dryer";  // First 4 are washers, next 4 are dryers
            int machineID = i + 1;  // Machine IDs ranging from 1 to 8
            boolean status = true;  // All machines have a status of true (active)
        
            // Create a new machine and add it to the ArrayList
            machines.add(new Machine(machineID, type, status));
        }
        
        for (int hour = 8; hour <= 20; hour++) {
            LocalTime startTime = LocalTime.of(hour, 0);
            LocalTime endTime = LocalTime.of(hour, 59);
            int startMinutes = startTime.getHour() * 60 + startTime.getMinute();
            int endMinutes = endTime.getHour() * 60 + endTime.getMinute();

            for (Machine machine : machines) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (int i = 0; i < 7; i++){
                    String date = (dateFormat.format(calendar.getTime()));
                    sessions.add(new Session(machine, startMinutes, endMinutes, date));
                    calendar.add(Calendar.DATE, 1);
                }
                
                // Create a session for this machine and this time slot, then add to the sessions list  
            }
              // Increment date by 1 day
        }  
    }

    public ArrayList<Session> getSessions(){
        return this.sessions;
    }

    public ArrayList<Machine> getMachines(){
        return this.machines;
    }

    public ArrayList<Customer> getUsers(){
        return this.users;
    }

    public ArrayList<Administrator> getAdmins(){
        return this.administrators;
    }
    
    public void saveData() {
        try {
            // Saving Users list to file
            FileOutputStream userFile = new FileOutputStream(USERS_FILE);
            ObjectOutputStream userOutStream = new ObjectOutputStream(userFile);
            userOutStream.writeObject(users);
            userOutStream.close();
            
            // Saving Sessions list to file
            FileOutputStream sessionFile = new FileOutputStream(SESSIONS_FILE);
            ObjectOutputStream sessionOutStream = new ObjectOutputStream(sessionFile);
            sessionOutStream.writeObject(sessions);
            sessionOutStream.close();
            
            // Saving Machines list to file
            FileOutputStream machineFile = new FileOutputStream(MACHINES_FILE);
            ObjectOutputStream machineOutStream = new ObjectOutputStream(machineFile);
            machineOutStream.writeObject(machines);
            machineOutStream.close();
            
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    
    public void loadData() {
        try {
            // Check if the files exist before loading
            File userFile = new File(USERS_FILE);
            if (userFile.exists()) {
                FileInputStream userInStream = new FileInputStream(USERS_FILE);
                ObjectInputStream userObjInStream = new ObjectInputStream(userInStream);
                users = (ArrayList<Customer>) userObjInStream.readObject();
                userObjInStream.close();
            }

            File sessionFile = new File(SESSIONS_FILE);
            if (sessionFile.exists()) {
                FileInputStream sessionInStream = new FileInputStream(SESSIONS_FILE);
                ObjectInputStream sessionObjInStream = new ObjectInputStream(sessionInStream);
                sessions = (ArrayList<Session>) sessionObjInStream.readObject();
                sessionObjInStream.close();
            }

            File machineFile = new File(MACHINES_FILE);
            if (machineFile.exists()) {
                FileInputStream machineInStream = new FileInputStream(MACHINES_FILE);
                ObjectInputStream machineObjInStream = new ObjectInputStream(machineInStream);
                machines = (ArrayList<Machine>) machineObjInStream.readObject();
                machineObjInStream.close();
            }

            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
    
    
    
    
    
    public static void main(String[] args) {
        Driver driver = new Driver();
        for (Session session : driver.getSessions()) {
            // Check if session matches selected machine, date, and start hour
            if (session.getMachine().toString().equals("Washer 3")){
                System.out.println(session);
                 // Found the session, return it
            }
        }
        for (Customer customer :driver.getUsers()){
            System.out.println(customer.getBookedSessions());
        }
        
    }
} 