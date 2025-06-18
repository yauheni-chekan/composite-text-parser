package com.textparser.parser.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
 * Can also handle lexemes that contain:
 * - Words with punctuation
 * - Words surrounded by brackets or parentheses
 * - Words surrounded by quotes
 */
public class WordParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger(WordParser.class);

    @Override
    public TextComponent parse(String text) {
        // First try to match a complete word
        if (matches(text, TextConstants.WORD_PATTERN)) {
            logger.debug("Found word: {}", text);
            return new Word(text);
        }
        
        // Try to match a word followed by punctuation
        Pattern pattern = Pattern.compile(TextConstants.WORD_WITH_PUNCTUATION_PATTERN, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()) {
            return createLexemeWithPunctuation(matcher);
        }

        // Try to match a word with brackets/parentheses
        Pattern bracketPattern = Pattern.compile(TextConstants.WORD_WITH_BRACKETS_PATTERN, Pattern.MULTILINE);
        Matcher bracketMatcher = bracketPattern.matcher(text);
        if (bracketMatcher.matches()) {
            return createLexemeWithSurroundings(bracketMatcher);
        }

        // Try to match a word with quotes
        Pattern quotePattern = Pattern.compile(TextConstants.WORD_WITH_QUOTES_PATTERN, Pattern.MULTILINE);
        Matcher quoteMatcher = quotePattern.matcher(text);
        if (quoteMatcher.matches()) {
            return createLexemeWithSurroundings(quoteMatcher);
        }

        logger.error("Failed to parse word: {}", text);
        return parseNext(text);
    }

    /**
     * Create a lexeme from a matcher that has matched a word with surrounding characters
     * @param matcher the matcher containing the matched groups
     * @return a new Lexeme containing the word and its surroundings
     */
    private Lexeme createLexemeWithSurroundings(Matcher matcher) {
        logger.debug("Creating lexeme with surroundings: {}", matcher.group(0));
        String openingChar = matcher.group(1);
        String wordText = matcher.group(2);
        String closingChar = matcher.group(3);
        String punctuationText = matcher.group(4);

        // Create a new lexeme to hold all components
        Lexeme lexeme = new Lexeme(matcher.group(0));

        // Add the opening character
        if (openingChar != null && !openingChar.isEmpty()) {
            Symbol opening = new Symbol(openingChar);
            logger.debug("Extracted opening character: {}", openingChar);
            lexeme.add(opening);
        }

        // Add the word
        Word word = new Word(wordText);
        logger.debug("Extracted word: {}", wordText);
        lexeme.add(word);

        // Add the closing character
        if (closingChar != null && !closingChar.isEmpty()) {
            Symbol closing = new Symbol(closingChar);
            logger.debug("Extracted closing character: {}", closingChar);
            lexeme.add(closing);
        }

        // Add punctuation if present
        if (punctuationText != null && !punctuationText.isEmpty()) {
            Symbol punctuation = new Symbol(punctuationText);
            logger.debug("Extracted punctuation: {}", punctuationText);
            lexeme.add(punctuation);
        }

        return lexeme;
    }

    /**
     * Create a lexeme from a matcher that has matched a word with punctuation
     * @param matcher the matcher containing the matched groups
     * @return a new Lexeme containing the word and its punctuation
     */
    private Lexeme createLexemeWithPunctuation(Matcher matcher) {
        logger.debug("Creating lexeme with punctuation: {}", matcher.group(0));
        Lexeme lexeme = new Lexeme(matcher.group(0));
        String wordText = matcher.group(1);
        String punctuationText = matcher.group(2);

        // Add the word
        Word word = new Word(wordText);
        logger.debug("Extracted word: {}", wordText);
        lexeme.add(word);

        // Add the punctuation symbol
        Symbol punctuation = new Symbol(punctuationText);
        logger.debug("Extracted punctuation: {}", punctuationText);
        lexeme.add(punctuation);

        return lexeme;
    }
}