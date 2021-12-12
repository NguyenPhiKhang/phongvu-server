package com.kltn.phongvuserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InputReviewProductDTO {
    private int orderItem;
    private String comment;
    private int star;
    private boolean incognito;
    private List<MultipartFile> listFiles;
}
