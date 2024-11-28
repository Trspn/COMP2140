import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate; 
import java.time.LocalDateTime; 
import java.time.LocalTime;

public class Driver {
    private User user;
    private ArrayList<User> users;
    private ArrayList<Session> sessions = new ArrayList<>();
    private ArrayList<Machine> machines = new ArrayList<>();

    private static final String USERS_FILE = "users.dat";
    private static final String SESSIONS_FILE = "sessions.dat";
    private static final String MACHINES_FILE = "machines.dat";



    public Driver() {
        this.users = new ArrayList<>();  // Initialize the users ArrayList
        loadSessions();
    }

    public void loadSessions(){
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
                // Create a session for this machine and this time slot, then add to the sessions list
                sessions.add(new Session(machine, startMinutes, endMinutes));
            }
            
        }
    }

    public ArrayList<Session> getSessions(){
        return this.sessions;
    }

    public ArrayList<Machine> getMachines(){
        return this.machines;
    }

    public ArrayList<User> getUsers(){
        return this.users;
    }

    public void authenticateUser() {
        Scanner input = new Scanner(System.in);
        System.out.println("C - Create an Account | L - Login");
        String choice = input.next();

        if (choice.equalsIgnoreCase("C")) {
            // Create new account
            System.out.println("Enter your name:");
            String name = input.nextLine();
            System.out.println("Enter your email:");
            String email = input.nextLine();
            System.out.println("Enter your password:");
            String password = input.nextLine();
            
            // Generate a new userID (simple approach here)
            int userID = users.size() + 1;

            // Create a new customer and add it to the list of users
            Customer newCustomer = new Customer(userID, name, email, password);
            users.add(newCustomer);
            System.out.println("Account created successfully!");
            executeUserAction(newCustomer);

        } else if (choice.equalsIgnoreCase("L")) {
            // Login
            System.out.println("Enter your user ID:");
            int userID = input.nextInt();
            System.out.println("Enter your password:");
            String password = input.next();

            // Check if the user ID and password match any existing user
            boolean found = false;
            for (User existingUser : users) {
                if (existingUser.getUserID() == userID && existingUser.getUserPassword().equals(password)) {
                    found = true;
                    user = existingUser;  // Set the user to the logged-in user
                    break;
                }
            }

            if (found) {
                System.out.println("Login successful!");
                // After successful login, pass the user to their interface
                executeUserAction(user);
            } else {
                System.out.println("Invalid user ID or password.");
            }
        } else {
            System.out.println("Invalid choice. Please select 'C' to create an account or 'L' to login.");
        }
        input.close();
    }
    
    private void launchUserInterface(User user) {
        // Create and show the Swing-based interface
        new DriverInterface(this).createAndShowGUI();
    }

    public void executeUserAction(User user) {
        launchUserInterface(user);
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
                users = (ArrayList<User>) userObjInStream.readObject();
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
        driver.loadData();
        System.out.println(driver.getUsers());
        driver.getUsers().clear();;
        driver.saveData();
        driver.loadData();
        System.out.println(driver.getUsers());  
    }
} 