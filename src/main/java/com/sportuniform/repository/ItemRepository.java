package com.sportuniform.repository;

import com.sportuniform.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.itemCategory.name = :categoryName AND i.name = :itemName")
    Optional<Item> findByCategoryNameAndItemName(@Param("categoryName") String categoryName, @Param("itemName") String itemName);
}