package com.kltn.phongvuserver.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class InputUserUpdateDTO {
    private int id;
    private String name;
    private String birthday;
    private String sex;
    private String phoneNumber;
    private MultipartFile image;
}
