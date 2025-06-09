package com.textparser.parser.impl;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Sentence;
import com.textparser.parser.AbstractTextParser;
import com.textparser.util.TextConstants;

/**
 * Parser for sentences.
 * Responsible for parsing text into sentences and their lexemes.
 * A valid sentence starts with a capital letter and ends with a sentence ending.
 */
public class SentenceParser extends AbstractTextParser {
    @Override
    public TextComponent parse(String text) {
        // Check if text looks like a sentence (starts with capital letter, ends with punctuation)
        if (!text.matches("^[A-ZА-Я].*[.!?…]$")) {
            return parseNext(text);
        }
        
        // Split into lexemes by whitespace
        String[] lexemeTexts = text.split("\\s+");
        
        Sentence sentence = new Sentence();
        for (String lexemeText : lexemeTexts) {
            lexemeText = lexemeText.trim();
            if (!lexemeText.isEmpty()) {
                TextComponent lexeme = parseNext(lexemeText);
                if (lexeme != null) {
                    sentence.add(lexeme);
                }
            }
        }
        
        return sentence;
    }
}