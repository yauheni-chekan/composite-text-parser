package com.textparser.operations.impl;

import com.textparser.composite.impl.Document;
import com.textparser.composite.impl.Sentence;
import com.textparser.operations.TextOperation;
import com.textparser.util.VowelConsonantUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Operation to count vowels and consonants in each sentence.
 * Requirement: "Подсчитать в предложении число гласных и согласных букв"
 */
public class CountVowelsConsonants implements TextOperation<Map<String, VowelConsonantUtils.VowelConsonantCount>> {
    private static final Logger logger = LogManager.getLogger(CountVowelsConsonants.class);

    @Override
    public Map<String, VowelConsonantUtils.VowelConsonantCount> execute(Document document) {
        logger.info("Counting vowels and consonants in sentences");
        
        Map<String, VowelConsonantUtils.VowelConsonantCount> results = new LinkedHashMap<>();
        int sentenceIndex = 1;

        for (Sentence sentence : document.getAllSentences()) {
            String sentenceText = sentence.getText();
            VowelConsonantUtils.VowelConsonantCount count = VowelConsonantUtils.analyze(sentenceText);
            
            String sentenceKey = String.format("Sentence %d", sentenceIndex++);
            results.put(sentenceKey, count);
            
            logger.debug("{}: {} (Text: '{}')", 
                        sentenceKey, 
                        count, 
                        sentenceText.length() > 50 ? 
                            sentenceText.substring(0, 50) + "..." : 
                            sentenceText);
        }
        
        // Calculate totals
        int totalVowels = results.values().stream()
                .mapToInt(VowelConsonantUtils.VowelConsonantCount::getVowels)
                .sum();
        int totalConsonants = results.values().stream()
                .mapToInt(VowelConsonantUtils.VowelConsonantCount::getConsonants)
                .sum();
        
        logger.info("Analysis complete: {} sentences analyzed. Total vowels: {}, Total consonants: {}", 
                   results.size(), totalVowels, totalConsonants);
        
        return results;
    }

    /**
     * Get summary statistics for the entire document
     * @param document the document to analyze
     * @return total vowel and consonant counts
     */
    public VowelConsonantUtils.VowelConsonantCount getDocumentSummary(Document document) {
        String fullText = document.getText();
        return VowelConsonantUtils.analyze(fullText);
    }

    /**
     * Get detailed analysis with sentence text included
     * @param document the document to analyze
     * @return map with sentence text as key and counts as value
     */
    public Map<String, VowelConsonantUtils.VowelConsonantCount> getDetailedAnalysis(Document document) {
        Map<String, VowelConsonantUtils.VowelConsonantCount> results = new LinkedHashMap<>();
        
        for (Sentence sentence : document.getAllSentences()) {
            String sentenceText = sentence.getText().trim();
            VowelConsonantUtils.VowelConsonantCount count = VowelConsonantUtils.analyze(sentenceText);
            results.put(sentenceText, count);
        }
        
        return results;
    }

    @Override
    public String getDescription() {
        return "Count vowels and consonants in each sentence";
    }
} 