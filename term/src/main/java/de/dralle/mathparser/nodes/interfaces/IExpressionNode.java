/**
 * 
 */
package de.dralle.mathparser.nodes.interfaces;

import de.dralle.mathparser.Sign;

/**
 * @author Nils Dralle
 *
 */
public interface IExpressionNode extends IMathParserBaseNode{
	@Override
	IExpressionNode getChild(int i);
	
	Sign getSign();
	void setSign(Sign sign);
}
