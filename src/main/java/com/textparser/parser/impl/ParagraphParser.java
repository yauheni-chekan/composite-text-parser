package com.textparser.parser.impl;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Paragraph;
import com.textparser.parser.AbstractTextParser;
import com.textparser.util.TextConstants;

/**
 * Parser for paragraphs.
 * Responsible for parsing paragraphs into sentences.
 */
public class ParagraphParser extends AbstractTextParser {
    @Override
    public TextComponent parse(String text) {

        // Split into sentences by sentence endings
        String[] sentences = text.split(TextConstants.SENTENCE_SPLIT_PATTERN);
        
        Paragraph paragraph = new Paragraph();
        for (String sentenceText : sentences) {
            String sentence = trim(sentenceText);
            if (!sentence.isEmpty()) {
                TextComponent sentenceComponent = parseNext(sentence);
                if (sentenceComponent != null) {
                    paragraph.add(sentenceComponent);
                }
            }
        } 
        return paragraph;
    }
} 