package ru.ansl;

import java.util.*;

public class Assistant {

    private static Scanner scanner = new Scanner(System.in);
    private static Dictionary dictionary = new Dictionary();

    private static final String MENU_MAIN = """
                    \n\033[93m1. Вывод на экран словаря;
                    2. Добавление слов;
                    3. Тестирование;
                    0. Выход из программы.\033[0m
                    """;

    private static final String MENU_TYPE_DICTIONARY = """
                Выберите словарь:
                1. Русско-английский;
                2. English - Russian;
                0. Выход из меню.
                """;
    public Assistant() {
        dictionary.fillDictionary();
        dictionary.saveDictionaryFile();
    }

    public void start() {

        while (true) {
            System.out.println(MENU_MAIN);
            switch (scanner.nextLine()) {
                case "1":
                    printDictionary();
                    break;
                case "2":
                    addWords();
                    break;
                case "3":
                    getTestingKnowledge();
                    break;
                case "0":
                    return;
            }
        }
    }

    private void getTestingKnowledge() {
        System.out.println(MENU_TYPE_DICTIONARY);

        switch (scanner.nextLine()) {
            case "1":
                System.out.println("Тестирование русско-английского перевода");
                testWords(dictionary.getRusEng());
                break;
            case "2":
                System.out.println("Тестирование англо-русского перевода");
                testWords(dictionary.getEngRus());
                break;
            case "0":
                return;
            default:
                System.out.println("Error. Введите цифру 0-2");
        }
    }

    private void printDictionary() {
        System.out.println(MENU_TYPE_DICTIONARY);

        switch (scanner.nextLine()) {
            case "1":
                System.out.println("\nПечатаем Русско-Английский словарь:\n");
                dictionary.printDictionary(dictionary.getRusEng());
                break;
            case "2":
                System.out.println("\nПечатаем Англо-Русский словарь:\n");
                dictionary.printDictionary(dictionary.getEngRus());
                break;
            case "0":
                return;
            default:
                System.out.println("Error. Введите цифру 0-2");
        }
    }



    private void addWords() {
        while (true) {
            System.out.println("Введите английское слово или цифру 0, если передумали:");

            String englishWord = scanner.nextLine();

            if (englishWord.isEmpty() || englishWord.equals("0") ) {
                return;
            }

            if (englishWord.matches("[A-Za-z]+")) {
                System.out.println("Введите русское слово:");
                String russianWord = scanner.nextLine();

                if (!russianWord.isEmpty()) {
                    if (!russianWord.equals("0") || russianWord.matches("[А-Яа-я, ]+")) {
                      return;
                    }
                    dictionary.getEngRus().put(englishWord, russianWord);
                    dictionary.getRusEng().put(russianWord, englishWord);
                }
            }
        }
    }

    // TO DO переделать метод
    private void testWords(Map<String, String> map) {
        List<String> keyTMPList = new ArrayList<>(map.keySet());
        while (true) {
            boolean isEndSecondWhile = false;
            String keyTMP = keyTMPList.get((int) (Math.random() * keyTMPList.size()));
            System.out.println("=".repeat(20));
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
}
