package com.projeto1.projeto1.listeners;

import com.projeto1.projeto1.models.User;

import java.util.ArrayList;

/**
 * Created by rafaelle on 06/07/17.
 */

public interface UserListener {
    void OnGetAllUsersFinished(boolean ready, ArrayList<User> users);
    void OnGetUserFinished(boolean find, User user);
    void OnPostUserFinished(boolean finished);
    void OnAddFavoriteSaleFinished(boolean finished);
    void OnRemoveFavoriteSaleFinished(boolean finished);
    void OnPutUserFinished(boolean finished);

}
