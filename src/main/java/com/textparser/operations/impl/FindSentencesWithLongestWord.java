package com.textparser.operations.impl;

import com.textparser.composite.impl.Document;
import com.textparser.composite.impl.Sentence;
import com.textparser.composite.impl.Word;
import com.textparser.operations.TextOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Operation to find sentences containing the longest word in the document.
 */
public class FindSentencesWithLongestWord implements TextOperation<List<Sentence>> {
    private static final Logger logger = LogManager.getLogger(FindSentencesWithLongestWord.class);

    @Override
    public List<Sentence> execute(Document document) {
        logger.info("Finding sentences with longest word");
        
        // Find the longest word length in the entire document
        int maxWordLength = document.getAllWords().stream()
                .mapToInt(word -> word.getText().length())
                .max()
                .orElse(0);
        
        logger.debug("Longest word length found: {}", maxWordLength);
        
        if (maxWordLength == 0) {
            logger.warn("No words found in document");
            return List.of();
        }
        
        // Find all sentences that contain at least one word of maximum length
        List<Sentence> sentencesWithLongestWord = document.getAllSentences().stream()
                .filter(sentence -> containsWordOfLength(sentence, maxWordLength))
                .collect(Collectors.toList());
        
        logger.info("Found {} sentences containing words of length {}", 
                   sentencesWithLongestWord.size(), maxWordLength);
        
        return sentencesWithLongestWord;
    }

    /**
     * Check if a sentence contains at least one word of the specified length
     */
    private boolean containsWordOfLength(Sentence sentence, int targetLength) {
        return sentence.getChildren().stream()
                .flatMap(component -> {
                    if (component instanceof Word) {
                        return java.util.stream.Stream.of((Word) component);
                    } else {
                        // Handle words inside lexemes
                        return component.getChildren().stream()
                                .filter(child -> child instanceof Word)
                                .map(child -> (Word) child);
                    }
                })
                .anyMatch(word -> word.getText().length() == targetLength);
    }

    @Override
    public String getDescription() {
        return "Find sentences containing the longest word(s) in the document";
    }
} 