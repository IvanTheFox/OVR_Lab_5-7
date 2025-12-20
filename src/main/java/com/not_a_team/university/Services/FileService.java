package com.not_a_team.university.Services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

/* ------------------------------- *\
| рабочая директория - resources!!! |
\* ------------------------------- */

public class FileService {
    private static String workingDir = "src\\main\\resources\\";
    private static String defaultDir = "temp\\";
    
    // Get file extension
    static public String getFileExtension(String fileName) {
        if (fileName.contains("."))
            return fileName.substring(fileName.lastIndexOf("."));
        else
            return "";
    }
    // Get file name
    static public String getFileName(String fileName) {
        if (fileName.contains("."))
            return fileName.substring(0, fileName.lastIndexOf("."));
        else
            return fileName;
    }
    
    // Saving files
    static public String saveFile(MultipartFile file, String path, String newName) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        
        Path destinationFile = Paths.get(workingDir + path).resolve(newName + fileExtension);
        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

        return newName + fileExtension;
    }
    static public String saveFile(MultipartFile file, String path) throws IOException {
        return saveFile(file, path, file.getOriginalFilename());
    }
    static public String saveFile(MultipartFile file) throws IOException {
        return saveFile(file, defaultDir);
    }
    // Deleting files
    static public void deleteFile(String filePath) throws IOException {
        Path destinationFile = Paths.get(workingDir + filePath);
        Files.delete(destinationFile);
    }
    // Checking file existance
    static public boolean findFileByName(String fileName, String directory) {
        Path dirPath = Paths.get(workingDir + directory);
        File dir = new File(dirPath.toString());
        for (String file : dir.list()) {
            if (getFileName(file).equals(fileName))
                return true;
        }
        return false;
    }
    static public boolean findFile(String fileName, String directory) {
        Path path = Paths.get(workingDir + directory + fileName);
        return Files.exists(path) && Files.isRegularFile(path);
    }
}
