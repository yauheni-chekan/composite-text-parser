package com.textparser.service;

import com.textparser.composite.impl.Document;
import com.textparser.composite.impl.Paragraph;
import com.textparser.composite.impl.Sentence;
import com.textparser.operations.impl.*;
import com.textparser.util.VowelConsonantUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Service providing access to all text operations.
 * Implements the required five text operations from the specification.
 */
public class TextOperationsService {
    private static final Logger logger = LogManager.getLogger(TextOperationsService.class);

    /**
     * Operation 1: Sort paragraphs by number of sentences
     */
    public List<Paragraph> sortParagraphsBySentenceCount(Document document) {
        logger.info("Executing operation: Sort paragraphs by sentence count");
        return new SortParagraphsBySentenceCount().execute(document);
    }

    /**
     * Operation 2: Find sentences with the longest word
     */
    public List<Sentence> findSentencesWithLongestWord(Document document) {
        logger.info("Executing operation: Find sentences with longest word");
        return new FindSentencesWithLongestWord().execute(document);
    }

    /**
     * Operation 3: Remove sentences with word count less than specified
     */
    public Document removeShortSentences(Document document, int minWordCount) {
        logger.info("Executing operation: Remove sentences with fewer than {} words", minWordCount);
        return new RemoveShortSentences(minWordCount).execute(document);
    }

    /**
     * Operation 4: Find and count identical words (case insensitive)
     */
    public Map<String, Integer> countIdenticalWords(Document document) {
        logger.info("Executing operation: Count identical words");
        return new CountIdenticalWords().execute(document);
    }

    /**
     * Get only words that appear more than once
     */
    public Map<String, Integer> getDuplicateWords(Document document) {
        logger.info("Executing operation: Get duplicate words only");
        return new CountIdenticalWords().execute(document);
    }

    /**
     * Operation 5: Count vowels and consonants in sentences
     */
    public Map<String, VowelConsonantUtils.VowelConsonantCount> countVowelsConsonants(Document document) {
        logger.info("Executing operation: Count vowels and consonants in sentences");
        return new CountVowelsConsonants().execute(document);
    }

    /**
     * Get detailed vowel/consonant analysis with sentence text
     */
    public Map<String, VowelConsonantUtils.VowelConsonantCount> getDetailedVowelConsonantAnalysis(Document document) {
        logger.info("Executing operation: Detailed vowel/consonant analysis");
        return new CountVowelsConsonants().getDetailedAnalysis(document);
    }

    /**
     * Get summary vowel/consonant statistics for entire document
     */
    public VowelConsonantUtils.VowelConsonantCount getDocumentVowelConsonantSummary(Document document) {
        logger.info("Executing operation: Document vowel/consonant summary");
        return new CountVowelsConsonants().getDocumentSummary(document);
    }

    /**
     * Execute all operations and return a comprehensive report
     */
    public TextAnalysisReport performCompleteAnalysis(Document document) {
        logger.info("Performing complete text analysis");
        
        TextAnalysisReport report = new TextAnalysisReport();
        
        try {
            report.sortedParagraphs = sortParagraphsBySentenceCount(document);
            report.sentencesWithLongestWord = findSentencesWithLongestWord(document);
            report.wordCounts = countIdenticalWords(document);
            report.vowelConsonantCounts = countVowelsConsonants(document);
            report.documentSummary = getDocumentVowelConsonantSummary(document);
            
            logger.info("Complete analysis finished successfully");
        } catch (Exception e) {
            logger.error("Error during complete analysis", e);
            throw new RuntimeException("Failed to perform complete analysis", e);
        }
        
        return report;
    }


    public void writeReportToFile(TextAnalysisReport report, String filePath) {
        logger.info("Writing report to file: {}", filePath);
        try {
            Files.writeString(Paths.get(filePath), report.toString());
        } catch (IOException e) {
            logger.error("Error writing report to file", e);
        }
    }

    /**
     * Data class to hold results of complete analysis
     */
    public static class TextAnalysisReport {
        public List<Paragraph> sortedParagraphs;
        public List<Sentence> sentencesWithLongestWord;
        public Map<String, Integer> wordCounts;
        public Map<String, VowelConsonantUtils.VowelConsonantCount> vowelConsonantCounts;
        public VowelConsonantUtils.VowelConsonantCount documentSummary;

        @Override
        public String toString() {
            return String.format(
                "TextAnalysisReport{paragraphs=%d, sentencesWithLongestWord=%d, uniqueWords=%d, %s}",
                sortedParagraphs != null ? sortedParagraphs.size() : 0,
                sentencesWithLongestWord != null ? sentencesWithLongestWord.size() : 0,
                wordCounts != null ? wordCounts.size() : 0,
                documentSummary != null ? documentSummary.toString() : "no summary"
            );
        }
    }
} 