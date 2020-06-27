/**
 * 
 */
package de.dralle.mathparser.nodes;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * Represents a Matrix in the expression tree.
 * 
 * @author Nils Dralle
 *
 */
public class MatrixNode extends ExpressionNode {
	private List<List<ExpressionNode>> elements; //rows first
	public MatrixNode() {
		elements=new ArrayList<List<ExpressionNode>>();
	}
	public MatrixNode(List<List<ExpressionNode>> rows) {
		elements=rows;
	}
	@Override
	public List<IMathParserBaseNode> getChilds() {
		List<IMathParserBaseNode> tmpList = new ArrayList<IMathParserBaseNode>();
		for (List<ExpressionNode> row : elements) {
			for (ExpressionNode e : row) {
				tmpList.add(e);
			}
		}
		return tmpList;
	}

	@Override
	public void clearChilds() {
	elements.clear();
		
	}
	@Override
	public void setChild(IMathParserBaseNode node, int i) {
		elements.get(i/getMatrixHeight()).set(i%getMatrixWidth(), (ExpressionNode) node);

	}
	public int getMatrixWidth() {
		return elements.get(0).size();
	}
	public int getMatrixHeight() {
		return elements.size();
	}

	@Override
	public int getChildcount() {
		return getMatrixWidth()*getMatrixHeight();
	}

	@Override
	public ExpressionNode clone() throws CloneNotSupportedException {
		List<List<ExpressionNode>> rowClones=new ArrayList<List<ExpressionNode>>(); //rows first
		for (List<ExpressionNode> list : elements) {
			List<ExpressionNode> elementClones=new ArrayList<ExpressionNode>();
			for (ExpressionNode e : list) {
				elementClones.add(e.clone());
			}
			rowClones.add(elementClones);
		}
		MatrixNode node = new MatrixNode(rowClones);	
		node.sign=sign;
		return node;
	}

	@Override
	public ExpressionNode getChild(int i) {
		return (ExpressionNode) getChilds().get(i);
	}
	public ExpressionNode getElement(int i,int j) {
		return elements.get(i).get(j);
	}

}
