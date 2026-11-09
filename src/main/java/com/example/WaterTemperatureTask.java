package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class WaterTemperatureTask {
    private static final int COUNT = 5;

    private final Path file;
    private final ConsoleInput input;

    public WaterTemperatureTask(Path file, ConsoleInput input) {
        this.file = file;
        this.input = input;
    }

    public void run() {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            writeValues();
            double average = readAverage();
            System.out.printf(Locale.US, "Средняя температура воды: %.2f%n", average);
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлом: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формата числа в файле: " + e.getMessage());
        }
    }

    private void writeValues() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            for (int i = 1; i <= COUNT; i++) {
                double value = input.readDouble("Введите температуру воды " + i + " из " + COUNT + ": ");
                writer.write(Double.toString(value));
                writer.newLine();
            }
        }
    }

    private double readAverage() throws IOException {
        double sum = 0;
        int count = 0;
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    sum += Double.parseDouble(line.trim());
                    count++;
                }
            }
        }
        if (count == 0) {
            throw new IOException("Файл не содержит данных");
        }
        return sum / count;
    }
}
