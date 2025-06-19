package com.textparser.composite.impl;

import com.textparser.composite.TextComposite;
import com.textparser.composite.TextComponent;

/**
 * Represents a lexeme in the text structure.
 * A lexeme is any part of text bounded by whitespace characters.
 * A lexeme can contain:
 * - Words
 * - Words with punctuation
 * - Numbers
 * - Math expressions
 * - Mixed content
 * - Unparseable characters
 */
public class Lexeme extends TextComposite {
    private final String originalText;

    public Lexeme(String text) {
        super();
        this.originalText = text;
    }

    /**
     * Get the original text before parsing into components
     * @return the original lexeme text
     */
    public String getOriginalText() {
        return originalText;
    }

    @Override
    public int getWordCount() {
        return getChildren().stream()
                .mapToInt(TextComponent::getWordCount)
                .sum();
    }

    @Override
    public String getText() {
        StringBuilder result = new StringBuilder();
        for (TextComponent child : getChildren()) {
            result.append(child.getText());
        }
        return result.toString();
    }
} 