package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CreateFile {
    private final String TASKS_FILE = "Tasks.dat";
    private final String COMPLETED_FILE = "CompletedTasks.dat";

    /**
     * Reads all tasks currently stored in Tasks.dat
     */
    public List<String> getAllTasks() {
        List<String> taskList = new ArrayList<>();
        try (DataInputStream in = new DataInputStream(new FileInputStream(TASKS_FILE))) {
            while (in.available() > 0) {
                taskList.add(in.readUTF());
            }
        } catch (IOException e) {
            // It's okay if the file doesn't exist yet; it just returns an empty list
        }
        return taskList;
    }

    /**
     * Moves a task from the 'Pending' file to the 'Completed' file
     */
    public void moveTaskToCompleted(String taskToComplete) {
        List<String> remainingTasks = getAllTasks();

        // Remove the specific task from our list in memory
        if (remainingTasks.remove(taskToComplete)) {

            // 1. Rewrite Tasks.dat (false flag wipes the file and starts fresh with the remaining list)
            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(TASKS_FILE, false))) {
                for (String t : remainingTasks) {
                    out.writeUTF(t);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 2. Append the completed task to CompletedTasks.dat (true flag adds to the end)
            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(COMPLETED_FILE, true))) {
                out.writeUTF(taskToComplete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Saves a new task assigned by the leader
     */
    public void saveTask(String task) {
        // Append mode is 'true' so we don't delete existing tasks
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(TASKS_FILE, true))) {
            out.writeUTF(task);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCompletedTasksReport() {
        File file = new File(COMPLETED_FILE);
        if (!file.exists()) {
            return "No tasks have been completed yet.";
        }

        StringBuilder sb = new StringBuilder("--- Completed Tasks ---\n");
        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            int count = 1;
            while (in.available() > 0) {
                sb.append(count).append(". ").append(in.readUTF()).append("\n");
                count++;
            }
        } catch (IOException e) {
            return "Error reading completed tasks.";
        }

        return sb.toString();
    }
}