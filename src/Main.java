import models.User;
import models.Student;
import models.Teacher;
import models.Manager;
import repository.UserRepository;
import UI.ANSIColors;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println(ANSIColors.getFCAIArt());
            System.out.println(ANSIColors.GREEN_BOLD + "Welcome to the College Management System!" + ANSIColors.RESET);

            // Login process
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            // Authenticate the user
            UserRepository userRepository = new UserRepository();
            User currentUser = userRepository.authenticateUser(username, password);

            if (currentUser != null) {
                System.out.println(ANSIColors.GREEN_BOLD + "Login successful!" + ANSIColors.RESET);
                System.out.println("Welcome, " + currentUser.getId() + "!");

                // Handle menu based on user role
                if (currentUser instanceof Manager) {
                    ((Manager) currentUser).handleMenu(userRepository, scanner);
                } else if (currentUser instanceof Student) {
                    ((Student) currentUser).handleMenu(scanner);
                } else if (currentUser instanceof Teacher) {
                    ((Teacher) currentUser).handleMenu(scanner);
                } else {
                    System.out.println(ANSIColors.RED_BOLD + "Unknown user role. Cannot display menu." + ANSIColors.RESET);
                }
            } else {
                System.out.println(ANSIColors.RED_BOLD + "Login failed. Invalid username or password." + ANSIColors.RESET);
            }


            // Ask if the user wants to exit the system
            System.out.print("Do you want to exit the system? (yes/no): ");
            String exitChoice = scanner.nextLine();
            if (exitChoice.equalsIgnoreCase("yes")) {
                running = false;
                System.out.println(ANSIColors.RED_BOLD + "Exiting the system. Goodbye!" + ANSIColors.RESET);
            }
        }

        scanner.close();
    }
}