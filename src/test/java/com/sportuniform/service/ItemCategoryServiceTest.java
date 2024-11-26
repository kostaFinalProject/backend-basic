package com.sportuniform.service;

import com.sportuniform.dto.ItemCategoryFormDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ItemCategoryServiceTest {

    @Autowired
    ItemCategoryService itemCategoryService;

    @Test
    void ItemCategoryService() {

        ItemCategoryFormDto dto1 = new ItemCategoryFormDto();
        dto1.setName("EPL");
        itemCategoryService.createItemCategory(dto1);

        ItemCategoryFormDto dto2 = new ItemCategoryFormDto();
        dto2.setName("Manchester United");
        dto2.setParentCategory("EPL");
        itemCategoryService.createItemCategory(dto2);

        ItemCategoryFormDto dto3 = new ItemCategoryFormDto();
        assertThatThrownBy(() -> itemCategoryService.createItemCategory(dto3))
                .isInstanceOf(IllegalArgumentException.class);
    }

}