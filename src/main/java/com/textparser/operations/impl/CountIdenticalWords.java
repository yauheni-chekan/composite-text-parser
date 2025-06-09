package com.textparser.operations.impl;

import com.textparser.composite.impl.Document;
import com.textparser.composite.impl.Word;
import com.textparser.operations.TextOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Operation to find and count identical words in the document (case insensitive).
 * Requirement: "Найти в тексте все одинаковые слова без учета регистра и посчитать их количество"
 */
public class CountIdenticalWords implements TextOperation<Map<String, Integer>> {
    private static final Logger logger = LogManager.getLogger(CountIdenticalWords.class);

    @Override
    public Map<String, Integer> execute(Document document) {
        logger.info("Counting identical words (case insensitive)");
        
        Map<String, Integer> wordCounts = document.getAllWords().stream()
                .collect(Collectors.groupingBy(
                    word -> word.getText().toLowerCase(), // Case insensitive grouping
                    Collectors.collectingAndThen(
                        Collectors.counting(),
                        Math::toIntExact
                    )
                ));
        
        logger.debug("Found {} unique words (case insensitive)", wordCounts.size());
        
        // Log words that appear more than once
        Map<String, Integer> duplicateWords = wordCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue
                ));
        
        logger.info("Found {} words that appear more than once", duplicateWords.size());
        
        // Log the most frequent words
        duplicateWords.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> 
                    logger.debug("Word '{}' appears {} times", entry.getKey(), entry.getValue())
                );
        
        return wordCounts;
    }

    /**
     * Get only the words that appear more than once
     * @param document the document to analyze
     * @return map of duplicate words and their counts
     */
    public Map<String, Integer> getDuplicateWords(Document document) {
        Map<String, Integer> allCounts = execute(document);
        return allCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue
                ));
    }

    @Override
    public String getDescription() {
        return "Count identical words in the document (case insensitive)";
    }
} 