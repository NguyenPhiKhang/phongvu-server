package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IAccountController;
import com.kltn.phongvuserver.mappers.impl.AccountDTOMapper;
import com.kltn.phongvuserver.messages.ResponseMessage;
import com.kltn.phongvuserver.models.Account;
import com.kltn.phongvuserver.models.User;
import com.kltn.phongvuserver.models.dto.AccountDTO;
import com.kltn.phongvuserver.models.dto.AccountRegisterDTO;
import com.kltn.phongvuserver.models.dto.InputChangePasswordDTO;
import com.kltn.phongvuserver.services.IAccountService;
import com.kltn.phongvuserver.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Transactional(rollbackFor = Throwable.class)
public class AccountController implements IAccountController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IUserService userService;

    @Autowired
    private AccountDTOMapper accountDTOMapper;

    @Override
    public ResponseEntity<AccountDTO> login(Account account) {
        AccountDTO accountLogin = accountDTOMapper.mapRow(accountService.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword()));
        return ResponseEntity.ok().body(accountLogin);
    }

    @Override
    public ResponseEntity<ResponseMessage<AccountDTO>> register(AccountRegisterDTO account, int role) {
        if(userService.checkExistEmailOrUsername(account.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage<>("Email đã được đăng ký!", null));
        }

        User user = userService.registerUser(account.getName(), account.getEmail());

        return ResponseEntity.ok().body(new ResponseMessage<>("Đăng ký thành công", accountDTOMapper.mapRow(accountService.registerAccount(account.getPassword(), user, role))));
    }

    @Override
    public ResponseEntity<ResponseMessage<Integer>> changePassword(int userId, InputChangePasswordDTO input) {
        if(accountService.checkPasswordCorrect(userId, input.getOldPassword())){
            accountService.updatePassword(userId, input.getNewPassword());
            return ResponseEntity.ok().body(new ResponseMessage<>("Thay đổi mật khẩu thành công!", 1));
        }else{
            return ResponseEntity.ok().body(new ResponseMessage<>("Mật khẩu không đúng!", -1));
        }
    }
}
