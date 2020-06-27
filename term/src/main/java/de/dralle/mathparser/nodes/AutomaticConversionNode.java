/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Functions;

/**
 * Marks an automatic added conversion function, for example between degrees and radians in trigonometry functions.
 * @author Nils Dralle
 *
 */
public abstract class AutomaticConversionNode extends FunctionNode{

	public AutomaticConversionNode(Functions function) {
		super(function);
	}

}
