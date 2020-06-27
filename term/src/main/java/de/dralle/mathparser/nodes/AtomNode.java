/**
 * 
 */
package de.dralle.mathparser.nodes;

import java.util.List;

import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * @author Nils Dralle
 *
 */
public abstract class AtomNode extends ExpressionNode{

	@Override
	public List<IMathParserBaseNode> getChilds() {
		return null;
	}

	@Override
	public ExpressionNode getChild(int i) {
		return null;
	}

	@Override
	public int getChildcount() {
		return 0;
	}

	@Override
	public void setChild(IMathParserBaseNode node, int i) {
		throw new IndexOutOfBoundsException();
		
	}

	@Override
	public void clearChilds() {
		throw new RuntimeException("Not possible");
		
	}

}
