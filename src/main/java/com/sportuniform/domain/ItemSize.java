package com.sportuniform.domain;

import com.sportuniform.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemSize {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_size_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    private Size size;

    private int stockQuantity;

    @Builder
    private ItemSize(Size size, int stockQuantity) {
        this.size = size;
        this.stockQuantity = stockQuantity;
    }

    public static ItemSize createItemSize(Size size, int stockQuantity) {
        return ItemSize.builder().size(size).stockQuantity(stockQuantity).build();
    }

    /** 연관 관계 메서드 */
    protected void setItem(Item item) {
        this.item = item;
        if (!item.getItemSizes().contains(this)) {
            item.getItemSizes().add(this);
        }
    }

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
        this.item.setAllStockQuantity();
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.stockQuantity = restStock;
        this.item.setAllStockQuantity();
    }
}
