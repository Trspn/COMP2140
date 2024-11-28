import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LaundromatGUI {
    private static ArrayList<String> machines = new ArrayList<>();
    private static ArrayList<String> times = new ArrayList<>();
    private static ArrayList<String> dates = new ArrayList<>();
    private static Driver driver;

    public static void main(String[] args) {
        Driver driver = new Driver();
        // Fill sample data for machines and time slots
        
        for (Machine machine: driver.getMachines()){
            machines.add(machine.toString());
        }
        
        // Generate time slots from 8:00 AM to 10:00 PM (each slot is 30 minutes)
        for (int i = 8; i <= 22; i++) {
            times.add(i + ":00 - " + i  + ":59");
        }

        // Generate dates for the next 30 days
        generateDates();

        // Show the login screen
        showLoginScreen();
    }

    // Method to generate the next 30 dates
    public static void generateDates() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < 30; i++) {
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

            if ("student".equals(userType)) {
                loginStudent(username, password);
            } else if ("admin".equals(userType)) {
                loginAdmin(username, password);
            }
            loginFrame.dispose();
        });
    }

    // Logic for student login
    public static void loginStudent(String username, String password) {
        // Simulate student login logic here
        JOptionPane.showMessageDialog(null, "Welcome, " + username + "! You are logged in as a student.");
        showStudentActionScreen();  // Show student action screen (the student will choose to reschedule/book/cancel appointment)
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

            // Here, you can save the new student data to a database or a file, for now, we simply simulate it.
            JOptionPane.showMessageDialog(registrationFrame, "Account successfully created for " + name + ".");
            registrationFrame.dispose();
        });
    }

    public static void showStudentActionScreen() {
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
                showStudentBookingScreen();  // Proceed to booking screen
            } else if ("Reschedule Appointment".equals(selectedAction)) {
                showRescheduleAppointmentScreen();
            } else if ("Cancel Appointment".equals(selectedAction)) {
                showCancelAppointmentScreen();
            }
            studentActionFrame.dispose();  // Close the action screen
        });
    }

    // Method to display the student booking screen
    public static void showStudentBookingScreen() {
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

            if ("Cash".equals(selectedPaymentMethod)) {
                JOptionPane.showMessageDialog(studentFrame, "Slot booked successfully for " + selectedMachine + " on " + selectedDate + " at " + selectedTime + " using Cash.");
            } else {
                // Debit card payment
                processCardPayment(studentFrame, selectedMachine, selectedDate, selectedTime);
            }
        });
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

    public static void showRescheduleAppointmentScreen() {
        // For simplicity, this example assumes we have a list of bookings (just strings for now)
        ArrayList<String> bookings = new ArrayList<>();
        bookings.add("Washer 1, 2024-12-10, 10:00 - 11:00");
        bookings.add("Dryer 2, 2024-12-12, 14:00 - 15:00");
    
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
    
            // Simulate rescheduling logic (update booking)
            JOptionPane.showMessageDialog(rescheduleFrame, "Appointment for " + selectedBooking + " has been rescheduled to " + newDate + " at " + newTime + ".");
            rescheduleFrame.dispose();
        });
    }

    public static void showCancelAppointmentScreen() {
        // For simplicity, this example assumes we have a list of bookings (just strings for now)
        ArrayList<String> bookings = new ArrayList<>();
        bookings.add("Washer 1, 2024-12-10, 10:00 - 11:00");
        bookings.add("Dryer 2, 2024-12-12, 14:00 - 15:00");
    
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
    
        // Set up frame
        adminFrame.setSize(300, 250);
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setLocationRelativeTo(null);  // Center the window
        adminFrame.setVisible(true);
    
        // Add button actions for admin
        reportMachineButton.addActionListener(e -> {
            // Add functionality to report machine issues
            JOptionPane.showMessageDialog(adminFrame, "Report machine feature coming soon...");
        });
    
        viewBookingsButton.addActionListener(e -> {
            // Add functionality to view all bookings
            JOptionPane.showMessageDialog(adminFrame, "View bookings feature coming soon...");
        });
    }
}