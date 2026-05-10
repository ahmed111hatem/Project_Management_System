package org.example;
import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        setSize(450, 400); // Slightly larger for better spacing
        setTitle("Corporate Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Name
        JLabel labelName = new JLabel("Full Name:");
        JTextField nameField = new JTextField(20); // Renamed to nameField for consistency

        // Email
        JLabel labelEmail = new JLabel("Gmail Address:");
        JTextField emailField = new JTextField(20);

        // Role
        JLabel labelRole = new JLabel("Role (Admin/PM/Leader/Emp):");
        JTextField roleField = new JTextField(20);

        // Password
        JLabel labelPass = new JLabel("Password:");
        JPasswordField pass = new JPasswordField(20);

        // Button
        JButton loginBtn = new JButton("Login to System");

        // Add components to panel
        panel.add(labelName);  panel.add(nameField);
        panel.add(labelEmail); panel.add(emailField);
        panel.add(labelRole);  panel.add(roleField);
        panel.add(labelPass);  panel.add(pass);
        panel.add(new JLabel("")); // Spacer
        panel.add(loginBtn);

        add(panel);

        // Auth
        nameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                if (Character.isDigit(e.getKeyChar())) {
                    e.consume(); // Reject the key press
                }
            }
        });

        // 2. Validation Logic
        loginBtn.addActionListener(_ -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().toLowerCase().trim();
            String role = roleField.getText().toLowerCase();
            String password = new String(pass.getPassword());

            // Check if name is empty (since numbers are already blocked)
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name.");
                return;
            }

            // Simple Email Check
            if (!email.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(this, "Access Denied: Use a @gmail.com address.");
                return;
            }

            // Role Routing
            if (role.contains("emp") && password.equals("emp123")) {
                new EmployeeDashboard();
            } else if (role.contains("leader") && password.equals("leader123")) {
                new LeaderDashboard();
            } else if (role.contains("pm") && password.equals("pm123")) {
                new PMDashboard();
            } else if (role.contains("admin") && password.equals("admin123")) {
                new AdminDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials or Role");
                return;
            }

            dispose(); // Close login window
        });

        setVisible(true);
    }
}