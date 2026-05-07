package org.example;

import javax.swing.*;

public class AdminDashboard extends JFrame {
    // 1. Initialize the data handler
    private final ManageFile fileHandler = new ManageFile();

    public AdminDashboard() {
        setTitle("Admin Panel - User Management");
        setSize(450, 400); // Increased size to fit more management buttons
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Buttons ---
        JButton viewProjects = new JButton("View Project Modes");
        viewProjects.setBounds(100, 50, 200, 30);

        JButton addUser = new JButton("Add New User");
        addUser.setBounds(100, 100, 200, 30);

        JButton manageUsers = new JButton("Update / Delete User");
        manageUsers.setBounds(100, 150, 200, 30);

        JButton logout = new JButton("Logout");
        logout.setBounds(100, 250, 200, 30);

        add(viewProjects);
        add(addUser);
        add(manageUsers);
        add(logout);

        // --- Action Listeners ---

        // View Projects grouped by Mode (Pending/Finished)
        viewProjects.addActionListener(_ -> {
            String report = fileHandler.getPMProjectReport();
            JTextArea area = new JTextArea(report);
            area.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(area), "Project Overview", JOptionPane.PLAIN_MESSAGE);
        });

        // Add User Logic
        addUser.addActionListener(_ -> {
            String name = JOptionPane.showInputDialog(this, "Enter Username:");
            if (name == null || name.trim().isEmpty()) return;

            String pass = JOptionPane.showInputDialog(this, "Enter Password:");
            if (pass == null || pass.trim().isEmpty()) return;

            String[] roles = {"Employee", "Team Leader", "PM", "Admin"};
            String role = (String) JOptionPane.showInputDialog(this, "Select Role:", "Role Selection",
                    JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

            if (role != null) {
                fileHandler.addUser(name, pass, role);
                JOptionPane.showMessageDialog(this, "User '" + name + "' added as " + role);
            }
        });

        // Update/Delete User Logic
        manageUsers.addActionListener(_ -> {
            String target = JOptionPane.showInputDialog(this, "Enter Username to Manage:");
            if (target == null || target.trim().isEmpty()) return;

            Object[] options = {"Update", "Delete", "Cancel"};
            int choice = JOptionPane.showOptionDialog(this, "What do you want to do with " + target + "?",
                    "Manage User", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[2]);

            if (choice == JOptionPane.YES_OPTION) { // UPDATE
                String newPass = JOptionPane.showInputDialog(this, "Enter New Password:");
                String[] roles = {"Employee", "Team Leader", "PM", "Admin"};
                String newRole = (String) JOptionPane.showInputDialog(this, "Select New Role:", "Update Role",
                        JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

                if (newPass != null && newRole != null) {
                    fileHandler.updateOrDeleteUser(target, newPass, newRole, false);
                    JOptionPane.showMessageDialog(this, "User updated successfully.");
                }
            } else if (choice == JOptionPane.NO_OPTION) { // DELETE
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + target + "?");
                if (confirm == JOptionPane.YES_OPTION) {
                    fileHandler.updateOrDeleteUser(target, "", "", true);
                    JOptionPane.showMessageDialog(this, "User deleted.");
                }
            }
        });

        logout.addActionListener(_ -> {
            dispose();
            SwingUtilities.invokeLater(LoginScreen::new);
        });

        setVisible(true);
    }
}