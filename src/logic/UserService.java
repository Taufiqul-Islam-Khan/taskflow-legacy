package logic;

import model.User;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> users;

    public UserService() {
        users = new ArrayList<>();
        seedUsers();
    }

    private void seedUsers() {
        users.add(new User("alice", "alice@company.com", "admin"));
        users.add(new User("bob",   "bob@company.com",   "user"));
        users.add(new User("carol", "carol@company.com", "user"));
    }

    public User findUser(String username) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) return u;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public boolean addUser(String username, String email, String role) {
        if (findUser(username) != null) {
            Logger.warn("User already exists: " + username);
            return false;
        }
        users.add(new User(username, email, role));
        return true;
    }
}
