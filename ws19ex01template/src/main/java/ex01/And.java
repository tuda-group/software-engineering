package ex01;

public class And implements BooleanExpression {
    private final BooleanExpression leftOp;
    private final BooleanExpression rightOp;

    public And(BooleanExpression leftOp, BooleanExpression rightOp) {
        this.leftOp = leftOp;
        this.rightOp = rightOp;
    }

    public String toPostfixString() {
        return this.leftOp.toPostfixString() + " " + this.rightOp.toPostfixString() + " &";
    }

    public boolean evaluate(Map<String, Boolean> varAssign) {
        return (this.leftOp.evaluate() && this.rightOp.evaluate());
    }

    public BooleanExpression toDNF() {
        BooleanExpression a = this.leftOp.toDNF();
        BooleanExpression b = this.rightOp.toDNF();
        if ((!a instanceof Or) && (!b instanceof Or)) {
            return new And(a, b);
        } else {
            List<BooleanExpression> aDisjunctiveTerms = a.disjunctiveTerms();
            List<BooleanExpression> bDisjunctiveTerms = b.disjunctiveTerms();
            List<BooleanExpression> andCrossProduct = new ArrayList<>();
            for (BooleanExpression exprA : aDisjunctiveTerms) {
                for (BooleanExpression exprB : bDisjunctiveTerms) {
                    andCrossProduct.add(new And(exprA, exprB));
                }
            }
            BooleanExpression dnf = andCrossProduct.get(0);
            for (int i = 1; i < andCrossProduct.size(); i++) {
                dnf = new Or(dnf, andCrossProduct.get(i));
            }
            return dnf;
        }
    }

    public BooleanExpression getLeftOp() {
        return leftOp;
    }

    public BooleanExpression getRightOp() {
        return rightOp;
    }
}