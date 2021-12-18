package com.kltn.phongvuserver.models.recommendsystem;

import com.kltn.phongvuserver.models.Product;

import java.util.HashMap;

/*
This class contains details such as the word frequency count for each term and term frequency for each term of the document.
 */
public class DocumentProperties {
    private Product product;
    private HashMap<String, Double> termFreqMap;
    private HashMap<String, Integer> DocWordCounts;

    public DocumentProperties() {
    }

    public HashMap<String, Double> getTermFreqMap() {
        return termFreqMap;
    }

    public HashMap<String, Integer> getWordCountMap() {
        return DocWordCounts;
    }

    public void setTermFreqMap(HashMap<String, Double> inMap) {
        termFreqMap = new HashMap<String, Double>(inMap);
    }


    public void setWordCountMap(HashMap<String, Integer> inMap) {
        DocWordCounts = new HashMap<String, Integer>(inMap);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
