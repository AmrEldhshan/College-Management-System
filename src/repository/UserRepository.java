package repository;

import models.Student;
import models.Teacher;
import models.Manager;
import models.User;
import java.util.List;
import java.util.ArrayList;

public class UserRepository {
    private FileHandler fileHandle;

    public UserRepository() {
        fileHandle = new FileHandler();
        fileHandle.initializeFile("users.txt");
    }

    // Get all the users from the file
    public List<String> getAllUsers() {
        return fileHandle.readLines("users.txt");
    }

    // Check if the user input exists in the file
    public boolean isUserExist(String userId) {
        List<String> usersLines = getAllUsers();
        for (String userLine : usersLines) {
            String[] userFields = userLine.split(","); // {id, password, name, role, gpa, courses...}
            if (userFields[0].equals(userId)) return true;
        }
        return false;
    }

    // Add a new user (used with teacher)
    public boolean addUser(User user) {
        if (isUserExist(user.getId())) {
            return false; // User already exists
        }

        // If user doesn't exist, write the user to the file
        StringBuilder userData = new StringBuilder();
        userData.append(user.getId()).append(",")
                .append(user.getPassword()).append(",")
                .append(user.getName()).append(",")
                .append(user.getRole());

        // Add GPA and courses if the user is a student or teacher
        if (user instanceof Student) {
            Student student = (Student) user;
            userData.append(",").append(student.getGpa());
            for (String course : student.getCourses()) {
                userData.append(",").append(course);
            }
        } else if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;
            userData.append(",").append("N/A"); // Teachers don't have GPA
            for (String course : teacher.getCourses()) {
                userData.append(",").append(course);
            }
        } else if (user instanceof Manager) {
            userData.append(",").append("N/A"); // Managers don't have GPA or courses
        }

        userData.append("\n");
        fileHandle.appendToFile("users.txt", userData.toString());
        return true; // User added successfully
    }

    // Method to authenticate a user and return their instance based on role
    public User authenticateUser(String id, String password) {
        List<String> userLines = fileHandle.readLines("users.txt");
        for (String line : userLines) {
            String[] userFields = line.split(",");
            String storedId = userFields[0];
            String storedPassword = userFields[1];
            String role = userFields[3]; // Id, pass , name, role , gpa, courses->...

            if (storedId.equals(id) && storedPassword.equals(password)) {
                // Determine the role and return the corresponding user object
                if ("admin".equals(role)) {
                    return new Manager(storedId, storedPassword, userFields[2]);
                } else if ("student".equals(role)) {
                    String gpa = userFields[4];
                    List<String> courses = new ArrayList<>();
                    for (int i = 5; i < userFields.length; i++) {
                        courses.add(userFields[i]);
                    }
                    return new Student(storedId, storedPassword, userFields[2], gpa, courses);
                } else if ("teacher".equals(role)) {
                    List<String> courses = new ArrayList<>();
                    for (int i = 5; i < userFields.length; i++) {
                        courses.add(userFields[i]);
                    }
                    return new Teacher(storedId, storedPassword, userFields[2], courses);
                }
            }
        }

        return null; // Return null if user is not found or authentication fails
    }

    public boolean deleteUser(String id, String password) {
        List<String> usersLines = getAllUsers();
        boolean userFound = false;

        // Create a new list without the user to delete
        List<String> updatedUsers = new ArrayList<>();
        for (String userLine : usersLines) {
            String[] userFields = userLine.split(",");
            if (userFields[0].equals(id) && userFields[1].equals(password)) {
                userFound = true; // User found and will be removed
            } else {
                updatedUsers.add(userLine); // Keep other users
            }
        }

        if (!userFound) {
            System.out.println("User not found or password incorrect.");
            return false;
        }

        // Write the updated list back to the file
        StringBuilder newContent = new StringBuilder();
        for (String user : updatedUsers) {
            newContent.append(user).append("\n");
        }
        fileHandle.writeFile("users.txt", newContent.toString());

        System.out.println("User deleted successfully.");
        return true;
    }

    // Method to get a student's courses
    public List<String> getStudentCourses(String id) { // start with index 3 until end
        List<String> usersLines = getAllUsers();
        for (String userLine : usersLines) {
            String[] userFields = userLine.split(",");
            if (userFields[0].equals(id) && "student".equals(userFields[3])) {
                List<String> courses = new ArrayList<>();
                for (int i = 5; i < userFields.length; i++) {
                    courses.add(userFields[i]);
                }
                return courses;
            }
        }
        return null;
    }

    // Method to update a student's courses
    public void updateStudentCourses(String id, List<String> courses) {
        List<String> usersLines = getAllUsers();
        List<String> updatedUsers = new ArrayList<>();

        for (String userLine : usersLines) {
            String[] userFields = userLine.split(",");
            if (userFields[0].equals(id) && "student".equals(userFields[3])) {
                // Update the student's courses
                StringBuilder updatedUserLine = new StringBuilder();
                updatedUserLine.append(userFields[0]).append(",")
                        .append(userFields[1]).append(",")
                        .append(userFields[2]).append(",")
                        .append(userFields[3]).append(",")
                        .append(userFields[4]); // GPA
                for (String course : courses) {
                    updatedUserLine.append(",").append(course);
                }
                updatedUsers.add(updatedUserLine.toString());
            } else {
                updatedUsers.add(userLine);
            }
        }

        // Write the updated list back to the file
        StringBuilder newContent = new StringBuilder();
        for (String user : updatedUsers) {
            newContent.append(user).append("\n");
        }
        fileHandle.writeFile("users.txt", newContent.toString());
    }
}