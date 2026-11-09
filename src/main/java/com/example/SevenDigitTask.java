package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SevenDigitTask {
    private static final int COUNT = 10;

    private final Path file;
    private final ConsoleInput input;

    public SevenDigitTask(Path file, ConsoleInput input) {
        this.file = file;
        this.input = input;
    }

    public void run() {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            writeNumbers();
            List<Integer> numbers = readNumbers();
            System.out.println("Числа из файла: " + numbers);
            int count = 0;
            for (int number : numbers) {
                if (containsSeven(number)) {
                    count++;
                }
            }
            System.out.println("Количество чисел, содержащих цифру 7: " + count);
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлом: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формата числа в файле: " + e.getMessage());
        }
    }

    private void writeNumbers() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            for (int i = 1; i <= COUNT; i++) {
                int value = input.readInt("Введите число " + i + " из " + COUNT + ": ");
                writer.write(String.valueOf(value));
                writer.newLine();
            }
        }
    }

    private List<Integer> readNumbers() throws IOException {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    numbers.add(Integer.parseInt(line.trim()));
                }
            }
        }
        return numbers;
    }

    private boolean containsSeven(int number) {
        return String.valueOf(Math.abs((long) number)).indexOf('7') >= 0;
    }
}
