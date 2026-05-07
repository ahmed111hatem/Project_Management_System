package org.example;

import javax.swing.*;
import java.util.List;

public class EmployeeDashboard extends JFrame {
    // Class-level field
    private final CreateFile fileHandler = new CreateFile();
    private int workingHours = 0;

    public EmployeeDashboard() {
        // 1. Basic Window Setup
        setTitle("Employee Panel");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2. Define ALL Buttons FIRST so the listeners can see them
        JButton checkIn = new JButton("Check In");
        checkIn.setBounds(50, 50, 120, 30);

        JButton checkOut = new JButton("Check Out");
        checkOut.setBounds(200, 50, 120, 30);

        JButton tasks = new JButton("View Tasks");
        tasks.setBounds(50, 100, 120, 30);

        JButton completeTask = new JButton("Complete Task");
        completeTask.setBounds(200, 100, 120, 30);

        JButton logout = new JButton("Logout");
        logout.setBounds(50, 200, 120, 30);

        JLabel status = new JLabel("Status: Idle");
        status.setBounds(50, 160, 300, 30);

        // 3. Add components to the frame
        add(checkIn);
        add(checkOut);
        add(tasks);
        add(completeTask);
        add(logout);
        add(status);

        // 4. Define ActionListeners
        checkIn.addActionListener(_ -> {
            status.setText("Checked In");
            workingHours++;
        });

        checkOut.addActionListener(_ -> {
            status.setText("Checked Out");
        });

        tasks.addActionListener(_ -> {
            List<String> allTasks = fileHandler.getAllTasks();
            if (allTasks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No tasks assigned to you!");
            } else {
                StringBuilder sb = new StringBuilder("Your Pending Tasks:\n");
                for (String t : allTasks) sb.append("- ").append(t).append("\n");
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        });

        completeTask.addActionListener(_ -> {
            List<String> allTasks = fileHandler.getAllTasks();
            if (allTasks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nothing to complete.");
                return;
            }

            // Using the choice dialog to select a task
            String choice = (String) JOptionPane.showInputDialog(
                    this,
                    "Select a task to complete:",
                    "Complete Task",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    allTasks.toArray(),
                    allTasks.get(0));

            if (choice != null) {
                fileHandler.moveTaskToCompleted(choice);
                JOptionPane.showMessageDialog(this, "Task marked as finished!");
            }
        });

        logout.addActionListener(_ -> {
            SwingUtilities.invokeLater(LoginScreen::new);
            dispose();
        });

        setVisible(true);
    }
}