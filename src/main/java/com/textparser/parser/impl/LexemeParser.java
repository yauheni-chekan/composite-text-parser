package com.textparser.parser.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Lexeme;
import com.textparser.parser.AbstractTextParser;

/**
 * Parser for lexemes.
 * Responsible for parsing text into lexemes.
 * A lexeme is any part of text bounded by whitespace characters.
 * This parser creates a Lexeme component and determines its type:
 * - Word
 * - Number
 * - Mathematical expression
 * - Word with punctuation
 * - Other (will be broken down into symbols)
 */
public class LexemeParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger(LexemeParser.class);

    @Override
    public TextComponent parse(String text) {
        logger.debug("Parsing lexeme: {}", text);
        // Create a lexeme and populate it with parsed components
        Lexeme lexeme = new Lexeme(text);
        // Try to parse as atomic components first
        TextComponent component = parseNext(text);
        if (component != null) {
            lexeme.add(component);
            return lexeme;
        }
        logger.error("Failed to parse lexeme: {}", text);
        return lexeme;
    }
} 