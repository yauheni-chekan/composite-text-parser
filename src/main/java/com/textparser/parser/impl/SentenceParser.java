package com.textparser.parser.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Sentence;
import com.textparser.parser.AbstractTextParser;
import com.textparser.util.TextConstants;

/**
 * Parser for sentences.
 * Responsible for parsing sentences into lexemes.
 */
public class SentenceParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger(SentenceParser.class);

    @Override
    public TextComponent parse(String text) {
        Sentence sentence = new Sentence();
        
        logger.info("Extracting lexemes from sentence.");
        // Split into lexemes by whitespace
        String[] lexemeTexts = split(text, TextConstants.LEXEME_SPLIT_PATTERN);
        for (String lexemeText : lexemeTexts) {
            String lexeme = trim(lexemeText);
            if (!lexeme.isEmpty()) {
                TextComponent lexemeComponent = parseNext(lexeme);
                if (lexemeComponent != null) {
                    sentence.add(lexemeComponent);
                } else {
                    logger.error("Failed to parse lexeme: {}", lexeme);
                }
            }
        }
        return sentence;
    }
}