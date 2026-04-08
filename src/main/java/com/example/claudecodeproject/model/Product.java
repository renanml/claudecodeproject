package com.example.claudecodeproject.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "price_id")
    private Price price;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "media_set_id")
    private MediaSet mediaSet;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Price getPrice() { return price; }
    public void setPrice(Price price) { this.price = price; }

    public MediaSet getMediaSet() { return mediaSet; }
    public void setMediaSet(MediaSet mediaSet) { this.mediaSet = mediaSet; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getModifiedAt() { return modifiedAt; }
}
