package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Account;
import com.kltn.phongvuserver.models.User;

public interface IAccountService {
    Account getAccountByUsernameAndPassword(String username, String password);
    Account registerAccount(String password, User user, int role);
    boolean checkPasswordCorrect(int userId, String password);
    void updatePassword(int userId, String newPassword);
}
