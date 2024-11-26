package com.sportuniform.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_item_category_id")
    private ItemCategory parentItemCategory;

    @OneToMany(mappedBy = "parentItemCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCategory> childrenItemCategories = new ArrayList<>();

    @Builder
    private ItemCategory(String name, ItemCategory parentItemCategory) {
        this.name = name;
        this.parentItemCategory = parentItemCategory;
    }

    public static ItemCategory createItemCategory(String name, ItemCategory parentItemCategory) {
        ItemCategory itemCategory = ItemCategory.builder().name(name)
                .parentItemCategory(parentItemCategory).build();

        if (parentItemCategory != null) {
            parentItemCategory.addChildItemCategory(itemCategory);
        }

        return itemCategory;
    }

    /** 연관 관계 편의 메서드 */
    protected void addChildItemCategory(ItemCategory childItemCategory) {
        this.childrenItemCategories.add(childItemCategory);
        childItemCategory.setParentItemCategory(this);
    }

    protected void setParentItemCategory(ItemCategory parentItemCategory) {
        this.parentItemCategory = parentItemCategory;
    }
}
