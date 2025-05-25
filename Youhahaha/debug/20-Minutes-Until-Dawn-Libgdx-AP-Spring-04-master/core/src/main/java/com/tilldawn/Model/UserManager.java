package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UserManager {
    private static UserManager instance;
    private User currentUser;
    private final String savePath = "user_data.json";
    private final String allUsersPath = "all_users.json";
    private final String[] avatarPaths = {
        "avatars/avatar1.png",
        "avatars/avatar2.png",
        "avatars/avatar3.png",
        "avatars/avatar4.png"
    };

    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void logout() {
        currentUser = null;
    }

    public void saveUser() {
        if (currentUser != null && !isGuest(currentUser)) {
            Json json = new Json();


            FileHandle file = Gdx.files.local(savePath);
            file.writeString(json.toJson(currentUser), false);


            List<User> users = loadAllUsers();
            users.removeIf(u -> u.getUsername().equalsIgnoreCase(currentUser.getUsername()));
            users.add(currentUser);
            saveAllUsers(users);
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        saveUser();
    }

    public User findUser(String username) {
        for (User user : loadAllUsers()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    private void saveAllUsers(List<User> users) {
        Json json = new Json();
        FileHandle file = Gdx.files.local(allUsersPath);
        file.writeString(json.toJson(users), false);
    }

    private List<User> loadAllUsers() {
        FileHandle file = Gdx.files.local(allUsersPath);
        if (!file.exists()) return new ArrayList<>();

        Json json = new Json();
        List<User> users = json.fromJson(ArrayList.class, User.class, file.readString());

        if (users == null) {
            return new ArrayList<>();
        }
        return users;
    }


    public boolean isUsernameTaken(String username) {
        return findUser(username) != null;
    }

    public String getRandomAvatarPath() {
        int idx = (int) (Math.random() * avatarPaths.length);
        return avatarPaths[idx];
    }

    public User registerUser(String username, String password, String securityQuestion, String securityAnswer, String avatarPath) {
        User newUser = new User(username, avatarPath, password, securityQuestion, securityAnswer);
        List<User> users = loadAllUsers();
        users.add(newUser);
        saveAllUsers(users);
        setCurrentUser(newUser);
        return newUser;
    }

    public void createGuestUser() {
        User guest = new User("Guest" + System.currentTimeMillis(), getRandomAvatarPath(), "no password", "", "");
        setCurrentUser(guest);
    }

    public User getUserByUsername(String username) {
        return findUser(username);
    }

    public void updateUser(User updatedUser) {
        List<User> users = loadAllUsers();
        boolean updated = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(updatedUser.getUsername())) {
                users.set(i, updatedUser);
                updated = true;
                break;
            }
        }
        if (!updated) {
            users.add(updatedUser);
        }
        saveAllUsers(users);
        if (currentUser != null && currentUser.getUsername().equalsIgnoreCase(updatedUser.getUsername())) {
            currentUser = updatedUser;
        }
    }

    public void removeUser(User user) {
        List<User> users = loadAllUsers();
        users.removeIf(u -> u.getUsername().equalsIgnoreCase(user.getUsername()));
        saveAllUsers(users);
        if (currentUser != null && currentUser.getUsername().equalsIgnoreCase(user.getUsername())) {
            currentUser = null;
        }
    }

    public boolean hasSave() {
        FileHandle file = Gdx.files.local(savePath);
        return file.exists();
    }

    public List<User> getTopUsers() {
        List<User> users = loadAllUsers();
        users.sort(Comparator.comparingInt(User::getScore).reversed());
        return users;
    }

    private boolean isGuest(User user) {
        return user.getUsername().toLowerCase().startsWith("guest");
    }
}
