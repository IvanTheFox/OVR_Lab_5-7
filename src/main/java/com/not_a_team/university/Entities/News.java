package com.not_a_team.university.Entities;

import java.io.IOException;
import java.util.ArrayList;

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
    private static String picturesPath = "uploads\\news_pictures\\";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private Long author;
    private ArrayList<String> pictures;

    public News() {
        pictures = new ArrayList<String>();
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

    // -- Picture management
    // Adding pictures
    public void addPicture(String picturePath) {
        this.pictures.add(picturePath);
    }
    public void addPicture(MultipartFile picture) throws IOException {
        int i = 0;
        String pictureName = this.id + "-" + getPictureCount();
        while (FileService.findFileByName(pictureName, picturesPath)) {
            i++;
            pictureName = this.id + "-" + (getPictureCount() + i);
        }
        this.pictures.add(FileService.saveFile(picture, picturesPath, pictureName));
    }
    // Removing pictures
    public void removePicture(String picturePath) {
        this.pictures.remove(picturePath);
    }
    public void removePicture(int pictureId) {
        this.pictures.remove(pictureId);
    }
    public void clearPictures() {
        this.pictures.clear();
    }
    // Picture info
    public int getPictureCount() {
        return this.pictures.size();
    }
}
