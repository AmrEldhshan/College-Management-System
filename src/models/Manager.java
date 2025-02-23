package models;

import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import UI.ANSIColors;

public class Manager extends User {

    // Constructor
    public Manager(String id, String password, String name) {
        super(id, password, name, "admin");
    }

    // Implement the getMenu() method for admin role
    @Override
    public String getMenu() {
        return ANSIColors.CYAN_BOLD + "\n----- Admin Menu -----\n" + ANSIColors.RESET +
                "1. Add New User\n" +
                "2. View All Users\n" +
                "3. Delete User\n" +
                "4. View Profile\n" +
                ANSIColors.RED_BOLD + "Enter 'q' to logout.\n" + ANSIColors.RESET;
    }

    // Method to handle admin menu options
    public void handleMenu(UserRepository userRepository, Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println(getMenu());
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addNewUser(userRepository);
                    break;
                case "2":
                    viewAllUsers(userRepository);
                    break;
                case "3":
                    deleteUser(userRepository, scanner);
                    break;
                case "4":
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

    // Method to add new users with roles (admin, student, teacher)
    public boolean addNewUser(UserRepository userRepository) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter new user ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter new password: ");
        String password = scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter role (admin/student/teacher): ");
        String role = scanner.nextLine().toLowerCase();

        User newUser;
        switch (role) {
            case "student":
                System.out.print("Enter GPA: ");
                String gpa = scanner.nextLine();
                System.out.print("Enter courses (comma-separated): ");
                String[] courses = scanner.nextLine().split(",");
                List<String> courseList = new ArrayList<>();
                for (String course : courses) {
                    courseList.add(course.trim());
                }
                newUser = new Student(id, password, name, gpa, courseList);
                break;
            case "teacher":
                System.out.print("Enter courses (comma-separated): ");
                String[] teacherCourses = scanner.nextLine().split(",");
                List<String> teacherCourseList = new ArrayList<>();
                for (String course : teacherCourses) {
                    teacherCourseList.add(course.trim());
                }
                newUser = new Teacher(id, password, name, teacherCourseList);
                break;
            case "admin":
                newUser = new Manager(id, password, name);
                break;
            default:
                System.out.println("Invalid role.");
                return false;
        }

        return userRepository.addUser(newUser);
    }

    // Method to view all users
    private void viewAllUsers(UserRepository userRepository) {
        System.out.println("\n----- All Users -----");
        List<String> users = userRepository.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (String user : users) {
                System.out.println(user);
            }
        }
    }

    // Method to delete a user by ID and password
    public boolean deleteUser(UserRepository userRepository, Scanner scanner) {
        System.out.print("Enter user ID to delete: ");
        String id = scanner.nextLine();

        System.out.print("Enter password to confirm deletion: ");
        String password = scanner.nextLine();

        return userRepository.deleteUser(id, password);
    }

    // Override viewProfile to include GPA (None for managers)
    @Override
    public String viewProfile() {
        return super.viewProfile() +
                "GPA: None\n"; // Managers don't have GPA
    }
}