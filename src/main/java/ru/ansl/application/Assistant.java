package ru.ansl.application;

import ru.ansl.model.Verb;

import java.util.*;

public class Assistant {
    private static Scanner scanner = new Scanner(System.in);
    private static Dictionary dictionary = new Dictionary();
    private static final String lineSep = System.lineSeparator();

    private static final String MENU_MAIN = "\033[93mГЛАВНОЕ МЕНЮ:" + lineSep +
            "1. Вывод содержимого словаря" + lineSep +
            "2. Добавление слов" + lineSep +
            "3. Тестирование" + lineSep +
            "esc. Выход из программы." +
            "\033[0m";

    private static final String MENU_TESTING_PRINT = "\033[93mМЕНЮ СЛОВАРЯ:" + lineSep +
            "1. Русско-английский;" + lineSep +
            "2. English - Russian;" + lineSep +
            "3. Неправильные глаголы;" + lineSep +
            "0. Выход в главное меню" +
            "\033[0m";

    public Assistant() {
        dictionary.fillDictionary();
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
                    getMenuTestingKnowledge();
                    break;
                case "esc":
                    dictionary.saveDictionaryFile();
                    return;
            }
        }
    }

    private void printDictionary() {
        System.out.println(MENU_TESTING_PRINT);
        switch (scanner.nextLine()) {
            case "1":
                System.out.println("Русско-английский словарь");
                dictionary
                        .getDicRusEng()
                        .entrySet()
                        .forEach(el -> System.out.println(el + " " + el));
                break;
            case "2":
                System.out.println("Англо-русский словарь");
                dictionary
                        .getDicEngRus()
                        .entrySet()
                        .forEach(el -> System.out.println(el + " " + el));
                break;
            case "3":
                System.out.println("Словарь неправильных глаголов");
                dictionary
                        .getDicIrregularVerbs()
                        .forEach(System.out::println);
                break;
            case "0":
                return;
            default:
                System.out.println("Error. Введите цифру 0-3");
        }
    }

    private void addWords() {
        System.out.println("Добавление новых слов");
        while (true) {
            System.out.println("=".repeat(90));
            System.out.print("Введите слово на английском: ");
            String englishWord = scanner.nextLine();

            if (englishWord.isEmpty() || englishWord.equals("0")) {
                return;
            }

            if (englishWord.matches("[A-Za-z]+")) {
                System.out.print("Введите слово на русском: ");
                String russianWord = scanner.nextLine();

                if (!russianWord.isEmpty()) {
                    if (russianWord.equals("0") || !russianWord.matches("[А-Яа-я, ]+")) {
                        return;
                    }
                    dictionary.getDicEngRus().put(englishWord, russianWord);
                    dictionary.getDicRusEng().put(russianWord, englishWord);
                }
            }
        }
    }

    private void getMenuTestingKnowledge() {
        System.out.println(MENU_TESTING_PRINT);

        switch (scanner.nextLine()) {
            case "1":
                System.out.println("Тестирование русско-английского перевода");
                getTestingTranslatingWords("rus-eng");
                break;
            case "2":
                System.out.println("Тестирование англо-русского перевода");
                getTestingTranslatingWords("eng-rus");
                break;
            case "3":
                System.out.println("Тестирование знания неправильных глаголов");
                getTestingIrregularVerbs();
                break;
            case "0":
                return;
            default:
                System.out.println("Error. Введите цифру 0-3");
        }
    }

    private void getTestingIrregularVerbs() {
        ArrayList<Verb> dictionary = new ArrayList<>(Assistant.dictionary.getDicIrregularVerbs());
        String input;
        System.out.println("=".repeat(90));
        while (true) {
            Verb verb = dictionary.get((int) (Math.random() * dictionary.size()));
            System.out.println(verb.getTranslate());
            input = scanner.nextLine();
            if (input.equals("0")) {
                return;
            }
            System.out.println("Правильный ответ: " + verb);
            System.out.println("_".repeat(90));
        }
    }


    private void getTestingTranslatingWords(String typeDic) {
        System.out.println("Тестирование знания перевода");
        System.out.println("=".repeat(90));
        List<String> keyTMPList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        switch (typeDic) {
            case "rus-eng" -> {
                map = dictionary.getDicEngRus();
                keyTMPList = new ArrayList<>(map.keySet());
            }
            case "eng-rus" -> {
                map = dictionary.getDicRusEng();
                keyTMPList = new ArrayList<>(map.keySet());
            }
        }

        while (true) {
            String keyTMP = keyTMPList.get((int) (Math.random() * keyTMPList.size()));
            System.out.println(keyTMP);

            String input = scanner.nextLine();

            System.out.println("Правильный ответ: " + map.get(keyTMP));
            System.out.println("_".repeat(90));

            if (input.equals("0")) {
                return;
            }
        }
    }
}
