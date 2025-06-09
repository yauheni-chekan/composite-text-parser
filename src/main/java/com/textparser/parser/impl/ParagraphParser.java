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
        // Check if text contains sentences (has sentence endings)
        if (!text.matches(".*[.!?…].*")) {
            return parseNext(text);
        }

        // Use the text as-is for content
        String content = text;
        
        // Split into sentences by sentence endings
        String[] sentenceTexts = content.split("(?<=[.!?…])\\s+");
        
        Paragraph paragraph = new Paragraph();
        for (String sentenceText : sentenceTexts) {
            sentenceText = trim(sentenceText);
            if (!sentenceText.isEmpty()) {
                TextComponent sentence = parseNext(sentenceText);
                if (sentence != null) {
                    paragraph.add(sentence);
                }
            }
        }
        
        return paragraph;
    }
} 