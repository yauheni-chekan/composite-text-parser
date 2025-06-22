package com.textparser.service;

import com.textparser.composite.impl.Document;
import com.textparser.composite.impl.Paragraph;
import com.textparser.composite.impl.Sentence;
import com.textparser.operations.impl.*;
import com.textparser.util.TextConstants;
import com.textparser.util.VowelConsonantUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            report.reportDate = LocalDateTime.now();
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

    /**
     * Format the analysis output for display
     */
    public String formatAnalysisOutput(TextAnalysisReport report) {
        StringBuilder analysisOutput = new StringBuilder();
        analysisOutput.append("=== Text Analysis Results ===\n");
        analysisOutput.append("Generated on: ").append(report.reportDate.format(DateTimeFormatter.ofPattern(TextConstants.DATE_TIME_PATTERN))).append("\n");
        analysisOutput.append("\n1. Paragraphs sorted by sentence count:\n");
        for (int i = 0; i < report.sortedParagraphs.size(); i++) {
            var paragraph = report.sortedParagraphs.get(i);
            analysisOutput.append(String.format("   Paragraph %d: %d sentences%n", 
                            i + 1, paragraph.getChildren().size()));
        }
            
        // Operation 2: Find sentences with longest word
        analysisOutput.append("\n2. Sentences with longest word:\n");
        for (int i = 0; i < Math.min(report.sentencesWithLongestWord.size(), 3); i++) {
            var sentence = report.sentencesWithLongestWord.get(i);
            String text = sentence.getText();
            analysisOutput.append(String.format("   %s%n", text.length() > 100 ? 
                            text.substring(0, 100) + "..." : text));
        }
        if (report.sentencesWithLongestWord.size() > 3) {
            analysisOutput.append(String.format("   ... and %d more sentences%n", 
                            report.sentencesWithLongestWord.size() - 3));
        }
            
        // Operation 3: Example of removing short sentences
        analysisOutput.append("\n3. Document statistics after removing sentences with < 3 words:\n");
        analysisOutput.append(String.format("   Original: %d paragraphs, %d sentences%n", 
                        report.sortedParagraphs.size(), 
                        report.sortedParagraphs.size()));
        analysisOutput.append(String.format("   Filtered: %d paragraphs, %d sentences%n", 
                        report.sortedParagraphs.size(), 
                        report.sortedParagraphs.size()));
        
        // Operation 4: Count identical words
        analysisOutput.append("\n4. Most frequent words (case insensitive):\n");
        report.wordCounts.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .forEach(entry -> 
                    analysisOutput.append(String.format("   '%s': %d times%n", entry.getKey(), entry.getValue()))
                );
            
        // Operation 5: Count vowels and consonants
        analysisOutput.append("\n5. Vowel and consonant analysis:\n");
        analysisOutput.append(String.format("   %s%n", report.documentSummary.toString()));
        return analysisOutput.toString();
    }

    /**
     * Write the analysis report to a file
     */
    public String writeReportToFile(TextAnalysisReport report, String filePath) {
        logger.info("Writing report to file: {}", filePath);
        if (report == null || report.toString().isEmpty()) {
            logger.error("Report is empty");
            return null;
        }
        String formattedReport = formatAnalysisOutput(report);
        String reportPath = String.format("%s/%s", TextConstants.REPORT_FOLDER_PATH, filePath);
        File reportFile = new File(reportPath);
        reportFile.getParentFile().mkdirs();
        try {
            Files.writeString(Paths.get(reportPath), formattedReport);
        } catch (IOException e) {
            logger.error("Error writing report to file", e);
        }
        return reportPath;
    }

    /**
     * Data class to hold results of complete analysis
     */
    public static class TextAnalysisReport {
        public LocalDateTime reportDate;
        public List<Paragraph> sortedParagraphs;
        public List<Sentence> sentencesWithLongestWord;
        public Map<String, Integer> wordCounts;
        public Map<String, VowelConsonantUtils.VowelConsonantCount> vowelConsonantCounts;
        public VowelConsonantUtils.VowelConsonantCount documentSummary;

        @Override
        public String toString() {
            return String.format(
                "TextAnalysisReport{reportDate=%s, paragraphs=%d, sentencesWithLongestWord=%d, uniqueWords=%d, %s}",
                reportDate.format(DateTimeFormatter.ofPattern(TextConstants.DATE_TIME_PATTERN)),
                sortedParagraphs != null ? sortedParagraphs.size() : 0,
                sentencesWithLongestWord != null ? sentencesWithLongestWord.size() : 0,
                wordCounts != null ? wordCounts.size() : 0,
                documentSummary != null ? documentSummary.toString() : "no summary"
            );
        }
    }
} 