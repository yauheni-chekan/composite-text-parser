package com.textparser.parser.impl;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Lexeme;
import com.textparser.parser.AbstractTextParser;
import com.textparser.util.TextConstants;

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
    @Override
    public TextComponent parse(String text) {
        // Try to parse it as a word
        if (matches(text, TextConstants.WORD_PATTERN)) {
            return new Lexeme(text);
        }
        
        // Try to parse it as a number
        if (matches(text, TextConstants.NUMBER_PATTERN)) {
            return new Lexeme(text);
        }
        
        // Try to parse it as an expression
        if (matches(text, TextConstants.EXPRESSION_PATTERN)) {
            return new Lexeme(text);
        }
        
        // Try to parse it as a word with punctuation
        if (matches(text, TextConstants.WORD_PATTERN + TextConstants.PUNCTUATION_PATTERN)) {
            return new Lexeme(text);
        }
        
        // If none of the above, return the lexeme as is
        // The next parser in chain will handle breaking it into symbols
        return new Lexeme(text);
    }
} 