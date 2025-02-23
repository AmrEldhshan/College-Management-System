package models;

import repository.AttendanceRepository;
import repository.StudentsMarksRepo; // Add this import
import java.util.List;
import java.util.Scanner;
import UI.ANSIColors;

public class Student extends User {
    private String gpa;
    private List<String> courses;
    private AttendanceRepository attendanceRepo; // Added AttendanceRepository
    private StudentsMarksRepo marksRepo; // Added StudentsMarksRepo

    // Constructor
    public Student(String id, String password, String name, String gpa, List<String> courses) {
        super(id, password, name, "student");
        this.gpa = gpa;
        this.courses = courses;
        this.attendanceRepo = new AttendanceRepository(); // Initialize AttendanceRepository
        this.marksRepo = new StudentsMarksRepo(); // Initialize StudentsMarksRepo
    }

    // Getters
    public String getGpa() {
        return gpa;
    }

    public List<String> getCourses() {
        return courses;
    }

    // Implement the getMenu() method for student role
    @Override
    public String getMenu() {
        return ANSIColors.CYAN_BOLD + "\n----- Student Menu -----\n" + ANSIColors.RESET +
                "1. View Courses\n" +
                "2. View Attendance\n" + // Added attendance option
                "3. View Marks\n" +
                "4. View Profile\n" +
                "5. Report to Teacher\n" +
                "6. View GPA\n" +
                ANSIColors.RED_BOLD + "Enter 'q' to logout.\n" + ANSIColors.RESET;
    }

    // Method to handle student menu options
    public void handleMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println(getMenu());
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewCourses();
                    break;
                case "2":
                    viewAttendance(); // Added attendance functionality
                    break;
                case "3":
                    viewMarks();
                    break;
                case "4":
                    System.out.println(viewProfile());
                    break;
                case "5":
                    reportToTeacher(scanner);
                    break;
                case "6":
                    getGPA();
                    break;
                case "q":
                    running = false;
                    System.out.println(ANSIColors.RED_BOLD + "Logging out..." + ANSIColors.RESET);
                    break;
                default:
                    System.out.println(ANSIColors.RED + "Invalid choice. Please try again." + ANSIColors.RESET);
            }
        }
    }

    // Method to view courses
    private void viewCourses() {
        System.out.println("\n----- Your Courses -----");
        List<String> studentCourses = marksRepo.getCoursesForStudent(getId()); // Fetch courses from studentsMarks.txt

        if (studentCourses.isEmpty()) {
            System.out.println("No courses enrolled.");
        } else {
            for (String course : studentCourses) {
                System.out.println("- " + course);
            }
        }
    }

    // Method to view attendance
    private void viewAttendance() {
        System.out.println("\n----- Your Attendance -----");
        List<String> absenceRecords = attendanceRepo.getAbsenceRecords(getId()); // Get absence records

        if (absenceRecords.isEmpty()) {
            System.out.println("You have no recorded absences.");
        } else {
            System.out.println("Dates you were absent:");
            for (String date : absenceRecords) {
                System.out.println("- " + date);
            }
        }
    }

    // Method to view marks
    private void viewMarks() {
        System.out.println("\n----- Your Marks -----");
        List<String> marks = marksRepo.getMarksForStudent(getId()); // Fetch marks from studentsMarks.txt

        if (marks.isEmpty()) {
            System.out.println("No marks available.");
        } else {
            for (String mark : marks) {
                System.out.println("- " + mark);
            }
        }
    }

    // Method to report to teacher
    private void reportToTeacher(Scanner scanner) {
        System.out.print("Enter your report message: ");
        String message = scanner.nextLine();
        System.out.println("Report sent to teacher: " + message);
    }

    // Method to get GPA
    private void getGPA() {
        System.out.println("\n----- Your GPA -----");
        System.out.println("Your current GPA is: " + gpa);
    }

    // Override viewProfile to include courses and GPA
    @Override
    public String viewProfile() {
        return super.viewProfile() +
                "Courses: " + marksRepo.getCoursesForStudent(getId()) + "\n" + // Fetch courses from studentsMarks.txt
                "GPA: " + gpa + "\n";
    }
}