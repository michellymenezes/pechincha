package com.projeto1.projeto1.listeners;

import com.projeto1.projeto1.models.User;

import java.util.ArrayList;

/**
 * Created by rafaelle on 06/07/17.
 */

public interface LoginListener {
    void OnPostLoginFinished(boolean finished);
    void OnGetAllUsersFinished(boolean ready, ArrayList<User> users);
    void OnGetUserFinished(boolean ready, User user);
}
