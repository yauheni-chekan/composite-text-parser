package com.textparser.parser.impl;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Lexeme;
import com.textparser.composite.impl.Symbol;
import com.textparser.composite.impl.Word;
import com.textparser.parser.AbstractTextParser;
import com.textparser.util.TextConstants;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for words.
 * Responsible for parsing text into words.
 * A word consists of letters, possibly with hyphens or apostrophes.
 * Can also handle lexemes that contain a word followed by punctuation.
 */
public class WordParser extends AbstractTextParser {
    @Override
    public TextComponent parse(String text) {
        // First try to match a complete word
        if (matches(text, TextConstants.WORD_PATTERN)) {
            return new Word(text);
        }

        // Try to match a word followed by punctuation
        Pattern pattern = Pattern.compile(TextConstants.WORD_WITH_PUNCTUATION_PATTERN, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()) {
            String wordText = matcher.group(1);
            String punctuationText = matcher.group(2);

            // Create a new lexeme to hold both word and punctuation
            Lexeme lexeme = new Lexeme(text);

            // Add the word
            Word word = new Word(wordText);
            lexeme.add(word);

            // Add the punctuation symbol
            Symbol punctuation = new Symbol(punctuationText);
            lexeme.add(punctuation);

            return lexeme;
        }

        return parseNext(text);
    }
} 