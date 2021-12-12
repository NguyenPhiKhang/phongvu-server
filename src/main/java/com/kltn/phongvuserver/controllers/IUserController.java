package com.kltn.phongvuserver.controllers;

import com.kltn.phongvuserver.models.dto.InputUserUpdateDTO;
import com.kltn.phongvuserver.models.dto.UserDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface IUserController {
    @PostMapping("/users/auto-created-email")
    String createEmailAuto();

    @RequestMapping(value = "/user/update", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    UserDTO updateUser(@ModelAttribute("input_user") InputUserUpdateDTO input_user) throws ParseException;

    @GetMapping("/user/{userId}/get-detail")
    UserDTO getDetailUserById(@PathVariable("userId") int userId);
}
