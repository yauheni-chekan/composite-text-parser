package com.textparser;

import com.textparser.composite.impl.Document;
import com.textparser.service.FileReaderService;
import com.textparser.service.TextOperationsService;
import com.textparser.service.TextOperationsService.TextAnalysisReport;
import com.textparser.service.TextParsingService;
import com.textparser.util.TextConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.format.DateTimeFormatter;
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
     * Perform all text analysis operations and save results to file
     */
    private void performAnalysis(Document document) {
        TextAnalysisReport report;
        
        try {
            report = textOperationsService.performCompleteAnalysis(document);
            String reportFileName = String.format("%s_%s", report.reportDate.format(DateTimeFormatter.ofPattern(TextConstants.DATE_TIME_PATTERN_FOR_FILE_NAME)), TextConstants.OUTPUT_FILE_PATH);
            // Write analysis to file
            String reportPath = textOperationsService.writeReportToFile(report, reportFileName);
            logger.info("Analysis results saved to: {}", reportPath);
        } catch (Exception e) {
            logger.error("Error during analysis", e);
        }
    }
} 