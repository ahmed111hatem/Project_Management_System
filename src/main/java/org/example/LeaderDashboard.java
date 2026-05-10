package org.example;

import javax.swing.*;
import java.util.List;

public class LeaderDashboard extends JFrame {
    private final ManageFile fileHandler = new ManageFile();

    public LeaderDashboard() {
        setTitle("Team Leader Panel");
        setSize(450, 450); // Increased size slightly for better fit
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Component Definitions ---
        JButton assignTask = new JButton("Assign Task");
        assignTask.setBounds(100, 50, 200, 30);

        JButton viewStatus = new JButton("Project Overview");
        viewStatus.setBounds(100, 100, 200, 30);

        JButton manageVacations = new JButton("Manage Vacations");
        manageVacations.setBounds(100, 150, 200, 30);

        JButton viewPMReports = new JButton("View PM Reports");
        viewPMReports.setBounds(100, 200, 200, 30); // Adjusted Y to avoid overlap

        JButton logout = new JButton("Logout");
        logout.setBounds(100, 250, 200, 30); // Adjusted Y to avoid overlap

        // --- Add to Frame ---
        add(assignTask);
        add(viewStatus);
        add(manageVacations);
        add(viewPMReports);
        add(logout);

        // --- Action Listeners ---
//        Assign Task Func
        assignTask.addActionListener(_ -> {
            String task = JOptionPane.showInputDialog(this, "Task description:");
            if (task != null && !task.trim().isEmpty()) {
                fileHandler.saveTask(task);
                JOptionPane.showMessageDialog(this, "Task Assigned!");
            }
        });

        viewStatus.addActionListener(_ -> {
            JTextArea area = new JTextArea(fileHandler.getProjectStatusReport());
            area.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(area), "Project Status", JOptionPane.INFORMATION_MESSAGE);
        });

        manageVacations.addActionListener(_ -> {
            List<String> requests = fileHandler.getVacationRequests();
            if (requests.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No requests found.");
                return;
            }

            String choice = (String) JOptionPane.showInputDialog(this, "Select Request:",
                    "Vacation Manager", JOptionPane.QUESTION_MESSAGE, null, requests.toArray(), requests.get(0));

            if (choice != null) {
                Object[] options = {"Accept", "Reject", "Cancel"};
                int n = JOptionPane.showOptionDialog(this,
                        "What would you like to do with this request?\n" + choice,
                        "Decision",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, options, options[2]);

                if (n == JOptionPane.YES_OPTION) {
                    fileHandler.processVacation(choice, "Accepted");
                    JOptionPane.showMessageDialog(this, "Request Accepted.");
                } else if (n == JOptionPane.NO_OPTION) {
                    fileHandler.processVacation(choice, "Rejected");
                    JOptionPane.showMessageDialog(this, "Request Rejected.");
                }
            }
        });

        viewPMReports.addActionListener(_ -> {
            String reportData = fileHandler.getAllEmployeeReports();
            JTextArea textArea = new JTextArea(reportData);
            textArea.setEditable(false);
            textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));

            JOptionPane.showMessageDialog(this, scrollPane, "Incoming PM Reports", JOptionPane.PLAIN_MESSAGE);
        });

        logout.addActionListener(_ -> {
            dispose();
            SwingUtilities.invokeLater(LoginScreen::new);
        });

        setVisible(true);
    }
}