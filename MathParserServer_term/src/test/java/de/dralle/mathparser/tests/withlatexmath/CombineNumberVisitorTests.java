/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.IntegerNode;
import de.dralle.mathparser.nodes.NumberNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;
import de.dralle.mathparser.visitors.NumberCombinationVisitor;

/**
 * @author Nils Dralle
 *
 */
class CombineNumberVisitorTests {

	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	
	
	@Test
	void testSimpleAddReplaceIntegerNotNull() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3+5");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		ExpressionNode node = ex.accept(visit);
		assertNotNull(node);
	}
	@Test
	void testSimpleAddReplaceIntegerNode() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3+5");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		ExpressionNode node = ex.accept(visit);
		assertTrue(node instanceof IntegerNode);
	}
	@Test
	void testSimpleAddReplaceNullIfNotInteger() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3.2+5.7");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		ExpressionNode node = ex.accept(visit);
		assertNull(node);
	}
	@Test
	void testSimpleAddReplaceRealResultInteger() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3.4+5.6");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		visit.setOnlyOnIntegers(false);
		ExpressionNode node = ex.accept(visit);
		assertNotNull(node);
	}
	@Test
	void testSimpleAddReplaceRealResultNotInteger() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3.4+5.61");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		visit.setOnlyOnIntegers(false);
		visit.setResultInteger(false);
		ExpressionNode node = ex.accept(visit);
		assertNotNull(node);
	}
	@Test
	void testSimpleAddReplaceRealResultNotIntegerCombineNotAllowed() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3.4+5.61");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		visit.setOnlyOnIntegers(false);
		ExpressionNode node = ex.accept(visit);
		assertNull(node);
	}
	@Test
	void testSimpleDivisionReplaceIntResultNotIntegerCombineNotAllowed() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3/5");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		ExpressionNode node = ex.accept(visit);
		assertNull(node);
	}
	@Test
	void testSimpleDivisionReplaceIntResultNotInteger() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3/5");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		visit.setResultInteger(false);
		ExpressionNode node = ex.accept(visit);
		assertNotNull(node);
	}
	@Test
	void testSimpleDivisionReplaceIntResultInteger() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("30/5");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		ExpressionNode node = ex.accept(visit);
		assertNotNull(node);
	}
	@Test
	void testReplaceReal() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3.0");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		visit.setOnlyOnIntegers(false);
		ExpressionNode node = ex.accept(visit);
		assertNotNull(node);
	}
	@Test
	void testReplaceRealNodeType() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3.0");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		visit.setOnlyOnIntegers(false);
		ExpressionNode node = ex.accept(visit);
		assertTrue(node instanceof IntegerNode);
	}
	
	@Test
	void testSimpleAddReplaceIntegerResult() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3+5");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		NumberNode node = (NumberNode) ex.accept(visit);
		assertEquals(8, node.getValue().intValue());
	}
	@Test
	void testSimpleAddReplaceRealResultIntegerResult() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3.4+5.6");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		visit.setOnlyOnIntegers(false);
		NumberNode node = (NumberNode) ex.accept(visit);
		assertEquals(9, node.getValue().intValue());
	}
	@Test
	void testSimpleAddReplaceRealResultNotIntegerResult() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3.4+5.61");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		visit.setOnlyOnIntegers(false);
		visit.setResultInteger(false);
		NumberNode node = (NumberNode) ex.accept(visit);
		assertEquals(9.01, node.getValue());
	}
	@Test
	void testSimpleDivisionReplaceIntResultNotIntegerResult() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3/5");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		visit.setResultInteger(false);
		NumberNode node = (NumberNode) ex.accept(visit);
		assertEquals(3.0d/5.0d, node.getValue());
	}
	@Test
	void testSimpleDivisionReplaceIntResultIntegerResult() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("30/5");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		NumberNode node = (NumberNode) ex.accept(visit);
		assertEquals(6, node.getValue().intValue());
	}
	@Test
	void testReplaceRealResult() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("3.0");
		NumberCombinationVisitor visit=new NumberCombinationVisitor();
		visit.setOnlyOnIntegers(false);
		NumberNode node = (NumberNode) ex.accept(visit);
		assertEquals(3, node.getValue().intValue());
	}
}
