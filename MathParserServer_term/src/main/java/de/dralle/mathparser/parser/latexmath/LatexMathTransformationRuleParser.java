/**
 * 
 */
package de.dralle.mathparser.parser.latexmath;

import org.antlr.v4.runtime.tree.ParseTree;

import de.dralle.generated.antlr4.latexmathParser;
import de.dralle.mathparser.antlr4.visitors.latexmath.TransformationRuleVisitor;
import de.dralle.mathparser.rules.GeneralTransformationRule;

/**
 * @author Nils Dralle
 *
 */
public class LatexMathTransformationRuleParser extends AbstractLatexMathParser<GeneralTransformationRule>{

	@Override
	public GeneralTransformationRule fromString(String str) {
		super.initalizeLexerParser(str);
		ParseTree pt = getParseTree();
		return pt.accept(new TransformationRuleVisitor());
	}

	@Override
	protected ParseTree getParseTree() {
		return ((latexmathParser)parser).transformation_rule();
	}

}
