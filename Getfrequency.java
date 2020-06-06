package src;

import java.util.LinkedHashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Anjali
 */
public class Getfrequency {

    /**
     * method : getFrequency calculate frequency of items
     *
     * @param sb
     * @return LinkedHashMap<String, Integer>
     */
    public LinkedHashMap<String, Integer> getFrequency(StringBuilder sb) {
        LinkedHashMap<String, Integer> has = new LinkedHashMap<>();

        try {

            while (sb.toString().contains("  ")) {
                sb = new StringBuilder(sb.toString().replace("  ", " "));
            }
            String[] str = sb.toString().replace("\n", " ").split(" ");
            int i = 0;
            while (i < str.length) {

                String s = str[i];
                s = s.trim();
                s = s.toLowerCase();
                if (!s.equals(" ")) {
                    if (!s.equals("")) {
                        if (has.containsKey(s)) {
                            int d = has.get(s);
                            d++;
                            has.put(s, d);
                        } else {
                            has.put(s, 1);
                        }
                    }
                }
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return has;
    }
}
