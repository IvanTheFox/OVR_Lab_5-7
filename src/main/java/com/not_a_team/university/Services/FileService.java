package com.not_a_team.university.Services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

/**
 * Класс-сервис для работы с файлами
 */
public class FileService {
    private static String workingDir = "src\\main\\resources\\";
    private static String defaultDir = "temp\\";
    
    /**
     * Метод для получения расширения файла
     * @param fileName - полное имя файла
     * @return - расширение файла
     */
    static public String getFileExtension(String fileName) {
        if (fileName.contains("."))
            return fileName.substring(fileName.lastIndexOf("."));
        else
            return "";
    }
    
    /**
     * Метод для получения имени файла (без расширения)
     * @param fileName - полное имя файла
     * @return - имя файла
     */
    static public String getFileName(String fileName) {
        if (fileName.contains("."))
            return fileName.substring(0, fileName.lastIndexOf("."));
        else
            return fileName;
    }
    
    /**
     * Метод сохранения файла на сервер
     * @param file - файл
     * @param path - путь к файлу
     * @param newName - новое имя файла
     * @return - результат операции
     * @throws IOException - ошибка при сохранении файла
     */
    static public String saveFile(MultipartFile file, String path, String newName) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        
        Path destinationFile = Paths.get(workingDir + path).resolve(newName + fileExtension);
        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

        return newName + fileExtension;
    }

    /**
     * Метод сохранения файла на сервер
     * @param file - файл
     * @param path - путь к файлу
     * @return - результат операции
     * @throws IOException - ошибка при сохранении файла
     */
    static public String saveFile(MultipartFile file, String path) throws IOException {
        return saveFile(file, path, file.getOriginalFilename());
    }

    /**
     * Метод сохранения файла на сервер
     * @param file - файл
     * @return - результат операции
     * @throws IOException - ошибка при сохранении файла
     */
    static public String saveFile(MultipartFile file) throws IOException {
        return saveFile(file, defaultDir);
    }

    /**
     * Метод удаления файла по пути
     * @param filePath - путь файла
     * @throws IOException - ошибка при удалении файла
     */
    static public void deleteFile(String filePath) throws IOException {
        Path destinationFile = Paths.get(workingDir + filePath);
        Files.delete(destinationFile);
    }

    /**
     * Метод поиска файла по его имени
     * @param fileName - имя файла
     * @param directory - директория поиска
     * @return - результат поиска
     */
    static public boolean findFileByName(String fileName, String directory) {
        Path dirPath = Paths.get(workingDir + directory);
        File dir = new File(dirPath.toString());
        for (String file : dir.list()) {
            if (getFileName(file).equals(fileName))
                return true;
        }
        return false;
    }

    /**
     * Метод поиска файла по его полному имени
     * @param fileName - полное имя файла
     * @param directory - директория поиска
     * @return - результат поиска
     */
    static public boolean findFile(String fileName, String directory) {
        Path path = Paths.get(workingDir + directory + fileName);
        return Files.exists(path) && Files.isRegularFile(path);
    }
}
