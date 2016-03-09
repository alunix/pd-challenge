package br.com.rf.purpledeckschallenge.util;

import java.text.Normalizer;

/**
 * Created by rodrigoferreira on 3/9/16.
 */
public class StringUtils {

    public static String removeAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}
