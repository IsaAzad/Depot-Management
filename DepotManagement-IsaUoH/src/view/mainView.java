package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class mainView {
    private JFrame frame;
    private JTextArea parcelsTextArea;
    private JTextArea customerQueueTextArea;
    private JTextArea currentParcelTextArea;
    private JButton processButton;

    public mainView() {
        initializeGUI();
    }

    private void initializeGUI() {
        // Main Frame
        frame = new JFrame("Parcel Processing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(1, 3));

        // Parcels Panel
        JPanel parcelsPanel = new JPanel(new BorderLayout());
        parcelsTextArea = new JTextArea();
        parcelsTextArea.setEditable(false);
        parcelsPanel.add(new JLabel("Parcels:"), BorderLayout.NORTH);
        parcelsPanel.add(new JScrollPane(parcelsTextArea), BorderLayout.CENTER);

        // Customer Queue Panel
        JPanel customerQueuePanel = new JPanel(new BorderLayout());
        customerQueueTextArea = new JTextArea();
        customerQueueTextArea.setEditable(false);
        customerQueuePanel.add(new JLabel("Customer Queue:"), BorderLayout.NORTH);
        customerQueuePanel.add(new JScrollPane(customerQueueTextArea), BorderLayout.CENTER);

        // Current Parcel Panel
        JPanel currentParcelPanel = new JPanel(new BorderLayout());
        currentParcelTextArea = new JTextArea();
        currentParcelTextArea.setEditable(false);
        processButton = new JButton("Process Next Customer");
        currentParcelPanel.add(new JLabel("Current Parcel:"), BorderLayout.NORTH);
        currentParcelPanel.add(new JScrollPane(currentParcelTextArea), BorderLayout.CENTER);
        currentParcelPanel.add(processButton, BorderLayout.SOUTH);

        // Add panels to the frame
        frame.add(parcelsPanel);
        frame.add(customerQueuePanel);
        frame.add(currentParcelPanel);
    }


    public void show() {
        frame.setVisible(true);
    }


    public void updateParcelDisplay(String text) {
        parcelsTextArea.setText(text);
    }


    public void updateCustomerQueueDisplay(String text) {
        customerQueueTextArea.setText(text);
    }


    public void updateCurrentParcelDisplay(String text) {
        currentParcelTextArea.setText(text);
    }

    public void setProcessButtonListener(ActionListener listener) {
        processButton.addActionListener(listener);
    }


    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    public void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(frame, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
