package com.textparser.parser.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Paragraph;
import com.textparser.parser.AbstractTextParser;
import com.textparser.util.TextConstants;

/**
 * Parser for paragraphs.
 * Responsible for parsing paragraphs into sentences.
 */
public class ParagraphParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger(ParagraphParser.class);

    @Override
    public TextComponent parse(String text) {
        Paragraph paragraph = new Paragraph();

        logger.info("Extracting sentences from paragraph.");
        // Split into sentences by sentence endings
        String[] sentences = text.split(TextConstants.SENTENCE_SPLIT_PATTERN);
        for (String sentenceText : sentences) {
            String sentence = trim(sentenceText);
            if (!sentence.isEmpty()) {
                TextComponent sentenceComponent = parseNext(sentence);
                if (sentenceComponent != null) {
                    paragraph.add(sentenceComponent);
                } else {
                    logger.error("Failed to parse sentence: {}", sentence);
                }
            }
        } 
        return paragraph;
    }
} 