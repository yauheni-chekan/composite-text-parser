package com.textparser.composite.impl;

import com.textparser.composite.TextComposite;
import com.textparser.util.TextConstants;

/**
 * Represents a paragraph in the text structure.
 * A paragraph is a composite node that can contain sentences.
 * According to requirements, paragraphs must start with a tab or 4 spaces.
 */
public class Paragraph extends TextComposite {
    @Override
    public String getText() {
        StringBuilder result = new StringBuilder();
        // Add paragraph indent (tab or 4 spaces)
        result.append("    "); // Use 4 spaces as default indent
        
        for (int i = 0; i < children.size(); i++) {
            result.append(children.get(i).getText());
            // Add space between sentences except for the last one
            if (i < children.size() - 1) {
                result.append(" ");
            }
        }
        
        return result.toString();
    }

    /**
     * Check if the given text starts with a valid paragraph indent
     * @param text the text to check
     * @return true if the text starts with a tab or 4 spaces
     */
    public static boolean hasValidIndent(String text) {
        return text.matches(TextConstants.PARAGRAPH_SPLIT_PATTERN + ".*");
    }
} 