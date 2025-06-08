package com.textparser.composite;

import java.util.List;

/**
 * Interface representing a component in the text structure.
 * This is the base interface for the Composite pattern.
 */
public interface TextComponent {
    /**
     * Add a child component
     * @param component the component to add
     */
    void add(TextComponent component);

    /**
     * Remove a child component
     * @param component the component to remove
     */
    void remove(TextComponent component);

    /**
     * Get a child component by index
     * @param index the index of the child
     * @return the child component
     */
    TextComponent getChild(int index);

    /**
     * Get all child components
     * @return list of child components
     */
    List<TextComponent> getChildren();

    /**
     * Get the text content of this component
     * @return the text content
     */
    String getText();

    /**
     * Get the number of words in this component
     * @return word count
     */
    int getWordCount();

    /**
     * Print the component's content
     */
    void print();
} 