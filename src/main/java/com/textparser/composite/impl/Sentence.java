package com.textparser.composite.impl;

import com.textparser.composite.TextComposite;

/**
 * Represents a sentence in the text structure.
 * A sentence is a composite node that can contain words and expressions.
 */
public class Sentence extends TextComposite {
    @Override
    public String getText() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < children.size(); i++) {
            result.append(children.get(i).getText());
            // Add space between lexemes except for the last one
            if (i < children.size() - 1) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    @Override
    public int getSentenceCount() {
        return 1;
    }
} 