package com.kltn.phongvuserver.models.recommendsystem;

import com.google.common.base.Strings;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.utils.VNCharacterUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
This class contains details such as the word frequency count for each term and term frequency for each term of the document.
 */

public class TfidfCalculation {

    //Calculates inverse Doc frequency.
    public HashMap<String, Double> calculateInverseDocFrequency(DocumentProperties[] docProperties, SortedSet<String> wordList) {
        HashMap<String, Double> InverseDocFreqMap = new HashMap<>();
        int size = docProperties.length;
        double wordCount;
        for (String word : wordList) {
            wordCount = 0;
            for (int i = 0; i < size; i++) {
                HashMap<String, Integer> tempMap = docProperties[i].getWordCountMap();
                if (tempMap.containsKey(word)) {
                    wordCount++;
                    continue;
                }
            }
            double temp = size / wordCount;
            double idf = 1 + Math.log(temp);
            InverseDocFreqMap.put(word, idf);
        }
        return InverseDocFreqMap;
    }

    public HashMap<String, Double> calculateInverseDocFrequency(DocumentProperties documentProperty, SortedSet<String> wordList) {
        HashMap<String, Double> InverseDocFreqMap = new HashMap<>();
        double wordCount;
        for (String word : wordList) {
            wordCount = 0;
            HashMap<String, Integer> tempMap = documentProperty.getWordCountMap();
            if (tempMap.containsKey(word)) {
                wordCount++;
                continue;
            }
            double temp = 1 / wordCount;
            double idf = 1 + Math.log(temp);
            InverseDocFreqMap.put(word, idf);
        }
        return InverseDocFreqMap;
    }

    public HashMap<String, Double> calculateTFIDF(DocumentProperties documentProperty, HashMap<String, Double> inverseDocFreqMap) {
        HashMap<String, Double> tfIDF = new HashMap<>();
        double tfIdfValue = 0.0;
        double idfVal = 0.0;

        HashMap<String, Double> tf = documentProperty.getTermFreqMap();
        Iterator itTF = tf.entrySet().iterator();
        while (itTF.hasNext()) {
            Map.Entry pair = (Map.Entry) itTF.next();
            double tfVal = (Double) pair.getValue();
            if (inverseDocFreqMap.containsKey((String) pair.getKey())) {
                idfVal = inverseDocFreqMap.get((String) pair.getKey());
            }
            tfIdfValue = tfVal * idfVal;
            tfIDF.put((pair.getKey().toString()), tfIdfValue);
        }

        return tfIDF;
    }

    private DocumentProperties getDocumentProperties(HashMap<String, Integer> wordCount) {
        DocumentProperties docProperty = new DocumentProperties();
        docProperty.setWordCountMap(wordCount);
        HashMap<String, Double> termFrequency = calculateTermFrequency(docProperty.getWordCountMap());
        docProperty.setTermFreqMap(termFrequency);
        return docProperty;
    }

    public DocumentProperties calculateTF(List<DescriptionCountDTO> listDescription, SortedSet<String> wordList) {
        HashMap<String, Integer> wordCount = getTerms(listDescription, wordList);
        return getDocumentProperties(wordCount);
    }

    public DocumentProperties calculateTF(String des, SortedSet<String> wordList) {
        HashMap<String, Integer> wordCount = getTerms(des, wordList);
        return getDocumentProperties(wordCount);
    }

    public DocumentProperties calculateTF(Product product, SortedSet<String> wordList, String sameFor) {
        DocumentProperties docProperty = new DocumentProperties();
        String docProduct = "";
        if (sameFor.equals("all")) {
            if (product.getShortDescription() == null || product.getShortDescription().isEmpty())
                docProduct = product.getName();
            else docProduct = product.getShortDescription();
        } else {
            if (sameFor.equals("name"))
                docProduct = product.getName();
            else if (sameFor.equals("description")) {
                docProduct = Jsoup.parse(product.getShortDescription()).body().text();
            } else if (sameFor.equals("category")) {
                docProduct = product.getCategory().getName();
            } else if(sameFor.equals("name&desc")){
                docProduct = Strings.repeat(product.getName() + " ", 10) + Jsoup.parse(product.getDescription()).body().text();
            }else if(sameFor.equals("name&short")){
                docProduct = Strings.repeat(product.getName() + " ", 10) + Jsoup.parse(product.getShortDescription()).body().text();
            }

        }
        HashMap<String, Integer> wordCount = getTerms(docProduct, wordList);
        docProperty.setWordCountMap(wordCount);
        HashMap<String, Double> termFrequency = calculateTermFrequency(docProperty.getWordCountMap());
        docProperty.setTermFreqMap(termFrequency);

        docProperty.setProduct(product);
        return docProperty;
    }

    //calculates Term frequency for all terms
    public HashMap<String, Double> calculateTermFrequency(HashMap<String, Integer> inputMap) {

        HashMap<String, Double> termFreqMap = new HashMap<>();
        double sum = 0.0;
        //Get the sum of all elements in hashmap
        for (float val : inputMap.values()) {
            sum += val;
        }

        //create a new hashMap with Tf values in it.
        Iterator it = inputMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            double tf = (Integer) pair.getValue() / sum;
            termFreqMap.put((pair.getKey().toString()), tf);
        }
        return termFreqMap;
    }

    //Returns if input contains numbers or not
    public boolean isDigit(String input) {
        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        boolean isMatched = matcher.matches();
        if (isMatched) {
            return true;
        }
        return false;
    }

    //Writes the contents of hashmap to CSV file
    public void outputAsCSV(HashMap<String, Double> treeMap, String OutputPath) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Double> keymap : treeMap.entrySet()) {
            builder.append(keymap.getKey());
            builder.append(",");
            builder.append(keymap.getValue());
            builder.append("\r\n");
        }
        String content = builder.toString().trim();
        BufferedWriter writer = new BufferedWriter(new FileWriter(OutputPath));
        writer.write(content);
        writer.close();
    }

    //cleaning up the input by removing .,:"
    public String cleanseInput(String input) {
        String newStr = input.replaceAll("[, . : ;\"]", "");
        newStr = newStr.replaceAll("\\p{P}", "");
        newStr = newStr.replaceAll("\t", "");
        return newStr;
    }

    public HashMap<String, Integer> getTerms(List<DescriptionCountDTO> listDescription, SortedSet<String> wordList) {
        HashMap<String, Integer> WordCount = new HashMap<String, Integer>();
        HashMap<String, Integer> finalMap = new HashMap<>();

        for (DescriptionCountDTO description : listDescription) {
            String[] words = description.getDescOrName().toLowerCase().split(" ");
            for (String term : words) {
                //cleaning up the term ie removing .,:"
                term = cleanseInput(term);
                //ignoring numbers
                if (isDigit(term)) {
                    continue;
                }
                if (term.length() == 0) {
                    continue;
                }
                String removeAccentTerm = VNCharacterUtil.removeAccent(term);
                wordList.add(removeAccentTerm);
                if (WordCount.containsKey(removeAccentTerm)) {
                    WordCount.put(removeAccentTerm, WordCount.get(removeAccentTerm) + description.getCount() > 5 ? 5 : description.getCount());
                } else {
                    WordCount.put(removeAccentTerm, Math.min(description.getCount(), 5));
                }
            }
        }

        Map<String, Integer> treeMap = new TreeMap<>(WordCount);
        finalMap = new HashMap<String, Integer>(treeMap);

        return finalMap;
    }

    public HashMap<String, Integer> getTerms(String description, SortedSet<String> wordList) {
        HashMap<String, Integer> WordCount = new HashMap<String, Integer>();
        HashMap<String, Integer> finalMap = new HashMap<>();

        String[] words = description.toLowerCase().split(" ");
        for (String term : words) {
            //cleaning up the term ie removing .,:"
            term = cleanseInput(term);
            //ignoring numbers
            if (isDigit(term)) {
                continue;
            }
            if (term.length() == 0) {
                continue;
            }
            String removeAccentTerm = VNCharacterUtil.removeAccent(term);
            wordList.add(removeAccentTerm);
            if (WordCount.containsKey(removeAccentTerm)) {
                WordCount.put(removeAccentTerm, WordCount.get(removeAccentTerm) + 1);
            } else {
                WordCount.put(removeAccentTerm, 1);
            }
        }

        Map<String, Integer> treeMap = new TreeMap<>(WordCount);
        finalMap = new HashMap<String, Integer>(treeMap);

        return finalMap;
    }
}
