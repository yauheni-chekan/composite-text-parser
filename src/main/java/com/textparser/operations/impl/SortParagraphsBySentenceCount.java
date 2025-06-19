package com.textparser.operations.impl;

import com.textparser.composite.impl.Document;
import com.textparser.composite.impl.Paragraph;
import com.textparser.operations.TextOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Operation to sort paragraphs by the number of sentences they contain.
 * Requirement: "Отсортировать абзацы по количеству предложений"
 */
public class SortParagraphsBySentenceCount implements TextOperation<List<Paragraph>> {
    private static final Logger logger = LogManager.getLogger(SortParagraphsBySentenceCount.class);

    @Override
    public List<Paragraph> execute(Document document) {
        logger.info("Sorting paragraphs by sentence count");
        
        List<Paragraph> sortedParagraphs = document.getParagraphs().stream()
                .sorted(Comparator.comparing(paragraph -> paragraph.getSentenceCount()))
                .collect(Collectors.toList());
        
        logger.debug("Sorted {} paragraphs by sentence count", sortedParagraphs.size());
        
        // Log the sorting results
        for (int i = 0; i < sortedParagraphs.size(); i++) {
            Paragraph p = sortedParagraphs.get(i);
            logger.debug("Paragraph {}: {} sentences", i + 1, p.getChildren().size());
        }
        
        return sortedParagraphs;
    }

    @Override
    public String getDescription() {
        return "Sort paragraphs by number of sentences (ascending order)";
    }
} 