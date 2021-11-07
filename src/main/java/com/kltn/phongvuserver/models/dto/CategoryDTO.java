package com.kltn.phongvuserver.models.dto;

import com.kltn.phongvuserver.models.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private int id;
    private String name;
    private String icon;
    private String path;
    private List<CategoryDTO> subCategories;
}
