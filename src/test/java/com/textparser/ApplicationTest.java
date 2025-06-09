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
    }
    
    @Test
    void testTextOperations() {
        String text = "    First paragraph with sentence. Another sentence here!\n\n    Second paragraph. Short.";
        
        Document document = textParsingService.parseText(text);
        
        // Test sorting paragraphs by sentence count
        var sortedParagraphs = textOperationsService.sortParagraphsBySentenceCount(document);
        assertThat(sortedParagraphs).hasSize(2);
        
        // Test word counting
        var wordCounts = textOperationsService.countIdenticalWords(document);
        assertThat(wordCounts).containsKey("paragraph");
        
        // Test vowel/consonant counting
        var vowelConsonantCounts = textOperationsService.countVowelsConsonants(document);
        assertThat(vowelConsonantCounts).isNotEmpty();
    }
} 