package com.zara;

public enum TokenType {
    // Keywords
    SET,
    SHOW,
    WHEN,
    LOOP,

    // Literals
    NUMBER,
    STRING,
    IDENTIFIER,

    // Arithmetic operators
    PLUS,       // +
    MINUS,      // -
    STAR,       // *
    SLASH,      // /

    // Comparison operators
    GREATER,    // >
    LESS,       // <
    EQUAL_EQUAL,// ==

    // Assignment / punctuation
    EQUAL,      // =
    COLON,      // :
    LPAREN,     // (
    RPAREN,     // )

    // Structure
    INDENT,
    DEDENT,
    NEWLINE,
    EOF
}
