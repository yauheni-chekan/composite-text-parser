package com.textparser.operations.impl;

import com.textparser.composite.impl.Document;
import com.textparser.composite.impl.Paragraph;
import com.textparser.composite.impl.Sentence;
import com.textparser.operations.TextOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Operation to remove sentences with fewer words than the specified threshold.
 */
public class RemoveShortSentences implements TextOperation<Document> {
    private static final Logger logger = LogManager.getLogger(RemoveShortSentences.class);
    private final int minWordCount;

    public RemoveShortSentences(int minWordCount) {
        this.minWordCount = minWordCount;
    }

    @Override
    public Document execute(Document document) {
        logger.info("Removing sentences with fewer than {} words", minWordCount);
        
        Document modifiedDocument = new Document();
        int removedSentences = 0;
        int totalSentences = 0;

        for (Paragraph paragraph : document.getParagraphs()) {
            Paragraph newParagraph = new Paragraph();
            
            for (var component : paragraph.getChildren()) {
                if (component instanceof Sentence) {
                    Sentence sentence = (Sentence) component;
                    totalSentences++;
                    
                    int wordCount = sentence.getWordCount();
                    if (wordCount >= minWordCount) {
                        newParagraph.add(sentence);
                        logger.debug("Keeping sentence with {} words", wordCount);
                    } else {
                        removedSentences++;
                        logger.debug("Removing sentence with {} words: '{}'", 
                                   wordCount, sentence.getText().substring(0, 
                                   Math.min(50, sentence.getText().length())));
                    }
                }
            }
            
            // Only add paragraph if it has remaining sentences
            if (!newParagraph.getChildren().isEmpty()) {
                modifiedDocument.add(newParagraph);
            }
        }
        
        logger.info("Removed {} out of {} sentences. {} sentences remaining", 
                   removedSentences, totalSentences, totalSentences - removedSentences);
        
        return modifiedDocument;
    }

    @Override
    public String getDescription() {
        return String.format("Remove sentences with fewer than %d words", minWordCount);
    }

    /**
     * Get the minimum word count threshold
     * @return the minimum word count
     */
    public int getMinWordCount() {
        return minWordCount;
    }
} 