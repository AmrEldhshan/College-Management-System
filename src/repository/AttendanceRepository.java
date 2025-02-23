package repository;

import java.util.List;
import java.util.ArrayList;

public class AttendanceRepository {
    private FileHandler fileHandle;

    public AttendanceRepository() {
        fileHandle = new FileHandler();
        fileHandle.initializeFile("attendance.txt"); // Hardcoded file name
    }

    // Method to record an absence for a user
    public void recordAbsence(String userId, String date) {
        // Get the existing absence records for the user
        List<String> absenceRecords = getAbsenceRecords(userId);

        // Add the new absence date if it doesn't already exist
        if (!absenceRecords.contains(date)) {
            absenceRecords.add(date);
        }

        // Update the absence records in the file
        updateAbsenceRecords(userId, absenceRecords);
    }

    // Method to get all absence records for a user
    public List<String> getAbsenceRecords(String userId) {
        List<String> absenceRecords = new ArrayList<>();
        List<String> allRecords = fileHandle.readLines("attendance.txt"); // Hardcoded file name

        for (String record : allRecords) {
            String[] parts = record.split(",");
            if (parts[0].equals(userId)) {
                // Add all absence dates for this user
                for (int i = 1; i < parts.length; i++) {
                    absenceRecords.add(parts[i]);
                }
                break;
            }
        }

        return absenceRecords;
    }

    // Method to update absence records for a user
    private void updateAbsenceRecords(String userId, List<String> absenceRecords) {
        List<String> allRecords = fileHandle.readLines("attendance.txt"); // Hardcoded file name
        List<String> updatedRecords = new ArrayList<>();
        boolean userFound = false;

        for (String record : allRecords) {
            String[] parts = record.split(",");
            if (parts[0].equals(userId)) {
                // Update the record for this user
                StringBuilder updatedRecord = new StringBuilder();
                updatedRecord.append(userId);
                for (String date : absenceRecords) {
                    updatedRecord.append(",").append(date);
                }
                updatedRecords.add(updatedRecord.toString());
                userFound = true;
            } else {
                updatedRecords.add(record);
            }
        }

        // If the user doesn't have any records yet, add a new record
        if (!userFound) {
            StringBuilder newRecord = new StringBuilder();
            newRecord.append(userId);
            for (String date : absenceRecords) {
                newRecord.append(",").append(date);
            }
            updatedRecords.add(newRecord.toString());
        }

        // Write the updated records back to the file
        StringBuilder newContent = new StringBuilder();
        for (String record : updatedRecords) {
            newContent.append(record).append("\n");
        }
        fileHandle.writeFile("attendance.txt", newContent.toString()); // Hardcoded file name
    }

    // Method to check if a user was absent on a specific date
    public boolean isAbsentOnDate(String userId, String date) {
        List<String> absenceRecords = getAbsenceRecords(userId);
        return absenceRecords.contains(date);
    }

    // Method to remove an absence record for a user on a specific date
    public void removeAbsence(String userId, String date) {
        List<String> absenceRecords = getAbsenceRecords(userId);
        absenceRecords.remove(date);
        updateAbsenceRecords(userId, absenceRecords);
    }
}