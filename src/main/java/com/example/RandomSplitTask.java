package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class RandomSplitTask {
    private static final int COUNT = 30;
    private static final int BOUND = 200;

    private final Path source;
    private final Path matching;
    private final Path other;

    public RandomSplitTask(Path source, Path matching, Path other) {
        this.source = source;
        this.matching = matching;
        this.other = other;
    }

    public void run() {
        try {
            Files.createDirectories(source.toAbsolutePath().getParent());
            generate();
            split();
            printFile("f", source);
            printFile("g (четные и кратные 3)", matching);
            printFile("h (остальные)", other);
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлом: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формата числа в файле: " + e.getMessage());
        }
    }

    private void generate() throws IOException {
        Random random = new Random();
        try (BufferedWriter writer = Files.newBufferedWriter(source, StandardCharsets.UTF_8)) {
            for (int i = 0; i < COUNT; i++) {
                writer.write(String.valueOf(random.nextInt(BOUND * 2 + 1) - BOUND));
                writer.newLine();
            }
        }
    }

    private void split() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(source, StandardCharsets.UTF_8);
             BufferedWriter g = Files.newBufferedWriter(matching, StandardCharsets.UTF_8);
             BufferedWriter h = Files.newBufferedWriter(other, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                int number = Integer.parseInt(line.trim());
                BufferedWriter target = number % 2 == 0 && number % 3 == 0 ? g : h;
                target.write(String.valueOf(number));
                target.newLine();
            }
        }
    }

    private void printFile(String title, Path path) throws IOException {
        System.out.println("Файл " + title + ": " + String.join(" ", Files.readAllLines(path, StandardCharsets.UTF_8)));
    }
}
