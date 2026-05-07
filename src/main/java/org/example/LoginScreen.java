package org.example;
import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    public LoginScreen() {

        setSize(400, 350);
        setTitle("Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        // Name
        JLabel labelName = new JLabel("Enter your name");
        JTextField labelField = new JTextField(20);

        // Email
        JLabel labelEmail = new JLabel("Enter your Email");
        JTextField emailField = new JTextField(20);

        // Role
        JLabel labelRole = new JLabel("Enter your Role");
        JTextField roleField = new JTextField(20);

        // Password
        JLabel labelPass = new JLabel("Enter your password");
        JPasswordField pass = new JPasswordField(20);

        // Buttons
        JButton loginBtn = new JButton("Login");

        // Add to panel
        panel.add(labelName);
        panel.add(labelField);

        panel.add(labelEmail);
        panel.add(emailField);

        panel.add(labelRole);
        panel.add(roleField);

        panel.add(labelPass);
        panel.add(pass);

        panel.add(loginBtn);

        add(panel);

        // Login Action
        loginBtn.addActionListener(e -> {
            String role = roleField.getText().toLowerCase();
            String password = new String(pass.getPassword());

            if (role.contains("emp") && password.equals("emp123")) {
                new EmployeeDashboard();

            } else if (role.contains("leader") && password.equals("leader123")) {
                new LeaderDashboard();

            } else if (role.contains("pm") && password.equals("pm123")) {
                new PMDashboard();
                dispose();

            } else if (role.contains("admin") && password.equals("admin123")) {
                new AdminDashboard();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
                return;
            }

            dispose();
        });

        setVisible(true);
    }
}