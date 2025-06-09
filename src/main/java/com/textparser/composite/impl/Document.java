package com.textparser.composite.impl;

import com.textparser.composite.TextComponent;
import com.textparser.composite.TextComposite;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the root document in the text structure.
 * A document contains paragraphs and serves as the top-level composite.
 */
public class Document extends TextComposite {
    
    /**
     * Get all paragraphs in this document
     * @return list of paragraphs
     */
    public List<Paragraph> getParagraphs() {
        return children.stream()
                .filter(child -> child instanceof Paragraph)
                .map(child -> (Paragraph) child)
                .collect(Collectors.toList());
    }

    /**
     * Get all sentences across all paragraphs
     * @return list of all sentences in the document
     */
    public List<Sentence> getAllSentences() {
        return getParagraphs().stream()
                .flatMap(paragraph -> paragraph.getChildren().stream())
                .filter(child -> child instanceof Sentence)
                .map(child -> (Sentence) child)
                .collect(Collectors.toList());
    }

    /**
     * Get all words across the entire document
     * @return list of all words in the document
     */
    public List<Word> getAllWords() {
        return getAllSentences().stream()
                .flatMap(sentence -> sentence.getChildren().stream())
                .flatMap(this::extractWords)
                .collect(Collectors.toList());
    }

    /**
     * Extract words from a component (handles lexemes and direct words)
     */
    private java.util.stream.Stream<Word> extractWords(TextComponent component) {
        if (component instanceof Word) {
            return java.util.stream.Stream.of((Word) component);
        } else if (component instanceof Lexeme) {
            return component.getChildren().stream()
                    .filter(child -> child instanceof Word)
                    .map(child -> (Word) child);
        }
        return java.util.stream.Stream.empty();
    }

    @Override
    public String getText() {
        StringBuilder result = new StringBuilder();
        for (TextComponent child : children) {
            result.append(child.getText());
            if (!child.getText().endsWith("\n\n")) {
                result.append("\n\n");
            }
        }
        return result.toString();
    }

    @Override
    public void print() {
        for (TextComponent child : children) {
            child.print();
            System.out.println();
        }
    }
} 