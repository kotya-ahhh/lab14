package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class GreaterThanTask {
    private static final int COUNT = 10;
    private static final int LIMIT = 25;

    private final Path file;

    public GreaterThanTask(Path file) {
        this.file = file;
    }

    public void run() {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            generate();
            int count = 0;
            StringBuilder numbers = new StringBuilder();
            try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.isBlank()) {
                        continue;
                    }
                    int number = Integer.parseInt(line.trim());
                    numbers.append(number).append(' ');
                    if (number > LIMIT) {
                        count++;
                    }
                }
            }
            System.out.println("Числа в файле: " + numbers.toString().trim());
            System.out.println("Количество чисел, больших " + LIMIT + ": " + count);
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлом: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формата числа в файле: " + e.getMessage());
        }
    }

    private void generate() throws IOException {
        Random random = new Random();
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            for (int i = 0; i < COUNT; i++) {
                writer.write(String.valueOf(random.nextInt(101) - 20));
                writer.newLine();
            }
        }
    }
}
