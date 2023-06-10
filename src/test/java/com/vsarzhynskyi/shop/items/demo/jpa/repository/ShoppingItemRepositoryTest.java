package com.vsarzhynskyi.shop.items.demo.jpa.repository;

import com.vsarzhynskyi.shop.items.demo.jpa.entity.ShoppingItemEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.Assert.*;

@DataJpaTest
@Transactional
public class ShoppingItemRepositoryTest {

    @Autowired
    private ShoppingItemRepository shoppingItemRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void shouldSaveShoppingItemEntity() {
        // save
        var shoppingItemEntity = ShoppingItemEntity.builder()
                .name("test")
                .description("demo")
                .build();

        var savedEntity = shoppingItemRepository.saveAndFlush(shoppingItemEntity);

        assertNotNull(savedEntity.getId());
        assertNotNull(savedEntity.getCreatedTimestamp());

        // fetch back by item name
        entityManager.clear();
        var fetchedItemOptional = shoppingItemRepository.findByName("test");
        assertTrue(fetchedItemOptional.isPresent());
        var fetchedItem = fetchedItemOptional.get();
        assertEquals(savedEntity.getId(), fetchedItem.getId());
    }

}
