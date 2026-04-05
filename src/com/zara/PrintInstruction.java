package com.zara;

public class PrintInstruction implements Instruction {
    private final Expression expression;


    public PrintInstruction(Expression expression) {
        this.expression = expression;
    }


    public void execute(Environment env) {
        Object value=expression.evaluate(env);
        System.out.println(value);

    }

}
