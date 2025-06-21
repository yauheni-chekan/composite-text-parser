package com.textparser.util;

/**
 * Utility class for vowel and consonant detection.
 * Supports both English and Russian letters.
 */
public final class VowelConsonantUtils {
    private VowelConsonantUtils() {
        // Prevent instantiation
    }

    /**
     * Check if a character is a vowel (English or Russian)
     * @param ch the character to check
     * @return true if the character is a vowel
     */
    public static boolean isVowel(char ch) {
        return String.valueOf(ch).matches(TextConstants.VOWEL_PATTERN_ENGLISH) || String.valueOf(ch).matches(TextConstants.VOWEL_PATTERN_RUSSIAN);
    }

    /**
     * Check if a character is a consonant (letter but not vowel)
     * @param ch the character to check
     * @return true if the character is a consonant
     */
    public static boolean isConsonant(char ch) {
        return String.valueOf(ch).matches(TextConstants.LETTER_PATTERN) && !isVowel(ch);
    }

    /**
     * Count vowels in a text string
     * @param text the text to analyze
     * @return number of vowels
     */
    public static int countVowels(String text) {
        return (int) text.chars()
                .mapToObj(c -> (char) c)
                .filter(VowelConsonantUtils::isVowel)
                .count();
    }

    /**
     * Count consonants in a text string
     * @param text the text to analyze
     * @return number of consonants
     */
    public static int countConsonants(String text) {
        return (int) text.chars()
                .mapToObj(c -> (char) c)
                .filter(VowelConsonantUtils::isConsonant)
                .count();
    }

    /**
     * Get detailed vowel and consonant counts for text
     * @param text the text to analyze
     * @return VowelConsonantCount object with detailed counts
     */
    public static VowelConsonantCount analyze(String text) {
        int vowels = countVowels(text);
        int consonants = countConsonants(text);
        return new VowelConsonantCount(vowels, consonants);
    }

    /**
     * Data class to hold vowel and consonant counts
     */
    public static class VowelConsonantCount {
        private final int vowels;
        private final int consonants;

        public VowelConsonantCount(int vowels, int consonants) {
            this.vowels = vowels;
            this.consonants = consonants;
        }

        public int getVowels() {
            return vowels;
        }

        public int getConsonants() {
            return consonants;
        }

        public int getTotal() {
            return vowels + consonants;
        }

        @Override
        public String toString() {
            return String.format("Vowels: %d, Consonants: %d, Total letters: %d", 
                               vowels, consonants, getTotal());
        }
    }
} 