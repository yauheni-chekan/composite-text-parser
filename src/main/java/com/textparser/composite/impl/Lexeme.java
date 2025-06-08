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
    public Lexeme(String text) {
        super();
    }

    @Override
    public int getWordCount() {
        // Count only children that are words
        return (int) getChildren().stream()
                .filter(child -> child instanceof Word)
                .count();
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