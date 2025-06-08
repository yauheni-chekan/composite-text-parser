package com.textparser.parser;

import com.textparser.composite.TextComponent;

/**
 * Interface for text parsers in the Chain of Responsibility pattern.
 * Each parser is responsible for parsing a specific part of the text.
 */
public interface TextParser {
    /**
     * Set the next parser in the chain
     * @param nextParser the next parser to handle the text
     */
    void setNext(TextParser nextParser);

    /**
     * Parse the given text into a TextComponent
     * @param text the text to parse
     * @return the parsed TextComponent, or null if this parser cannot handle the text
     */
    TextComponent parse(String text);
} 