package org.example;

import javax.swing.*;

public class PMDashboard extends JFrame {
    private final ManageFile fileHandler = new ManageFile();

    public PMDashboard() {
        setTitle("Project Manager Panel");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Renamed button to better reflect its purpose
        JButton viewProjects = new JButton("View All Projects");
        viewProjects.setBounds(50, 50, 200, 30);

        JButton reportBtn = new JButton("Report to Team Leader");
        reportBtn.setBounds(50, 100, 200, 30);

        JButton logout = new JButton("Logout");
        logout.setBounds(50, 150, 200, 30);

        add(viewProjects);
        add(reportBtn);
        add(logout);

        // --- Action Listeners ---

        // View Projects grouped by Mode (Pending/Finished)
        viewProjects.addActionListener(_ -> {
            String report = fileHandler.getPMProjectReport();

            JTextArea textArea = new JTextArea(report);
            textArea.setEditable(false);
            textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(350, 300));

            // Displays the grouped text list directly
            JOptionPane.showMessageDialog(this, scrollPane, "Project Modes", JOptionPane.PLAIN_MESSAGE);
        });

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

        logout.addActionListener(_ -> {
            dispose();
            SwingUtilities.invokeLater(LoginScreen::new);
        });

        setVisible(true);
    }
}