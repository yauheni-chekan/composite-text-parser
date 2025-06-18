package com.textparser.parser.impl;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Document;
import com.textparser.parser.AbstractTextParser;
import com.textparser.util.TextConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Parser for the document level. Splits text into paragraphs and delegates to the next parser.
 */
public class DocumentParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger(DocumentParser.class);

    @Override
    public TextComponent parse(String text) {
        Document document = new Document();

        logger.info("Extracting paragraphs from text.");
        String[] paragraphs = split(text, TextConstants.PARAGRAPH_SPLIT_PATTERN);
        for (String paragraph : paragraphs) {
            if (!trim(paragraph).isEmpty()) {
                TextComponent paragraphComponent = parseNext(paragraph);
                if (paragraphComponent != null) {
                    document.add(paragraphComponent);
                } else {
                    logger.error("Failed to parse paragraph: {}", paragraph);
                }
            }
        }
        return document;
    }
}