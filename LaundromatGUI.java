import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LaundromatGUI {
    private static ArrayList<String> machines = new ArrayList<>();
    private static ArrayList<String> times = new ArrayList<>();
    private static ArrayList<String> dates = new ArrayList<>();
    private static Driver driver;

    public static void main(String[] args) {
        driver = new Driver();
        // Fill sample data for machines and time slots
        
        for (Machine machine: driver.getMachines()){
            machines.add(machine.toString());
        }
        
        // Generate time slots from 8:00 AM to 10:00 PM (each slot is 30 minutes)
        for (int i = 8; i <= 20; i++) {
            String h = String.format("%02d", i);
            times.add(h + ":00 - " + h  + ":59");
        }

        // Generate dates for the next 7 days
        generateDates();

        // Show the login screen
        showLoginScreen();
    }

    // Method to generate the next 30 dates
    public static void generateDates() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < 7; i++) {
            dates.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);  // Increment date by 1 day
        }
    }

    // Method to show the login screen and authenticate user
    public static void showLoginScreen() {
        JFrame frame = new JFrame("Laundromat Login");

        // Set up login screen
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        JLabel label = new JLabel("Are you a student or administrator?");
        JButton studentButton = new JButton("Student");
        JButton adminButton = new JButton("Administrator");
        JButton registerButton = new JButton("Create Account");

        panel.add(label);
        panel.add(studentButton);
        panel.add(adminButton);
        panel.add(registerButton);

        frame.add(panel);
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  // Center the window
        frame.setVisible(true);

        // Button actions
        studentButton.addActionListener(e -> showLoginForm("student"));
        adminButton.addActionListener(e -> showLoginForm("admin"));
        registerButton.addActionListener(e -> showRegistrationForm());
    }

    // Method to show login form based on user type
    public static void showLoginForm(String userType) {
        JFrame loginFrame = new JFrame("Login");
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        // Add username and password fields
        loginPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        loginPanel.add(usernameField);

        loginPanel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        // Add Login button
        JButton loginButton = new JButton("Login");
        loginPanel.add(loginButton);

        // Set up login frame
        loginFrame.add(loginPanel);
        loginFrame.setSize(300, 200);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);  // Center the window
        loginFrame.setVisible(true);

        // Handle login action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            boolean isValid=false;
            Customer authUser = null;
            Administrator access = null;

            if ("student".equals(userType)) {
                for (Customer user: driver.getUsers()){
                    if (user.getUserName().equals(username) && user.getUserPassword().equals(password)){
                        authUser = user;
                        isValid=true;
                        break;
                    }
                }
                if (isValid){
                    loginStudent(username, authUser);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
                }
            } else if ("admin".equals(userType)) {
                for (Administrator admin:driver.getAdmins()){
                    if (admin.getUserName().equals(username) && admin.getUserPassword().equals(password)){
                        access = admin;
                        isValid=true;
                        break;
                    }
                }
                if (isValid){
                    loginAdmin(username, password);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
                }
            }
            loginFrame.dispose();
        });
    }

    // Logic for student login
    public static void loginStudent(String username, Customer user) {
        // Simulate student login logic here
        JOptionPane.showMessageDialog(null, "Welcome, " + username + "! You are logged in as a student.");
        showStudentActionScreen(user);  // Show student action screen (the student will choose to reschedule/book/cancel appointment)
    }

    // Logic for administrator login
    public static void loginAdmin(String username, String password) {
        // Simulate administrator login logic here
        JOptionPane.showMessageDialog(null, "Welcome, " + username + "! You are logged in as an administrator.");
        showAdminScreen();  // Show administrator screen
    }

    // Method to display the student registration screen
    public static void showRegistrationForm() {
        JFrame registrationFrame = new JFrame("Create Account");

        // Create form components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        // Name field
        panel.add(new JLabel("Full Name:"));
        JTextField nameField = new JTextField();
        panel.add(nameField);

        // ID number field
        panel.add(new JLabel("ID Number:"));
        JTextField idField = new JTextField();
        panel.add(idField);

        // Email field
        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        panel.add(emailField);

        // Password field
        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);

        // Confirm Password field
        panel.add(new JLabel("Confirm Password:"));
        JPasswordField confirmPasswordField = new JPasswordField();
        panel.add(confirmPasswordField);

        // Submit button
        JButton submitButton = new JButton("Create Account");
        panel.add(submitButton);

        // Set up frame
        registrationFrame.add(panel);
        registrationFrame.setSize(400, 300);
        registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registrationFrame.setLocationRelativeTo(null);  // Center the window
        registrationFrame.setVisible(true);

        // Handle account creation
        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String id = idField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Validate password
            if (password.length() < 8) {
                JOptionPane.showMessageDialog(registrationFrame, "Password must be at least 8 characters long.");
                return;
            }

            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(registrationFrame, "Passwords do not match.");
                return;
            }
            Customer newStudent = new Customer(Integer.parseInt(id),name,email,password);
            driver.getUsers().add(newStudent);
            driver.saveData();
            // Here, you can save the new student data to a database or a file, for now, we simply simulate it.
            JOptionPane.showMessageDialog(registrationFrame, "Account successfully created for " + name + ".");
            registrationFrame.dispose();
        });
    }

    public static void showStudentActionScreen(Customer customer) {
        JFrame studentActionFrame = new JFrame("Student Actions");
        studentActionFrame.setLayout(new GridLayout(4, 1));
    
        // Show options to book, reschedule, or cancel an appointment
        String[] options = {"Book New Appointment", "Reschedule Appointment", "Cancel Appointment"};
        JComboBox<String> actionComboBox = new JComboBox<>(options);
        studentActionFrame.add(new JLabel("Select an action:"));
        studentActionFrame.add(actionComboBox);
    
        // Confirm button
        JButton confirmButton = new JButton("Confirm");
        studentActionFrame.add(confirmButton);
    
        // Set up frame
        studentActionFrame.setSize(300, 200);
        studentActionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        studentActionFrame.setLocationRelativeTo(null);  // Center the window
        studentActionFrame.setVisible(true);
    
        // Handle action selection
        confirmButton.addActionListener(e -> {
            String selectedAction = (String) actionComboBox.getSelectedItem();
    
            if ("Book New Appointment".equals(selectedAction)) {
                showStudentBookingScreen(customer);  // Proceed to booking screen
            } else if ("Reschedule Appointment".equals(selectedAction)) {
                showRescheduleAppointmentScreen(customer);
            } else if ("Cancel Appointment".equals(selectedAction)) {
                showCancelAppointmentScreen(customer);
            }
            studentActionFrame.dispose();  // Close the action screen
        });
    }

    // Method to display the student booking screen
    public static void showStudentBookingScreen(Customer User) {
        JFrame studentFrame = new JFrame("Student Booking Screen");
        studentFrame.setLayout(new GridLayout(7, 1));
    
        // Choose machine
        JComboBox<String> machineComboBox = new JComboBox<>(machines.toArray(new String[0]));
        studentFrame.add(new JLabel("Select a machine:"));
        studentFrame.add(machineComboBox);
    
        // Choose date
        JComboBox<String> dateComboBox = new JComboBox<>(dates.toArray(new String[0]));
        studentFrame.add(new JLabel("Select a date:"));
        studentFrame.add(dateComboBox);
    
        // Choose time slot
        JComboBox<String> timeComboBox = new JComboBox<>(times.toArray(new String[0]));
        studentFrame.add(new JLabel("Select a time slot:"));
        studentFrame.add(timeComboBox);
    
        // Payment method
        JComboBox<String> paymentComboBox = new JComboBox<>(new String[] {"Cash", "Debit Card"});
        studentFrame.add(new JLabel("Select payment method:"));
        studentFrame.add(paymentComboBox);
    
        // Book button
        JButton bookButton = new JButton("Book");
        studentFrame.add(bookButton);
    
        // View Machine Statuses button
        JButton viewMachineStatusButton = new JButton("View Machine Statuses");
        studentFrame.add(viewMachineStatusButton, BorderLayout.SOUTH);
    
        // Set up frame
        studentFrame.setSize(400, 300);
        studentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        studentFrame.setLocationRelativeTo(null);  // Center the window
        studentFrame.setVisible(true);
    
        // Handle booking
        bookButton.addActionListener(e -> {
            String selectedMachine = (String) machineComboBox.getSelectedItem();
            String selectedDate = (String) dateComboBox.getSelectedItem();
            String selectedTime = (String) timeComboBox.getSelectedItem();
            String selectedPaymentMethod = (String) paymentComboBox.getSelectedItem();
            String startTime = selectedTime.split("-")[0].trim();
            Session sesh = findSession(selectedMachine, selectedDate, startTime);
    
            if ((sesh != null) && sesh.getStatus() == true) {
                sesh.markTaken();
                User.getBookedSessions().add(sesh);
                driver.saveData();
    
                if ("Cash".equals(selectedPaymentMethod)) {
                    JOptionPane.showMessageDialog(studentFrame, "Slot booked successfully for " + selectedMachine + " on " + selectedDate + " at " + selectedTime + " using Cash." + startTime);
                } else {
                    // Debit card payment
                    processCardPayment(studentFrame, selectedMachine, selectedDate, selectedTime);
                }
            } else {
                JOptionPane.showMessageDialog(studentFrame, "Machine " + selectedMachine + " is already booked for this time slot.");
            }
        });
    
        // Action listener for the View Machine Statuses button
        viewMachineStatusButton.addActionListener(e -> {
            showMachineStatuses();
        });
    }
    

    public static Session findSession(String selectedMachine, String selectedDate, String startHour) {
        for (Session session : driver.getSessions()) {
            // Check if session matches selected machine, date, and start hour
            if (session.getMachine().toString().equals(selectedMachine) &&
            session.getDate().equals(selectedDate)&&
            session.getStartTime().equals(startHour)){
                System.out.println(session);
                return session;  // Found the session, return it
            }
        }
        return null;  // No matching session found, meaning the slot is available
    }

    // Method to process card payment
    public static void processCardPayment(JFrame studentFrame, String selectedMachine, String selectedDate, String selectedTime) {
        JFrame cardFrame = new JFrame("Enter Card Details");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Card number, CVV, and expiry fields
        panel.add(new JLabel("Card Number:"));
        JTextField cardNumberField = new JTextField();
        panel.add(cardNumberField);

        panel.add(new JLabel("CVV:"));
        JTextField cvvField = new JTextField();
        panel.add(cvvField);

        panel.add(new JLabel("Expiry Date:"));
        JTextField expiryField = new JTextField();
        panel.add(expiryField);

        // Submit button
        JButton submitPaymentButton = new JButton("Submit Payment");
        panel.add(submitPaymentButton);

        // Set up card frame
        cardFrame.add(panel);
        cardFrame.setSize(400, 300);
        cardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cardFrame.setLocationRelativeTo(studentFrame);  // Center relative to student frame
        cardFrame.setVisible(true);

        // Handle card payment
        submitPaymentButton.addActionListener(e -> {
            String cardNumber = cardNumberField.getText();
            String cvv = cvvField.getText();
            String expiry = expiryField.getText();

            // Simulate card payment
            JOptionPane.showMessageDialog(cardFrame, "Payment successful for " + selectedMachine + " at " + selectedTime + ".");
            JOptionPane.showMessageDialog(studentFrame, "Booking confirmed for " + selectedMachine + " at " + selectedTime + " on " + selectedDate + ".");
            cardFrame.dispose();
            studentFrame.dispose(); // Close student booking screen
        });
    }

    public static void showRescheduleAppointmentScreen(Customer customer) {
        // For simplicity, this example assumes we have a list of bookings (just strings for now)
        ArrayList<String> bookings = new ArrayList<>();
        for (Session session:customer.getBookedSessions()){
            bookings.add(session.toString());
        }
    
        // Show a list of existing bookings
        JFrame rescheduleFrame = new JFrame("Reschedule Appointment");
        JComboBox<String> bookingsComboBox = new JComboBox<>(bookings.toArray(new String[0]));
        rescheduleFrame.add(new JLabel("Select an appointment to reschedule:"));
        rescheduleFrame.add(bookingsComboBox);
    
        // Select new date and time
        JComboBox<String> newDateComboBox = new JComboBox<>(dates.toArray(new String[0]));
        rescheduleFrame.add(new JLabel("Select new date:"));
        rescheduleFrame.add(newDateComboBox);
    
        JComboBox<String> newTimeComboBox = new JComboBox<>(times.toArray(new String[0]));
        rescheduleFrame.add(new JLabel("Select new time slot:"));
        rescheduleFrame.add(newTimeComboBox);
    
        // Confirm button
        JButton rescheduleButton = new JButton("Reschedule Appointment");
        rescheduleFrame.add(rescheduleButton);
    
        rescheduleFrame.setLayout(new GridLayout(5, 1));
        rescheduleFrame.setSize(400, 300);
        rescheduleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rescheduleFrame.setLocationRelativeTo(null);
        rescheduleFrame.setVisible(true);
    
        // Handle rescheduling
        rescheduleButton.addActionListener(e -> {
            String selectedBooking = (String) bookingsComboBox.getSelectedItem();
            String newDate = (String) newDateComboBox.getSelectedItem();
            String newTime = (String) newTimeComboBox.getSelectedItem();
            String newBooking = selectedBooking.split(",")[1].trim();
            System.out.print(newBooking);
            String newStartHour = newTime.split("-")[0].trim();
            int ID = Integer.parseInt(selectedBooking.split(",")[0].trim().split("=")[1]);
            Session s = findSession(newBooking, newDate, newStartHour);
            s.markTaken();
            customer.getBookedSessions().add(s);
            driver.saveData();
            Iterator<Session> iterator = customer.getBookedSessions().iterator();
            while (iterator.hasNext()) {
                Session session = iterator.next();
                if (ID == session.getSessionID()) {
                    session.markFree();
                    iterator.remove(); // Safely remove session using iterator
                    driver.saveData();
                break; // Exit after removing the session
                }
        }

    
            // Simulate rescheduling logic (update booking)
            JOptionPane.showMessageDialog(rescheduleFrame, "Appointment for " + selectedBooking + " has been rescheduled to " + newDate + " at " + newTime + ".");
            rescheduleFrame.dispose();
        });
    }

    public static void showCancelAppointmentScreen(Customer customer) {
        // For simplicity, this example assumes we have a list of bookings (just strings for now)
        ArrayList<String> bookings = new ArrayList<>();
        for (Session session:customer.getBookedSessions()){
            bookings.add(session.toString());
        }
    
        // Show a list of existing bookings
        JFrame cancelFrame = new JFrame("Cancel Appointment");
        JComboBox<String> bookingsComboBox = new JComboBox<>(bookings.toArray(new String[0]));
        cancelFrame.add(new JLabel("Select an appointment to cancel:"));
        cancelFrame.add(bookingsComboBox);
    
        // Confirm cancel button
        JButton cancelButton = new JButton("Cancel Appointment");
        cancelFrame.add(cancelButton);
    
        cancelFrame.setLayout(new GridLayout(3, 1));
        cancelFrame.setSize(400, 200);
        cancelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cancelFrame.setLocationRelativeTo(null);
        cancelFrame.setVisible(true);
    
        // Handle canceling
        cancelButton.addActionListener(e -> {
            String selectedBooking = (String) bookingsComboBox.getSelectedItem();
            int ID = Integer.parseInt(selectedBooking.split(",")[0].trim().split("=")[1]);
            Iterator<Session> iterator = customer.getBookedSessions().iterator();
            while (iterator.hasNext()) {
                Session session = iterator.next();
                if (ID == session.getSessionID()) {
                    session.markFree();
                    iterator.remove(); // Safely remove session using iterator
                    driver.saveData();
                break; // Exit after removing the session
        }
    }
            // Simulate canceling logic (remove booking)
            JOptionPane.showMessageDialog(cancelFrame, "Your appointment for " + selectedBooking + " has been canceled.");
            cancelFrame.dispose();
        });
    }

    // Admin screen with manage machines and view bookings
    public static void showAdminScreen() {
        JFrame adminFrame = new JFrame("Admin Screen");
        adminFrame.setLayout(new GridLayout(4, 1));
    
        // Report machine button (instead of manage machines)
        JButton reportMachineButton = new JButton("Report Machine");
        adminFrame.add(reportMachineButton);
    
        // View bookings button
        JButton viewBookingsButton = new JButton("View Bookings");
        adminFrame.add(viewBookingsButton);

        JButton manageMachinesButton = new JButton("Manage Machines");
        adminFrame.add(manageMachinesButton);
    
        // Set up frame
        adminFrame.setSize(300, 250);
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setLocationRelativeTo(null);  // Center the window
        adminFrame.setVisible(true);
    
        // Add button actions for admin
        manageMachinesButton.addActionListener(e -> {
            showManageMachinesScreen();
        });
    
        reportMachineButton.addActionListener(e -> {
            // Add functionality to report machine issues
            showReportMachineScreen();
        });
    
        viewBookingsButton.addActionListener(e -> {
            showBookingsScreen();
        });
    }

    public static void showBookingsScreen() {
        // Get list of customers and their booked sessions
        ArrayList<Customer> allCustomers = driver.getUsers();  // Get all customers
        ArrayList<Session> allSessions = new ArrayList<>();
        
        // Collect all booked sessions from each customer
        for (Customer customer : allCustomers) {
            allSessions.addAll(customer.getBookedSessions());
        }
    
        // Create data for the JTable
        String[] columnNames = {"Machine", "Date", "Time Slot", "Customer Name", "Session ID"};
        Object[][] data = new Object[allSessions.size()][5];  // 5 columns for machine, date, time, customer name, session ID
        
        // Populate the data array with session details
        for (int i = 0; i < allSessions.size(); i++) {
            Session session = allSessions.get(i);
            
            // Directly get the customer's name
            String customerName = "Unknown";
            for (Customer customer : allCustomers) {
                if (customer.getBookedSessions().contains(session)) {
                    customerName = customer.getUserName();  // Get the customer’s username directly
                    break;
                }
            }
    
            data[i][0] = session.getMachine().toString();  // Machine name
            data[i][1] = session.getDate();  // Date
            data[i][2] = session.getStartTime() + " - " + session.getEndTime();  // Time slot
            data[i][3] = customerName;  // Customer Name
            data[i][4] = session.getSessionID();  // Session ID
        }
    
        // Create a JTable to display the data
        JTable bookingsTable = new JTable(data, columnNames);
        bookingsTable.setFillsViewportHeight(true);  // Make table fill the scroll pane
        
        // Add the table inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
    
        // Create a frame to display the bookings
        JFrame bookingsFrame = new JFrame("View Bookings");
        bookingsFrame.setLayout(new BorderLayout());
        bookingsFrame.add(scrollPane, BorderLayout.CENTER);
    
        // Add a close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> bookingsFrame.dispose());
        bookingsFrame.add(closeButton, BorderLayout.SOUTH);
    
        // Set up the frame
        bookingsFrame.setSize(600, 400);
        bookingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookingsFrame.setLocationRelativeTo(null);  // Center the window
        bookingsFrame.setVisible(true);
    }

    public static void showReportMachineScreen() {
        // Create a dropdown with machine names
        JComboBox<String> machineComboBox = new JComboBox<>(machines.toArray(new String[0]));
    
        // Create a text area for the admin to type the issue
        JTextArea issueTextArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(issueTextArea);
    
        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
    
        // Add components to the panel
        panel.add(new JLabel("Select Machine:"));
        panel.add(machineComboBox);
        panel.add(new JLabel("Describe the issue:"));
        panel.add(scrollPane);
    
        // Create a submit button to report the issue
        JButton reportButton = new JButton("Report Issue");
    
        // Create a frame for reporting
        JFrame reportFrame = new JFrame("Report Machine Issue");
        reportFrame.setLayout(new BorderLayout());
        reportFrame.add(panel, BorderLayout.CENTER);
        reportFrame.add(reportButton, BorderLayout.SOUTH);
    
        // Set up the frame
        reportFrame.setSize(400, 300);
        reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reportFrame.setLocationRelativeTo(null);  // Center the window
        reportFrame.setVisible(true);
    
        // Handle the report button action
        reportButton.addActionListener(e -> {
            String selectedMachine = (String) machineComboBox.getSelectedItem();
            String issue = issueTextArea.getText().trim();
    
            if (issue.isEmpty()) {
                JOptionPane.showMessageDialog(reportFrame, "Please describe the issue.");
                return;
            }
    
            // Save the reported issue to a file (append mode)
            saveReportedIssue(selectedMachine, issue);
    
            // Inform the admin that the issue was reported
            JOptionPane.showMessageDialog(reportFrame, "The issue has been reported for " + selectedMachine + ".");
            reportFrame.dispose();
        });
    }

    public static void saveReportedIssue(String machine, String issue) {
    // Assuming you want to save the report in a text file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("reported_issues.txt", true))) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        writer.write("Machine: " + machine + "\n");
        writer.write("Issue: " + issue + "\n");
        writer.write("Reported on: " + date + "\n");
        writer.write("--------------------------------------------------\n");
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving reported issue.");
    }
}

public static void showManageMachinesScreen() {
    JFrame manageFrame = new JFrame("Manage Machine Status");

    // Get list of machines
    ArrayList<Machine> allMachines = driver.getMachines();
    
    // Create drop-down for selecting a machine
    JComboBox<String> machineComboBox = new JComboBox<>();
    for (Machine machine : allMachines) {
        machineComboBox.addItem("Machine ID: " + machine.getMachineID());  // Use machine ID in the drop-down
    }

    // Create drop-down for selecting a status
    String[] statusOptions = {"Available", "In Use", "Out of Service"};
    JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);

    // Create panel for layout
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 1));
    panel.add(new JLabel("Select a Machine:"));
    panel.add(machineComboBox);
    panel.add(new JLabel("Select Status:"));
    panel.add(statusComboBox);

    // Add buttons
    JButton updateButton = new JButton("Update Status");
    panel.add(updateButton);

    // Set up frame
    manageFrame.add(panel);
    manageFrame.setSize(300, 200);
    manageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    manageFrame.setLocationRelativeTo(null);  // Center the window
    manageFrame.setVisible(true);

    // Handle the update status button click
    updateButton.addActionListener(e -> {
        int selectedMachineIndex = machineComboBox.getSelectedIndex();  // Get selected machine index
        Machine selectedMachine = allMachines.get(selectedMachineIndex);  // Get the machine object

        String selectedStatus = (String) statusComboBox.getSelectedItem();  // Get selected status
        boolean statusFlag = false;
        
        // Determine the status as a boolean (assuming 0 = available, 1 = in use, 2 = out of service)
        switch (selectedStatus) {
            case "Available":
                statusFlag = true;  // true means available
                break;
            case "In Use":
                statusFlag = false;  // false means in use
                break;
            case "Out of Service":
                statusFlag = false;  // false means out of service
                break;
        }

        // Update the status of the selected machine
        selectedMachine.setStatus(statusFlag);
        
        // Provide feedback to the administrator
        JOptionPane.showMessageDialog(manageFrame, "Machine ID " + selectedMachine.getMachineID() + " status updated to: " + selectedStatus);
    });
}

public static void showMachineStatuses() {
    // Get list of machines from the driver or system
    ArrayList<Machine> allMachines = driver.getMachines();

    // Create a frame to display machine statuses
    JFrame statusFrame = new JFrame("Machine Statuses");

    // Create data for the table
    String[] columnNames = {"Machine ID", "Status"};
    Object[][] data = new Object[allMachines.size()][2];  // 2 columns: machine ID and status

    // Populate the data array with machine IDs and statuses
    for (int i = 0; i < allMachines.size(); i++) {
        Machine machine = allMachines.get(i);
        data[i][0] = machine.getMachineID();  // Machine ID
        data[i][1] = machine.isAvailable() ? "Available" : "Not Available";  // Status
    }

    // Create a JTable to display machine statuses
    JTable machineStatusTable = new JTable(data, columnNames);
    machineStatusTable.setFillsViewportHeight(true);

    // Add the table inside a JScrollPane
    JScrollPane scrollPane = new JScrollPane(machineStatusTable);

    // Create a frame to display the table
    statusFrame.add(scrollPane, BorderLayout.CENTER);

    // Add a close button
    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(e -> statusFrame.dispose());
    statusFrame.add(closeButton, BorderLayout.SOUTH);

    // Set up the status frame
    statusFrame.setSize(400, 300);
    statusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    statusFrame.setLocationRelativeTo(null);  // Center the window
    statusFrame.setVisible(true);
}
    
}