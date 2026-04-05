package com.zara;

public class BinaryOpNode implements Expression{
    private Expression left;
    private String operator;
    private Expression right;
    public BinaryOpNode(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Object evaluate(Environment env) {
        Object leftVal= left.evaluate(env);
        Object rightVal= right.evaluate(env);

        if(operator.equals("+")) {
            return ((Double) leftVal) + ((Double) rightVal);
        }
        if (operator.equals("-")) {
            return ((Double) leftVal)-((Double) rightVal);
        }
        if (operator.equals("*")) {
            return ((Double) leftVal)*((Double) rightVal);
        }
        if(operator.equals("/")){
            return ((Double) leftVal)/((Double) rightVal);
        }
        if(operator.equals(">")) {
            return ((Double) leftVal)>((Double) rightVal);
        }
        if(operator.equals("<")) {
            return ((Double) leftVal) < ((Double) rightVal);
        }
        if(operator.equals("==")) {
            return leftVal.equals(rightVal);
        }

        throw new RuntimeException("Unknown operator: "+ operator);

    }


}


