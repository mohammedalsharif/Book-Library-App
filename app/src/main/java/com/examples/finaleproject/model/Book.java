package com.examples.finaleproject.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Book implements Serializable {
    private String name;
    private String authorName;
    private byte[] imageUrl;
    private int releaseYear;
    private int pagesNumber;
    private int categoryId;
    private int id;
    private String isFavourite;


    public Book(String name, String authorName, byte[] imageUrl, int releaseYear, int pagesNumber, int categoryId, int id) {
        this.name = name;
        this.authorName = authorName;
        this.imageUrl = imageUrl;
        this.releaseYear = releaseYear;
        this.pagesNumber = pagesNumber;
        this.categoryId = categoryId;
        this.id = id;
    }

    public Book(String name, String authorName, byte[] imageUrl, int releaseYear, int pagesNumber, int categoryId) {
        this.name = name;
        this.authorName = authorName;
        this.imageUrl = imageUrl;
        this.releaseYear = releaseYear;
        this.pagesNumber = pagesNumber;
        this.categoryId = categoryId;
    }

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
