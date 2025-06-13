package com.textparser.parser.impl;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Sentence;
import com.textparser.parser.AbstractTextParser;
import com.textparser.util.TextConstants;

/**
 * Parser for sentences.
 * Responsible for parsing sentences into lexemes.
 */
public class SentenceParser extends AbstractTextParser {
    @Override
    public TextComponent parse(String text) {
        Sentence sentence = new Sentence();
        
        // Split into lexemes by whitespace
        String[] lexemeTexts = split(text, TextConstants.LEXEME_SPLIT_PATTERN);
        
        for (String lexemeText : lexemeTexts) {
            String lexeme = trim(lexemeText);
            if (!lexeme.isEmpty()) {
                TextComponent lexemeComponent = parseNext(lexeme);
                if (lexemeComponent != null) {
                    sentence.add(lexemeComponent);
                }
            }
        }
        return sentence;
    }
}