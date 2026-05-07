package org.example;

import javax.swing.*;

public class LeaderDashboard extends JFrame {

    // 1. Declare the fileHandler at the class level
    private CreateFile fileHandler;

    public LeaderDashboard() {
        // 2. Initialize the fileHandler
        fileHandler = new CreateFile();

        setTitle("Team Leader Panel");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton assignTask = new JButton("Assign Task");
        assignTask.setBounds(50, 50, 150, 30);

        JButton viewCompleted = new JButton("Completed Tasks");
        viewCompleted.setBounds(50, 100, 150, 30);

        JButton Logout = new JButton("Logout");
        Logout.setBounds(50, 150, 150, 30);

        add(assignTask);
        add(viewCompleted);
        add(Logout);

        // ACTION LISTENERS
        assignTask.addActionListener(_ -> {
            String task = JOptionPane.showInputDialog(this, "Enter the task for the employee:");

            if (task != null && !task.trim().isEmpty()) {
                // 3. Now this correctly references the class-level variable
                fileHandler.saveTask(task);
                JOptionPane.showMessageDialog(this, "Task '" + task + "' assigned!");
            }
        });

        viewCompleted.addActionListener(_ -> {
            // 1. Get the report string from our file handler
            String report = fileHandler.getCompletedTasksReport();

            // 2. Display it in a scrollable text area (in case the list is long)
            JTextArea textArea = new JTextArea(report);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(300, 200));

            JOptionPane.showMessageDialog(this, scrollPane, "Task History", JOptionPane.INFORMATION_MESSAGE);
        });

        Logout.addActionListener(_ -> {
            SwingUtilities.invokeLater(LoginScreen::new);
            dispose();
        });

        setVisible(true);
    }
}