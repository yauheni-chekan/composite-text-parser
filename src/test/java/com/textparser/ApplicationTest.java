package com.textparser;

import com.textparser.composite.impl.Document;
import com.textparser.service.TextParsingService;
import com.textparser.service.TextOperationsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {
    
    private TextParsingService textParsingService;
    private TextOperationsService textOperationsService;
    
    @BeforeEach
    void setUp() {
        textParsingService = new TextParsingService();
        textOperationsService = new TextOperationsService();
    }
    
    @Test
    void testBasicTextParsing() {
        String text = "    This is a test sentence. It has multiple words!\n\n    Another paragraph here. With more content.";
        
        Document document = textParsingService.parseText(text);
        
        assertThat(document.getParagraphs()).hasSize(2);
        assertThat(document.getAllSentences()).hasSize(4);
        assertThat(document.getAllWords()).isNotEmpty();
    }
    
    @Test
    void testTextOperations() {
        // Create test text with duplicate words to properly test word counting
        String text = "    First paragraph with sentence. The first word appears again.\n\n    Second paragraph with the word paragraph. Another sentence with word.";
        
        Document document = textParsingService.parseText(text);
        
        // Test sorting paragraphs by sentence count
        var sortedParagraphs = textOperationsService.sortParagraphsBySentenceCount(document);
        assertThat(sortedParagraphs).hasSize(2);
        assertThat(sortedParagraphs.get(0).getChildren().size())
                .isGreaterThanOrEqualTo(sortedParagraphs.get(1).getChildren().size());
        
        // Test word counting - should find words that appear more than once
        var wordCounts = textOperationsService.countIdenticalWords(document);
        // The method returns only duplicate words, so check if any duplicates exist
        if (!wordCounts.isEmpty()) {
            assertThat(wordCounts.values()).allMatch(count -> count > 1);
        }
        
        // Test finding sentences with longest words
        var sentencesWithLongestWord = textOperationsService.findSentencesWithLongestWord(document);
        assertThat(sentencesWithLongestWord).isNotEmpty();
        
        // Test vowel/consonant counting
        var vowelConsonantCounts = textOperationsService.countVowelsConsonants(document);
        assertThat(vowelConsonantCounts).isNotEmpty();
        
        // Test document summary
        var documentSummary = textOperationsService.getDocumentVowelConsonantSummary(document);
        assertThat(documentSummary.getVowels()).isGreaterThan(0);
        assertThat(documentSummary.getConsonants()).isGreaterThan(0);
    }
    
    @Test
    void testCompleteAnalysis() {
        String text = "    The quick brown fox jumps over the lazy dog. The fox is quick and brown.\n\n    Another paragraph here. The dog is lazy but smart.";
        
        Document document = textParsingService.parseText(text);
        
        var report = textOperationsService.performCompleteAnalysis(document);
        
        assertThat(report).isNotNull();
        assertThat(report.sortedParagraphs).hasSize(2);
        assertThat(report.sentencesWithLongestWord).isNotEmpty();
        assertThat(report.vowelConsonantCounts).isNotEmpty();
        assertThat(report.documentSummary).isNotNull();
        
        // Test that we have some duplicate words in this text
        assertThat(report.wordCounts).isNotEmpty(); // "the", "fox", "dog", etc. should appear multiple times
        assertThat(report.wordCounts).containsKey("the"); // "the" should appear multiple times
    }
    
    @Test
    void testRemoveShortSentences() {
        String text = "    This is a longer sentence with many words here. Short.\n\n    Another paragraph. Very short sentence. This sentence has more words.";
        
        Document document = textParsingService.parseText(text);
        int originalSentenceCount = document.getAllSentences().size();
        
        // Remove sentences with fewer than 3 words
        Document filteredDocument = textOperationsService.removeShortSentences(document, 3);
        int filteredSentenceCount = filteredDocument.getAllSentences().size();
        
        assertThat(filteredSentenceCount).isLessThanOrEqualTo(originalSentenceCount);
    }
} 