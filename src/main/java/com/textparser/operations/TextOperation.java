package com.textparser.operations;

import com.textparser.composite.impl.Document;

/**
 * Strategy pattern interface for text operations.
 * Each concrete implementation provides a specific text manipulation operation.
 */
public interface TextOperation<T> {
    /**
     * Execute the operation on the given document
     * @param document the document to operate on
     * @return the result of the operation
     */
    T execute(Document document);

    /**
     * Get a description of what this operation does
     * @return operation description
     */
    String getDescription();
} 