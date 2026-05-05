package org.example;

import javax.swing.*;

public class EmployeeDashboard extends JFrame {

    int workingHours = 0;

    public EmployeeDashboard() {
        setTitle("Employee Panel");
        setSize(400, 300);
        setLayout(null);

        JButton checkIn = new JButton("Check In");
        checkIn.setBounds(50,50,120,30);

        JButton checkOut = new JButton("Check Out");
        checkOut.setBounds(200,50,120,30);

        JButton tasks = new JButton("View Tasks");
        tasks.setBounds(50,100,120,30);

        JButton completeTask = new JButton("Complete Task");
        completeTask.setBounds(200,100,120,30);

        JButton Logout = new JButton("Logout");
        Logout.setBounds(50,200,120,30);

        add(checkIn);
        add(checkOut);
        add(tasks);
        add(completeTask);
        add(Logout);

        JLabel status = new JLabel("Status: Idle");
        status.setBounds(50,160,300,30);
        add(status);

        checkIn.addActionListener(e -> {
            status.setText("Checked In");
            workingHours++;
        });

        checkOut.addActionListener(e -> {
            status.setText("Checked Out");
        });

        tasks.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Task: Build Login UI");
        });

        completeTask.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Task Completed!");
        });

        Logout.addActionListener(e -> {
//            new LoginScreen();
//            SwingUtilities.invokeLater(() -> new LoginScreen());
            SwingUtilities.invokeLater(LoginScreen::new);
            dispose();
        });

        setVisible(true);
    }
}