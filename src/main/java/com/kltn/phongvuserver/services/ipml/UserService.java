package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.User;
import com.kltn.phongvuserver.repositories.UserRepository;
import com.kltn.phongvuserver.services.IUserService;
import net.andreinc.mockneat.MockNeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public List<Integer> getListIdUser() {
        return null;
    }

    @Override
    public List<User> getListUserFilter(String search, int active, int page, int pageSize) {
        return null;
    }

    @Override
    public int countListUserFilter(String search, int active) {
        return 0;
    }

    @Override
    public void autoCreateEmail() {
        List<User> users = userRepository.getListUserFilter("", -1, 0, 1000);

        MockNeat mock = MockNeat.threadLocal();

        users.forEach(user -> {
            String corpEmail;
            do {
                corpEmail = mock.emails().domain("gmail.com").val();
            } while (checkExistEmail(corpEmail));

            user.setEmail(corpEmail);
        });

        userRepository.saveAll(users);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return false;
    }

    @Override
    public User registerUser(String name, String email) {
        return null;
    }
}
