package org.example;
import java.awt.Desktop;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        setSize(450, 450); // Increased height to fit the new button
        setTitle("Corporate System - Access");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Updated to 6 rows to make room for the Report Button
        JPanel panel = new JPanel(new GridLayout(6, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField roleField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");


        // The New Report Button
        JButton viewReportBtn = new JButton(" View Project Report");


        // Add components
        panel.add(new JLabel("Full Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Gmail:"));
        panel.add(emailField);
        panel.add(new JLabel("Role:"));
        panel.add(roleField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);

        // Bottom Row
        panel.add(viewReportBtn); // New button on the left
        panel.add(loginBtn);      // Login button on the right

        // --- Logic for the New Button ---
        viewReportBtn.addActionListener(_ -> {
            try {
                // 1. Specify the file path (Must match the name of the generated PDF)
                File pdfFile = new File("Project_System_Documentation.pdf");

                if (pdfFile.exists()) {
                    // 2. Check if the system supports the Desktop API
                    if (Desktop.isDesktopSupported()) {
                        // 3. Open the file (this typically opens in the default Browser or PDF viewer)
                        Desktop.getDesktop().open(pdfFile);
                    } else {
                        JOptionPane.showMessageDialog(this, "Desktop utility is not supported on this system.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "The documentation file was not found.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening the report: " + ex.getMessage());
            }
        });

        // --- Logic for Name Authentication ---
        nameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                if (Character.isDigit(e.getKeyChar())) e.consume();
            }
        });

        // --- Login Logic ---
        loginBtn.addActionListener(_ -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().toLowerCase().trim();
            String role = roleField.getText().toLowerCase();
            String pass = new String(passField.getPassword());

            if (name.isEmpty() || !email.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid name and @gmail.com email!");
                return;
            }

            // Simple hardcoded auth for demo
            if (role.contains("admin") && pass.equals("admin123")) new AdminDashboard();
            else if (role.contains("pm") && pass.equals("pm123")) new PMDashboard();
            else if (role.contains("leader") && pass.equals("leader123")) new LeaderDashboard();
            else if (role.contains("emp") && pass.equals("emp123")) new EmployeeDashboard();
            else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!");
                return;
            }
            dispose();
        });

        add(panel);
        setVisible(true);
    }
}