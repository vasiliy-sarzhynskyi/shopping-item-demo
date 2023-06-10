package com.vsarzhynskyi.shop.items.demo.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "shop_items")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "mediumtext")
    @Lob
    private String description;

    @Column(name = "created_ts", updatable = false)
    @CreationTimestamp
    private Instant createdTimestamp;

    @Column(name = "updated_ts")
    @UpdateTimestamp
    private Instant updatedTimestamp;

    @Version
    private Long version;

}
