package com.kltn.phongvuserver.utils;

import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.recommendsystem.DocumentProperties;
import com.kltn.phongvuserver.models.recommendsystem.RatingRSDTO;
import com.kltn.phongvuserver.models.recommendsystem.TfidfCalculation;

import java.util.*;

public class RecommendSystemUtil {

    /**
     * @return: Cosine similarity.
     */
    public static double cosineSimilarity(List<RatingRSDTO> user_rated_product1, List<RatingRSDTO> user_rated_product2) {
        if (user_rated_product1 == null || user_rated_product2 == null || user_rated_product1.size() == 0 ||user_rated_product2.size() == 0) {
            return 0.0;
        }

        double sumProduct = 0;
        double sumASq = 0;
        double sumBSq = 0;
        int size = user_rated_product1.size();
        for (int i = 0; i < user_rated_product1.size(); i++) {
//            for(int j = 0; j<)

            sumProduct += user_rated_product1.get(i).getValue()*user_rated_product2.get(i).getValue();
            sumASq += user_rated_product1.get(i).getValue() * user_rated_product1.get(i).getValue();
            sumBSq += user_rated_product2.get(i).getValue() * user_rated_product2.get(i).getValue();
        }
        if (sumASq == 0 && sumBSq == 0) {
            return 0.0;
        }
        return sumProduct / (Math.sqrt(sumASq) * Math.sqrt(sumBSq));
    }

    public static HashMap<Product, Double> calcCosineSimilarityText(String search, List<Product> list, String sameFor){
        int noOfDocs = list.size();

        TfidfCalculation TfidfObj = new TfidfCalculation();

        //containers for documents and their properties required to calculate final score
        DocumentProperties[] docProperties = new DocumentProperties[noOfDocs];
        SortedSet<String> wordList = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < noOfDocs; i++) {
            docProperties[i] = TfidfObj.calculateTF(list.get(i), wordList, sameFor);
        }

        //calculating InverseDocument frequency
        HashMap<String, Double> inverseDocFreqMap = TfidfObj.calculateInverseDocFrequency(docProperties, wordList);

        //Calculating tf-idf
        HashMap<Product, HashMap<String, Double>> listTFIDF = new HashMap<>();
        for (int i = 0; i < noOfDocs; i++) {
            listTFIDF.put(list.get(i), TfidfObj.calculateTFIDF(docProperties[i], inverseDocFreqMap));
        }

        SortedSet<String> wordListSearch = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        DocumentProperties documentProperty = TfidfObj.calculateTF(search, wordListSearch);

        HashMap<String, Double> tfidfSearch = TfidfObj.calculateTFIDF(documentProperty, inverseDocFreqMap);

        HashMap<Product, Double> listProductSearch = new HashMap<>();

        for (Map.Entry<Product, HashMap<String, Double>> pd : listTFIDF.entrySet()) {
            Iterator<Map.Entry<String, Double>> it = tfidfSearch.entrySet().iterator();
            double dot_pd = 0.0;
            double norm_search = 0.0;
            while (it.hasNext()) {
                Map.Entry<String, Double> pair = it.next();
                if (pd.getValue().containsKey((String) pair.getKey())) {
                    dot_pd += pd.getValue().get(pair.getKey()) * (double) pair.getValue();
                }
                norm_search += (double) pair.getValue() * (double) pair.getValue();
            }
            double norm_pd = 0.0;
            for (double v : pd.getValue().values()) {
                norm_pd += v * v;
            }

            double cosine = dot_pd / (Math.sqrt(norm_pd) * Math.sqrt(norm_search));
            if (cosine > 0.0)
                listProductSearch.put(pd.getKey(), cosine);
        }

        System.out.println(listProductSearch.size());

        return listProductSearch;
    }

    public static HashMap<String, Double> calcCosineSimilaritySearch(String search, List<String> list){
        int noOfDocs = list.size();

        TfidfCalculation TfidfObj = new TfidfCalculation();

        //containers for documents and their properties required to calculate final score
        DocumentProperties[] docProperties = new DocumentProperties[noOfDocs];
        SortedSet<String> wordList = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < noOfDocs; i++) {
            docProperties[i] = TfidfObj.calculateTF(list.get(i), wordList);
        }

        //calculating InverseDocument frequency
        HashMap<String, Double> inverseDocFreqMap = TfidfObj.calculateInverseDocFrequency(docProperties, wordList);

        //Calculating tf-idf
        HashMap<String, HashMap<String, Double>> listTFIDF = new HashMap<>();
        for (int i = 0; i < noOfDocs; i++) {
            listTFIDF.put(list.get(i), TfidfObj.calculateTFIDF(docProperties[i], inverseDocFreqMap));
        }

        SortedSet<String> wordListSearch = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        DocumentProperties documentProperty = TfidfObj.calculateTF(search, wordListSearch);

        HashMap<String, Double> tfidfSearch = TfidfObj.calculateTFIDF(documentProperty, inverseDocFreqMap);

        HashMap<String, Double> listProductSearch = new HashMap<>();

        for (Map.Entry<String, HashMap<String, Double>> pd : listTFIDF.entrySet()) {
            Iterator<Map.Entry<String, Double>> it = tfidfSearch.entrySet().iterator();
            double dot_pd = 0.0;
            double norm_search = 0.0;
            while (it.hasNext()) {
                Map.Entry<String, Double> pair = it.next();
                if (pd.getValue().containsKey((String) pair.getKey())) {
                    dot_pd += pd.getValue().get(pair.getKey()) * (double) pair.getValue();
                }
                norm_search += (double) pair.getValue() * (double) pair.getValue();
            }
            double norm_pd = 0.0;
            for (double v : pd.getValue().values()) {
                norm_pd += v * v;
            }

            double cosine = dot_pd / (Math.sqrt(norm_pd) * Math.sqrt(norm_search));
            if (cosine > 0.0)
                listProductSearch.put(pd.getKey(), cosine);
        }

        System.out.println(listProductSearch.size());

        return listProductSearch;
    }

    public static HashMap<Product, String> calcCosineSimilaritySearch(List<String> topSearch, List<Product> list){
        int noOfDocs = list.size();

        TfidfCalculation TfidfObj = new TfidfCalculation();

        //containers for documents and their properties required to calculate final score
        DocumentProperties[] docProperties = new DocumentProperties[noOfDocs];
        SortedSet<String> wordList = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < noOfDocs; i++) {
            docProperties[i] = TfidfObj.calculateTF(list.get(i), wordList, "category");
        }

        //calculating InverseDocument frequency
        HashMap<String, Double> inverseDocFreqMap = TfidfObj.calculateInverseDocFrequency(docProperties, wordList);

        //Calculating tf-idf
        HashMap<Product, HashMap<String, Double>> listTFIDF = new HashMap<>();
        for (int i = 0; i < noOfDocs; i++) {
            listTFIDF.put(list.get(i), TfidfObj.calculateTFIDF(docProperties[i], inverseDocFreqMap));
        }

        HashMap<Product, String> products = new HashMap<>();

        for(String search: topSearch){
            SortedSet<String> wordListSearch = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            DocumentProperties documentProperty = TfidfObj.calculateTF(search, wordListSearch);

            HashMap<String, Double> tfidfSearch = TfidfObj.calculateTFIDF(documentProperty, inverseDocFreqMap);

            Product product = new Product();
            double max_cosine = 0.0;

            for (Map.Entry<Product, HashMap<String, Double>> pd : listTFIDF.entrySet()) {
                Iterator<Map.Entry<String, Double>> it = tfidfSearch.entrySet().iterator();
                double dot_pd = 0.0;
                double norm_search = 0.0;
                while (it.hasNext()) {
                    Map.Entry<String, Double> pair = it.next();
                    if (pd.getValue().containsKey((String) pair.getKey())) {
                        dot_pd += pd.getValue().get(pair.getKey()) * (double) pair.getValue();
                    }
                    norm_search += (double) pair.getValue() * (double) pair.getValue();
                }
                double norm_pd = 0.0;
                for (double v : pd.getValue().values()) {
                    norm_pd += v * v;
                }

                double cosine = dot_pd / (Math.sqrt(norm_pd) * Math.sqrt(norm_search));
                if (cosine > 0.0 && cosine > max_cosine){
                    product = pd.getKey();
                    max_cosine = cosine;
                }
            }
            products.put(product, search);
        }

        return products;
    }
}
