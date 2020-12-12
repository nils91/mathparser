/**
 * 
 */
package de.dralle.mathparser.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.ExpressionUtil;
import de.dralle.mathparser.Functions;
import de.dralle.mathparser.NumericalEquationUtil;
import de.dralle.mathparser.SymbolIdentifier;
import de.dralle.mathparser.SymbolTable;
import de.dralle.mathparser.SymbolicEqualityUtil;
import de.dralle.mathparser.TransformationRuleParser;
import de.dralle.mathparser.nodes.AbsoluteNode;
import de.dralle.mathparser.nodes.AcosNode;
import de.dralle.mathparser.nodes.AcotNode;
import de.dralle.mathparser.nodes.AsinNode;
import de.dralle.mathparser.nodes.AtanNode;
import de.dralle.mathparser.nodes.BinomNode;
import de.dralle.mathparser.nodes.CosNode;
import de.dralle.mathparser.nodes.CoshNode;
import de.dralle.mathparser.nodes.CotNode;
import de.dralle.mathparser.nodes.CothNode;
import de.dralle.mathparser.nodes.Degree2RadiansConversionNode;
import de.dralle.mathparser.nodes.DeterminantNode;
import de.dralle.mathparser.nodes.EqualityNode;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.FunctionNode;
import de.dralle.mathparser.nodes.IntegerNode;
import de.dralle.mathparser.nodes.LdNode;
import de.dralle.mathparser.nodes.LgNode;
import de.dralle.mathparser.nodes.LnNode;
import de.dralle.mathparser.nodes.MathmaticalRootNode;
import de.dralle.mathparser.nodes.MultiAddNode;
import de.dralle.mathparser.nodes.MultiMultiplyNode;
import de.dralle.mathparser.nodes.RealNode;
import de.dralle.mathparser.nodes.SinNode;
import de.dralle.mathparser.nodes.SinhNode;
import de.dralle.mathparser.nodes.SqrtNode;
import de.dralle.mathparser.nodes.TanNode;
import de.dralle.mathparser.nodes.TanhNode;
import de.dralle.mathparser.nodes.TextNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.parser.latexmath.LatexMathTransformationRuleParser;
import de.dralle.mathparser.rules.CombineAddNodes;
import de.dralle.mathparser.rules.CommonFactorCombinationRule2;
import de.dralle.mathparser.rules.CompositeTransformationRule;
import de.dralle.mathparser.rules.GeneralTransformationRule;
import de.dralle.mathparser.rules.ReducePowerRule;
import de.dralle.mathparser.rules.RepeatingTransformationRule;
import de.dralle.mathparser.rules.TransformationRuleBuilder;
import de.dralle.mathparser.visitors.CalculateNumericalValueVisitor;

/**
 * All tests in here were used during development to test for a specific issue
 * and fix bugs. All test with the suffix '_old' are tests referring to an old,
 * private tracker.
 * 
 * @author Nils Dralle
 *
 */
class IssueTests {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		EquationParser.setParser(new LatexMathEquationParser());
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
	}

	@Test
	void possible_issue_old() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("\\sin{-1}");
		double r = ex.accept(new CalculateNumericalValueVisitor());
		System.out.println(r);// should be -0.8414...
	}

	@Test
	void issue29_old() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("9.1");
		if (ex instanceof RealNode) {
			assertEquals(9.1, ((RealNode) ex).getValue().doubleValue());
		} else {
			fail("#29");
		}
	}

	@Test
	void issue22_old() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("f*f+e");
		SymbolTable st = SymbolTable.fromTree(ex);
		assertEquals(2, st.getIdentifierCount("f", null));
		assertEquals(1, st.getIdentifierCount("e", null));

	}

	@Test
	void issue25_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("a^n");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("a^{(n-1)}*a");
		ReducePowerRule rule = new ReducePowerRule();
		rule.setOnlyOnIntegers(false);
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(start);
		} catch (Exception e) {
			fail("#25");
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void issue31_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("a^{(n-1)}*a");
		assertNotNull(ex);
	}

	@Test
	void issue32_old() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("a^n");
		ReducePowerRule rule = new ReducePowerRule();
		rule.setOnlyOnIntegers(false);
		try {
			assertNotNull(rule.apply(ex));
		} catch (Exception e) {
			fail("#32");
		}
	}

	@Test
	void issue30_old() {
		ExpressionNode ex = ExpressionParser.buildExpressionTreeFromString("a^n");
		ReducePowerRule rule = new ReducePowerRule();
		rule.setOnlyOnIntegers(false);
		try {
			rule.apply(ex, true);
		} catch (Exception e) {
			fail("#30");
		}
	}

	@Test
	void issue27_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("3x^2+5x^2");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("8x^2");
		CompositeTransformationRule rule = TransformationRuleBuilder.buildCombinePowersRule();
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(start, true);
		} catch (Exception e) {
			fail("#27");
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	private static Stream<Arguments> provideValuesIssue35Test() {
		return Stream.of(Arguments.of(Functions.ABSOLUTE, AbsoluteNode.class),
				Arguments.of(Functions.ACOS, AcosNode.class), Arguments.of(Functions.ACOT, AcotNode.class),
				Arguments.of(Functions.ASIN, AsinNode.class), Arguments.of(Functions.ATAN, AtanNode.class),
				Arguments.of(Functions.BINOM, BinomNode.class), Arguments.of(Functions.COS, CosNode.class),
				Arguments.of(Functions.COSH, CoshNode.class), Arguments.of(Functions.COT, CotNode.class),
				Arguments.of(Functions.COTH, CothNode.class),
				Arguments.of(Functions.DEG2RAD, Degree2RadiansConversionNode.class),
				Arguments.of(Functions.DET, DeterminantNode.class), Arguments.of(Functions.LD, LdNode.class),
				Arguments.of(Functions.LG, LgNode.class), Arguments.of(Functions.LN, LnNode.class),
				Arguments.of(Functions.ROOT, MathmaticalRootNode.class), Arguments.of(Functions.SIN, SinNode.class),
				Arguments.of(Functions.SINH, SinhNode.class), Arguments.of(Functions.SQRT, SqrtNode.class),
				Arguments.of(Functions.TAN, TanNode.class), Arguments.of(Functions.TANH, TanhNode.class));
	}

	@ParameterizedTest
	@MethodSource("provideValuesIssue35Test")
	void issue35_old(Functions f, Class<?> expectedType) {
		FunctionNode node = FunctionNode.buildNode(f);
		assertNotNull(node);
		assertEquals(expectedType, node.getClass());
	}

	@Test
	void issue36_old() {
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("(((x*x)*x)*x)");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(x^2*x)*x");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*a \\to a^2");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(start, false);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			assertEquals(target, actual);
		} catch (Exception e) {
			fail("#36");
		}
	}

	@Test
	void issue38_old() {
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("(a+b)*b");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("ab+bb");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("(b+c)a \\to ba+ca");
		try {
			assertTrue(ExpressionUtil.isEqual(target, rule.apply(start, true)));
		} catch (Exception e) {
			fail("#38");
		}
	}

	@Test
	void issue38_2_old() {
		TransformationRuleParser.setParser(new LatexMathTransformationRuleParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("b*(a+b)");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("ba+bb");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a(b+c) \\to ab+ac");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(start, true);
		} catch (Exception e) {
			fail("#38");
		}
		assertEquals(target, actual);
	}

	@Test
	void issue33_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("a_0");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("a_{00}");
		assertFalse(ExpressionUtil.isEqual(target, start));
	}

	@Test
	void issue33_2_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("a_0");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("a_00");
		assertFalse(ExpressionUtil.isEqual(target, start));
	}

	@Test
	void issue34_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("\\sum_{i=0}^{n}{a}");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("0");
		boolean found = false;
		for (int i = 0; i < start.getChildcount(); i++) {
			if (ExpressionUtil.isEqual(start.getChild(i), target)) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	void issue34_1_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("\\sum_{i=0}^{n}{a}");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("n");
		boolean found = false;
		for (int i = 0; i < start.getChildcount(); i++) {
			if (ExpressionUtil.isEqual(start.getChild(i), target)) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	void issue34_2_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("\\prod_{i=0}^{n}{a}");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("0");
		boolean found = false;
		for (int i = 0; i < start.getChildcount(); i++) {
			if (ExpressionUtil.isEqual(start.getChild(i), target)) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	void issue34_3_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("\\prod_{i=0}^{n}{a}");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("n");
		boolean found = false;
		for (int i = 0; i < start.getChildcount(); i++) {
			if (ExpressionUtil.isEqual(start.getChild(i), target)) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	void issue40_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("\\prod_{i=0}^{n}{a}");
		SymbolTable st = SymbolTable.fromTree(start);
		boolean found = false;
		for (SymbolIdentifier id : st.getIdentifiers()) {
			if (id.getName().equals("i") && id.getIndex() == null) {
				found = true;
			}
		}
		assertFalse(found);
	}

	@Test
	void issue40_2_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("\\sum_{i=0}^{n}{a}");
		SymbolTable st = SymbolTable.fromTree(start);
		List<SymbolIdentifier> identifiers = st.getIdentifiers();
		boolean found = false;
		for (SymbolIdentifier symbolIdentifier : identifiers) {
			if (symbolIdentifier.getName().equals("i") && symbolIdentifier.getIndex() == null) {
				found = true;
			}
		}
		assertFalse(found);
	}

	@Test
	void issue34__40_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("\\sum_{i=0}^{n}{a}");
		SymbolTable st = SymbolTable.fromTree(start);
		List<SymbolIdentifier> identifiers = st.getIdentifiers();
		boolean found = false;
		for (SymbolIdentifier symbolIdentifier : identifiers) {
			if (symbolIdentifier.getName().equals("n") && symbolIdentifier.getIndex() == null) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	void issue34__40_2_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("\\sum_{i=0}^{n}{a}");
		SymbolTable st = SymbolTable.fromTree(start);
		List<SymbolIdentifier> identifiers = st.getIdentifiers();
		boolean found = false;
		for (SymbolIdentifier symbolIdentifier : identifiers) {
			if (symbolIdentifier.getName().equals("a") && symbolIdentifier.getIndex() == null) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	void issue41_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("\\prod_{i=0}^{n}{a}");
		assertEquals(start.getChildcount(), start.getChilds().size());
	}

	@Test
	void issue41_2_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("\\sum_{i=0}^{n}{a}");
		assertEquals(start.getChildcount(), start.getChilds().size());
	}

	@Test
	void issue39_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("a_0");
		ExpressionNode target = new TextNode("0");
		boolean found = false;
		for (int i = 0; i < start.getChildcount(); i++) {
			if (ExpressionUtil.isEqual(start.getChild(i), target)) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	void issue39_2_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("a_n");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("n");
		boolean found = false;
		for (int i = 0; i < start.getChildcount(); i++) {
			if (ExpressionUtil.isEqual(start.getChild(i), target)) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	void issue39__33_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("a_{00}");
		ExpressionNode target = new TextNode("00");
		boolean found = false;
		for (int i = 0; i < start.getChildcount(); i++) {
			if (ExpressionUtil.isEqual(start.getChild(i), target)) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	void issue42_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("a_n");
		SymbolTable st = SymbolTable.fromTree(start);
		boolean found = false;
		for (SymbolIdentifier identifier : st.getIdentifiers()) {
			if (identifier.getName().contains("a")) {
				if (identifier.getIndex() == null) {
					found = true;
				}
			}
		}
		assertFalse(found);
	}

	@Test
	void issue42_2_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("a_0");
		SymbolTable st = SymbolTable.fromTree(start);
		boolean found = false;
		for (SymbolIdentifier identifier : st.getIdentifiers()) {
			if (identifier.getName().contains("a")) {
				if (identifier.getIndex() != null && identifier.getIndex().equals("0")) {
					found = true;
				}
			}
		}
		assertTrue(found);
	}

	@Test
	void issue44_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("a_0");
		assertEquals(1, start.getChildcount());
	}

	@Test
	void issue44_2_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("a_i");
		assertEquals(1, start.getChildcount());
	}

	@Test
	void issue44_3_old() {
		ExpressionParser.setParser(new LatexMathExpressionParser());
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("a");
		assertEquals(0, start.getChildcount());
	}

	@Test
	void issue46_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2x+x");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("3x");
		ExpressionNode target2 = ExpressionParser.buildExpressionTreeFromString("(2+1)x");
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		rule.setOnlyForConstantValues(false);
		ExpressionNode transformed = null;
		try {
			transformed = rule.apply(start);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed) || ExpressionUtil.isEqual(target2, transformed));
	}

	@Test
	void issue45_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("2a+3a+3b");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(2+3)a+3b");
		ExpressionNode target2 = ExpressionParser.buildExpressionTreeFromString("2a+(a+b)*3");
		CombineAddNodes prerule = new CombineAddNodes();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		rule.setOnlyForConstantValues(false);
		ExpressionNode transformed = null;
		try {
			transformed = prerule.apply(start);
			if (transformed != null) {
				start = transformed;
			}
			transformed = rule.apply(start);
		} catch (Exception e) {
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed) || ExpressionUtil.isEqual(target2, transformed));
	}

	@Test
	void issue48_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("((a*b*2)+(a*a)+(b*b))");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("((((b*2)+1)*(a*a))+(1*(b*b)))");
		CompositeTransformationRule prerule = TransformationRuleBuilder.buildNodeCombineRule();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		rule.setOnlyForConstantValues(true);
		ExpressionNode transformed = null;
		try {
			transformed = prerule.apply(start);
			transformed = rule.apply(transformed);
		} catch (Exception e) {
		}
		assertFalse(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void issue50_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("(((a^2)*2)+((b^2)*2)+(a*b*2)+(a*b*2))");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(1+1)*(a*b*2)+a^2*2+b^2*2");
		CompositeTransformationRule prerule = TransformationRuleBuilder.buildNodeCombineRule();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		ExpressionNode transformed = null;
		try {
			transformed = prerule.apply(start, true);
			if (transformed != null) {
				start = transformed;
			}
			transformed = rule.apply(start);
			transformed = prerule.apply(transformed, true);
		} catch (Exception e) {
			fail("#50");
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void issue49_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("(a^2)+(a^2)+(b^2)+(b^2)+(a*b*4)");
		ExpressionNode target = ExpressionParser.buildExpressionTreeFromString("(1+1)a^2+(1+1)b^2+a*b*4");
		CompositeTransformationRule prerule = TransformationRuleBuilder.buildNodeCombineRule();
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		ExpressionNode transformed = null;
		try {
			ExpressionNode transformedTarget = prerule.apply(target);
			if (transformedTarget != null) {
				target = transformedTarget;
			}
			transformed = prerule.apply(start);
			if (transformed != null) {
				start = transformed;
			}
			transformed = rule.apply(start);
		} catch (Exception e) {
			fail("#49");
		}
		assertTrue(ExpressionUtil.isEqual(target, transformed));
	}

	@Test
	void issue54_old() {
		ExpressionNode c1 = ExpressionParser.buildExpressionTreeFromString("\\epsilon");
		ExpressionNode c2 = ExpressionParser.buildExpressionTreeFromString("\\epsilon");
		boolean equal = false;
		try {
			equal = c1.equals(c2);
		} catch (Exception e) {
			e.printStackTrace();
			fail("#54 failed :" + e.getMessage());
		}
		assertTrue(equal);
	}

	@Test
	void issue54_2_old() {
		ExpressionNode c1 = ExpressionParser.buildExpressionTreeFromString("\\epsilon+\\epsilon");
		ExpressionNode c2 = ExpressionParser.buildExpressionTreeFromString("\\epsilon+\\epsilon");
		boolean equal = false;
		try {
			equal = c1.equals(c2);
		} catch (Exception e) {
			e.printStackTrace();
			fail("#54 failed :" + e.getMessage());
		}
		assertTrue(equal);
	}

	@Test
	void issue58_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("\\epsilon");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("\\epsilon \\to \\epsilon");
		try {
			rule.apply(start);
		} catch (Exception e) {
			e.printStackTrace();
			fail("#58 failed :" + e.getMessage());
		}
	}

	@Test
	void issue58_2_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("1*\\epsilon");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("1*\\epsilon \\to \\epsilon");
		try {
			rule.apply(start);
		} catch (Exception e) {
			e.printStackTrace();
			fail("#58 failed :" + e.getMessage());
		}
	}

	@Test
	void issue58_3_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("1*\\epsilon");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("\\epsilon \\to \\epsilon");
		try {
			rule.apply(start);
		} catch (Exception e) {
			e.printStackTrace();
			fail("#58 failed :" + e.getMessage());
		}
	}

	@Test
	void issue56_old() {
		ExpressionNode c1 = ExpressionParser.buildExpressionTreeFromString("\\log_{10}{2}");
		ExpressionNode c2 = ExpressionParser.buildExpressionTreeFromString("\\lg{2}");
		EqualityNode eq = new EqualityNode();
		eq.setLeftExpression(c1);
		eq.setRightExpression(c2);
		assertTrue(NumericalEquationUtil.checkNumericalCorrectnessPercentage(eq, 1, 10, 1, 0.0001) > 0.99);
	}

	@Test
	void issue70_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("((2*x)+3)-3");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("(a-b) -> (a+(-(1)*b))");
		CompositeTransformationRule postrule = TransformationRuleBuilder.buildNodeCombineRule();
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("((2*x)+3)+(-1*3)");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(expression);
			actual = postrule.apply(actual);
		} catch (Exception e) {
			fail("#70 failed: " + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue71_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("(((2*x)+3)+(-(1)*3))");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a->a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("(((2*x)+3)+(-(1)*3))");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(expression);
		} catch (Exception e) {
			e.printStackTrace();
			fail("#71 failed: " + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue72_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("1");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a->-a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("-1");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(expression);
		} catch (Exception e) {
			fail("#72 failed: " + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue74_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("-(a*1)");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*1->a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("-(a)");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(expression);
		} catch (Exception e) {
			fail("#74 failed: " + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue74_nested_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("-((a*1)+b)");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*1->a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("-(a+b)");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(expression);
		} catch (Exception e) {
			fail("#74_n failed: " + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue74_nnested_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("-(b*(-b+(a*1)))");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*1->a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("-(b*(-b+a))");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(expression);
		} catch (Exception e) {
			fail("#74_nn failed: " + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue74_possible_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("-(a*1)");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*1->a");
		assertTrue(rule.isPossible(expression));
	}

	@Test
	void issue74_nested_possible_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("-((a*1)+b)");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*1->a");
		assertTrue(rule.isPossible(expression));
	}

	@Test
	void issue74_nnested_possible_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("-(b*(-b+(a*1)))");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*1->a");
		assertTrue(rule.isPossible(expression));
	}

	@Test
	void issue74_ta_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("-(a*1)");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*1->a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("-(a)");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(expression, true);
		} catch (Exception e) {
			fail("#74 failed: " + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue74_nested_ta_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("-((a*1)+b)");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*1->a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("-(a+b)");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(expression, true);
		} catch (Exception e) {
			fail("#74_n_ta failed: " + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue74_nnested_ta_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("-(b*(-b+(a*1)))");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*1->a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("-(b*(-b+a))");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(expression, true);
		} catch (Exception e) {
			fail("#74_nn_ta failed: " + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue74_ta_f_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("-(a*1)");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*1->a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("-(a)");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(expression, false);
		} catch (Exception e) {
			fail("#74 failed: " + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue74_nested_ta_f_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("-((a*1)+b)");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*1->a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("-(a+b)");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(expression, false);
		} catch (Exception e) {
			fail("#74_n_ta_f failed: " + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue74_nnested_ta_f_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("-(b*(-b+(a*1)))");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*1->a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("-(b*(-b+a))");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(expression, false);
		} catch (Exception e) {
			fail("#74_nn_ta_f failed: " + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue75_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("(x+y)^2");
		GeneralTransformationRule transformationRule = TransformationRuleBuilder.buildRuleFromString("a+b->b+a");
		boolean result = transformationRule.isPossible(expression, false);
		assertFalse(result);
	}

	@Test
	void issue75_2_old() {
		ExpressionNode expression = ExpressionParser.buildExpressionTreeFromString("(x+y)^2");
		GeneralTransformationRule transformationRule = TransformationRuleBuilder.buildRuleFromString("a+b->b+a");
		boolean result = transformationRule.isPossible(expression, true);
		assertTrue(result);
	}

	@Test
	void issue77_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("-((x*2))");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("(a*-(b)) -> -((a*b))");
		ExpressionNode notExpexted = ExpressionParser.buildExpressionTreeFromString("(x*2)");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(start);
			assertNotEquals(notExpexted, actual);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void issue78_old() {
		ExpressionNode start = buildExpIssue78();
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("-1*a+a->0");
		ExpressionNode notExpected = ExpressionParser.buildExpressionTreeFromString("0");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(start, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotEquals(notExpected, actual);
	}

	private ExpressionNode buildExpIssue78() {
		MultiAddNode root = new MultiAddNode();
		root.setChild(ExpressionParser.buildExpressionTreeFromString("(2*x)+3"), -1);
		root.setChild(ExpressionParser.buildExpressionTreeFromString("-1*3"), -1);
		return root;
	}

	@Test
	void issue80_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("0+x*2");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("0+a -> a");
		ExpressionNode notExpected = ExpressionParser.buildExpressionTreeFromString("0");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(start, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotEquals(notExpected, actual);
	}

	@Test
	void issue82_old() {
		ExpressionNode start = ExpressionParser
				.buildExpressionTreeFromString("((a^2)+(2*a*b)+(b^2))+((a*a)+(a*b))+((b*a)+(b*b))");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*a -> a^2");
		ExpressionNode notExpected = ExpressionParser
				.buildExpressionTreeFromString("((a^2)+(2*a)+(b^2))+((a^2)+(a*b))+((b*a)+(b^2))");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(start, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotEquals(notExpected, actual);
	}

	@Test
	void issue83_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("x*x*y*y*z*z");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*a -> a^2");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("x^2y^2z^2");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(start, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue83_2_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("x*x*y*y");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*a -> a^2");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("x^2y^2");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(start, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue84_old() {
		String name = "a";
		SymbolIdentifier siObj = new SymbolIdentifier(name, null);
		assertTrue(siObj.equals(name));
	}

	@Test
	void issue84_2_old() {
		String name = "a";
		SymbolIdentifier siObj = new SymbolIdentifier(name, null);
		assertEquals(siObj.hashCode(), name.hashCode());
	}

	@Test
	void issue85_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("((a^2)+(a*b)+-((a*b))+-((b*b)))");
		CommonFactorCombinationRule2 rule = new CommonFactorCombinationRule2();
		ExpressionNode expected = buildExpectedExpressionForIssue85();
		ExpressionNode actual = null;
		try {
			actual = rule.apply(start, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotEquals(expected, actual);
	}

	private ExpressionNode buildExpectedExpressionForIssue85() {
		ExpressionNode child1 = buildChild1Issue85();
		ExpressionNode full = ExpressionParser.buildExpressionTreeFromString("((2*(a*b))+(a^2)+-((b*b)))");
		full.setChild(child1, 0);
		return full;
	}

	private ExpressionNode buildChild1Issue85() {
		ExpressionNode n = new MultiMultiplyNode();
		n.setChild(new IntegerNode(2), -1);
		n.setChild(ExpressionParser.buildExpressionTreeFromString("a*b"), -1);
		return n;
	}

	@Test
	void issue_Nxt_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("(((5*x)+(2*x*x))*3)");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("a*a->a^2");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("(((5*x)+(2*x^2))*3)");
		ExpressionNode actual = null;
		boolean possible = rule.isPossible(start);
		System.out.println(possible);
		try {
			actual = rule.apply(start, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expected, actual);
	}

	@Test
	void testRepeatingRule_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("x*x*y*y");
		GeneralTransformationRule embRule = TransformationRuleBuilder.buildRuleFromString("a*a -> a^2");
		RepeatingTransformationRule rule = new RepeatingTransformationRule(embRule);
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("x^2y^2");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(start, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expected, actual);
	}

	@Test
	void testCompositeRule_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("x*x*y*1");
		GeneralTransformationRule embRule = TransformationRuleBuilder.buildRuleFromString("a*a -> a^2");
		GeneralTransformationRule embRule2 = TransformationRuleBuilder.buildRuleFromString("a*1 -> a");
		CompositeTransformationRule rule = new CompositeTransformationRule();
		rule.addChildRule(embRule);
		rule.addChildRule(embRule2);
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("x^2y");
		ExpressionNode actual = null;
		try {
			actual = rule.apply(start, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue86_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("x^2*y*1");
		GeneralTransformationRule embRule2 = TransformationRuleBuilder.buildRuleFromString("a*1 -> a");
		ExpressionNode expected = ExpressionParser.buildExpressionTreeFromString("x^2y");
		ExpressionNode actual = null;
		try {
			actual = embRule2.apply(start, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expected, actual);
	}

	@Test
	void issue86_2_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("x^2*y*1");
		GeneralTransformationRule embRule2 = TransformationRuleBuilder.buildRuleFromString("a*1 -> a");
		assertTrue(embRule2.isPossible(start));
	}

	@Test
	void issue87_2_old() {
		ExpressionNode start = ExpressionParser.buildExpressionTreeFromString("x*2+0");
		GeneralTransformationRule embRule2 = TransformationRuleBuilder.buildRuleFromString("0+a -> a");
		assertFalse(embRule2.isPossible(start));
	}

	@Test
	void issue3() {
		assertFalse(SymbolicEqualityUtil.checkSymbolicEquality("-(a+b)=a-b",
				SymbolicEqualityUtil.buildSimplificationRule()));
	}

	@Test
	void issue4() {
		EquationNode start = EquationParser.buildEquationTreeFromString("1=1");
		GeneralTransformationRule rule = TransformationRuleBuilder.buildRuleFromString("f->f*x");
		EquationNode expected = EquationParser.buildEquationTreeFromString("1*x=1*x");
		assertTrue(SymbolicEqualityUtil.checkEquivalizationTransform(start, expected, rule));
	}

	@Test
	void issue39() { // this test is not to test the mathparser implementation, but instead is used
						// to test if tests are executed at all. It checks for a file created by an
						// github action, and if it exists, creates a new one for which the action can
						// check
		File originalToCheckFor = new File("../test/JUnitTestExecutionTestfile");
		if (originalToCheckFor.exists()) {
			File copy = new File(originalToCheckFor.getAbsoluteFile() + ".new");
			try {
				if (!copy.createNewFile()) {
					fail();
				}
			} catch (IOException e) {
				fail();
			}
		}

	}

}
