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
    public static final String PARAGRAPH_SPLIT_PATTERN = "\\t|\\s{4}(?=[A-Z])"; // 4 spaces or tab and a capital letter

    // Sentence patterns
    public static final String SENTENCE_END_PATTERN = String.format("\\Q%s\\E|[.!?]", ELLIPSIS);
    public static final String SENTENCE_SPLIT_PATTERN = String.format("(?<=%s)\\s+", SENTENCE_END_PATTERN);

    // Lexeme patterns
    public static final String LEXEME_SPLIT_PATTERN = "\\s+";
    public static final String WHITESPACE_PATTERN = "\\s+";

    // Symbol patterns
    public static final String PUNCTUATION_PATTERN = String.format("\\Q%s\\E|[.,!?;:]", ELLIPSIS);
    public static final String LETTER_PATTERN = "[a-zA-Z]";
    public static final String DIGIT_PATTERN = "\\d";

    // Word patterns
    public static final String WORD_PATTERN = "[a-zA-Z]+(?:[-'][a-zA-Z]+)*";
    public static final String WORD_WITH_PUNCTUATION_PATTERN = String.format("^(%s)(%s)$", WORD_PATTERN, PUNCTUATION_PATTERN);
    public static final String WORD_BOUNDARY_PATTERN = "\\b";

    // Expression patterns
    public static final String NUMBER_PATTERN = "\\d+(?:\\.\\d+)?";
    public static final String OPERATOR_PATTERN = "[+\\-*/=<>!&|]";
    public static final String EXPRESSION_PATTERN = String.format("%s\\s*(?:%s\\s*%s\\s*)*",
        NUMBER_PATTERN, OPERATOR_PATTERN, NUMBER_PATTERN);
} 