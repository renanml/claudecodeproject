package com.example.claudecodeproject.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "media_set")
public class MediaSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String thumbnail;

    private String medium;

    private String large;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public String getMedium() { return medium; }
    public void setMedium(String medium) { this.medium = medium; }

    public String getLarge() { return large; }
    public void setLarge(String large) { this.large = large; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getModifiedAt() { return modifiedAt; }
}
