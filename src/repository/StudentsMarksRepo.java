package repository;

import java.util.List;
import java.util.ArrayList;

public class StudentsMarksRepo {
    private FileHandler fileHandle;

    public StudentsMarksRepo() {
        fileHandle = new FileHandler();
        fileHandle.initializeFile("studentsMarks.txt");
    }

    // Method to get courses for a specific student
    public List<String> getCoursesForStudent(String username) {
        List<String> courses = new ArrayList<>();
        List<String> allRecords = fileHandle.readLines("studentsMarks.txt");

        for (String record : allRecords) {
            String[] parts = record.split(",");
            if (parts[0].equals(username)) {
                // Extract courses (odd indices starting from 1)
                for (int i = 1; i < parts.length; i += 2) {
                    courses.add(parts[i]);
                }
                break;
            }
        }

        return courses;
    }

    // Method to get marks for a specific student
    public List<String> getMarksForStudent(String username) {
        List<String> marks = new ArrayList<>();
        List<String> allRecords = fileHandle.readLines("studentsMarks.txt");

        for (String record : allRecords) {
            String[] parts = record.split(",");
            if (parts[0].equals(username)) {
                // Extract marks (even indices starting from 2)
                for (int i = 2; i < parts.length; i += 2) {
                    marks.add(parts[i - 1] + ": " + parts[i]); // Course: Mark
                }
                break;
            }
        }

        // If no marks are found, return "N/A"
        if (marks.isEmpty()) {
            marks.add("N/A");
        }

        return marks;
    }

    // Method to add or update marks for a student
    public void addOrUpdateMarks(String username, String course, String mark) {
        List<String> allRecords = fileHandle.readLines("studentsMarks.txt");
        List<String> updatedRecords = new ArrayList<>();
        boolean userFound = false;

        for (String record : allRecords) {
            String[] parts = record.split(",");
            if (parts[0].equals(username)) {
                // Update the record for this user
                StringBuilder updatedRecord = new StringBuilder();
                updatedRecord.append(username);

                // Check if the course already exists in the record
                boolean courseFound = false;
                for (int i = 1; i < parts.length; i += 2) {
                    if (parts[i].equals(course)) {
                        // Update the mark for the existing course
                        updatedRecord.append(",").append(course).append(",").append(mark);
                        courseFound = true;
                    } else {
                        // Keep the existing course and mark
                        updatedRecord.append(",").append(parts[i]).append(",").append(parts[i + 1]);
                    }
                }

                // If the course was not found, add it to the record
                if (!courseFound) {
                    updatedRecord.append(",").append(course).append(",").append(mark);
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
            newRecord.append(username).append(",").append(course).append(",").append(mark);
            updatedRecords.add(newRecord.toString());
        }

        // Write the updated records back to the file
        StringBuilder newContent = new StringBuilder();
        for (String record : updatedRecords) {
            newContent.append(record).append("\n");
        }
        fileHandle.writeFile("studentsMarks.txt", newContent.toString());
    }
}