package controller;

import model.Customer;
import model.Parcel;
import model.ParcelMap;
import model.QueueofCustomers;
import utils.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class Manager {
    private QueueofCustomers customerQueue;
    private ParcelMap parcelMap;
    private Worker worker;
    private Log log;


    // GUI Component
    private JFrame frame;
    private JTextArea parcelsTextArea;
    private JTextArea customerQueueTextArea;
    private JTextArea currentParcelTextArea;

    public Manager() {
        customerQueue = new QueueofCustomers();
        parcelMap = new ParcelMap();
        worker = new Worker();
        log = Log.getInstance(); // Singleton instance

        buildGUI();
        loadParcelData("Parcels.csv"); // Load data
        loadCustomerData("Custs.csv");
    }

    // GUI Building!
    private void buildGUI() {
        frame = new JFrame("Parcel Processing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(1, 3));

        // Parcels Panel
        JPanel parcelsPanel = new JPanel(new BorderLayout());
        parcelsTextArea = new JTextArea();
        parcelsPanel.add(new JLabel("Parcels:"), BorderLayout.NORTH);
        parcelsPanel.add(new JScrollPane(parcelsTextArea), BorderLayout.CENTER);

        // Customer Queue Panel
        JPanel customerQueuePanel = new JPanel(new BorderLayout());
        customerQueueTextArea = new JTextArea();
        customerQueuePanel.add(new JLabel("Customer Queue:"), BorderLayout.NORTH);
        customerQueuePanel.add(new JScrollPane(customerQueueTextArea), BorderLayout.CENTER);

        // Current Parcel Panel
        JPanel currentParcelPanel = new JPanel(new BorderLayout());
        currentParcelTextArea = new JTextArea();
        JButton processButton = new JButton("Process Next Customer");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processNextCustomer();
            }
        });
        currentParcelPanel.add(new JLabel("Current Parcel:"), BorderLayout.NORTH);
        currentParcelPanel.add(new JScrollPane(currentParcelTextArea), BorderLayout.CENTER);
        currentParcelPanel.add(processButton, BorderLayout.SOUTH);

        // Add panels to the frame
        frame.add(parcelsPanel);
        frame.add(customerQueuePanel);
        frame.add(currentParcelPanel);

        frame.setVisible(true);
    }

    // Data Loading Methods
    private void loadParcelData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Parse parcel data from the line (assuming CSV format)
                String[] parts = line.split(",");
                String parcelId = parts[0];
                int daysInDepot = Integer.parseInt(parts[1]);
                double weight = Double.parseDouble(parts[2]);
                String dimensions = parts[3];
                Parcel parcel = new Parcel(parcelId, daysInDepot, weight, dimensions);
                parcelMap.addParcel(parcel);
            }
            log.logEvent("Parcel data loaded from file: " + filename);
            updateParcelDisplay();
        } catch (IOException e) {
            log.logError("Error loading parcel data: " + e.getMessage());
            // Handle the error (e.g., display error message)
        }
    }

    private void loadCustomerData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int sequenceNumber = 1;
            while ((line = br.readLine()) != null) {
                // Parse customer data from the line
                String[] parts = line.split(",");
                String name = parts[0];
                String parcelId = parts[1];

                String sequenceStr = String.valueOf(sequenceNumber++);
                Customer customer = new Customer(sequenceStr,name,parcelId);
                customerQueue.enqueueCustomer(customer);
            }
            log.logEvent("Customer data loaded from file: " + filename);
            updateCustomerQueueDisplay();
        } catch (IOException e) {
            log.logError("Error loading customer data: " + e.getMessage());
            // Handle the error (e.g., display error message)
        }
    }

    // GUI Update Methods
    private void updateParcelDisplay() {
        StringBuilder sb = new StringBuilder();
        Set<String> trackingNumbers = parcelMap.getAllParcelIDs();
        for (String trackingNumber : trackingNumbers) {
            Parcel parcel = parcelMap.getParcel(trackingNumber);
            sb.append(parcel).append("\n"); // Assuming 'Parcel' class has a 'toString()' method
        }
        parcelsTextArea.setText(sb.toString());
    }

    private void updateCustomerQueueDisplay() {
        StringBuilder sb = new StringBuilder();

        for (Customer customer : customerQueue.getAllCustomers()){
            sb.append("Customer Name: ").append(customer.getName())
                    .append(", Parcel ID: ").append(customer.getParcelId()).append("\n");
        }

        customerQueueTextArea.setText(sb.toString());

    }

    private void updateCurrentParcelDisplay(Parcel parcel) {
        if (parcel != null){
            String parcelDetails = "Parcel ID: " + parcel.getParcelId() + "\n" +
                    "Weight: " + parcel.getWeight() + "\n" +
                    "Dimensions: " + parcel.getDimensions() + "\n" +
                    "Days in Depot: " + parcel.getDaysInDepot() + "\n" +
                    "Fee: " + worker.calculateFee(parcel);
            currentParcelTextArea.setText(parcelDetails);
        } else {
            currentParcelTextArea.setText("No parcel being processed.");
        }
    }

    public void processNextCustomer() {
        if (!customerQueue.isEmpty()) {
            // Dequeue the customer and get their parcel
            Customer customer = customerQueue.dequeueCustomer();
            String parcelId = customer.getParcelId();
            Parcel parcel = parcelMap.getParcel(parcelId);

            if (parcel != null) {
                // Update the parcel status
                parcel.setStatus("Processed");

                // Log the processing
                double totalfee = worker.calculateFee(parcel);
                log.logEvent("Processed customer: " + customer.getName()
                        + ", Parcel: " + parcel.getParcelId()
                        + ", Fee: " + totalfee
                        + ", Status: " + parcel.getStatus());

                // Update the GUI
                updateCustomerQueueDisplay();  // Refresh customer queue
                updateParcelDisplay();         // Refresh parcels column (left side)
                updateCurrentParcelDisplay(parcel);

            } else {
                log.logError("Parcel not found for Parcel ID: " + parcelId);
                currentParcelTextArea.setText("Error: Parcel not found for Parcel ID: " + parcelId);
            }
        } else {
            log.logEvent("No customers in the queue.");
            currentParcelTextArea.setText("No customers in the queue.");
        }
    }
    // Main Application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Manager();
            }
        });
    }


}