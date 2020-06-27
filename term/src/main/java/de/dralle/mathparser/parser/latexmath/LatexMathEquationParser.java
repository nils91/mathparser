/**
 * 
 */
package de.dralle.mathparser.parser.latexmath;

import org.antlr.v4.runtime.tree.ParseTree;

import de.dralle.generated.antlr4.latexmathParser;
import de.dralle.mathparser.antlr4.visitors.latexmath.EquationVisitor;
import de.dralle.mathparser.nodes.EquationNode;

/**
 * @author Nils Dralle
 *
 */
public class LatexMathEquationParser extends AbstractLatexMathParser<EquationNode> {

	@Override
	public EquationNode fromString(String str) {
		super.initalizeLexerParser(str);
		ParseTree pt = getParseTree();
		return pt.accept(new EquationVisitor());
	}

	@Override
	protected ParseTree getParseTree() {
		return ((latexmathParser)parser).equation();
	}

}
