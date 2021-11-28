package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.Account;
import com.kltn.phongvuserver.models.User;
import com.kltn.phongvuserver.repositories.AccountPermissionRepository;
import com.kltn.phongvuserver.repositories.AccountRepository;
import com.kltn.phongvuserver.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AccountService implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountPermissionRepository accountPermissionRepository;

    @Override
    public Account getAccountByUsernameAndPassword(String username, String password) {
        return accountRepository.findByUsernameOrEmailAndPassword(username, password);
    }

    @Override
    public Account registerAccount(String password, User user, int role) {
        Account account = new Account();
        account.setUsername(String.valueOf(user.getId()));
        account.setPassword(password);
        account.setActive(true);
        account.setUser(user);
        account.setPermission(accountPermissionRepository.getById(role));

        return accountRepository.save(account);
    }

    @Override
    public boolean checkPasswordCorrect(int userId, String password) {
        return accountRepository.checkPasswordCorrect(userId, password)>0;
    }

    @Override
    public void updatePassword(int userId, String newPassword) {
        accountRepository.updatePassword(newPassword, userId);
    }
}

