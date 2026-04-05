package com.zara;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Instruction> parse() {
        List<Instruction> instructions = new ArrayList<>();

        skipNewlines();

        while (!isAtEnd()) {
            instructions.add(parseInstruction());
            skipNewlines();
        }

        return instructions;
    }

    private Instruction parseInstruction() {
        if (match(TokenType.SET)) {
            Token name = consume(TokenType.IDENTIFIER, "Expected variable name");
            consume(TokenType.EQUAL, "Expected '=' after variable name");
            Expression expr = parseComparison();
            return new AssignInstruction(name.getValue(), expr);
        }

        if (match(TokenType.SHOW)) {
            Expression expr = parseComparison();
            return new PrintInstruction(expr);
        }

        if (match(TokenType.WHEN)) {
            Expression condition = parseComparison();
            consume(TokenType.COLON, "Expected ':' after condition");
            consume(TokenType.NEWLINE, "Expected newline after ':'");

            List<Instruction> body = new ArrayList<>();
            consume(TokenType.INDENT, "Expected indentation after ':'");
            while (!isAtEnd() && !check(TokenType.DEDENT)) {
                if (check(TokenType.NEWLINE)) {
                    advance();
                    continue;
                }

                body.add(parseInstruction());
                skipNewlines();
            }
            if (check(TokenType.DEDENT)) advance();

            return new IfInstruction(condition, body);
        }

        if (match(TokenType.LOOP)) {
            Token countToken = consume(TokenType.NUMBER, "Expected repeat count");
            consume(TokenType.COLON, "Expected ':' after repeat count");
            consume(TokenType.NEWLINE, "Expected newline after ':'");

            List<Instruction> body = new ArrayList<>();
            consume(TokenType.INDENT, "Expected indentation after ':'");
            while (!isAtEnd() && !check(TokenType.DEDENT)) {
                if (check(TokenType.NEWLINE)) {
                    advance();
                    continue;
                }

                body.add(parseInstruction());
                skipNewlines();
            }
            if (check(TokenType.DEDENT)) advance();

            int count = Integer.parseInt(countToken.getValue());
            return new RepeatInstruction(count, body);
        }

        throw new RuntimeException(
                "Unexpected token: " + peek().getValue() + " at line " + peek().getLine());
    }

    private Expression parseComparison() {
        Expression left = parseExpression();

        while (match(TokenType.GREATER) || match(TokenType.LESS) || match(TokenType.EQUAL_EQUAL)) {
            String operator = previous().getValue();
            Expression right = parseExpression();
            left = new BinaryOpNode(left, operator, right);
        }

        return left;
    }

    private Expression parseExpression() {
        Expression left = parseTerm();

        while (match(TokenType.PLUS) || match(TokenType.MINUS)) {
            String operator = previous().getValue();
            Expression right = parseTerm();
            left = new BinaryOpNode(left, operator, right);
        }

        return left;
    }

    private Expression parseTerm() {
        Expression left = parsePrimary();

        while (match(TokenType.STAR) || match(TokenType.SLASH)) {
            String operator = previous().getValue();
            Expression right = parsePrimary();
            left = new BinaryOpNode(left, operator, right);
        }

        return left;
    }

    private Expression parsePrimary() {

        // 🔥 unary minus handle
        if (match(TokenType.MINUS)) {
            Expression right = parsePrimary();
            return new BinaryOpNode(new NumberNode(0), "-", right);
        }

        // 🔥 parenthesized expression handle
        if (match(TokenType.LPAREN)) {
            Expression expr = parseComparison();
            consume(TokenType.RPAREN, "Expected ')' after expression");
            return expr;
        }

        if (match(TokenType.NUMBER)) {
            return new NumberNode(Double.parseDouble(previous().getValue()));
        }

        if (match(TokenType.STRING)) {
            return new StringNode(previous().getValue());
        }

        if (match(TokenType.IDENTIFIER)) {
            return new VariableNode(previous().getValue());
        }

        throw new RuntimeException("Expected expression at line " + peek().getLine());
    }

    private void skipNewlines() {
        while (match(TokenType.NEWLINE)) {
            // skip
        }
    }

    private boolean match(TokenType type) {
        if (check(type)) {
            advance();
            return true;
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) {
            return advance();
        }
        throw new RuntimeException(message + " at line " + peek().getLine());
    }

    private boolean check(TokenType type) {
        return !isAtEnd() && peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) {
            pos++;
        }
        return tokens.get(pos - 1);
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(pos);
    }

    private Token previous() {
        return tokens.get(pos - 1);
    }
}

