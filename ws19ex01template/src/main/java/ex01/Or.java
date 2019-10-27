package ex01;

public class Or implements BooleanExpression {
    private final BooleanExpression leftOp;
    private final BooleanExpression rightOp;

    public Or(BooleanExpression leftOp, BooleanExpression rightOp) {
        this.leftOp = leftOp;
        this.rightOp = rightOp;
    }

    public String toPostfixString() {
        return this.leftOp.toPostfixString() + " " + this.rightOp.toPostfixString() + " |";
    }

    public boolean evaluate(Map<String, Boolean> varAssign) {
        return (this.leftOp.evaluate() || this.rightOp.evaluate());
    }

    public List<BooleanExpression> disjunctiveTerms() {
        List<BooleanExpression> list = new ArrayList<>();
        list.addAll(this.leftOp.disjunctiveTerms());
        list.addAll(this.rightOp.disjunctiveTerms());
        return list;
    }

    public BooleanExpression toDNF() {
        return new Or(this.leftOp.toDNF(), this.rightOp.toDNF());
    }

    public BooleanExpression getLeftOp() {
        return leftOp;
    }

    public BooleanExpression getRightOp() {
        return rightOp;
    }
}