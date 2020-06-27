/**
 * 
 */
package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.SymbolIdentifier;
import de.dralle.mathparser.SymbolTable;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;
import de.dralle.mathparser.visitors.ExpressionTreeSymbolTableVisitor;

/**
 * @author Nils Dralle
 *
 */
class SymbolTableTests {
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	@Test
	void testSymbolTableCreation() {
		ExpressionNode sampleExpression = ExpressionParser.buildExpressionTreeFromString("a+b");
		assertNotNull(sampleExpression.accept(new ExpressionTreeSymbolTableVisitor()));
	}
	@Test
	void testSymbolTableIdentifiersCount() {
		ExpressionNode sampleExpression = ExpressionParser.buildExpressionTreeFromString("a+b");
		SymbolTable st = (sampleExpression.accept(new ExpressionTreeSymbolTableVisitor()));
		SymbolIdentifier[] foundIdentifiers = st.getIdentifiersAsArray();
		assertEquals(2, foundIdentifiers.length);
	}
	@Test
	void testSymbolTableIdentifiersCountNested() {
		ExpressionNode sampleExpression = ExpressionParser.buildExpressionTreeFromString("ac+bd");
		SymbolTable st = (sampleExpression.accept(new ExpressionTreeSymbolTableVisitor()));
		SymbolIdentifier[] foundIdentifiers = st.getIdentifiersAsArray();
		assertEquals(4, foundIdentifiers.length);
	}
	@Test
	void testSymbolTableNIdentifiersCountNested() {
		ExpressionNode sampleExpression = ExpressionParser.buildExpressionTreeFromString("ab+ab");
		SymbolTable st = (sampleExpression.accept(new ExpressionTreeSymbolTableVisitor()));
		SymbolIdentifier[] foundIdentifiers = st.getIdentifiersAsArray();
		assertEquals(2, foundIdentifiers.length);
	}

}
