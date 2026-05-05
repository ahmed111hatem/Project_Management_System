package org.example;

import javax.swing.*;

public class LeaderDashboard extends JFrame {

    public LeaderDashboard() {
        setTitle("Team Leader Panel");
        setSize(400, 300);
        setLayout(null);

        JButton assignTask = new JButton("Assign Task");
        assignTask.setBounds(50,50,150,30);

        JButton viewCompleted = new JButton("Completed Tasks");
        viewCompleted.setBounds(50,100,150,30);

        JButton Logout = new JButton("Logout");
        Logout.setBounds(50, 150, 150, 30);

        add(assignTask);
        add(viewCompleted);
        add(Logout);

        assignTask.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Task assigned to Employee");
        });

        viewCompleted.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "No completed tasks yet");
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