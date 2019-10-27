package ex01;

public class Var implements BooleanExpression {
    private final String name;

    public String toPostfixString() {
        return name;
    }

    public boolean evaluate(Map<String, Boolean> varAssign) {
        return varAssign.get(this.name);
    }

    public BooleanExpression toDNF() {
        return this;
    }

    public Var(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}