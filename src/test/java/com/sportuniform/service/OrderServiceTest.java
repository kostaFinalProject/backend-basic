package com.sportuniform.service;

import com.sportuniform.domain.Item;
import com.sportuniform.domain.Member;
import com.sportuniform.dto.*;
import com.sportuniform.repository.ItemRepository;
import com.sportuniform.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemCategoryService itemCategoryService;

    @Test
    @Rollback(value = false)
    void OrderTest() {
        ItemCategoryFormDto dto1 = new ItemCategoryFormDto();
        dto1.setName("EPL");
        itemCategoryService.createItemCategory(dto1);

        ItemCategoryFormDto dto2 = new ItemCategoryFormDto();
        dto2.setName("Manchester United");
        dto2.setParentCategory("EPL");
        itemCategoryService.createItemCategory(dto2);

        ItemCategoryFormDto dto3 = new ItemCategoryFormDto();
        dto3.setName("Manchester City");
        dto3.setParentCategory("EPL");
        itemCategoryService.createItemCategory(dto3);

        ItemSizeDto sizeDto1 = new ItemSizeDto();
        sizeDto1.setSize("XS");
        sizeDto1.setStockQuantity(200);

        ItemSizeDto sizeDto2 = new ItemSizeDto();
        sizeDto2.setSize("S");
        sizeDto2.setStockQuantity(200);

        ItemSizeDto sizeDto3 = new ItemSizeDto();
        sizeDto3.setSize("M");
        sizeDto3.setStockQuantity(200);

        ItemSizeDto sizeDto4 = new ItemSizeDto();
        sizeDto4.setSize("L");
        sizeDto4.setStockQuantity(200);

        ItemSizeDto sizeDto5 = new ItemSizeDto();
        sizeDto5.setSize("XL");
        sizeDto5.setStockQuantity(200);

        ItemSizeDto sizeDto6 = new ItemSizeDto();
        sizeDto6.setSize("2XL");
        sizeDto6.setStockQuantity(200);

        ItemDto itemDto1 = new ItemDto();
        itemDto1.setItemCategory("Manchester United");
        itemDto1.setManufacturer("Adidas");
        itemDto1.setName("24-25 Home Replica");
        itemDto1.setPrice(150000);
        itemDto1.getItemSizes().add(sizeDto1);
        itemDto1.getItemSizes().add(sizeDto2);
        itemDto1.getItemSizes().add(sizeDto3);
        itemDto1.getItemSizes().add(sizeDto4);
        itemDto1.getItemSizes().add(sizeDto5);
        itemDto1.getItemSizes().add(sizeDto6);

        itemService.saveItem(itemDto1);

        ItemDto itemDto2 = new ItemDto();
        itemDto2.setItemCategory("Manchester City");
        itemDto2.setManufacturer("Puma");
        itemDto2.setName("24-25 Home Replica");
        itemDto2.setPrice(160000);
        itemDto2.getItemSizes().add(sizeDto1);
        itemDto2.getItemSizes().add(sizeDto2);
        itemDto2.getItemSizes().add(sizeDto3);
        itemDto2.getItemSizes().add(sizeDto4);
        itemDto2.getItemSizes().add(sizeDto5);
        itemDto2.getItemSizes().add(sizeDto6);

        itemService.saveItem(itemDto2);

        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("Member");
        memberService.saveMember(memberFormDto);

        Member member = memberRepository.findByName("Member")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        OrderItemRequestDto orderItem1 = new OrderItemRequestDto();
        OrderItemRequestDto orderItem2 = new OrderItemRequestDto();

        Item item1 = itemRepository.findByCategoryNameAndItemName("Manchester United", "24-25 Home Replica")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        Item item2 = itemRepository.findByCategoryNameAndItemName("Manchester City", "24-25 Home Replica")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        orderItem1.setItemId(item1.getId());
        orderItem1.setSize("M");
        orderItem1.setCount(2);

        orderItem2.setItemId(item2.getId());
        orderItem2.setSize("XL");
        orderItem2.setCount(1);

        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.getOrderItems().add(orderItem1);
        orderRequestDto.getOrderItems().add(orderItem2);

        orderService.saveOrder(member.getId(), orderRequestDto);
    }
}