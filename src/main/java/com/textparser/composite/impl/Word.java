package com.textparser.composite.impl;

import com.textparser.composite.TextLeaf;
import com.textparser.util.TextConstants;

/**
 * Represents a word in the text structure.
 * A word is a leaf node that consists of letters.
 */
public class Word extends TextLeaf {
    public Word(String text) {
        super(text);
        if (!text.matches(TextConstants.WORD_PATTERN)) {
            throw new IllegalArgumentException("Text must be a valid word: " + text);
        }
    }

    @Override
    public int getWordCount() {
        return 1; // A Word is always a word
    }
} 