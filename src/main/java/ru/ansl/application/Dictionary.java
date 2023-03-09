package ru.ansl.application;

import lombok.Getter;
import ru.ansl.model.Verb;
import ru.ansl.util.FileUtil;

import java.util.*;

@Getter
public class Dictionary {
    private final Map<String, String> dicEngRus = new HashMap<>();
    private final Map<String, String> dicRusEng = new HashMap<>();
    private final List<Verb> dicIrregularVerbs = new LinkedList<>();
    private static final String FILE_IRREGULAR_VERBS = "C:\\Users\\irregularVerbs.txt";
    private static final String FIle_WORDS = "C:\\Users\\words.txt";


    public void fillDictionary(){
        FileUtil.fillDicVerb(dicIrregularVerbs, FILE_IRREGULAR_VERBS);
        FileUtil.fillDicEngRus(dicRusEng, dicEngRus, FIle_WORDS);
    }

    public void saveDictionaryFile(){
        FileUtil.fileOutput(dicEngRus, FIle_WORDS);
    }

}
