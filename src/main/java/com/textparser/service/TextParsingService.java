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
            // Pass the whole text to the parser chain (now starts with DocumentParser)
            TextComponent document = parserChain.parse(text);
            if (document instanceof Document) {
                logger.info("Text parsing completed. Document contains {} paragraphs", ((Document) document).getParagraphs().size());
                return (Document) document;
            } else {
                logger.error("Parser chain did not return a Document instance");
                throw new RuntimeException("Failed to parse text: not a Document");
            }
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
        
        DocumentParser documentParser = new DocumentParser();
        ParagraphParser paragraphParser = new ParagraphParser();
        SentenceParser sentenceParser = new SentenceParser();
        LexemeParser lexemeParser = new LexemeParser();
        WordParser wordParser = new WordParser();
        SymbolParser symbolParser = new SymbolParser();
        ExpressionParser expressionParser = new ExpressionParser();

        // Configure the chain
        documentParser.setNext(paragraphParser);
        paragraphParser.setNext(sentenceParser);
        sentenceParser.setNext(lexemeParser);
        lexemeParser.setNext(wordParser);
        wordParser.setNext(expressionParser);
        expressionParser.setNext(symbolParser);

        logger.debug("Parser chain configured successfully");
        return documentParser;
    }

    /**
     * Get the configured parser chain (for testing)
     * @return the parser chain
     */
    TextParser getParserChain() {
        return parserChain;
    }
} 