package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.DataImage;
import com.kltn.phongvuserver.models.User;
import com.kltn.phongvuserver.models.dto.InputUserUpdateDTO;
import com.kltn.phongvuserver.repositories.UserRepository;
import com.kltn.phongvuserver.services.IImageDataService;
import com.kltn.phongvuserver.services.IUserService;
import com.kltn.phongvuserver.utils.ImageUtil;
import com.kltn.phongvuserver.utils.StringUtil;
import net.andreinc.mockneat.MockNeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Random;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IImageDataService imageDataService;

    @Override
    public User getUserById(int userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public List<Integer> getListIdUser() {
        return userRepository.getAllIdUser();
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

    @Override
    public User updateUser(InputUserUpdateDTO input_user) throws ParseException {
        User user = userRepository.findUserById(input_user.getId());
        user.setName(input_user.getName());
//        user.setEmail(input_user.getEmail());
        user.setTimeUpdated(new Timestamp(System.currentTimeMillis()));
        user.setPhoneNumber(input_user.getPhoneNumber());
        if (!(input_user.getBirthday() == null || input_user.getBirthday().isEmpty()))
            user.setBirthday(StringUtil.convertStringToDate(input_user.getBirthday(), "yyyy-MM-dd"));
        user.setSex(input_user.getSex());

        String idImage = "";

        if (input_user.getImage() != null) {
            try{
                if (user.getImageAvatar() != null){
                    idImage = user.getImageAvatar().getId();
                }
                user.setImageAvatar(saveImage(input_user.getImage()));
            }catch (RuntimeException ex){
                idImage = "";
            }
        }

        User userUpdate = userRepository.save(user);

        if (!idImage.equals(""))
            imageDataService.removeImageById(idImage);

        return userUpdate;
    }

    private DataImage saveImage(MultipartFile image) {
        try {
            String fileName = ImageUtil.fileName(imageDataService, image);
            MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, image.getContentType(), image.getInputStream());
            return imageDataService.storeImageData(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
