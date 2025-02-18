package academy.javapro;

import java.util.*;
import java.util.regex.*;

/* Project: Lexical Analyzer
 * Class: Lexer.java
 * Author: MEHMET SOYDAN
 * Date: FEB 17 2024
 * This program implements a lexical analyzer that processes Java-like source code and breaks it
 * down into a sequence of tokens, such as keywords, identifiers, literals, operators, and punctuation.
 */
public class Lexer {
    // Predefined patterns for token recognition
    private static final Pattern[] PATTERNS = {
            Pattern.compile("\\s+"),                                       // whitespace
            Pattern.compile("\\b(if|else|for|while|int|float|String)\\b"), // keywords
            Pattern.compile("\\b\\d+(\\.\\d+)?\\b"),                      // literals
            Pattern.compile("==|<=|>=|!=|&&|\\|\\||[+\\-*/=<>!]"),        // operators
            Pattern.compile("[;,.(){}\\[\\]]"),                           // punctuation
            Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b")               // identifiers
    };

    // Token types corresponding to the patterns
    private static final String[] TYPES = {
            "WHITESPACE",
            "KEYWORD",
            "LITERAL",
            "OPERATOR",
            "PUNCTUATION",
            "IDENTIFIER"
    };

    private String input;         // Input source code
    private List<String[]> tokens; // List to store tokens
    private int position;         // Current position in the input

    // ---------------------------------------------------------------
    // Constructor to initialize the lexer with the input string
    public Lexer(String input) {
        this.input = input;
        this.tokens = new ArrayList<>();
        this.position = 0;
    }

    // ---------------------------------------------------------------
    // Tokenize the input string and categorize tokens
    public void tokenize() {
        while (position < input.length()) {
            String remainingInput = input.substring(position);
            boolean matchFound = false;

            // Try to match each pattern
            for (int i = 0; i < PATTERNS.length; i++) {
                Matcher matcher = PATTERNS[i].matcher(remainingInput);
                if (matcher.lookingAt()) {
                    String matchedText = matcher.group();
                    if (!TYPES[i].equals("WHITESPACE")) {
                        // Add token to the list if it's not whitespace
                        tokens.add(new String[]{TYPES[i], matchedText});
                    }
                    position += matchedText.length(); // Update position
                    matchFound = true;
                    break;
                }
            }

            // If no pattern matches, throw an exception for invalid input
            if (!matchFound) {
                throw new RuntimeException("Invalid input at position " + position);
            }
        }
    }

    // ---------------------------------------------------------------
    // Return the list of tokens
    public List<String[]> getTokens() {
        return tokens;
    }

    // ---------------------------------------------------------------
    // Main method to test the lexer
    public static void main(String[] args) {
        String code = "int x = 10; if (x > 5) { x = x + 1; }";
        Lexer lexer = new Lexer(code);
        lexer.tokenize();
        for (String[] token : lexer.getTokens()) {
            System.out.println(token[0] + ": " + token[1]);
        }
    }
}
