package ru.ansl.util;

import ru.ansl.model.Verb;

import java.io.*;
import java.util.List;
import java.util.Map;

public class FileUtil {
    public static void fillDicVerb(List<Verb> list, String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                String[] spl1 = str.split("-");
                String present = spl1[0];
                String past = spl1[1];
                String[] spl2 = spl1[2].split("[(]");
                String participle = spl2[0];
                String translate = spl2[1].replace(")", "");

                Verb verb = Verb.builder()
                        .present(present)
                        .past(past)
                        .participle(participle)
                        .translate(translate)
                        .build();

                list.add(verb);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fillDicEngRus(Map<String, String> rusEng, Map<String, String> engRus, String fileName) {
        File file = new File(fileName);
        String str;
        if (!file.exists()) {
            System.out.println("Файл со словами пустой");
            return;
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            while ((str = bufferedReader.readLine()) != null) {
                if (str.isEmpty()) {
                    continue;
                }
                String[] words = str.split("-");
                engRus.put(words[0].trim(), words[1].trim());
                rusEng.put(words[1].trim(), words[0].trim());
            }
        } catch (IOException e) {
            System.out.println("\033[92mОшибка считывания файла");
        }
    }

    public static void fileOutput(Map<String, String> engRus, String fileName) {
        String fileNameBackup = "C:\\Users\\words_.txt";
        File file = new File(fileName);
        File fileBackup = new File(fileNameBackup);
        if (fileBackup.exists()) {
            fileBackup.delete();
        }
        file.renameTo(new File(fileNameBackup));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
            for (String s : engRus.keySet()) {
                bw.write(s + " - " + engRus.get(s) + "\n");
            }
        } catch (IOException ex) {
            System.out.println("\033[92mОшибка записи в файл");
        }
    }

}




