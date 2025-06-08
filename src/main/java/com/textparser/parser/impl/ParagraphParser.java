package com.textparser.parser.impl;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Paragraph;
import com.textparser.composite.impl.Symbol;
import com.textparser.parser.AbstractTextParser;
import com.textparser.util.TextConstants;

/**
 * Parser for paragraphs.
 * Responsible for parsing text into paragraphs and their sentences.
 */
public class ParagraphParser extends AbstractTextParser {
    @Override
    public TextComponent parse(String text) {
        if (!matches(text, TextConstants.PARAGRAPH_START_PATTERN + ".*")) {
            return parseNext(text);
        }

        // Remove the paragraph indent
        String content = Paragraph.removeIndent(text);
        
        // Split into sentences while preserving endings
        String[] sentenceTexts = split(content, TextConstants.SENTENCE_SPLIT_PATTERN);
        
        Paragraph paragraph = new Paragraph();
        for (String sentenceText : sentenceTexts) {
            sentenceText = trim(sentenceText);
            if (!sentenceText.isEmpty()) {
                // Extract the sentence ending
                String ending = sentenceText.substring(sentenceText.length() - 1);
                if (ending.equals(".") && sentenceText.endsWith(TextConstants.ELLIPSIS)) {
                    ending = TextConstants.ELLIPSIS;
                }
                
                // Create the sentence without the ending
                String sentenceContent = sentenceText.substring(0, sentenceText.length() - ending.length());
                TextComponent sentence = parseNext(sentenceContent);
                
                if (sentence != null) {
                    // Add the sentence ending as a Symbol
                    sentence.add(new Symbol(ending));
                    paragraph.add(sentence);
                }
            }
        }
        
        return paragraph;
    }
} 