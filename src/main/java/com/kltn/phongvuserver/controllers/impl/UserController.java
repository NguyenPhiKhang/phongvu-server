package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IUserController;
import com.kltn.phongvuserver.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@Transactional(rollbackFor = Throwable.class)
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    @Override
    public String createEmailAuto() {
        userService.autoCreateEmail();
        return "done";
    }
}
