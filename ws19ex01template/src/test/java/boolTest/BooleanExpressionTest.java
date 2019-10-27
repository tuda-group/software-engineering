package boolTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import ex01.And;
import ex01.BooleanExpression;
import ex01.Not;
import ex01.Or;
import ex01.Var;

@DisplayName("Boolean Expression Test")
class BooleanExpressionTest {
	
	@Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("Parse tests")
    class ParseTests {

        @DisplayName("parse: VAR")
        @Test
        public void parseVar() {
            BooleanExpression parsed = BooleanExpression.parseExpression("let");
            assertTrue(parsed instanceof Var);
            assertEquals(((Var) parsed).getName(), "let");
        }
        
        @DisplayName("parse: A AND B")
        @Test
        public void parseAnd() {
            BooleanExpression parsed = BooleanExpression.parseExpression("a b &");
            assertTrue(parsed instanceof And);
            assertEquals(((Var) ((And) parsed).getLeftOp()).getName(), "a");
            assertEquals(((Var) ((And) parsed).getRightOp()).getName(), "b");
        }
        
        @DisplayName("parse: A OR B")
        @Test
        public void parseOr() {
            BooleanExpression parsed = BooleanExpression.parseExpression("a b |");
            assertTrue(parsed instanceof Or);
            assertEquals(((Var) ((Or) parsed).getLeftOp()).getName(), "a");
            assertEquals(((Var) ((Or) parsed).getRightOp()).getName(), "b");
        }
        
        @DisplayName("parse: NOT VAR")
        @Test
        public void parseNot() {
            BooleanExpression parsed = BooleanExpression.parseExpression("varname !");
            assertTrue(parsed instanceof Not);
            assertEquals(((Var) ((Not) parsed).getOp()).getName(), "varname");
        }
        
        @DisplayName("parse: nested")
        @Test
        public void parseNested() {
            BooleanExpression parsed = BooleanExpression.parseExpression("a b ! & c |");
            assertTrue(parsed instanceof Or);
            BooleanExpression leftOpOr = ((Or) parsed).getLeftOp();
            BooleanExpression rightOpOr = ((Or) parsed).getRightOp();
            assertTrue(leftOpOr instanceof And);
            assertTrue(rightOpOr instanceof Var);
            assertEquals(((Var) rightOpOr).getName(), "c");
            assertTrue(((And) leftOpOr).getLeftOp() instanceof Var);
            assertTrue(((And) leftOpOr).getRightOp() instanceof Not);
            assertEquals(((Var) ((And) leftOpOr).getLeftOp()).getName(), "a");
            assertEquals(((Var) ((Not) ((And) leftOpOr).getRightOp()).getOp()).getName(), "b");
        }
    }
	
	@Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("To String tests")
    class ToStringTests {
		
		@DisplayName("ToPostFixString 1")
        @Test
        public void toStringTest1() {
			BooleanExpression expr = new And(new Not(new Var("foo")), new Var("bar"));
			assertEquals(expr.toPostfixString(), "foo ! bar &");
		}
		
		@DisplayName("ToPostFixString 2")
        @Test
        public void toStringTest2() {		
			BooleanExpression expr = new Not(new And(new Or(new Not(new Var("a")), new Not(new Var("b"))), new Var("c")));
			assertEquals(expr.toPostfixString(), "a ! b ! | c & !");
		}
	}
	
	@Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("Evaluate tests")
    class EvaluateTests {
	
		@DisplayName("Evaluate 1")
        @Test
        public void evaluateTest1() {
			
			Map<String, Boolean> varAssign = new HashMap<>();
			varAssign.put("foo", true);
			varAssign.put("bar", false);
			
			BooleanExpression expr = new And(new Not(new Var("foo")), new Var("bar"));
			assertFalse(expr.evaluate(varAssign));		
		}
		
		@DisplayName("Evaluate 2")
        @Test
        public void evaluateTest2() {
			
			Map<String, Boolean> varAssign = new HashMap<>();
			varAssign.put("a", true);
			varAssign.put("b", true);
			varAssign.put("c", true);
			varAssign.put("d", false);
			
			BooleanExpression expr = new Not(new And(new Or(new Not(new Var("a")), new Not(new Var("b"))), new Var("c")));
			assertTrue(expr.evaluate(varAssign));		
		}
		
	}
	
	@Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("DNF tests")
    class DNFTests {
		
		@DisplayName("DNF 1")
        @Test
        public void dnfTest1() {
			BooleanExpression expr = new Not(new And(new Or(new Not(new Var("a")), new Not(new Var("b"))), new Var("c")));
			assertEquals(expr.toDNF().toPostfixString(), "a b & c ! |");	
		}
		
		@DisplayName("DNF 2")
        @Test
        public void dnfTest2() {
			BooleanExpression expr = new And(new Or(new Var("a"), new Var("b")) , new Or(new Not(new Var("c")), new Var("d")));
			assertEquals(expr.toDNF().toPostfixString(), "a c ! & a d & | b c ! & | b d & |");	
		}
	}
}


