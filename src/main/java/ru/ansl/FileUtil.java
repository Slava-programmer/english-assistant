package ru.ansl;

import java.io.*;
import java.util.Map;

public class FileUtil {
    public static void fileOutput(Map<String, String> engRus) {
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
            }
            bw.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void fileInput(Map<String, String> rusEng, Map<String, String> engRus) {
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
}
