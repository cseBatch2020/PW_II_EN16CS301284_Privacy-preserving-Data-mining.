/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import gui.AprioriAlgorithm;
import static gui.AprioriAlgorithm.afterRemove;
import static gui.Preprocess.dataset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * @author Anjali
 */
public class Aproiri {

    HashMap<String, Integer> removeItems = new HashMap<>();
    static ArrayList<String> removeList = new ArrayList<>();
    static ArrayList<String> candidateSet = new ArrayList<>();
    static ArrayList<String> final_candidateSet = new ArrayList<>();
    static ArrayList<String> frequentItemSets = new ArrayList<>();
    public static StringBuilder outputBuff = new StringBuilder();
    public static HashMap<String, Integer> frequentSetWithFrequencey = new HashMap<>();

    public Aproiri() {
        System.out.println("Apriori starting...");
    }

    /**
     * method : aprioriAlgo apriori algorithm code
     *
     * @param minSupport
     * @param frequent_map
     * @return *
     */
    public void aprioriAlgo(int minSupport, LinkedHashMap<String, Integer> frequent_map) {
        int count = 1;
        removeItems = removeItemset(minSupport, frequent_map);
        for (Map.Entry<String, Integer> entry : removeItems.entrySet()) {
            afterRemove.append(entry.getKey() + " : " + entry.getValue() + "\n");
            removeList.add(entry.getKey());
            final_candidateSet.add(entry.getKey());
        }
        AprioriAlgorithm.jTextArea1.setText(afterRemove.toString());

        int maxlength = getmaxlength();
        System.out.println("max length=" + maxlength);
        for (int i = 1; i <= maxlength; i++) {
            generateCandidates(i);
            calculatefrequentItemsets(minSupport);
        }
        System.out.println("" + final_candidateSet);
        while (count <= maxlength) {
            printOutput(count);
            count++;
        }
    }

    /**
     * method : removeItemset remove value which is less than minimum support
     *
     * @param minsupport
     * @param frequent_map
     * @return LinkedHashMap
     */
    public HashMap<String, Integer> removeItemset(int minsupport, LinkedHashMap<String, Integer> frequent_map) {
        HashMap<String, Integer> removeItems = new HashMap<>();
        Set entryset = frequent_map.keySet();
        for (Object key : entryset) {
            int freq = frequent_map.get(key);
            if (freq >= minsupport) {
                removeItems.put((String) key, freq);
                frequentSetWithFrequencey.put(key.toString(), freq);
            }
        }

        return removeItems;
    }

    /**
     * method : generateCandidates generate candidate sets of n length
     *
     * @param n
     * @return
     */
    public void generateCandidates(int n) {
        ArrayList<String> tempCandidates = new ArrayList<>();//temporary candidate string vector
        String str1, str2; //strings that will be used for comparisons
        StringTokenizer st1, st2; //string tokenizers for the two itemsets being compared

        //if its the first set, candidates are just the numbers
        if (n == 1) {
            for (int i = 1; i <= removeList.size(); i++) {
                tempCandidates.add((Integer.toString(i)));
            }

        } else if (n == 2) //second itemset is just all combinations of itemset 1
        {

            //add each itemset from the previous frequent itemsets together
            for (int i = 0; i < removeList.size(); i++) {
                st1 = new StringTokenizer(removeList.get(i));
                str1 = st1.nextToken();
                for (int j = i + 1; j < removeList.size(); j++) {
                    st2 = new StringTokenizer(removeList.get(j));
                    str2 = st2.nextToken();
                    tempCandidates.add(str1 + " " + str2);
                }
            }
        } else {
            //for each itemset
            for (int i = 0; i < candidateSet.size(); i++) {
                //compare to the next itemset
                for (int j = i + 1; j < candidateSet.size(); j++) {
                    //create the strings
                    str1 = "";
                    str2 = "";
                    //create the tokenizers
                    st1 = new StringTokenizer(candidateSet.get(i));
                    st2 = new StringTokenizer(candidateSet.get(j));

                    //make a string of the first n-2 tokens of the strings
                    for (int s = 0; s < n - 2; s++) {
                        str1 = str1 + " " + st1.nextToken();
                        str2 = str2 + " " + st2.nextToken();
                    }

                    //if they have the same n-2 tokens, add them together
                    if (str2.compareTo(str1) == 0) {
                        tempCandidates.add((str1 + " " + st1.nextToken() + " " + st2.nextToken()).trim());
                    }
                }
            }
        }
        //clear the old candidates
        candidateSet.clear();
        //set the new ones
        candidateSet = new ArrayList<>(tempCandidates);
        tempCandidates.clear();
    }

    /**
     * method : getmaxlength for maximum row length
     *
     * @return
     */
    public int getmaxlength() {
        int maxlength = dataset.get(0).split(" ").length;
        for (int i = 0; i < dataset.size(); i++) {
            int l = dataset.get(i).split(" ").length;
            if (l > maxlength) {
                maxlength = l;
            }
        }
        return maxlength;
    }

    /**
     * method : calculatefrequentItemsets for get frequent itemsets from
     * generated candidatesets
     *
     * @param minsupport
     * @return
     */
    public void calculatefrequentItemsets(int minsupport) {
        System.out.println("in calculate frequent items :");
        for (int i = 0; i < candidateSet.size(); i++) {
            String canditate = candidateSet.get(i);

            String[] str = canditate.split(" ");
            int freq = 0;
            for (int j = 0; j < dataset.size(); j++) {
                String[] data = dataset.get(j).split(" ");
                int count = 0;
                for (int k = 0; k < str.length; k++) {
                    int dataIndex = 0;
                    while (dataIndex < data.length) {
                        if (data[dataIndex].equals(str[k])) {
                            count++;
                        }
                        dataIndex++;
                    }
                }
                if (count == str.length) {
                    freq = freq + 1;
//                    candidateSet.remove(canditate);
                }
            }
            if (freq >= minsupport) {
                if (!final_candidateSet.contains(canditate)) {
                    final_candidateSet.add(canditate);
                    frequentSetWithFrequencey.put(canditate, freq);
                }
            }
        }
    }

    /**
     * method : printOutput append frequent itemsets into stringbuffer
     *
     * @param maxlength
     * @return
     */
    public static void printOutput(int maxlength) {
        int count = 1;
        for (int i = 0; i < final_candidateSet.size(); i++) {
            String items = final_candidateSet.get(i);
            if (items.split(" ").length == maxlength) {
                if (count == 1) {
                    outputBuff.append("Frequent  " + maxlength + ":   Itemsets " + "\n");
                    count++;
                }
                outputBuff.append(items + " \n");
            }
        }
        outputBuff.append("\n");
    }

    public ArrayList<String> generateRules(HashMap<String, Integer> itemsetWithFrequency, int minconfidence) {
        ArrayList<String> rules = new ArrayList<String>();
        List<String> itemset = new ArrayList<String>(itemsetWithFrequency.keySet());
        try {
            for (int i = 0; i < itemset.size(); i++) {
                String[] items = itemset.get(i).split(" ");
                if (items.length > 1) {
                    if (items.length == 2) {
                        String s = items[0] + " " + items[1];
                        double support = itemsetWithFrequency.get(s);
                        int support1 = itemsetWithFrequency.get(items[0]);
                        int support2 = itemsetWithFrequency.get(items[1]);
                        double finalsupport1 = (support / support1) * 100;
                        double finalsupport2 = (support / support2) * 100;
                        if (finalsupport1 > minconfidence) {
                            rules.add(items[0] + "----->" + items[1]);
                        }
                        if (finalsupport2 > minconfidence) {
                            rules.add(items[1] + "----->" + items[0]);
                        }
                    } else {
                        for (int j = 0; j < itemset.size(); j++) {
                            String s = itemset.get(i);

                            String[] item1 = itemset.get(j).split(" ");
                            if (!(item1.length >= items.length)) {
                                int flag = 0;
                                for (int k = 0; k < item1.length; k++) {

                                    if (s.contains(item1[k])) {
                                        int index = s.indexOf(item1[k]);
                                        if (index > 1) {
                                            int a = index + item1[k].length();
                                            if (a == s.length()) {
                                                s = s.substring(0, index - 1);
                                            } else {
                                                String s1 = s.substring(0, index - 1);
                                                String s2 = s.substring(index + item1[k].length() + 1);
                                                s = s1 + " " + s2;
                                            }
                                        } else {
                                            s = s.substring(item1[k].length() + 1);
                                        }
                                        flag = 1;
                                    } else {
                                        flag = 0;
                                    }
                                }
                                if (flag != 0) {
                                    double support = itemsetWithFrequency.get(itemset.get(i));
                                    int support1 = itemsetWithFrequency.get(itemset.get(j));
                                    int support2 = 1;
                                    if (itemsetWithFrequency.containsKey(s)) {
                                        support2 = itemsetWithFrequency.get(s);
                                    }
                                    double finalsupport1 = (support / support1) * 100;
                                    double finalsupport2 = (support / support2) * 100;
                                    if (finalsupport1 > minconfidence) {
                                        rules.add(itemset.get(j) + "----->" + s);
                                    }
                                    if (finalsupport2 > minconfidence) {
                                        rules.add(s + "----->" + itemset.get(j));
                                    }

                                }
                            }
                        }
                    }

                }
            }
            for (int i = 0; i < rules.size(); i++) {
                String str = rules.get(i);
                if (rules.contains(str)) {
                    rules.remove(str);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rules;
    }

    public static ArrayList<String> getoriginalData(ArrayList<String> data) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {

            String temp = data.get(i).replace("----->", ",");
            String[] st = temp.split(",");
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < st.length; j++) {
                String[] ss = st[j].split(" ");
                for (int k = 0; k < ss.length; k++) {
//                    sb.append(Dodec((byte) enc, ss[k]) + " ");
                    sb.append(ss[k] + " ");
                }
                sb.append(",");
            }

            String decrypted = sb.substring(0, sb.length() - 1);
            decrypted = decrypted.replace(",", "----->");
            list.add(decrypted);
        }
        return list;
    }
}
