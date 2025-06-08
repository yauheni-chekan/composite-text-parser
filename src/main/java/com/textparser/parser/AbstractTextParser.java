package com.textparser.parser;

import com.textparser.composite.TextComponent;

/**
 * Abstract base class for text parsers.
 * Implements the Chain of Responsibility pattern with common functionality.
 */
public abstract class AbstractTextParser implements TextParser {
    protected TextParser nextParser;

    @Override
    public void setNext(TextParser nextParser) {
        this.nextParser = nextParser;
    }

    /**
     * Try to parse the text with the next parser in the chain
     * @param text the text to parse
     * @return the parsed TextComponent, or null if no parser can handle the text
     */
    protected TextComponent parseNext(String text) {
        if (nextParser != null) {
            return nextParser.parse(text);
        }
        return null;
    }

    /**
     * Check if the text matches the given pattern
     * @param text the text to check
     * @param pattern the pattern to match against
     * @return true if the text matches the pattern
     */
    protected boolean matches(String text, String pattern) {
        return text.matches(pattern);
    }

    /**
     * Split the text by the given pattern
     * @param text the text to split
     * @param pattern the pattern to split by
     * @return array of split parts
     */
    protected String[] split(String text, String pattern) {
        return text.split(pattern);
    }

    /**
     * Trim whitespace from the text
     * @param text the text to trim
     * @return the trimmed text
     */
    protected String trim(String text) {
        return text.trim();
    }
} 