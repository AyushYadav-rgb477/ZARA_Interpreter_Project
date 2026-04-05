package com.zara;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private final String source;
    private int pos = 0;
    private int line = 1;
    private java.util.Stack<Integer> indentStack = new java.util.Stack<>();

    public Tokenizer(String source) {
        this.source = source;
        indentStack.push(0);
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        boolean atLineStart = true;

        while (pos < source.length()) {
            char current = source.charAt(pos);

            // Handle indentation at the beginning of a line
            if (atLineStart) {
                atLineStart = false;
                int spaces = 0;
                while (pos < source.length() && (source.charAt(pos) == ' ' || source.charAt(pos) == '\t')) {
                    if (source.charAt(pos) == ' ') spaces++;
                    else if (source.charAt(pos) == '\t') spaces += 4;
                    pos++;
                }

                if (pos < source.length() && (source.charAt(pos) == '\n' || source.charAt(pos) == '\r')) {
                    // Blank line, ignore indentation
                    continue;
                }
                
                if (spaces > indentStack.peek()) {
                    indentStack.push(spaces);
                    tokens.add(new Token(TokenType.INDENT, "", line));
                } else if (spaces < indentStack.peek()) {
                    while (indentStack.peek() > spaces) {
                        indentStack.pop();
                        tokens.add(new Token(TokenType.DEDENT, "", line));
                    }
                }
                if (pos >= source.length()) break;
                current = source.charAt(pos);
            }

            // newline
            if (current == '\n') {
                tokens.add(new Token(TokenType.NEWLINE, "\\n", line));
                line++;
                pos++;
                atLineStart = true;
                continue;
            }
            if (current == '\r') {
                pos++;
                continue;
            }

            // whitespace skip
            if (Character.isWhitespace(current)) {
                pos++;
                continue;
            }

            // number
            if (Character.isDigit(current)) {
                tokens.add(readNumber());
                continue;
            }

            // identifier / keyword
            if (Character.isLetter(current)) {
                tokens.add(readIdentifier());
                continue;
            }

            // operators / symbols
            switch (current) {
                case '+': tokens.add(new Token(TokenType.PLUS, "+", line)); break;
                case '-': tokens.add(new Token(TokenType.MINUS, "-", line)); break;
                case '*': tokens.add(new Token(TokenType.STAR, "*", line)); break;
                case '/': tokens.add(new Token(TokenType.SLASH, "/", line)); break;
                case '=': tokens.add(new Token(TokenType.EQUAL, "=", line)); break;
                case '>': tokens.add(new Token(TokenType.GREATER, ">", line)); break;
                case '<': tokens.add(new Token(TokenType.LESS, "<", line)); break;
                case ':': tokens.add(new Token(TokenType.COLON, ":", line)); break;
                case '(': tokens.add(new Token(TokenType.LPAREN, "(", line)); break;
                case ')': tokens.add(new Token(TokenType.RPAREN, ")", line)); break;
                case '"': tokens.add(readString()); continue;
            }

            pos++;
        }

        while (indentStack.size() > 1) {
            indentStack.pop();
            tokens.add(new Token(TokenType.DEDENT, "", line));
        }

        tokens.add(new Token(TokenType.EOF, "", line));
        return tokens;
    }

    private Token readIdentifier() {
        StringBuilder sb = new StringBuilder();

        while (pos < source.length() && Character.isLetterOrDigit(source.charAt(pos))) {
            sb.append(source.charAt(pos));
            pos++;
        }

        String word = sb.toString();

        if (word.equals("set")) {
            return new Token(TokenType.SET, word, line);
        }
        if (word.equals("show")) {
            return new Token(TokenType.SHOW, word, line);
        }
        if (word.equals("when")) {
            return new Token(TokenType.WHEN, word, line);
        }
        if (word.equals("loop")) {
            return new Token(TokenType.LOOP, word, line);
        }

        return new Token(TokenType.IDENTIFIER, word, line);
    }
    private Token readNumber() {
        StringBuilder sb = new StringBuilder();

        while (pos < source.length() && Character.isDigit(source.charAt(pos))) {
            sb.append(source.charAt(pos));
            pos++;
        }

        return new Token(TokenType.NUMBER, sb.toString(), line);
    }
    private Token readString() {
        StringBuilder sb = new StringBuilder();

        pos++; // skip opening "

        while (pos < source.length() && source.charAt(pos) != '"') {
            sb.append(source.charAt(pos));
            pos++;
        }

        pos++; // skip closing "

        return new Token(TokenType.STRING, sb.toString(), line);
    }
}
