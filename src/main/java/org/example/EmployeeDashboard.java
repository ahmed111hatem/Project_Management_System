package org.example;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import static org.example.theme.DARK_BG;

public class EmployeeDashboard extends JFrame {
    private final ManageFile fileHandler = new ManageFile();
    private final AtomicLong startTime = new AtomicLong(0);

    public EmployeeDashboard() {
        setTitle("Employee Panel");
        setSize(450, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window

        this.getContentPane().setBackground(DARK_BG);

        // --- All your buttons remain the same ---
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

        JButton penaltyBtn = new JButton("View Penalties");
        penaltyBtn.setBounds(220, 130, 150, 30);

        JButton viewHoursBtn = new JButton("Monthly Hours");
        viewHoursBtn.setBounds(50, 180, 150, 30);

        JButton viewVacations = new JButton("My Vacations");
        viewVacations.setBounds(220, 180, 150, 30);
        JButton logout = new JButton("Logout");
        logout.setBounds(220, 230, 150, 30);

        // This is the status label we will update
        JLabel status = new JLabel("Status: Idle");
        status.setBounds(50, 270, 350, 30);
        status.setFont(new Font("Segue UI", Font.BOLD, 12));

        // Add everything to the frame
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

        // --- UPDATED ACTION LISTENERS ---

        checkIn.addActionListener(_ -> {
            // 1. Logic for formatting the time
            LocalTime time = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String formattedTime = time.format(formatter);

            // 2. Set the variables
            startTime.set(System.currentTimeMillis());

            // 3. Update the UI Label
            status.setText("Status: Checked In at " + formattedTime);
            status.setForeground(new Color(46, 204, 113)); // Professional Green color

            JOptionPane.showMessageDialog(this, "Clock started at " + formattedTime);
        });

        checkOut.addActionListener(_ -> {
            if (startTime.get() == 0) {
                JOptionPane.showMessageDialog(this, "You haven't checked in yet!");
                return;
            }

            double hours = (double) (System.currentTimeMillis() - startTime.get()) / 3600000;
            fileHandler.saveWorkLog("EmployeeName", hours);

            // Reset status to Idle
            status.setText(String.format("Last Session: %.2f hours. (Status: Idle)", hours));
            status.setForeground(Color.BLACK);

            startTime.set(0);
            JOptionPane.showMessageDialog(this, "Shift ended. Data saved.");
        });

        // --- The rest of your listeners (tasks, vacation, etc.) stay exactly as they were ---
        tasks.addActionListener(_ -> {
            List<String> list = fileHandler.getAllTasks();
            JOptionPane.showMessageDialog(this, list.isEmpty() ? "No tasks." : String.join("\n", list));
        });

        completeTask.addActionListener(_ -> {
            List<String> list = fileHandler.getAllTasks();
            if (list.isEmpty()) return;
            String choice = (String) JOptionPane.showInputDialog(this, "Select Task:", "Complete",
                    JOptionPane.QUESTION_MESSAGE, null, list.toArray(), list.getFirst());
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
                if (r.startsWith("EmployeeName")) {
                    myStatus.append(r).append("\n");
                    found = true;
                }
            }
            JOptionPane.showMessageDialog(this, !found ? "No history found." : myStatus.toString());
        });

        penaltyBtn.addActionListener(_ -> JOptionPane.showMessageDialog(this, fileHandler.getEmployeePenalties("EmployeeName")));
        viewHoursBtn.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Monthly Hours: " + fileHandler.getMonthlyHours("EmployeeName")));
        logout.addActionListener(_ -> { dispose(); new LoginScreen(); });

        setVisible(true);
    }
}