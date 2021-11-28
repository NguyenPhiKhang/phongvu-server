package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.Account;
import com.kltn.phongvuserver.models.dto.AccountDTO;
import com.kltn.phongvuserver.utils.EnvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDTOMapper implements RowMapper<AccountDTO, Account> {
    @Autowired
    private UserDTOMapper userDTOMapper;

    @Override
    public AccountDTO mapRow(Account account) {
        try {
            return new AccountDTO(account.getUsername(), account.getPassword(), account.isActive(), account.getPermission(), userDTOMapper.mapRow(account.getUser()));
        } catch (Exception ex) {
            return null;
        }
    }
}
