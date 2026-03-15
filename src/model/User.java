package model;

// Represents a user in the system
public class User {

    private String username;
    private String email;
    private String role; // admin or user

    public User(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public String toString() {
        return username + " <" + email + "> [" + role + "]";
    }
}
