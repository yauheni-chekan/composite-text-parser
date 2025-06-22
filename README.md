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

### Core Structure Implementation
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

### Parser Implementation (Chain of Responsibility)
1. Create base parser interface/abstract class
2. Implement concrete parsers:
   - `ParagraphParser`
   - `SentenceParser`
   - `LexemeParser`
   - `WordParser`
   - `ExpressionParser`
3. Implement parser chain configuration

### Expression Interpreter
1. Create expression grammar
2. Implement expression components:
   - `Expression` interface
3. Implement expression evaluator using functional interfaces and reverse polish notation

### Text Operations
Implement five text operations:
1. Text statistics (word count, sentence count, etc.)
2. Text formatting (capitalization, spacing)
3. Expression evaluation
4. Text search
5. Text transformation

### Logging Implementation
1. Configure Log4J2
2. Implement logging for:
   - Parser operations
   - Expression evaluation
   - Text operations
   - Error handling

### Testing
1. Unit tests for:
   - Composite components
   - Parsers
   - Expression interpreter
   - Text operations
2. Integration tests
3. Performance tests

## Design Patterns Used
1. Composite Pattern - Text structure
2. Chain of Responsibility - Text parsing
3. Interpreter Pattern - Expression evaluation
4. Strategy Pattern - Text operations
5. Factory Pattern - Object creation

## Dependencies
- Java 17+
- JUnit 5
- Log4J2
- Maven
- AssertJ (for testing)

## Usage

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Building the Application

1. Clone the repository and navigate to the project directory
2. Build the project using Maven:
```bash
mvn clean compile
```

3. Run tests:
```bash
mvn test
```

4. Create executable JAR:
```bash
mvn package
```

### Running the Application

#### Method 1: Command Line with File Argument
```bash
java -jar target/composite-text-parser-1.0-SNAPSHOT-jar-with-dependencies.jar sample-text.txt
```

#### Method 2: Interactive Mode
```bash
java -jar target/composite-text-parser-1.0-SNAPSHOT-jar-with-dependencies.jar
```

When running in interactive mode, you'll see:
```
=== Composite Text Parser ===
Enter the path to your text file:
> sample-text.txt
```

Enter `exit` or `quit` to terminate the application.

### Sample Input File Format

The application expects text files with paragraphs separated by indentation (tab or 4 spaces):
```
    This is the first paragraph with a simple sentence. It contains multiple words and punctuation! The result of 2+3 equals five.
    Here is the second paragraph. This one has arithmetic: 10-5 gives us the result. Mathematical expressions like 4*6 should be evaluated properly...
    The third paragraph is shorter. It has fewer words than others...
```

### Text Operations Performed

The application automatically performs five text analysis operations:

1. **Sort Paragraphs by Sentence Count**
   - Orders paragraphs from most to least sentences
   - Useful for identifying content density

2. **Find Sentences with Longest Words**
   - Identifies sentences containing the longest words in the document
   - Helps locate complex or technical content

3. **Remove Short Sentences** (Configurable)
   - Filters out sentences with fewer than specified words
   - Useful for content cleanup and analysis

4. **Count Identical Words** (Case Insensitive)
   - Generates frequency map of all words
   - Identifies most commonly used terms

5. **Count Vowels and Consonants**
   - Analyzes vowel/consonant distribution per sentence
   - Provides phonetic analysis of the text

### Output and Reports

The application generates:
- Console output with analysis summary
- Detailed report files in the `reports/` directory
- Log files in the `logs/` directory for debugging

Sample output:
```
=== Text Analysis Results ===
Generated on: 2024-01-15 14:30:45

1. Paragraphs sorted by sentence count:
   Paragraph 1: 3 sentences
   Paragraph 2: 4 sentences
   Paragraph 3: 2 sentences

2. Sentences with longest word:
   Finally, the last paragraph demonstrates various features.
   Mathematical expressions like 4*6 should be evaluated properly...

3. Most frequent words (case insensitive):
   'the': 8 times
   'and': 6 times
   'is': 4 times

4. Vowel and consonant analysis:
   Total vowels: 245, Total consonants: 398
```

### Expression Evaluation

The application can evaluate arithmetic expressions within the text:
- Basic operations: `+`, `-`, `*`, `/`
- Expressions like `2+3`, `10-5`, `4*6`, `15/3`
- Results are processed using the Interpreter pattern
