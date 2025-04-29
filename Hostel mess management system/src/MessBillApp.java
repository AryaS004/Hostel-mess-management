/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Anju
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessBillApp {
     private JTextField admnNoField;
    private JTextField nameField;
    // Add other text fields for remaining columns

    public MessBillApp() {
        // Initialize Swing components, set layouts, etc.
        JFrame frame = new JFrame("Mess Bill Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel admnNoLabel = new JLabel("Admission Number:");
        admnNoField = new JTextField(10);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);

        // Add other labels and text fields for remaining columns

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRecord();
            }
        });

        panel.add(admnNoLabel);
        panel.add(admnNoField);
        panel.add(nameLabel);
        panel.add(nameField);
        // Add other labels and text fields

        panel.add(saveButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void saveRecord() {
        String admnNo = admnNoField.getText();
        String name = nameField.getText();
        // Get other values from text fields

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel", "root", "aryasql");
            String insertQuery = "INSERT INTO mess_bill (ADMN_NO, NAME, BRANCH, YR_OF_ADMN, MESS_PRESENT, MESS_RATE, RENT_ESTABLISHMENT, UTILITY_CHARGES) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, admnNo);
            pstmt.setString(2, name);
            // Set other parameters
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Record saved successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving record. Please check input values.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MessBillApp();
        });
    }
}

