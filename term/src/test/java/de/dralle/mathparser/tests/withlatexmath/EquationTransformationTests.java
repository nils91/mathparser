package de.dralle.mathparser.tests.withlatexmath;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.SymbolicEqualityUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;
import de.dralle.mathparser.rules.GeneralTransformationRule;
import de.dralle.mathparser.rules.TransformationRuleBuilder;

public class EquationTransformationTests {

	
	@BeforeAll
	public static void setup() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}
	
	@Test
	public void testEquationTransformation() {
		String startString = "2x+3=12";
		String targetString = "2x=9";
		String ruleString = "f->f-3";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
	
	
	@Test
	public void testEquationTransformation2() {
		String startString = "2x=9";
		String targetString = "x=4.5";
		String ruleString = "f->f/2";
		EquationNode startEquation = EquationParser.buildEquationTreeFromString(startString);
		EquationNode targetEquation = EquationParser.buildEquationTreeFromString(targetString);
		
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString(ruleString);
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(startEquation, targetEquation, rule));
	}
}
