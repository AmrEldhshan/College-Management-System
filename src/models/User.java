package models;

public abstract class User {
    private String id; // Changed from username to id
    private String password;
    private String role;
    private String name; // Added name field

    // Constructor
    public User(String id, String password, String name, String role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    // Abstract method to get menu based on user role
    public abstract String getMenu();

    // Method to view profile
    public String viewProfile() {
        return "\n----- Profile -----\n" +
                "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Role: " + role + "\n";
    }

    // Authenticate method to check if entered password matches the user's password
    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}