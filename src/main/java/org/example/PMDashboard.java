package org.example;

import javax.swing.*;

public class PMDashboard extends JFrame {

    // 1. Initialize the data handler
    private final ManageFile fileHandler = new ManageFile();

    public PMDashboard() {
        setTitle("Project Manager Panel");
        // 2. Increased height to 400 so the bottom buttons are visible
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton progress = new JButton("View Progress");
        progress.setBounds(50, 50, 200, 30);

        JButton reportBtn = new JButton("Report to Team Leader");
        reportBtn.setBounds(50, 100, 200, 30);

        JButton logout = new JButton("Logout");
        logout.setBounds(50, 150, 200, 30);

        add(progress);
        add(reportBtn);
        add(logout);

        // --- Action Listeners ---

        // View Dynamic Progress %
        progress.addActionListener(_ -> {
            double percent = fileHandler.getCompletionPercentage();
            JOptionPane.showMessageDialog(this,
                    String.format("Current Project Progress: %.1f%%", percent));
        });

        // Send Report to Team Leader
        reportBtn.addActionListener(_ -> {
            String empName = JOptionPane.showInputDialog(this, "Target Employee Name:");
            if (empName != null && !empName.trim().isEmpty()) {
                String details = JOptionPane.showInputDialog(this, "Enter Report Details:");
                if (details != null && !details.trim().isEmpty()) {
                    fileHandler.saveEmployeeReport(empName, details);
                    JOptionPane.showMessageDialog(this, "Performance report logged for Team Leader.");
                }
            }
        });

        // Logout
        logout.addActionListener(_ -> {
            SwingUtilities.invokeLater(LoginScreen::new);
            dispose();
        });

        setVisible(true);
    }
}