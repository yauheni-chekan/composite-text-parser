package com.textparser.service;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Document;
import com.textparser.parser.TextParser;
import com.textparser.parser.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service for orchestrating the text parsing process.
 * Creates and configures the parser chain, then processes text into
 * the composite structure.
 */
public class TextParsingService {
    private static final Logger logger = LogManager.getLogger(TextParsingService.class);
    private final TextParser parserChain;

    public TextParsingService() {
        this.parserChain = createParserChain();
    }

    /**
     * Parse text content into a Document structure
     * @param text the text to parse
     * @return the parsed Document
     */
    public Document parseText(String text) {
        logger.info("Starting text parsing process");
        
        try {
            // Split text into paragraphs first
            String[] paragraphs = text.split("\n\\s*\n");
            Document document = new Document();
            
            for (String paragraphText : paragraphs) {
                paragraphText = paragraphText.trim();
                if (!paragraphText.isEmpty()) {
                    logger.debug("Processing paragraph: {}", paragraphText.substring(0, Math.min(50, paragraphText.length())));
                    
                    TextComponent paragraph = parserChain.parse(paragraphText);
                    if (paragraph != null) {
                        document.add(paragraph);
                        logger.debug("Successfully parsed paragraph with {} sentences", 
                                   paragraph.getChildren().size());
                    } else {
                        logger.warn("Failed to parse paragraph: {}", paragraphText.substring(0, Math.min(50, paragraphText.length())));
                    }
                }
            }
            
            logger.info("Text parsing completed. Document contains {} paragraphs", 
                       document.getParagraphs().size());
            return document;
            
        } catch (Exception e) {
            logger.error("Error during text parsing", e);
            throw new RuntimeException("Failed to parse text", e);
        }
    }

    /**
     * Create and configure the parser chain
     * Chain order: Document -> Paragraph -> Sentence -> Expression -> Lexeme -> Word -> Symbol
     */
    private TextParser createParserChain() {
        logger.debug("Creating parser chain");
        
        ParagraphParser paragraphParser = new ParagraphParser();
        SentenceParser sentenceParser = new SentenceParser();
        ExpressionParser expressionParser = new ExpressionParser();
        LexemeParser lexemeParser = new LexemeParser();
        WordParser wordParser = new WordParser();
        SymbolParser symbolParser = new SymbolParser();

        // Configure the chain
        paragraphParser.setNext(sentenceParser);
        sentenceParser.setNext(expressionParser);
        expressionParser.setNext(lexemeParser);
        lexemeParser.setNext(wordParser);
        wordParser.setNext(symbolParser);

        logger.debug("Parser chain configured successfully");
        return paragraphParser;
    }

    /**
     * Get the configured parser chain (for testing)
     * @return the parser chain
     */
    TextParser getParserChain() {
        return parserChain;
    }
} 