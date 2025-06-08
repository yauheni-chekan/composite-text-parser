package com.textparser.parser.impl;

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
    @Override
    public TextComponent parse(String text) {
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
            return new Symbol(symbol);
        }

        return parseNext(text);
    }
} 