/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Operator;
import de.dralle.mathparser.nodes.interfaces.IAddOperationNode;

/**
 * @author Nils Dralle
 *
 */
public class MultiAddNode extends MultiNode implements IAddOperationNode{
	public MultiAddNode() {
		super(Operator.ADD);
	}
}
