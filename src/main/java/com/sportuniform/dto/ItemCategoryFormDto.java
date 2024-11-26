package com.sportuniform.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class ItemCategoryFormDto {

    private String name;
    private String parentCategory;
}
