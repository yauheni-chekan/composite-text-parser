# Composite Text Parser

A Java application that parses text files and performs various operations using design patterns.

## Requirements Analysis

### Core Requirements
1. Parse text into a composite structure (paragraphs, sentences, lexemes, words, expressions, symbols)
2. Implement five different text operations
3. Use Composite pattern for text structure
4. Use Chain of Responsibility for parsing
5. Use Interpreter pattern for arithmetic expressions
6. Implement logging with Log4J2
7. Ensure extensibility without code rewriting
8. Maintain clean architecture with separate entity and logic classes
9. Include unit tests

## Implementation Plan

### Phase 1: Project Setup
1. Set up Maven/Gradle project structure
2. Configure Log4J2
3. Set up testing framework (JUnit 5)
4. Create basic project documentation

### Phase 2: Core Structure Implementation
1. Create Composite Pattern Components:
   - `TextComponent` interface
   - `TextLeaf` abstract class
   - `TextComposite` abstract class
   - Concrete implementations:
     - `Paragraph`
     - `Sentence`
     - `Lexeme`
     - `Word`
     - `Expression`
     - `Symbol`

2. Define Regular Expressions Constants:
   - Paragraph detection (tab or 4 spaces)
   - Sentence endings (., ?, !, ...)
   - Word boundaries
   - Expression patterns

### Phase 3: Parser Implementation (Chain of Responsibility)
1. Create base parser interface/abstract class
2. Implement concrete parsers:
   - `ParagraphParser`
   - `SentenceParser`
   - `LexemeParser`
   - `WordParser`
   - `ExpressionParser`
3. Implement parser chain configuration

### Phase 4: Expression Interpreter
1. Create expression grammar
2. Implement expression components:
   - `Expression` interface
   - `NumberExpression`
   - `BinaryExpression`
   - `UnaryExpression`
3. Implement expression evaluator using functional interfaces

### Phase 5: Text Operations
Implement five text operations:
1. Text statistics (word count, sentence count, etc.)
2. Text formatting (capitalization, spacing)
3. Expression evaluation
4. Text search
5. Text transformation

### Phase 6: Logging Implementation
1. Configure Log4J2
2. Implement logging for:
   - Parser operations
   - Expression evaluation
   - Text operations
   - Error handling

### Phase 7: Testing
1. Unit tests for:
   - Composite components
   - Parsers
   - Expression interpreter
   - Text operations
2. Integration tests
3. Performance tests

### Phase 8: Documentation and Cleanup
1. Code documentation
2. Usage examples
3. Performance optimization
4. Code review and cleanup

## Project Structure
```
src/
├── main/
│ ├── java/
│ │ └── com/
│ │ └── textparser/
│ │ ├── composite/
│ │ ├── parser/
│ │ ├── interpreter/
│ │ ├── operations/
│ │ └── util/
│ └── resources/
│ └── log4j2.xml
└── test/
└── java/
└── com/
└── textparser/
├── composite/
├── parser/
├── interpreter/
└── operations/
```

## Design Patterns Used
1. Composite Pattern - Text structure
2. Chain of Responsibility - Text parsing
3. Interpreter Pattern - Expression evaluation
4. Strategy Pattern - Text operations
5. Factory Pattern - Object creation

## Dependencies
- JUnit 5
- Log4J2
- Maven/Gradle
- AssertJ (for testing)

## Next Steps
1. Set up project structure
2. Implement basic composite components
3. Create parser chain
4. Add expression interpreter
5. Implement text operations
6. Add logging
7. Write tests
