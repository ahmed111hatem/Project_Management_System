package org.example;

import java.awt.Desktop;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;

import static org.example.theme.INPUT_BG;
import static org.example.theme.ACCENT_GREEN;
import static org.example.theme.ACCENT_BLUE;
import static org.example.theme.DARK_BG;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        setSize(450, 450); // Increased height to fit the new button
        setTitle("Login System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.getContentPane().setBackground(DARK_BG);

        // Updated to 6 rows to make room for the Report Button
        JPanel panel = new JPanel(new GridLayout(6, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false); // to fix the color view
        // font Label
        Font fieldFont  = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        Font btnFont    = new Font(Font.SANS_SERIF, Font.BOLD, 13);

        // 4. Styling Text Fields (Dark background, white text, inner padding)
        var inputBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(63, 75, 117), 1, true),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        );

        // Define them cleanly in one line each
        JTextField nameField  = setupDarkField(new JTextField(), fieldFont, inputBorder);
        JTextField emailField = setupDarkField(new JTextField(), fieldFont, inputBorder);
        JTextField roleField  = setupDarkField(new JTextField(), fieldFont, inputBorder);
        JPasswordField passField = (JPasswordField) setupDarkField(new JPasswordField(), fieldFont, inputBorder);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(btnFont);
        loginBtn.setBackground(ACCENT_GREEN);
        loginBtn.setForeground(Color.black);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // The New Report Button
        JButton viewReportBtn = new JButton(" View Project Report");
        viewReportBtn.setFont(btnFont);
        viewReportBtn.setBackground(ACCENT_BLUE);
        viewReportBtn.setForeground(Color.black);
        viewReportBtn.setFocusPainted(false);
        viewReportBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add components
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Gmail:");
        emailLabel.setForeground(Color.WHITE);
        panel.add(emailLabel);
        panel.add(emailField);

        JLabel RoleLabel = new JLabel("Role:");
        RoleLabel.setForeground(Color.WHITE);
        panel.add(RoleLabel);
        panel.add(roleField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        panel.add(passLabel);
        panel.add(passField);

        // Bottom Row
        panel.add(viewReportBtn); // New button on the left
        panel.add(loginBtn);      // Login button on the right

        // --- Logic for the New Button ---
        viewReportBtn.addActionListener(_ -> {
            try {
                Desktop.getDesktop().open(new File("Project_System_Documentation.pdf"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Could not open PDF: " + ex.getMessage());
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

    private JTextField setupDarkField(JTextField field, Font font, javax.swing.border.Border border) {
        field.setFont(font);
        field.setBackground(INPUT_BG); // Uses the static import directly!
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(border);
        return field;
    }
}