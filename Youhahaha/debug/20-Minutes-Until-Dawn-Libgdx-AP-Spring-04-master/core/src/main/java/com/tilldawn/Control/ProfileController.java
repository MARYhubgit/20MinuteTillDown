package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.tilldawn.Model.User;
import com.tilldawn.Model.UserManager;

public class ProfileController {

    private final UserManager userDatabase;
    private User currentUser;

    public ProfileController(User currentUser, UserManager userDatabase) {
        this.currentUser = currentUser;
        this.userDatabase = userDatabase;
    }

    public boolean changeUsername(String newUsername) {
        User user = userDatabase.getUserByUsername(newUsername);
        if (user != null) {
            return false;
        }
        currentUser.setUsername(newUsername);
        userDatabase.saveUser();
        return true;
    }

    public boolean changePassword(String newPassword,String userName) {
        if (!RegisterController.isPasswordStrong(newPassword)) {
            return false;
        }
        currentUser.setPassword(newPassword);
        userDatabase.updateUser(currentUser);
        userDatabase.saveUser();
        return true;
    }


    public void deleteAccount() {
        userDatabase.removeUser(currentUser);
        currentUser = null;
    }


    public boolean changeAvatarFromInternal(String internalPath) {
        FileHandle handle = Gdx.files.internal(internalPath);
        if (!handle.exists()) return false;

        currentUser.setAvatarPath(internalPath);
        userDatabase.saveUser();
        return true;
    }

    public boolean changeAvatarFromAbsolute(String absolutePath) {
        FileHandle handle = Gdx.files.absolute(absolutePath);
        if (!handle.exists()) return false;

        currentUser.setAvatarPath(absolutePath);
        userDatabase.saveUser();
        return true;
    }

    public void changeAvatarToInternal(String internalPath,UserManager userManager) {
        currentUser.setAvatarPath("avatars/" + internalPath);
        userManager.saveUser();
    }

}
