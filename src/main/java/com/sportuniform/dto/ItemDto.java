package com.sportuniform.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemDto {
    private String itemCategory;
    private String manufacturer;
    private String name;
    private int price;
    private List<ItemSizeDto> itemSizes = new ArrayList<>();
}
