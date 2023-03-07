package ru.ansl;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


@Getter
public class Dictionary {
    private Map<String, String> engRus = new HashMap<>();
    private Map<String, String> rusEng = new HashMap<>();

    public void fillDictionary(){
        FileUtil.fileInput(rusEng, engRus);
    }

    public void saveDictionaryFile(){
        FileUtil.fileOutput(engRus);
    }

    public void printDictionary(Map<String, String> map) {
        for (String s : map.keySet()) {
            System.out.println(s + " - " + map.get(s));
        }
    }
}
