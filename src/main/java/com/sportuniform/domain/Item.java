package com.sportuniform.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_category_id")
    private ItemCategory itemCategory;

    private String manufacturer;
    private String name;
    private int price;
    private int allStockQuantity;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 6)
    private List<ItemSize> itemSizes = new ArrayList<>();

    @Builder
    private Item(ItemCategory itemCategory, String manufacturer, String name, int price, List<ItemSize> itemSizes) {
        this.itemCategory = itemCategory;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
        this.itemSizes = itemSizes;
    }

    public static Item createItem(ItemCategory itemCategory, String manufacturer, String name,
                                  int price, List<ItemSize> itemSizes) {
        Item item = Item.builder().itemCategory(itemCategory).manufacturer(manufacturer).name(name)
                .price(price).itemSizes(itemSizes).build();

        for (ItemSize itemSize : itemSizes) {
            itemSize.setItem(item);
        }

        item.setAllStockQuantity();

        return item;
    }

    public void updateItem(ItemCategory itemCategory, String name, int price, String manufacturer) {
        this.itemCategory = itemCategory;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
    }

    protected void setAllStockQuantity() {
        this.allStockQuantity = getAllStockQuantity();
    }

    /** 모든 사이즈의 재고를 합한 총 재고 수량 */
    public int getAllStockQuantity() {
        return itemSizes.stream()
                .mapToInt(ItemSize::getStockQuantity)
                .sum();
    }

    public ItemSize getItemSizeBySize(String size) {
        return itemSizes.stream()
                .filter(itemSize -> itemSize.getSize().getSize().equals(size))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 사이즈의 상품이 존재하지 않습니다."));
    }
}