package com.textparser.parser.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Symbol;
import com.textparser.parser.AbstractTextParser;
import com.textparser.util.TextConstants;

/**
 * Parser for symbols.
 * Responsible for parsing text into individual symbols (letters, digits, punctuation).
 * Special case: "..." is treated as a single symbol for ellipsis.
 */
public class SymbolParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger(SymbolParser.class);

    @Override
    public TextComponent parse(String text) {
        logger.debug("Parsing symbol: {}", text);
        // Handle ellipsis special case
        if (text.equals(TextConstants.ELLIPSIS)) {
            return new Symbol(text);
        }

        // Handle single character symbols
        if (text.length() != 1) {
            return parseNext(text);
        }

        char symbol = text.charAt(0);
        String symbolStr = String.valueOf(symbol);

        if (symbolStr.matches(TextConstants.LETTER_PATTERN) ||
            symbolStr.matches(TextConstants.DIGIT_PATTERN) ||
            symbolStr.matches(TextConstants.PUNCTUATION_PATTERN)) {
            logger.debug("Found symbol: {}", symbolStr);
            return new Symbol(symbol);
        }

        logger.error("Failed to parse symbol: {}", symbolStr);
        return null;
    }
} 