package com.sportuniform.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Domain 기능 Test (DB 사용 X)
 */
class ItemCategoryTest {

    @Test
    void ItemCategoryTest1() {
        //given
        ItemCategory parent = ItemCategory.createItemCategory("parent", null);
        ItemCategory child1 = ItemCategory.createItemCategory("child1", null);
        ItemCategory child2 = ItemCategory.createItemCategory("child2", null);

        //when
        parent.addChildItemCategory(child1);
        parent.addChildItemCategory(child2);

        //then
        assertThat(parent.getChildrenItemCategories().size()).isEqualTo(2);
        assertThat(child1.getParentItemCategory()).isEqualTo(parent);
    }

    @Test
    void ItemCategoryTest2() {
        //given
        ItemCategory parent = ItemCategory.createItemCategory("parent", null);

        //when
        ItemCategory child1 = ItemCategory.createItemCategory("child1", parent);
        ItemCategory child2 = ItemCategory.createItemCategory("child2", parent);

        //then
        assertThat(parent.getChildrenItemCategories().size()).isEqualTo(2);
        assertThat(child1.getParentItemCategory()).isEqualTo(parent);
        assertThat(child2.getParentItemCategory()).isEqualTo(parent);
    }

}