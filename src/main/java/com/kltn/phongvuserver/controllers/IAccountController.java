package com.kltn.phongvuserver.controllers;

import com.kltn.phongvuserver.messages.AccountMessage;
import com.kltn.phongvuserver.messages.ResponseMessage;
import com.kltn.phongvuserver.models.Account;
import com.kltn.phongvuserver.models.dto.AccountDTO;
import com.kltn.phongvuserver.models.dto.AccountRegisterDTO;
import com.kltn.phongvuserver.models.dto.InputChangePasswordDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface IAccountController{
    @PostMapping("/account/login")
    ResponseEntity<AccountMessage> login(@RequestBody Account account);

    @PostMapping("/account/register")
    ResponseEntity<AccountMessage> register(@RequestBody AccountRegisterDTO account, @RequestParam(value = "role", defaultValue = "2") int role);

    @PutMapping("/account/{userId}/change-password")
    ResponseEntity<ResponseMessage<Integer>> changePassword(@PathVariable("userId") int userId, @RequestBody InputChangePasswordDTO input);
}
