package org.example;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class EmployeeDashboard extends JFrame {
    private final ManageFile fileHandler = new ManageFile();
    private final AtomicLong startTime = new AtomicLong(0);

    public EmployeeDashboard() {
        setTitle("Employee Panel");
        setSize(450, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton checkIn = new JButton("Check In");
        checkIn.setBounds(50, 30, 150, 30);

        JButton checkOut = new JButton("Check Out");
        checkOut.setBounds(220, 30, 150, 30);


        JButton tasks = new JButton("View Tasks");
        tasks.setBounds(50, 80, 150, 30);


        JButton completeTask = new JButton("Complete Task");
        completeTask.setBounds(220, 80, 150, 30);


        JButton vacationBtn = new JButton("Request Vacation");
        vacationBtn.setBounds(50, 130, 150, 30);


        JButton logout = new JButton("Logout");
        logout.setBounds(220, 230, 150, 30);


        JButton penaltyBtn = new JButton("View Penalties");
        penaltyBtn.setBounds(220, 130, 150, 30);


        JButton viewHoursBtn = new JButton("Monthly Hours");
        viewHoursBtn.setBounds(50, 180, 150, 30);


        JButton viewVacations = new JButton("My Vacations");
        viewVacations.setBounds(220, 180, 150, 30);


        JLabel status = new JLabel("Status: Idle");
        status.setBounds(50, 230, 350, 30);

        add(checkIn);

        add(checkOut);

        add(tasks);

        add(completeTask);

        add(vacationBtn);

        add(penaltyBtn);

        add(viewHoursBtn);

        add(viewVacations);

        add(logout);

        add(status);

        checkIn.addActionListener(_ -> {
            startTime.set(System.currentTimeMillis());
            status.setText("Status: Checked In");
            JOptionPane.showMessageDialog(this, "Clock started!");
        });

        checkOut.addActionListener(_ -> {
            if (startTime.get() == 0) return;
            double hours = (double) (System.currentTimeMillis() - startTime.get()) / 3600000;
            fileHandler.saveWorkLog("EmployeeName", hours);
            status.setText(String.format("Logged %.2f hours.", hours));
            startTime.set(0);
        });

        tasks.addActionListener(_ -> {
            List<String> list = fileHandler.getAllTasks();
            JOptionPane.showMessageDialog(this, list.isEmpty() ? "No tasks." : String.join("\n", list));
        });

        completeTask.addActionListener(_ -> {
            List<String> list = fileHandler.getAllTasks();
            if (list.isEmpty()) return;
            String choice = (String) JOptionPane.showInputDialog(this, "Select Task:", "Complete",
                    JOptionPane.QUESTION_MESSAGE, null, list.toArray(), list.get(0));
            if (choice != null) fileHandler.moveTaskToCompleted(choice);
        });

        vacationBtn.addActionListener(_ -> {
            String reason = JOptionPane.showInputDialog(this, "Reason:");
            if (reason != null) fileHandler.requestVacation("EmployeeName", reason);
        });

        viewVacations.addActionListener(_ -> {
            List<String> allReqs = fileHandler.getVacationRequests();
            StringBuilder myStatus = new StringBuilder("--- Your Vacation History ---\n");
            boolean found = false;

            for (String r : allReqs) {
                // This checks if the request line starts with the current employee's name
                if (r.startsWith("EmployeeName")) { // Replace "EmployeeName" with your user variable
                    myStatus.append(r).append("\n");
                    found = true;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "You haven't requested any vacations yet.");
            } else {
                JOptionPane.showMessageDialog(this, myStatus.toString());
            }
        });

        penaltyBtn.addActionListener(_ -> JOptionPane.showMessageDialog(this, fileHandler.getEmployeePenalties("EmployeeName")));

        viewHoursBtn.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Monthly Hours: " + fileHandler.getMonthlyHours("EmployeeName")));

        logout.addActionListener(_ -> { dispose(); new LoginScreen(); });

        setVisible(true);
    }
}