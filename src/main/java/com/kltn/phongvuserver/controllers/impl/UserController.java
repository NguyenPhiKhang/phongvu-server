package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IUserController;
import com.kltn.phongvuserver.mappers.impl.UserDTOMapper;
import com.kltn.phongvuserver.models.dto.InputUserUpdateDTO;
import com.kltn.phongvuserver.models.dto.UserDTO;
import com.kltn.phongvuserver.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/")
@Transactional(rollbackFor = Throwable.class)
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserDTOMapper userDTOMapper;

    @Override
    public String createEmailAuto() {
        userService.autoCreateEmail();
        return "done";
    }

    @Override
    public UserDTO updateUser(InputUserUpdateDTO input_user) throws ParseException {
        return userDTOMapper.mapRow(userService.updateUser(input_user));
    }

    @Override
    public UserDTO getDetailUserById(int userId) {
        return userDTOMapper.mapRow(userService.getUserById(userId));
    }
}
