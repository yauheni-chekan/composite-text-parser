package com.textparser.parser.impl;

import com.textparser.composite.TextComponent;
import com.textparser.composite.impl.Expression;
import com.textparser.interpreter.ExpressionInterpreter;
import com.textparser.parser.AbstractTextParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Parser for arithmetic expressions.
 * Uses the Interpreter pattern to evaluate expressions and replace them
 * with their calculated values in the text structure.
 */
public class ExpressionParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger(ExpressionParser.class);
    private final ExpressionInterpreter interpreter;

    public ExpressionParser() {
        this.interpreter = new ExpressionInterpreter();
    }

    @Override
    public TextComponent parse(String text) {
        logger.debug("Parsing expression: {}", text);
        if (interpreter.isValidExpression(text)) {
            logger.debug("Expression is valid: {}", text);
            try {
                double result = interpreter.evaluate(text);
                logger.debug("Successfully evaluated expression '{}' = {}", text, result);
                return new Expression(text, result);
            } catch (Exception e) {
                logger.warn("Failed to evaluate expression '{}': {}", text, e.getMessage());
                // If evaluation fails, pass to next parser
                return parseNext(text);
            }
        }
        logger.error("Invalid expression: {}", text);
        return parseNext(text);
    }
} 