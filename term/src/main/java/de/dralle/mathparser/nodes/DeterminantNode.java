/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Functions;

/**
 * @author Nils Dralle
 *
 */
public class DeterminantNode extends FunctionNode {

	public DeterminantNode() {
		super(Functions.DET);
	}
	public DeterminantNode(MatrixNode child) {
		super(Functions.DET);
		addChild(child);
	}
	public MatrixNode getChildMatrix() {
		return (MatrixNode) getChild(0); //grammar only allows matrix here
	}
	public void setChildMatrix(MatrixNode child) {
		if(getChildcount()>0) {
			setChild(child, 0);
		}else {
			addChild(child);
		}
	}
}
