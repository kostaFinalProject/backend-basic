package com.sportuniform.service;

import com.sportuniform.domain.Item;
import com.sportuniform.domain.ItemCategory;
import com.sportuniform.domain.ItemSize;
import com.sportuniform.domain.Size;
import com.sportuniform.dto.ItemDto;
import com.sportuniform.repository.ItemCategoryRepository;
import com.sportuniform.repository.ItemRepository;
import com.sportuniform.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final SizeRepository sizeRepository;

    @Transactional
    public void saveItem(ItemDto dto) {

        ItemCategory itemCategory = itemCategoryRepository.findByName(dto.getItemCategory())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 카테고리가 없습니다."));

        List<ItemSize> itemSizes = dto.getItemSizes().stream()
                .map(itemSizeDto -> {
                    Size size = sizeRepository.findBySize(itemSizeDto.getSize())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사이즈입니다."));

                    return ItemSize.createItemSize(size, itemSizeDto.getStockQuantity());
                })
                .toList();

        Item item = Item.createItem(itemCategory, dto.getManufacturer(), dto.getName(), dto.getPrice(), itemSizes);
        itemRepository.save(item);
    }
}
