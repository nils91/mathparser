/**
 * 
 */
package de.dralle.mathparser.antlr4.visitors.latexmath;

import java.util.ArrayList;
import java.util.List;

import de.dralle.generated.antlr4.latexmathBaseVisitor;
import de.dralle.generated.antlr4.latexmathParser.MatrixContext;
import de.dralle.generated.antlr4.latexmathParser.MatrixLineContext;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.MatrixNode;

/**
 * @author Nils Dralle
 *
 */
public class MatrixVisitor extends latexmathBaseVisitor<MatrixNode>{

	@Override
	public MatrixNode visitMatrix(MatrixContext ctx) {
		List<List<ExpressionNode>> rows=new ArrayList<List<ExpressionNode>>();
		for (MatrixLineContext line : ctx.matrixLine()) {
			rows.add(line.accept(new MatrixLineVisitor()));
		}
		MatrixNode node = new MatrixNode(rows);
		return node;
	}

}

