import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DriverInterface {
    private Driver driver;

    public DriverInterface(Driver driver) {
        this.driver = driver;
    }

    public static void main(String[] args) {
        // Sample driver with initialized sessions and machines
        Driver driver = new Driver();
        driver.loadSessions();
        new DriverInterface(driver).createAndShowGUI();
    }

    // This method initializes the GUI
    public void createAndShowGUI() {
        // Set up the JFrame
        JFrame frame = new JFrame("Laundromat System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create the table to display sessions
        JTable sessionTable = createSessionTable();
        JScrollPane scrollPane = new JScrollPane(sessionTable);

        // Add the table to the frame
        frame.add(scrollPane, BorderLayout.CENTER);

        // Refresh button to update the table
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable(sessionTable));
        frame.add(refreshButton, BorderLayout.SOUTH);

        // Set the frame visible
        frame.setVisible(true);
    }

    // Method to create the JTable for displaying session information
    private JTable createSessionTable() {
        // Column names for the table
        String[] columnNames = {"Machine Type", "Start Time", "End Time", "Status"};
        
        // Data for the table (this will be populated from sessions)
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        // Populate the model with session data
        for (Session session : driver.getSessions()) {
            String machineType = session.getMachine().getType();
            String startTime = session.getStartTime();
            String endTime = session.getEndTime();
            String status = session.getStatus() ? "Available" : "Booked";
            
            model.addRow(new Object[]{machineType, startTime, endTime, status});
        }

        // Create the JTable
        JTable table = new JTable(model);
        return table;
    }

    // Method to refresh the table if any data changes
    private void refreshTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);  // Clear existing rows
        
        // Add new rows to the table based on updated session data
        for (Session session : driver.getSessions()) {
            String machineType = session.getMachine().getType();
            String startTime = session.getStartTime();
            String endTime = session.getEndTime();
            String status = session.getStatus() ? "Available" : "Booked";
            
            model.addRow(new Object[]{machineType, startTime, endTime, status});
        }
    }
}