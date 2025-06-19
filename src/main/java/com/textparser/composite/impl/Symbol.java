package com.textparser.composite.impl;

import com.textparser.composite.TextLeaf;
import com.textparser.util.TextConstants;

/**
 * Represents a single symbol (character) in the text structure.
 * A symbol is the smallest leaf node in our composite structure.
 * Special case: "..." is treated as a single symbol for ellipsis.
 */
public class Symbol extends TextLeaf {
    public Symbol(char symbol) {
        super(String.valueOf(symbol));
    }

    public Symbol(String symbol) {
        super(symbol);
        if (symbol.length() != 1 && !symbol.equals(TextConstants.ELLIPSIS)) {
            throw new IllegalArgumentException(String.format("Symbol must be a single character or '%s'", TextConstants.ELLIPSIS));
        }
    }

    /**
     * Get the character value of this symbol
     * @return the character
     */
    public char getChar() {
        if (isEllipsis()) {
            throw new UnsupportedOperationException("Cannot get char from ellipsis symbol");
        }
        return text.charAt(0);
    }

    @Override
    public int getParagraphCount() {
        return 0;
    }

    @Override
    public int getSentenceCount() {
        return 0;
    }

    @Override
    public int getWordCount() {
        // A single symbol is never a word
        return 0;
    }


    /**
     * Check if this symbol is an ellipsis
     * @return true if the symbol is "..."
     */
    public boolean isEllipsis() {
        return text.equals(TextConstants.ELLIPSIS);
    }
} 