package com.textparser.composite;

import java.util.Collections;
import java.util.List;

/**
 * Abstract class representing a leaf node in the text structure.
 * Leaf nodes cannot have children.
 */
public abstract class TextLeaf implements TextComponent {
    protected String text;

    public TextLeaf(String text) {
        this.text = text;
    }

    @Override
    public void add(TextComponent component) {
        throw new UnsupportedOperationException("Cannot add to a leaf component");
    }

    @Override
    public void remove(TextComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a leaf component");
    }

    @Override
    public TextComponent getChild(int index) {
        throw new UnsupportedOperationException("Leaf components have no children");
    }

    @Override
    public List<TextComponent> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void print() {
        System.out.print(text);
    }
} 