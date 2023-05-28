package com.example.bookstore.domain.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "book")
public class BookItem implements Serializable {

    public BookItem() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "book_name")
    private String name;

    @NonNull
    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "annotation")
    private String description;

    @ColumnInfo(name = "favorite")
    private boolean isFavorite;

    @ColumnInfo(name = "to_read")
    private boolean isToRead;

    @ColumnInfo(name = "cover_image_url")
    private String imageUrl;
    @ColumnInfo(name = "preview_image")
    private String imageData;


    public BookItem(@NonNull String title, @NonNull String author, String description) {
        this.name = title;
        this.author = author;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String bookName) {
        this.name = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String authorFullName) {
        this.author = authorFullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isToRead() {
        return isToRead;
    }

    public void setToRead(boolean toRead) {
        isToRead = toRead;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
