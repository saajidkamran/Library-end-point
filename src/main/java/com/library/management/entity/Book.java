package com.library.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity  // 1️⃣ tells Spring Boot this class is a DB table
public class Book {

    @Id  // 2️⃣ marks 'id' as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 3️⃣ auto-increments ID in the database
    private Long id;

    private String title;
    private String author;
    private boolean available = true; // default → book is available

    // 4️⃣ Empty constructor → required by JPA (Hibernate)
    public Book() {}

    // 5️⃣ Constructor with fields (helpful when creating new Book objects)
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
    }

    // 6️⃣ Getters and setters (Lombok can reduce this later)
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
