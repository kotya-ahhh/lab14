package com.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Path dir = Paths.get("data");
        Scanner scanner = new Scanner(System.in);
        ConsoleInput input = new ConsoleInput(scanner);

        while (true) {
            System.out.println();
            System.out.println("1 - Информация о любимом времени года");
            System.out.println("2 - Числа с клавиатуры, цифра 7");
            System.out.println("3 - Случайные числа: четные и кратные 3");
            System.out.println("4 - Температура воды, среднее значение");
            System.out.println("5 - Количество чисел больше 25");
            System.out.println("6 - Выполнить все задания");
            System.out.println("0 - Выход");
            int choice = input.readInt("Выберите пункт: ");
            System.out.println();

            switch (choice) {
                case 1 -> task1(dir);
                case 2 -> task2(dir, input);
                case 3 -> task3(dir);
                case 4 -> task4(dir, input);
                case 5 -> task5(dir);
                case 6 -> {
                    task1(dir);
                    task2(dir, input);
                    task3(dir);
                    task4(dir, input);
                    task5(dir);
                }
                case 0 -> {
                    scanner.close();
                    return;
                }
                default -> System.out.println("Неверный пункт меню");
            }
        }
    }

    private static void task1(Path dir) {
        new FavoriteSeasonTask(dir.resolve("season.txt")).run();
    }

    private static void task2(Path dir, ConsoleInput input) {
        new SevenDigitTask(dir.resolve("f.txt"), input).run();
    }

    private static void task3(Path dir) {
        new RandomSplitTask(dir.resolve("f3.txt"), dir.resolve("g.txt"), dir.resolve("h.txt")).run();
    }

    private static void task4(Path dir, ConsoleInput input) {
        new WaterTemperatureTask(dir.resolve("water.txt"), input).run();
    }

    private static void task5(Path dir) {
        new GreaterThanTask(dir.resolve("f5.txt")).run();
    }
}
