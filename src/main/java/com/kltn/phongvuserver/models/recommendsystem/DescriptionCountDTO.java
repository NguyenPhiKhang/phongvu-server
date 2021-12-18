package com.kltn.phongvuserver.models.recommendsystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionCountDTO {
    private String descOrName;
    private int count;
}