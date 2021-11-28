package com.kltn.phongvuserver.models.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegisterDTO {
    private String email;
    private String password;
    private String name;
}
