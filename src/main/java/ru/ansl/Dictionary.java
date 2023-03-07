package ru.ansl;

import java.io.*;
import java.util.*;

public class Dictionary {

    static Scanner scanner = new Scanner(System.in);
    static boolean englishFirst;
    static Map<String, String> engRus = new HashMap<>();
    static Map<String, String> rusEng = new HashMap<>();

    public void start() {
        fileInput();
        mainMenu();
        fileOutput();
    }

    public void mainMenu() {
        while (true) {
            System.out.println("""
                    \n\033[93m1. Вывод на экран словаря;
                    2. Добавление слов;
                    3. Тестирование;
                    0. Выход из программы.\033[0m
                    """);
            switch (scanner.nextLine()) {
                case "1":
                    choiceLang();
                    if (englishFirst) {
                        System.out.println("\nПечатаем Англо-Русский словарь:\n");
                        printDictionary(engRus);
                    } else {
                        System.out.println("\nПечатаем Русско-Английский словарь:\n");
                        printDictionary(rusEng);
                    }
                    break;
                case "2":
                    addWords();
                    break;
                case "3":
                    choiceLang();
                    if (englishFirst) {
                        System.out.println("Я буду писать английское слово, в ответ пишите русское");
                        testWords(engRus);
                    } else {
                        System.out.println("Я буду писать русское слово, в ответ пишите английское");
                        testWords(rusEng);
                    }
                    break;
                case "0":
                    return;
            }
        }
    }

    public void printDictionary(Map<String, String> map) {
        for (String s : map.keySet()) {
            System.out.println(s + " - " + map.get(s));
        }
    }

    public void addWords() {
        while (true) {

            System.out.println("Введите английское слово или цифру 0, если передумали:");
            String englishWord = scanner.nextLine();
            if (!englishWord.isEmpty()) {
                if (!englishWord.equals("0") || englishWord.matches("[A-Za-z]+")) {
                    System.out.println("Введите русское слово:");
                    String russianWord = scanner.nextLine();
                    if (!russianWord.equals("")) {
                        if (!russianWord.equals("0") || russianWord.matches("[А-Яа-я, ]+")) {
                            engRus.put(englishWord, russianWord);
                            rusEng.put(russianWord, englishWord);
                        } else return;
                    }
                } else return;
            }
        }
    }

    public void testWords(Map<String, String> map) {
        List<String> keyTMPList = new ArrayList<>(map.keySet());
        while (true) {
            boolean isEndSecondWhile = false;
            String keyTMP = keyTMPList.get((int) (Math.random() * keyTMPList.size()));
            System.out.println("======================================================");
            System.out.println("\nЕсли хотите выйти из теста, то просто нажмите Enter\nВведите перевод слова:\033[96m" + keyTMP + "\033[0m");
            while (!isEndSecondWhile) {
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase(map.get(keyTMP))) {
                    System.out.println("Правильно, \033[96m" + keyTMP + " - " + map.get(keyTMP) + "\033[0m\nСледующее слово:");
                    break;
                } else if (answer.equals("")) {
                    return;
                } else {
                    System.out.println("Неправильно. Ещё попытка или показать результат? (1/0)");
                    switch (scanner.nextLine()) {
                        case "1" -> System.out.println("Введите ещё раз значение слова \033[96m" + keyTMP + "\033[0m");
                        case "0" -> {
                            System.out.println("\033[96m" + keyTMP + " - " + map.get(keyTMP) + "\033[0m\nСледующее слово:");
                            isEndSecondWhile = true;
                        }
                    }
                }
            }
        }
    }

    public void choiceLang() {
        System.out.println("""
                1. Русско-английский;
                2. English - Russian;
                0. Выход из меню.
                """);
        switch (scanner.nextLine()) {
            case "1":
                englishFirst = false;
                break;
            case "2":
                englishFirst = true;
                break;
            case "0":
                return;
            default:
                System.out.println("Введите цифру 0-2");
        }
    }

    public void fileInput() {
        String fileName = "src\\main\\resources\\words.txt";
        File file = new File(fileName);
        String tmp;
        if (!file.exists()) {
            System.out.println("Словарь пустой, некоторые возможности программы недоступны");

        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((tmp = br.readLine()) != null) {
                String[] words = tmp.split("-");
                engRus.put(words[0].trim(), words[1].trim());
                rusEng.put(words[1].trim(), words[0].trim());
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fileOutput() {
        String fileName = "src\\main\\resources\\words.txt";
        String fileNameBackup = "src\\main\\resources\\words_.txt";
        File file = new File(fileName);
        File fileBackup = new File(fileNameBackup);
        if (fileBackup.exists()) {
            fileBackup.delete();
        }
        file.renameTo(new File(fileNameBackup));
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName), 500);
            for (String s : engRus.keySet()) {
                bw.write(s + " - " + engRus.get(s) + "\n");
//                System.out.println(s + " - " + engRus.get(s));
            }
            bw.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
