package ru.ansl.application;

import ru.ansl.model.Verb;
import ru.ansl.util.ColorConsole;

import java.util.*;

public class Assistant {
    private static Scanner scanner = new Scanner(System.in);
    private static Dictionary dictionary = new Dictionary();
    private static final String lineSep = System.lineSeparator();

    private static final String MENU_MAIN = ColorConsole.YELLOW + "ГЛАВНОЕ МЕНЮ:" + lineSep +
            "1. Вывод содержимого словаря" + lineSep +
            "2. Добавление слов" + lineSep +
            "3. Тестирование" + lineSep +
            "esc. Выход из программы." + ColorConsole.RESET;

    private static final String MENU_TESTING_PRINT = ColorConsole.YELLOW + "МЕНЮ СЛОВАРЯ:" + lineSep +
            "1. Русско-английский;" + lineSep +
            "2. English - Russian;" + lineSep +
            "3. Неправильные глаголы;" + lineSep +
            "0. Выход в главное меню" + ColorConsole.RESET;

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
                System.out.println(ColorConsole.BLUE + "Русско-английский словарь" + ColorConsole.RESET);
                dictionary
                        .getDicRusEng()
                        .entrySet()
                        .forEach(el -> System.out.println(el + " " + el));
                break;
            case "2":
                System.out.println(ColorConsole.BLUE + "Англо-русский словарь" + ColorConsole.RESET);
                dictionary
                        .getDicEngRus()
                        .entrySet()
                        .forEach(el -> System.out.println(el + " " + el));
                break;
            case "3":
                System.out.println(ColorConsole.BLUE + "Словарь неправильных глаголов" + ColorConsole.RESET);
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
        System.out.println(ColorConsole.BLUE + "Добавление новых слов" + ColorConsole.RESET);
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
                System.out.println(ColorConsole.BLUE +
                        "Тестирование русско-английского перевода" +
                        ColorConsole.RESET);
                getTestingTranslatingWords("rus-eng");
                break;
            case "2":
                System.out.println(ColorConsole.BLUE +
                        "Тестирование англо-русского перевода" +
                        ColorConsole.RESET);
                getTestingTranslatingWords("eng-rus");
                break;
            case "3":
                System.out.println(ColorConsole.BLUE +
                        "Тестирование знания неправильных глаголов" +
                        ColorConsole.RESET);
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
            System.out.println(ColorConsole.GREEN + "Правильный ответ: " + ColorConsole.RESET + verb);
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

            System.out.println(ColorConsole.GREEN + "Правильный ответ: " + ColorConsole.RESET + map.get(keyTMP));
            System.out.println("_".repeat(90));

            if (input.equals("0")) {
                return;
            }
        }
    }
}
