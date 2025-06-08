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
        if (!matches(text, TextConstants.VALID_SENTENCE_PATTERN)) {
            return parseNext(text);
        }
        
        // Split into lexemes
        String[] lexemeTexts = split(text, TextConstants.WHITESPACE_PATTERN);
        
        Sentence sentence = new Sentence();
        for (String lexemeText : lexemeTexts) {
            lexemeText = trim(lexemeText);
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