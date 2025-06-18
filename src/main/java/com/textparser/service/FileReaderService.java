package com.textparser.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service for reading text files.
 * Handles file I/O operations and provides clean text content.
 */
public class FileReaderService {
    private static final Logger logger = LogManager.getLogger(FileReaderService.class);

    /**
     * Read text content from a file
     * @param filePath path to the text file
     * @return the text content
     * @throws IOException if file cannot be read
     */
    public String readTextFromFile(String filePath) throws IOException {
        logger.debug("Reading text from file: {}", filePath);
        
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            logger.error("File does not exist: {}", filePath);
            throw new IOException("File does not exist: " + filePath);
        }
        
        if (!Files.isRegularFile(path)) {
            logger.error("Path is not a regular file: {}", filePath);
            throw new IOException("Path is not a regular file: " + filePath);
        }
        
        try {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            logger.debug("Successfully read {} characters from file", content.length());
            return content;
        } catch (IOException e) {
            logger.error("Failed to read file: {}", filePath, e);
            throw e;
        }
    }

    /**
     * Check if a file exists and is readable
     * @param filePath path to check
     * @return true if file exists and is readable
     */
    public boolean isFileReadable(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.exists(path) && Files.isRegularFile(path) && Files.isReadable(path);
        } catch (Exception e) {
            logger.warn("Error checking file readability: {}", filePath, e);
            return false;
        }
    }
} 