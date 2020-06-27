/**
 * 
 */
package de.dralle.mathparser.visitors;

import de.dralle.mathparser.SymbolTable;
import de.dralle.mathparser.nodes.ConstantNode;
import de.dralle.mathparser.nodes.IdentifierNode;
import de.dralle.mathparser.nodes.TextNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * @author Nils Dralle
 *
 */
public class ExpressionTreeSymbolTableVisitor extends ExpressionTreeBaseVisitor<SymbolTable> {

	@Override
	public SymbolTable visit(IMathParserBaseNode node) {
		SymbolTable st = new SymbolTable();
		if(node instanceof IdentifierNode) {
			IdentifierNode ident=(IdentifierNode)node;
			if(ident.hasIndice()) {
				if(ident.hasSimpleTextualNumericIndice()) {
					st.addIdentifier(ident.getName(),(((TextNode)ident.getIndice()).getContent()));
				}
			}else {
				st.addIdentifier(((IdentifierNode)node).getName());
			}
		}
		if(node instanceof ConstantNode) {
			ConstantNode constant=(ConstantNode)node;
			if(constant.hasIndice()) {
				if(constant.hasSimpleTextualNumericIndice()) {
					st.addConstant(constant.getConstant(),(((TextNode)constant.getIndice()).getContent()));
				}
			}else {
				st.addConstant(constant.getConstant());
			}
		}
		for (int i = 0; i < node.getChildcount(); i++) {
			st.combineInto(node.getChild(i).accept(new ExpressionTreeSymbolTableVisitor()));
		}
		return st;
	}

	
	
}
