package org.example;

import javax.swing.*;

import static javax.swing.JOptionPane.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Panel");
        setSize(400, 300);
        setLayout(null);

        JButton viewProjects = new JButton("View Projects");
        viewProjects.setBounds(50,50,150,30);

        JButton addUser = new JButton("Add User");
        addUser.setBounds(50,100,150,30);

        JButton Logout = new JButton("Logout");
        Logout.setBounds(50,150,150,30);

        add(viewProjects);
        add(addUser);
        add(Logout);

        viewProjects.addActionListener(_ -> showMessageDialog(this, "Project A, Project B"));

        addUser.addActionListener(_ -> showMessageDialog(this, "User Added Successfully"));

        Logout.addActionListener(_ -> {
//            new LoginScreen();
//            SwingUtilities.invokeLater(() -> new LoginScreen());
            SwingUtilities.invokeLater(LoginScreen::new);
            dispose();
        });
        setVisible(true);
    }
}