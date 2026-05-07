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
        File file = new File(TASKS_FILE);
        if (!file.exists()) return taskList;

        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            while (in.available() > 0) taskList.add(in.readUTF());
        } catch (IOException e) { e.printStackTrace(); }
        return taskList;
    }

    public void moveTaskToCompleted(String task) {
        List<String> remaining = getAllTasks();
        if (remaining.remove(task)) {
            // Rewrite Tasks.dat without the completed task
            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(TASKS_FILE, false))) {
                for (String t : remaining) out.writeUTF(t);
            } catch (IOException e) { e.printStackTrace(); }

            // Append to CompletedTasks.dat
            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(COMPLETED_FILE, true))) {
                out.writeUTF(task);
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    // --- Project Progress ---
    public double getCompletionPercentage() {
        int pending = getAllTasks().size();
        int completed = 0;
        File file = new File(COMPLETED_FILE);
        if (file.exists()) {
            try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
                while (in.available() > 0) {
                    in.readUTF();
                    completed++;
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
        int total = pending + completed;
        return (total == 0) ? 0 : ((double) completed / total) * 100;
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
        int currentMonth = cal.get(Calendar.MONTH);
        File file = new File(LOGS_FILE);
        if (!file.exists()) return 0;

        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            while (in.available() > 0) {
                String name = in.readUTF();
                double h = in.readDouble();
                long time = in.readLong();
                cal.setTimeInMillis(time);
                if (name.equalsIgnoreCase(empName) && cal.get(Calendar.MONTH) == currentMonth) {
                    total += h;
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return total;
    }

    // --- Vacations ---
    public void requestVacation(String emp, String reason) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(VACATION_FILE, true))) {
            out.writeUTF(emp);
            out.writeUTF(reason);
            out.writeUTF("Pending");
        } catch (IOException e) { e.printStackTrace(); }
    }

    public List<String> getVacationRequests() {
        List<String> list = new ArrayList<>();
        File file = new File(VACATION_FILE);
        if (!file.exists()) return list;

        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            while (in.available() > 0) {
                list.add(in.readUTF() + " | Reason: " + in.readUTF() + " | Status: " + in.readUTF());
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    public void processVacation(String selectedRequest, String newStatus) {
        List<VacationEntry> allEntries = new ArrayList<>();
        File file = new File(VACATION_FILE);
        if (!file.exists()) return;

        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            while (in.available() > 0) {
                String emp = in.readUTF();
                String reason = in.readUTF();
                String status = in.readUTF();
                String lineIdentifier = emp + " | Reason: " + reason + " | Status: " + status;

                if (lineIdentifier.equals(selectedRequest)) {
                    status = newStatus;
                }
                allEntries.add(new VacationEntry(emp, reason, status));
            }
        } catch (IOException e) { e.printStackTrace(); }

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(VACATION_FILE, false))) {
            for (VacationEntry ve : allEntries) {
                out.writeUTF(ve.emp);
                out.writeUTF(ve.reason);
                out.writeUTF(ve.status);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Inner helper class for processing
    private static class VacationEntry {
        String emp, reason, status;
        VacationEntry(String e, String r, String s) { emp = e; reason = r; status = s; }
    }

    // --- Reports & Penalties ---
    public String getEmployeePenalties(String name) {
        StringBuilder sb = new StringBuilder("Penalties for " + name + ":\n");
        File file = new File(PENALTY_FILE);
        if (!file.exists()) return "No penalties file found.";

        boolean found = false;
        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            while (in.available() > 0) {
                String u = in.readUTF();
                String r = in.readUTF();
                double amt = in.readDouble();
                if (u.equalsIgnoreCase(name)) {
                    sb.append("- ").append(r).append(" (Deduction: ").append(amt).append(")\n");
                    found = true;
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return found ? sb.toString() : "No penalties found for this employee.";
    }

    public String getProjectStatusReport() {
        StringBuilder sb = new StringBuilder("--- Project Status ---\n\n[IN PROGRESS]\n");
        List<String> tasks = getAllTasks();
        if (tasks.isEmpty()) sb.append("   (None)\n");
        else for (String t : tasks) sb.append(" • ").append(t).append("\n");

        sb.append("\n[COMPLETED]\n");
        File file = new File(COMPLETED_FILE);
        if (!file.exists()) sb.append("   (None)\n");
        else {
            try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
                while (in.available() > 0) sb.append(" ✓ ").append(in.readUTF()).append("\n");
            } catch (IOException e) { e.printStackTrace(); }
        }
        return sb.toString();
    }

    public void saveEmployeeReport(String empName, String reportDetails) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(REPORTS_FILE, true))) {
            out.writeUTF(empName);
            out.writeUTF(reportDetails);
            out.writeLong(System.currentTimeMillis());
        } catch (IOException e) { e.printStackTrace(); }
    }

    public String getAllEmployeeReports() {
        File file = new File(REPORTS_FILE);
        if (!file.exists()) return "No reports from PM yet.";

        StringBuilder sb = new StringBuilder("--- PM Reports ---\n\n");
        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            while (in.available() > 0) {
                String name = in.readUTF();
                String details = in.readUTF();
                long date = in.readLong();
                sb.append("Employee: ").append(name)
                        .append("\nDate: ").append(new java.util.Date(date))
                        .append("\nDetails: ").append(details)
                        .append("\n---------------------------\n");
            }
        } catch (IOException e) { e.printStackTrace(); }
        return sb.toString();
    }
}