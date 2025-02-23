package models;

import repository.UserRepository;
import repository.AttendanceRepository;
import repository.StudentsMarksRepo; // Add this import
import java.util.List;
import java.util.Scanner;
import UI.ANSIColors;

public class Teacher extends User {
    private List<String> courses;
    private AttendanceRepository attendanceRepo; // Added AttendanceRepository
    private StudentsMarksRepo marksRepo; // Added StudentsMarksRepo

    // Constructor
    public Teacher(String id, String password, String name, List<String> courses) {
        super(id, password, name, "teacher");
        this.courses = courses;
        this.attendanceRepo = new AttendanceRepository(); // Initialize AttendanceRepository
        this.marksRepo = new StudentsMarksRepo(); // Initialize StudentsMarksRepo
    }

    // Getters
    public List<String> getCourses() {
        return courses;
    }

    // Implement the getMenu() method for teacher role
    @Override
    public String getMenu() {
        return ANSIColors.CYAN_BOLD + "\n----- Teacher Menu -----\n" + ANSIColors.RESET +
                "1. View Courses\n" +
                "2. View Attendance\n" + // Added attendance option
                "3. Edit Student Marks\n" +
                "4. Add Courses for Student\n" +
                "5. View Profile\n" +
                ANSIColors.RED_BOLD + "Enter 'q' to logout.\n" + ANSIColors.RESET;
    }

    // Method to handle teacher menu options
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
                    editStudentMarks(scanner);
                    break;
                case "4":
                    addCoursesForStudent(scanner);
                    break;
                case "5":
                    System.out.println(viewProfile());
                    break;
                case "q":
                    running = false;
                    System.out.println(ANSIColors.RED_BOLD + "Logging out..." + ANSIColors.RESET);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to view courses
    private void viewCourses() {
        System.out.println("\n----- Your Courses -----");
        if (courses.isEmpty()) {
            System.out.println("No courses assigned.");
        } else {
            for (String course : courses) {
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

    // Method to edit student marks
    private void editStudentMarks(Scanner scanner) {
        System.out.println("\n----- Edit Student Marks -----");
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        // Check if the student exists
        UserRepository userRepository = new UserRepository();
        if (!userRepository.isUserExist(studentId)) {
            System.out.println("Student not found.");
            return;
        }

        // Display the teacher's courses
        System.out.println("Your courses: " + courses);
        System.out.print("Enter course to edit marks (must be one of your courses): ");
        String course = scanner.nextLine();

        // Check if the course is one of the teacher's courses
        if (!courses.contains(course)) {
            System.out.println("You are not assigned to this course.");
            return;
        }

        // Get the current marks for the student
        List<String> marks = marksRepo.getMarksForStudent(studentId);
        boolean courseFound = false;
        for (String mark : marks) {
            if (mark.startsWith(course + ":")) {
                courseFound = true;
                break;
            }
        }

        if (!courseFound) {
            System.out.println("No marks found for this course.");
        }

        System.out.print("Enter new marks: ");
        String newMarks = scanner.nextLine();

        // Update the marks in the file
        marksRepo.addOrUpdateMarks(studentId, course, newMarks);
        System.out.println("Marks updated for student " + studentId + " in course " + course + ": " + newMarks);
    }

    // Method to add courses for a student
    private void addCoursesForStudent(Scanner scanner) {
        System.out.println("\n----- Add Courses for Student -----");
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        // Check if the student exists
        UserRepository userRepository = new UserRepository();
        if (!userRepository.isUserExist(studentId)) {
            System.out.println("Student not found.");
            return;
        }

        // Display the teacher's courses
        System.out.println("Your courses: " + courses);
        System.out.print("Enter course to add (must be one of your courses): ");
        String course = scanner.nextLine();

        // Check if the course is one of the teacher's courses
        if (!courses.contains(course)) {
            System.out.println("You are not assigned to this course.");
            return;
        }

        // Add the course to the student
        List<String> studentCourses = userRepository.getStudentCourses(studentId);
        if (studentCourses == null) {
            System.out.println("Error: Student is not a valid student.");
            return;
        }

        if (studentCourses.contains(course)) {
            System.out.println("Student is already enrolled in this course.");
            return;
        }

        studentCourses.add(course);
        userRepository.updateStudentCourses(studentId, studentCourses);
        System.out.println("Course added for student: " + studentId);
    }

    // Override viewProfile to include courses and GPA (None for teachers)
    @Override
    public String viewProfile() {
        return super.viewProfile() +
                "Courses: " + courses + "\n" +
                "GPA: None\n"; // Teachers don't have GPA
    }
}