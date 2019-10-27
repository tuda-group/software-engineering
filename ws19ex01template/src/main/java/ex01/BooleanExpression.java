package ex01;

public Interface BooleanExpression{
    String toPostfixString();
    boolean evaluate(Map<String, Boolean> varAssign);

    default List<BooleanExpression> disjunctiveTerms() {
        List<BooleanExpression> list = new ArrayList<>();
        list.add(this);
        return list;
    }

    BooleanExpression toDNF();

    static BooleanExpression parseExpression(String expr){
        Stack<BooleanExpression> stack=new Stack();
        try{
            for(int i=0;i<expr.length;i++){
                if(expr.charAt(i)=='&'){
                    BooleanExpression rightOp=stack.pop();
                    BooleanExpression leftOp=stack.pop();
                    And and=new And(leftOp,rightOp);
                    stack.push(and);
                }else if(expr.charAt(i)=='|'){
                    BooleanExpression rightOp=stack.pop();
                    BooleanExpression leftOp=stack.pop();
                    Or or=new Or(leftOp,rightOp);
                    stack.push(or);
                }else if(expr.charAt(i)=='!'){
                    BooleanExpression op=stack.pop();
                    Not not=new Not(op);
                    stack.push(not);
                }else if(expr.charAt(i)!=' '){
                    int indexOfNextSpace=expr.indexOf(' ',i)
                    String varName=expr.substring(i,indexOfNextSpace);
                    Var var=new Var(varName);
                    stack.push(var);
                    i=indexOfNextSpace;
                }
            }
        } catch(EmptyStackException e) {
            throw new IllegalArgumentException();
        }
        if(stack.size() != 1){
            throw new IllegalArgumentException();
        }
        return stack.pop();
    }
}
