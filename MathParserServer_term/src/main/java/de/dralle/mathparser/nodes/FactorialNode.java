/**
 * 
 */
package de.dralle.mathparser.nodes;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * @author Nils Dralle
 *
 */
public class FactorialNode extends ExpressionNode {
	private ExpressionNode onlyChild;

	@Override
	public void clearChilds() {
		onlyChild=null;
		
	}

	public FactorialNode(ExpressionNode onlyChild) {
		super();
		this.onlyChild = onlyChild;
	}

	public ExpressionNode getOnlyChild() {
		return onlyChild;
	}

	public void setOnlyChild(ExpressionNode onlyChild) {
		this.onlyChild = onlyChild;
	}

	@Override
	public List<IMathParserBaseNode> getChilds() {
		List<IMathParserBaseNode> tmpList = new ArrayList<IMathParserBaseNode>();
		tmpList.add(onlyChild);
		return tmpList;
	}

	@Override
	public ExpressionNode getChild(int i) {
		return (ExpressionNode) getChilds().get(i);
	}
	@Override
	public void setChild(IMathParserBaseNode node, int i) {
		if(i==0) {
			onlyChild=(ExpressionNode) node;
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public int getChildcount() {
		return getChilds().size();
	}

	@Override
	public FactorialNode clone() throws CloneNotSupportedException {
		FactorialNode node = new FactorialNode(onlyChild.clone());
		node.sign=sign;
		return node;
	}



}
