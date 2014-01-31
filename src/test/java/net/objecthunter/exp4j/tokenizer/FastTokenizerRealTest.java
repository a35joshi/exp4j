package net.objecthunter.exp4j.tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.objecthunter.exp4j.exception.UnparseableExpressionException;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;
import net.objecthunter.exp4j.tokens.Token;

import org.junit.Test;

public class FastTokenizerRealTest {

	private final String defaultExpression1 = "1+6.77-14*sin(x)/log(y)-14*abs(2.445)--12*cos(x)-sqrt(x)/(sin(x)*cos(x)*cos(x/2)";
	private final String defaultExpression2 = "6.77-14*sin(x)/log(y)-14*abs(2.445)--12*cos(x)-sqrt(x)/(sin(x)*cos(x)*cos(x/2)+1";
	private final String defaultExpression3 = "-14*sin(x)/log(y)-14*abs(2.445)--12*cos(x)-sqrt(x)/(sin(x)*cos(x)*cos(x/2)+1+6.77";
	private final String defaultExpression4 = "sin(x)/log(y)-14*abs(2.445)--12*cos(x)-sqrt(x)/(sin(x)*cos(x)*cos(x/2)+1+1+6.77-14";

	private final char[] defaultCharArray = "1+6.77-14*sin(x)/log(y)-14*abs(2.445)--12*cos(x)-sqrt(x)/(sin(x)*cos(x)*cos(x/2)"
			.toCharArray();

	@Test
	public void testTokenization1() throws Exception {
		FastTokenizer tok = new FastTokenizer("2.2");
		assertFalse(tok.isEOF());
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}

	@Test
	public void testTokenization2() throws Exception {
		FastTokenizer tok = new FastTokenizer("2.2 +   3.1445 - -17");
		assertFalse(tok.isEOF());
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.UNARY_OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}

	@Test
	public void testTokenization3() throws Exception {
		FastTokenizer tok = new FastTokenizer(
				"2.2 / - sin(3.1445) * 3.1445 - -17");
		assertFalse(tok.isEOF());
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.UNARY_OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.FUNCTION);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_LEFT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_RIGHT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.UNARY_OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}

	@Test
	public void testTokenization4() throws Exception {
		String expression = "2 + 2";
		FastTokenizer tok = new FastTokenizer(expression);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}

	@Test
	public void testTokenization5() throws Exception {
		String expression = "sin(2)";
		FastTokenizer tok = new FastTokenizer(expression);
		tok.nextToken();
		assertTrue(tok.getType() == Token.FUNCTION);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_LEFT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_RIGHT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}

	@Test
	public void testTokenization6() throws Exception {
		String expression = "3 * sin(2)/0.5";
		FastTokenizer tok = new FastTokenizer(expression);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.FUNCTION);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_LEFT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_RIGHT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}

	@Test
	public void testTokenization7() throws Exception {
		String expression = "3 * sin(2+sin(12.745))/0.5";
		FastTokenizer tok = new FastTokenizer(expression);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.FUNCTION);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_LEFT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.FUNCTION);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_LEFT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_RIGHT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_RIGHT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}

	@Test
	public void testTokenization8() throws Exception {
		String expression = "log(sin(1.0) + abs(2.5))";
		FastTokenizer tok = new FastTokenizer(expression);
		tok.nextToken();
		assertTrue(tok.getType() == Token.FUNCTION);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_LEFT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.FUNCTION);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_LEFT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_RIGHT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.FUNCTION);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_LEFT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_RIGHT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_RIGHT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}

	@Test
	public void testTokenization9() throws Exception {
		String expression = "-1";
		FastTokenizer tok = new FastTokenizer(expression);
		tok.nextToken();
		assertTrue(tok.getType() == Token.UNARY_OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}

	@Test
	public void testTokenization10() throws Exception {
		String expression = "-1 * -sin(3 * (-1.2))";
		FastTokenizer tok = new FastTokenizer(expression);
	}

	@Test
	public void testTokenization11() throws Exception {
		String expression = "-1.3422E2";
		FastTokenizer tok = new FastTokenizer(expression);
		tok.nextToken();
		assertTrue(tok.getType() == Token.UNARY_OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
	}

	@Test
	public void testTokenization12() throws Exception {
		Map<String, Double> variables = new HashMap<>();
		variables.put("x", 1d);
		String expression = "-x";
		FastTokenizer tok = new FastTokenizer(expression, variables);
		tok.nextToken();
		assertTrue(tok.getType() == Token.UNARY_OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.VARIABLE);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}

	@Test
	public void testTokenization13() throws Exception {
		Map<String, Double> variables = new HashMap<>();
		variables.put("x", 1d);
		variables.put("y", 2d);
		String expression = "-x*sin(y)";
		FastTokenizer tok = new FastTokenizer(expression, variables);
		tok.nextToken();
		assertTrue(tok.getType() == Token.UNARY_OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.VARIABLE);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.FUNCTION);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_LEFT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.VARIABLE);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_RIGHT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}

	@Test
	public void testCustomOperator1() throws Exception {
		Map<String, Operator> customOperators = getCustomOperatorMap(new String[] { "!" });
		String expression = "1!";
		FastTokenizer tok = new FastTokenizer(expression, null, null,
				customOperators);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
	}

	private Map<String, Operator> getCustomOperatorMap(String[] strings) {
		Map<String, Operator> ops = new HashMap<>();
		for (String s : strings) {
			ops.put(s, new Operator(s, 2, true, Operators.PRECEDENCE_ADDITION) {
				@Override
				public double apply(double... args) {
					return args[0] + args[1];
				}
			});
		}
		return ops;
	}

	@Test
	public void testCustomOperator2() throws Exception {
		Map<String, Operator> customOperators = getCustomOperatorMap(new String[] { "$" });
		String expression = "-1$6";
		FastTokenizer tok = new FastTokenizer(expression, null, null,
				customOperators);
		tok.nextToken();
		assertTrue(tok.getType() == Token.UNARY_OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
	}

	@Test
	public void testCustomOperator3() throws Exception {
		Map<String, Operator> customOperators = getCustomOperatorMap(new String[] { "~" });
		String expression = "~1.44";
		FastTokenizer tok = new FastTokenizer(expression, null, null,
				customOperators);
		tok.nextToken();
		assertTrue(tok.getType() == Token.UNARY_OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
	}

	@Test
	public void testCustomOperator4() throws Exception {
		Map<String, Operator> customOperators = getCustomOperatorMap(new String[] { "++" });
		String expression = "1++";
		FastTokenizer tok = new FastTokenizer(expression, null, null,
				customOperators);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.isEOF());
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testCustomOperator5() throws Exception {
		Map<String, Operator> customOperators = getCustomOperatorMap(new String[] { ">=" });
		String expression = "1>>2";
		FastTokenizer tok = new FastTokenizer(expression, null, null,
				customOperators);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testCustomOperator6() throws Exception {
		Map<String, Operator> customOperators = getCustomOperatorMap(new String[] { "++" });
		String expression = "1+>2";
		FastTokenizer tok = new FastTokenizer(expression, null, null,
				customOperators);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testCustomFunction1() throws Exception {
		String expression = "invalid(1,2,3)";
		FastTokenizer tok = new FastTokenizer(expression);
		tok.nextToken();
	}

	@Test
	public void testCustomFunction2() throws Exception {
		Map<String, Function> customFunctions = getCustomFunctionMap(new String[] { "sum" });
		String expression = "sum(1,2,3)";
		FastTokenizer tok = new FastTokenizer(expression, null, customFunctions);
		tok.nextToken();
		assertTrue(tok.getType() == Token.FUNCTION);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_LEFT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.ARGUMENT_SEPARATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.ARGUMENT_SEPARATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_RIGHT);
	}

	private Map<String, Function> getCustomFunctionMap(String[] strings) {
		Map<String, Function> funcs = new HashMap<>();
		for (String name : strings) {
			funcs.put(name, new Function(name) {
				@Override
				public double apply(double... args) {
					return args[0];
				}
			});
		}
		return funcs;
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testCustomFunction3() throws Exception {
		Map<String, Function> customFunctions = getCustomFunctionMap(new String[] { "sum" });
		String expression = "su(1,2,3)";
		FastTokenizer tok = new FastTokenizer(expression, null, customFunctions);
		tok.nextToken();
	}

	@Test
	public void testCustomFunction4() throws Exception {
		Map<String, Function> customFunctions = getCustomFunctionMap(new String[] { "sum" });
		String expression = "sin(sum(1,2+3,3))";
		FastTokenizer tok = new FastTokenizer(expression, null, customFunctions);
		tok.nextToken();
		assertTrue(tok.getType() == Token.FUNCTION);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_LEFT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.FUNCTION);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_LEFT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.ARGUMENT_SEPARATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.ARGUMENT_SEPARATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_RIGHT);
		tok.nextToken();
		assertTrue(tok.getType() == Token.PARANTHESES_RIGHT);
		tok.nextToken();
		assertTrue(tok.isEOF());
	}
}
