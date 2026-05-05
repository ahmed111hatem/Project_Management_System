package org.example;

import javax.swing.*;

public class PMDashboard extends JFrame {

    public PMDashboard() {
        setTitle("Project Manager");
        setSize(400, 300);
        setLayout(null);

        JButton progress = new JButton("View Progress");
        progress.setBounds(50,50,150,30);

        JButton report = new JButton("Report Employee");
        report.setBounds(50,100,150,30);

        JButton Logout = new JButton("Logout");
        Logout.setBounds(50,150,150,30);

        add(progress);
        add(report);
        add(Logout);

        progress.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Project Progress: 60%");
        });

        report.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Report sent to Team Leader");
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