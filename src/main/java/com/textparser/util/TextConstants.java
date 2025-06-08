package com.textparser.util;

/**
 * Constants used throughout the text parser application.
 * Contains regular expressions and other literal constants.
 */
public final class TextConstants {
    private TextConstants() {
        // Prevent instantiation
    }

    // Special symbols
    public static final String ELLIPSIS = "...";

    // Paragraph patterns
    public static final String PARAGRAPH_INDENT = "\\s{4}"; // 4 spaces
    public static final String PARAGRAPH_START_PATTERN = String.format("^(?:\\t|%s)", PARAGRAPH_INDENT);
    public static final String PARAGRAPH_END_PATTERN = "\n\n";

    // Sentence patterns
    public static final String SENTENCE_END_PATTERN = String.format("[.!?]|\\Q%s\\E", ELLIPSIS);
    public static final String SENTENCE_START_PATTERN = "^[A-Z]";
    public static final String VALID_SENTENCE_PATTERN = String.format("%s.*%s$",
        SENTENCE_START_PATTERN, SENTENCE_END_PATTERN);
    public static final String SENTENCE_SPLIT_PATTERN = String.format("(?<=%s)", SENTENCE_END_PATTERN);

    // Lexeme patterns
    public static final String LEXEME_PATTERN = "\\S+";
    public static final String WHITESPACE_PATTERN = "\\s+";

    // Symbol patterns
    public static final String PUNCTUATION_PATTERN = String.format("[.,!?;:\"'\\[\\](){}]|\\Q%s\\E", ELLIPSIS);
    public static final String LETTER_PATTERN = "[a-zA-Z]";
    public static final String DIGIT_PATTERN = "\\d";

    // Word patterns
    public static final String WORD_PATTERN = "[a-zA-Z]+(?:[-'][a-zA-Z]+)*";
    public static final String WORD_WITH_PUNCTUATION_PATTERN = "^(" + WORD_PATTERN + ")(" + PUNCTUATION_PATTERN + ")$";
    public static final String WORD_BOUNDARY_PATTERN = "\\b";

    // Expression patterns
    public static final String NUMBER_PATTERN = "\\d+(?:\\.\\d+)?";
    public static final String OPERATOR_PATTERN = "[+\\-*/=<>!&|]";
    public static final String EXPRESSION_PATTERN = String.format("%s\\s*(?:%s\\s*%s\\s*)*",
        NUMBER_PATTERN, OPERATOR_PATTERN, NUMBER_PATTERN);

    // Validation patterns
    public static final String VALID_TEXT_PATTERN = String.format("%s.*(?:%s\\s*)*%s",
        PARAGRAPH_START_PATTERN, VALID_SENTENCE_PATTERN, PARAGRAPH_END_PATTERN);
} 