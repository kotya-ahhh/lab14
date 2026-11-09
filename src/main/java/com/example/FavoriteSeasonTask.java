package com.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.List;

public class FavoriteSeasonTask {
    private final Path file;

    public FavoriteSeasonTask(Path file) {
        this.file = file;
    }

    public void run() {
        try {
            createFile();
            printFileInfo();
            printFileContent();
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлом: " + e.getMessage());
        }
    }

    private void createFile() throws IOException {
        Files.createDirectories(file.toAbsolutePath().getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            writer.write("Любимое время года: лето");
            writer.newLine();
            writer.write("Месяцы: июнь, июль, август");
            writer.newLine();
            writer.write("Средняя температура воздуха: от +20 до +28 градусов");
            writer.newLine();
            writer.write("Чем нравится: длинные дни, каникулы, море и путешествия");
            writer.newLine();
            writer.write("Любимые занятия: плавание, велопрогулки, походы");
            writer.newLine();
        }
        System.out.println("Файл создан: " + file);
    }

    private void printFileInfo() throws IOException {
        FileTime modified = Files.getLastModifiedTime(file);
        System.out.println("--- Данные о файле ---");
        System.out.println("Имя: " + file.getFileName());
        System.out.println("Абсолютный путь: " + file.toAbsolutePath());
        System.out.println("Размер: " + Files.size(file) + " байт");
        System.out.println("Последнее изменение: " + modified);
        System.out.println("Доступен для чтения: " + Files.isReadable(file));
        System.out.println("Доступен для записи: " + Files.isWritable(file));
        System.out.println("Является каталогом: " + Files.isDirectory(file));
    }

    private void printFileContent() throws IOException {
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        System.out.println("--- Содержимое файла ---");
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
