package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.User;
import com.kltn.phongvuserver.models.dto.UserDTO;
import com.kltn.phongvuserver.utils.EnvUtil;
import com.kltn.phongvuserver.utils.ImageUtil;
import com.kltn.phongvuserver.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

@Component
public class UserDTOMapper implements RowMapper<UserDTO, User> {
    @Autowired
    private EnvUtil envUtil;

    @Override
    public UserDTO mapRow(User user) {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setActive(user.isActive());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setBirthday(user.getBirthday() != null ? StringUtil.convertDateToString(user.getBirthday()) : null);
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setSex(user.getSex());
            userDTO.setTimeCreated(StringUtil.convertTimestampToString(user.getTimeCreated()));
            userDTO.setTimeUpdated(StringUtil.convertTimestampToString(user.getTimeUpdated()));

            try {
                userDTO.setImage_url(ImageUtil.getUrlImage(user.getImageAvatar(), envUtil.getServerUrlPrefi()));
            } catch (UnknownHostException | NullPointerException e) {
                userDTO.setImage_url(ImageUtil.DEFAULT_USER_IMAGE_URL);
            }

            user.getAddresses().forEach(a->{
                if(a.isDefaultIs()){
                    userDTO.setAddress(new AddressDTOMapper().mapRow(a));
                }
            });

            return userDTO;
        } catch (Exception ex) {
            return null;
        }
    }
}
