package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.User;

import java.util.List;

public interface IUserService {
    User getUserById(int userId);
    List<Integer> getListIdUser();
    List<User> getListUserFilter(String search, int active, int page, int pageSize);
    int countListUserFilter(String search, int active);
    void autoCreateEmail();
    boolean checkExistEmailOrUsername(String email);
    User registerUser(String name, String email);
}
