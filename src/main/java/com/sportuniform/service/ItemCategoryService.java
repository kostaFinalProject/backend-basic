package com.sportuniform.service;

import com.sportuniform.domain.ItemCategory;
import com.sportuniform.dto.ItemCategoryFormDto;
import com.sportuniform.repository.ItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    @Transactional
    public void createItemCategory(ItemCategoryFormDto dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("카테고리의 이름이 지정되지 않았습니다.");
        }

        ItemCategory itemCategory;

        if (dto.getParentCategory() != null && !dto.getParentCategory().isBlank()) {
            ItemCategory parentCategory = itemCategoryRepository.findByName(dto.getParentCategory())
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 상위 카테고리가 없습니다."));

            itemCategory = ItemCategory.createItemCategory(dto.getName(), parentCategory);
            itemCategoryRepository.save(itemCategory);
        } else {
            itemCategory = ItemCategory.createItemCategory(dto.getName(), null);
            itemCategoryRepository.save(itemCategory);
        }
    }
}
