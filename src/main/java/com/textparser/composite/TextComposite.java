package com.textparser.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a composite node in the text structure.
 * Composite nodes can have children.
 */
public abstract class TextComposite implements TextComponent {
    protected List<TextComponent> children = new ArrayList<>();

    @Override
    public void add(TextComponent component) {
        children.add(component);
    }

    @Override
    public void remove(TextComponent component) {
        children.remove(component);
    }

    @Override
    public TextComponent getChild(int index) {
        return children.get(index);
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public String getText() {
        StringBuilder result = new StringBuilder();
        for (TextComponent child : children) {
            result.append(child.getText());
        }
        return result.toString();
    }

    @Override
    public int getParagraphCount() {
        return children.stream()
                .mapToInt(TextComponent::getParagraphCount)
                .sum();
    }
    
    @Override
    public int getSentenceCount() {
        return children.stream()
                .mapToInt(TextComponent::getSentenceCount)
                .sum();
    }

    @Override
    public int getWordCount() {
        return children.stream()
                .mapToInt(TextComponent::getWordCount)
                .sum();
    }


    @Override
    public void print() {
        for (TextComponent child : children) {
            child.print();
        }
    }
} 