package ex01;

import java.util.Map;

public class Not implements BooleanExpression {
    private final BooleanExpression op;

    public Not(BooleanExpression op) {
        this.op = op;
    }

    public String toPostfixString() {
        return this.op.toPostfixString() + " !";
    }

    public boolean evaluate(Map<String, Boolean> varAssign) {
        return !this.op.evaluate(varAssign);
    }

    public BooleanExpression toDNF() {
        if (this.op instanceof Var) {
            return this;
        } else if (this.op instanceof Not) {
            return ((Not) this.op).getOp().toDNF();
        } else if (this.op instanceof And) {
            return new Or(new Not(((And) this.op).getLeftOp()).toDNF(), new Not(((And) this.op).getRightOp()).toDNF());
        } else {
            return new And(new Not(((Or) this.op).getLeftOp()).toDNF(), new Not(((Or) this.op).getRightOp())).toDNF();
        }
    }

    public BooleanExpression getOp() {
        return op;
    }
}