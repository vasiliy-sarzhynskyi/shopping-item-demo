package com.vsarzhynskyi.shop.items.demo.jpa.repository;

import com.vsarzhynskyi.shop.items.demo.jpa.entity.ShoppingItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ShoppingItemRepository extends JpaRepository<ShoppingItemEntity, Long> {

    Optional<ShoppingItemEntity> findByName(String name);

}
