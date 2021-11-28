package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.User;
import com.kltn.phongvuserver.repositories.UserRepository;
import com.kltn.phongvuserver.services.IUserService;
import net.andreinc.mockneat.MockNeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public User getUserById(int userId) {
        return userRepository.findUserById(userId);
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
            } while (checkExistEmailOrUsername(corpEmail));

            user.setEmail(corpEmail);
        });

        userRepository.saveAll(users);
    }

    @Override
    public boolean checkExistEmailOrUsername(String email) {
        return userRepository.existsUserByEmailOrUsername(email);
    }

    @Override
    public User registerUser(String name, String email) {
        Random rd = new Random();

        int idUser;
        do {
            idUser = 100000000 + rd.nextInt(2000000000);
        } while (userRepository.existsById(idUser));

        User user = new User();
        user.setId(idUser);
        user.setName(name);
        user.setEmail(email);
        user.setActive(true);
        user.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        user.setTimeUpdated(new Timestamp(System.currentTimeMillis()));

        return userRepository.save(user);
    }
}
