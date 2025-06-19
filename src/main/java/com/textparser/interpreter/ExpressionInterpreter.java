package com.textparser.interpreter;

import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;

import com.textparser.util.TextConstants;

/**
 * Expression interpreter using functional interfaces.
 * Implements the Interpreter pattern for evaluating arithmetic expressions.
 * Supports basic arithmetic operations: +, -, *, /
 */
public class ExpressionInterpreter {
    
    private final Map<String, BinaryOperator<Double>> operators;
    private final Map<String, Integer> precedence;
    
    public ExpressionInterpreter() {
        // Define operators using functional interfaces
        operators = Map.of(
            "+", Double::sum,
            "-", (a, b) -> a - b,
            "*", (a, b) -> a * b,
            "/", (a, b) -> {
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
            }
        );
        
        // Define operator precedence
        precedence = Map.of(
            "+", 1,
            "-", 1,
            "*", 2,
            "/", 2
        );
    }
    
    /**
     * Evaluate an arithmetic expression
     * @param expression the expression to evaluate
     * @return the calculated result
     * @throws IllegalArgumentException if the expression is invalid
     */
    public double evaluate(String expression) {
        try {
            return evaluatePostfix(infixToPostfix(expression.trim()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid expression: " + expression, e);
        }
    }
    
    /**
     * Check if the given string is a valid arithmetic expression
     * @param text the text to check
     * @return true if it's a valid expression
     */
    public boolean isValidExpression(String text) {
        String pattern = TextConstants.EXPRESSION_PATTERN;
        return text.matches(pattern);
    }
    
    /**
     * Convert infix expression to postfix notation (Shunting Yard algorithm)
     */
    private String infixToPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<String> operatorStack = new Stack<>();
        
        String[] tokens = tokenize(infix);
        final String LEFT_PAREN = "(";
        final String RIGHT_PAREN = ")";
        
        for (String token : tokens) {
            if (isNumber(token)) {
                output.append(token).append(" ");
            } else if (operators.containsKey(token)) {
                while (!operatorStack.isEmpty() && 
                       operators.containsKey(operatorStack.peek()) &&
                       precedence.get(operatorStack.peek()) >= precedence.get(token)) {
                    output.append(operatorStack.pop()).append(" ");
                }
                operatorStack.push(token);
            } else if (LEFT_PAREN.equals(token)) {
                operatorStack.push(token);
            } else if (RIGHT_PAREN.equals(token)) {
                boolean foundLeftParen = false;
                while (!operatorStack.isEmpty()) {
                    String op = operatorStack.pop();
                    if (LEFT_PAREN.equals(op)) {
                        foundLeftParen = true;
                        break;
                    } else {
                        output.append(op).append(" ");
                    }
                }
                if (!foundLeftParen) {
                    throw new IllegalArgumentException("Mismatched parentheses in expression");
                }
            }
        }
        
        while (!operatorStack.isEmpty()) {
            String op = operatorStack.pop();
            if (LEFT_PAREN.equals(op) || RIGHT_PAREN.equals(op)) {
                throw new IllegalArgumentException("Mismatched parentheses in expression");
            }
            output.append(op).append(" ");
        }
        
        return output.toString().trim();
    }
    
    /**
     * Evaluate a postfix expression
     */
    private double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split(TextConstants.WHITESPACE_PATTERN);
        
        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (operators.containsKey(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression");
                }
                double b = stack.pop();
                double a = stack.pop();
                double result = operators.get(token).apply(a, b);
                stack.push(result);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }
        
        return stack.pop();
    }
    
    /**
     * Tokenize the expression into numbers and operators
     */
    private String[] tokenize(String expression) {
        Pattern pattern = Pattern.compile(TextConstants.TOKEN_PATTERN);
        Matcher matcher = pattern.matcher(expression);
        
        java.util.List<String> tokens = new java.util.ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        
        return tokens.toArray(new String[0]);
    }
    
    /**
     * Check if a token is a number
     */
    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
} 