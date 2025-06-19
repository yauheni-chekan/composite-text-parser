package com.textparser.composite.impl;

import com.textparser.composite.TextLeaf;

/**
 * Represents an arithmetic expression in the text structure.
 * According to requirements, expressions should be evaluated and the result
 * should replace the original expression in the final text.
 */
public class Expression extends TextLeaf {
    private final String originalExpression;
    private final double evaluatedValue;

    public Expression(String originalExpression, double evaluatedValue) {
        super(String.valueOf(evaluatedValue));
        this.originalExpression = originalExpression;
        this.evaluatedValue = evaluatedValue;
    }

    /**
     * Get the original expression before evaluation
     * @return the original expression string
     */
    public String getOriginalExpression() {
        return originalExpression;
    }

    /**
     * Get the evaluated numeric value
     * @return the calculated value
     */
    public double getEvaluatedValue() {
        return evaluatedValue;
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
        return 0; // Expressions are not words
    }

    @Override
    public String getText() {
        // Return the evaluated value as required by specification
        return String.valueOf(evaluatedValue);
    }
} 