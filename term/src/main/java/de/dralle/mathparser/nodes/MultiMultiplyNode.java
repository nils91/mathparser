package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Operator;
import de.dralle.mathparser.nodes.interfaces.IMultiplyOperationNode;

/**
 * @author Nils Dralle
 *
 */
public class MultiMultiplyNode extends MultiNode
		implements IMultiplyOperationNode{

	public MultiMultiplyNode() {
		super(Operator.MULTIPLY);
	}

}
