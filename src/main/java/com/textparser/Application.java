package com.textparser;

import com.textparser.composite.impl.Document;
import com.textparser.service.FileReaderService;
import com.textparser.service.TextOperationsService;
import com.textparser.service.TextParsingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * Main application class for the Composite Text Parser.
 * Provides command-line interface for text file parsing and analysis.
 */
public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);
    
    private final FileReaderService fileReaderService;
    private final TextParsingService textParsingService;
    private final TextOperationsService textOperationsService;

    public Application() {
        this.fileReaderService = new FileReaderService();
        this.textParsingService = new TextParsingService();
        this.textOperationsService = new TextOperationsService();
    }

    public static void main(String[] args) {
        logger.info("Starting Composite Text Parser Application");
        
        Application app = new Application();
        
        if (args.length > 0) {
            // File provided as command line argument
            app.processFile(args[0]);
        } else {
            // Interactive mode
            app.runInteractiveMode();
        }
        
        logger.info("Application finished");
    }

    /**
     * Process a single file
     */
    public void processFile(String filePath) {
        try {
            logger.info("Processing file: {}", filePath);
            
            // Read file
            String text = fileReaderService.readTextFromFile(filePath);
            logger.info("File read successfully. Content length: {} characters", text.length());
            
            // Parse text
            Document document = textParsingService.parseText(text);
            logger.info("Text parsed successfully. Document contains {} paragraphs, {} sentences, {} words", 
                             document.getParagraphCount(), document.getSentenceCount(), document.getWordCount());
            
            // Perform analysis
            performAnalysis(document);
            
        } catch (Exception e) {
            logger.error("Error processing file: {}", filePath, e);
        }
    }

    /**
     * Run in interactive mode
     */
    public void runInteractiveMode() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Composite Text Parser ===");
        System.out.println("Enter the path to your text file:");
        
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            
            if ("exit".equalsIgnoreCase(input) || "quit".equalsIgnoreCase(input)) {
                break;
            }
            
            if (input.isEmpty()) {
                System.out.println("Please enter a file path or 'exit' to quit.");
                continue;
            }
            
            if (!fileReaderService.isFileReadable(input)) {
                System.out.println("File not found or not readable: " + input);
                continue;
            }
            
            processFile(input);
            
            System.out.println("\nEnter another file path or 'exit' to quit:");
        }
        
        scanner.close();
        System.out.println("Goodbye!");
    }

    /**
     * Perform all text analysis operations
     */
    private void performAnalysis(Document document) {
        System.out.println("\n=== Text Analysis Results ===");
        
        try {
            // Operation 1: Sort paragraphs by sentence count
            System.out.println("\n1. Paragraphs sorted by sentence count:");
            var sortedParagraphs = textOperationsService.sortParagraphsBySentenceCount(document);
            for (int i = 0; i < sortedParagraphs.size(); i++) {
                var paragraph = sortedParagraphs.get(i);
                System.out.printf("   Paragraph %d: %d sentences%n", 
                                i + 1, paragraph.getChildren().size());
            }
            
            // Operation 2: Find sentences with longest word
            System.out.println("\n2. Sentences with longest word:");
            var sentencesWithLongestWord = textOperationsService.findSentencesWithLongestWord(document);
            for (int i = 0; i < Math.min(sentencesWithLongestWord.size(), 3); i++) {
                var sentence = sentencesWithLongestWord.get(i);
                String text = sentence.getText();
                System.out.printf("   %s%n", text.length() > 100 ? 
                                text.substring(0, 100) + "..." : text);
            }
            if (sentencesWithLongestWord.size() > 3) {
                System.out.printf("   ... and %d more sentences%n", 
                                sentencesWithLongestWord.size() - 3);
            }
            
            // Operation 3: Example of removing short sentences
            System.out.println("\n3. Document statistics after removing sentences with < 3 words:");
            var filteredDocument = textOperationsService.removeShortSentences(document, 3);
            System.out.printf("   Original: %d paragraphs, %d sentences%n", 
                            document.getParagraphs().size(), 
                            document.getAllSentences().size());
            System.out.printf("   Filtered: %d paragraphs, %d sentences%n", 
                            filteredDocument.getParagraphs().size(), 
                            filteredDocument.getAllSentences().size());
            
            // Operation 4: Count identical words
            System.out.println("\n4. Most frequent words (case insensitive):");
            var wordCounts = textOperationsService.getDuplicateWords(document);
            wordCounts.entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .limit(10)
                    .forEach(entry -> 
                        System.out.printf("   '%s': %d times%n", entry.getKey(), entry.getValue())
                    );
            
            // Operation 5: Count vowels and consonants
            System.out.println("\n5. Vowel and consonant analysis:");
            var documentSummary = textOperationsService.getDocumentVowelConsonantSummary(document);
            System.out.printf("   Document totals: %s%n", documentSummary);
            
            var sentenceAnalysis = textOperationsService.countVowelsConsonants(document);
            System.out.printf("   Analyzed %d sentences individually%n", sentenceAnalysis.size());
            
        } catch (Exception e) {
            logger.error("Error during analysis", e);
            System.err.println("Error during analysis: " + e.getMessage());
        }
    }
} 