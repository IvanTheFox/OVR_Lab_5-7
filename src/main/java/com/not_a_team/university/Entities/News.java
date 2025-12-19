package com.not_a_team.university.Entities;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.not_a_team.university.Services.FileService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "news")
public class News {
    private static String picturesPath = "static\\uploads\\news_pictures\\";
    private static final int maxPictureCount = 5;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String text;
    private Long author;
    private Long publishTime;
    private int pictureCount;
    private String[] pictures;

    public News() {
        pictures = new String[maxPictureCount];
        publishTime = System.currentTimeMillis() / 1000L;
    }
    public News(Long author) {
        this();
        this.author = author;
    }

    // -- Getters and setters
    // Id
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    // Title
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    // Text
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    // Author
    public Long getAuthor() {
        return this.author;
    }
    public void setAuthor(Long author) {
        this.author = author;
    }
    // Publish time
    public Long getPublishTime() {
        return this.publishTime;
    }
    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public String[] getPictures() {
        return this.pictures;
    }
    public void setPictures(String[] pictures) {
        this.pictures = pictures;
    }

    public int getPictureCount() {
        return this.pictureCount;
    }
    public void setPictureCount(int pictureCount) {
        this.pictureCount = pictureCount;
    }

    // -- Picture management
    // Adding pictures
    public void addPicture(String picturePath) {
        if (pictureCount < maxPictureCount) {
            pictures[pictureCount] = picturePath;
            pictureCount++;
        }
    }
    public void addPicture(MultipartFile picture) throws IOException {
        int i = 0;
        String pictureName = this.id + "-" + pictureCount;
        while (FileService.findFile(pictureName+FileService.getFileExtension(picture.getOriginalFilename()), picturesPath)) {
            i++;
            pictureName = this.id + "-" + (pictureCount + i);
        }
        addPicture(FileService.saveFile(picture, picturesPath, pictureName));
    }
    // Removing pictures
    public void removePicture(int pictureId) {
        for (int i = pictureId; i < maxPictureCount - 1; i++)
            pictures[i] = pictures[i + 1];
        pictures[maxPictureCount - 1] = null;
        pictureCount--;
    }
    public void removePicture(String picturePath) {
        for (int i = 0; i < pictureCount; i++) {
            if (pictures[i].equals(picturePath)) {
                removePicture(i);
                break;
            }
        }
    }
    public void clearPictures() {
        for (int i = 0; i < pictureCount; i++)
            pictures[i] = null;
    }
}
