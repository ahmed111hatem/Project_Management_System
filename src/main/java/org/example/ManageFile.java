package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManageFile {
    private final String TASKS_FILE = "Tasks.dat";
    private final String COMPLETED_FILE = "CompletedTasks.dat";
    private final String LOGS_FILE = "WorkLogs.dat";
    private final String VACATION_FILE = "Vacations.dat";
    private final String PENALTY_FILE = "Penalties.dat";
    private final String REPORTS_FILE = "Reports.dat";

    // --- Task Management ---
    public void saveTask(String task) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(TASKS_FILE, true))) {
            out.writeUTF(task);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public List<String> getAllTasks() {
        List<String> taskList = new ArrayList<>();
        try (DataInputStream in = new DataInputStream(new FileInputStream(TASKS_FILE))) {
            while (in.available() > 0) taskList.add(in.readUTF());
        } catch (IOException _) { }
        return taskList;
    }

    public void moveTaskToCompleted(String task) {
        List<String> remaining = getAllTasks();
        if (remaining.remove(task)) {
            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(TASKS_FILE, false))) {
                for (String t : remaining) out.writeUTF(t);
            } catch (IOException _) { }
            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(COMPLETED_FILE, true))) {
                out.writeUTF(task);
            } catch (IOException _) { }
        }
    }

    // --- Time & Attendance ---
    public void saveWorkLog(String empName, double hours) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(LOGS_FILE, true))) {
            out.writeUTF(empName);
            out.writeDouble(hours);
            out.writeLong(System.currentTimeMillis());
        } catch (IOException e) { e.printStackTrace(); }
    }

    public double getMonthlyHours(String empName) {
        double total = 0;
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        try (DataInputStream in = new DataInputStream(new FileInputStream(LOGS_FILE))) {
            while (in.available() > 0) {
                String name = in.readUTF();
                double h = in.readDouble();
                long time = in.readLong();
                cal.setTimeInMillis(time);
                if (name.equalsIgnoreCase(empName) && cal.get(Calendar.MONTH) == month) total += h;
            }
        } catch (IOException e) { }
        return total;
    }

    // --- Vacations & Reports ---
    public void requestVacation(String emp, String reason) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(VACATION_FILE, true))) {
            out.writeUTF(emp); out.writeUTF(reason); out.writeUTF("Pending");
        } catch (IOException e) { }
    }

    public List<String> getVacationRequests() {
        List<String> list = new ArrayList<>();
        try (DataInputStream in = new DataInputStream(new FileInputStream(VACATION_FILE))) {
            while (in.available() > 0) {
                list.add(in.readUTF() + " | Reason: " + in.readUTF() + " | Status: " + in.readUTF());
            }
        } catch (IOException e) { }
        return list;
    }

    public void processVacation(String selectedRequest, String newStatus) {
        List<String> allData = new ArrayList<>();
        try (DataInputStream in = new DataInputStream(new FileInputStream(VACATION_FILE))) {
            while (in.available() > 0) {
                String emp = in.readUTF();
                String reason = in.readUTF();
                String currentStatus = in.readUTF();

                String lineIdentifier = emp + " | Reason: " + reason + " | Status: " + currentStatus;

                // If this matches the one selected in the UI, update the status
                if (lineIdentifier.equals(selectedRequest)) {
                    currentStatus = newStatus;
                }

                allData.add(emp);
                allData.add(reason);
                allData.add(currentStatus);
            }
        } catch (IOException e) { e.printStackTrace(); }

        // Rewrite file with the change
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(VACATION_FILE, false))) {
            for (String s : allData) {
                out.writeUTF(s);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void approveVacation(String selected) {
        List<String> data = new ArrayList<>();
        try (DataInputStream in = new DataInputStream(new FileInputStream(VACATION_FILE))) {
            while (in.available() > 0) {
                String e = in.readUTF(), r = in.readUTF(), s = in.readUTF();
                String line = e + " | Reason: " + r + " | Status: " + s;
                data.add(e); data.add(r); data.add(line.equals(selected) ? "Approved" : s);
            }
        } catch (IOException e) { }
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(VACATION_FILE, false))) {
            for (String s : data) out.writeUTF(s);
        } catch (IOException e) { }
    }

    public String getEmployeePenalties(String name) {
        StringBuilder sb = new StringBuilder("Penalties:\n");
        try (DataInputStream in = new DataInputStream(new FileInputStream(PENALTY_FILE))) {
            while (in.available() > 0) {
                String u = in.readUTF(), r = in.readUTF(); double amt = in.readDouble();
                if (u.equalsIgnoreCase(name)) sb.append("- ").append(r).append(" (").append(amt).append(")\n");
            }
        } catch (IOException e) { return "No penalties found."; }
        return sb.toString();
    }

    public String getProjectStatusReport() {
        StringBuilder sb = new StringBuilder("--- Project Status ---\n\n[IN PROGRESS]\n");
        for (String t : getAllTasks()) sb.append("• ").append(t).append("\n");
        sb.append("\n[COMPLETED]\n");
        try (DataInputStream in = new DataInputStream(new FileInputStream(COMPLETED_FILE))) {
            while (in.available() > 0) sb.append("✓ ").append(in.readUTF()).append("\n");
        } catch (IOException e) { }
        return sb.toString();
    }
}