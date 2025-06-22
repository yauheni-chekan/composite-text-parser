package com.textparser.util;

/**
 * Constants used throughout the text parser application.
 * Contains regular expressions and other literal constants.
 */
public final class TextConstants {
    private TextConstants() {
        // Prevent instantiation
    }

    // File paths
    public static final String REPORT_FOLDER_PATH = "reports";
    public static final String OUTPUT_FILE_PATH = "text_analysis_report.txt";

    // Date and time patterns
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_PATTERN_FOR_FILE_NAME = "yyyy-MM-dd_HH-mm-ss";

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
    public static final String PUNCTUATION_PATTERN = String.format("\\Q%s\\E|[.,!?;:-]", ELLIPSIS);
    public static final String LETTER_PATTERN = "[а-яА-Яa-zA-Z]";
    public static final String VOWEL_PATTERN_RUSSIAN = "[аеёиоуыэюяАЕЁИОУЫЭЮЯ]";
    public static final String VOWEL_PATTERN_ENGLISH = "[aeiouyAEIOUY]";
    public static final String DIGIT_PATTERN = "\\d";

    // Word patterns
    public static final String WORD_PATTERN = "[а-яА-Яa-zA-Z0-9_]+(?:[-\\.'][а-яА-Яa-zA-Z0-9_]+)*";
    public static final String WORD_WITH_PUNCTUATION_PATTERN = String.format("^(%s)(%s)$", WORD_PATTERN, PUNCTUATION_PATTERN);
    
    // New patterns for bracketed words
    public static final String BRACKET_PATTERN = "[\\[\\](){}]";
    public static final String QUOTE_PATTERN = "[\"'“”]";
    public static final String WORD_WITH_BRACKETS_PATTERN = String.format("^(%s)?(%s)(%s)?(%s)?$", 
        BRACKET_PATTERN, WORD_PATTERN, BRACKET_PATTERN, PUNCTUATION_PATTERN);
    public static final String WORD_WITH_QUOTES_PATTERN = String.format("^(%s)?(%s)(%s)?(%s)?$", 
        QUOTE_PATTERN, WORD_PATTERN, QUOTE_PATTERN, PUNCTUATION_PATTERN);

    // Expression patterns
    public static final String NUMBER_PATTERN = "\\d+(?:\\.\\d+)?";
    public static final String OPERATOR_PATTERN = "[+\\-*/=<>!&|]+";
    public static final String EXPRESSION_PATTERN = String.format("%s\\s*(?:%s\\s*%s\\s*)*",
        NUMBER_PATTERN, OPERATOR_PATTERN, NUMBER_PATTERN);
    public static final String TOKEN_PATTERN = String.format("(%s)|(%s)", NUMBER_PATTERN, OPERATOR_PATTERN);
} 