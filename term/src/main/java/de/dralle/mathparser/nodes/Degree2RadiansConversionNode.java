/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Functions;

/**
 * Nodetype to represent the conversion from degrees to radians in an expression tree. In rules and expressions this is denoted with ° following the number (Only works in trigonometry functions).
 * 
 * @author Nils Dralle
 * 
 */
public class Degree2RadiansConversionNode extends AutomaticConversionNode {

	public Degree2RadiansConversionNode() {
		super(Functions.DEG2RAD);
	}

	public Degree2RadiansConversionNode(ExpressionNode param) {
		super(Functions.DEG2RAD);
		addChild(param);
	}

}
