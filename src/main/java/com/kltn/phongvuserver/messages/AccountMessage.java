package com.kltn.phongvuserver.messages;

import com.kltn.phongvuserver.models.User;
import com.kltn.phongvuserver.models.dto.AccountDTO;

public class AccountMessage extends AbstractMessage<AccountDTO> {
    public AccountMessage(int statusCode, String statusText, String message, AccountDTO value){
        super(statusCode, statusText, message, value);
    }
}
