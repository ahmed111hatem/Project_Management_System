package org.example;

import javax.swing.*;
import java.util.List;

public class LeaderDashboard extends JFrame {
    private final ManageFile fileHandler = new ManageFile();

    public LeaderDashboard() {
        setTitle("Team Leader Panel");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton assignTask = new JButton("Assign Task");
        assignTask.setBounds(100, 50, 200, 30);


        JButton viewStatus = new JButton("Project Overview");
        viewStatus.setBounds(100, 100, 200, 30);


        JButton manageVacations = new JButton("Manage Vacations");
        manageVacations.setBounds(100, 150, 200, 30);


        JButton logout = new JButton("Logout");
        logout.setBounds(100, 250, 200, 30);

        add(assignTask); add(viewStatus); add(manageVacations); add(logout);

        assignTask.addActionListener(_ -> {
            String task = JOptionPane.showInputDialog(this, "Task description:");
            if (task != null) fileHandler.saveTask(task);
        });

        viewStatus.addActionListener(_ -> {
            JTextArea area = new JTextArea(fileHandler.getProjectStatusReport());
            JOptionPane.showMessageDialog(this, new JScrollPane(area));
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
                // Create a custom dialog with Accept/Reject buttons
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

        logout.addActionListener(_ -> { dispose(); new LoginScreen(); });

        setVisible(true);
    }
}