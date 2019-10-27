package ex01;

public class Not implements BooleanExpression {
    private final BooleanExpression op;

    public Not(BooleanExpression op) {
        this.op = op;
    }

    public String toPostfixString() {
        return this.op.toPostfixString() + " !";
    }

    public boolean evaluate(Map<String, Boolean> varAssign) {
        return !this.op.evaluate();
    }

    public BooleanExpression toDNF() {
        if (this.op instanceof Var) {
            return this;
        } else if (this.op instanceof Not) {
            return toDNF(this.op.getOp());
        } else if (this.op instanceof And) {
            return new Or(new Not(this.op.getLeftOp()).toDNF(), new Not(this.op.getRightOp()).toDNF());
        } else {
            return new And(new Not(this.op.getLeftOp()), new Not(this.op.getRightOp()));
        }
    }

    public BooleanExpression getOp() {
        return op;
    }
}