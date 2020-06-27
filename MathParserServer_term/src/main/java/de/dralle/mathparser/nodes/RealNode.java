/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Sign;

/**
 * @author Nils Dralle
 *
 */
public class RealNode extends NumberNode {
	private double value;
	public RealNode(double value) {
		super();
		if(value<0) {
			setSign(Sign.NEGATIVE);
			value*=-1;
		}
		this.value = value;
	}


	@Override
	public RealNode clone() throws CloneNotSupportedException {
		RealNode node = new RealNode(value);
		node.sign=sign;
		return node;
	}

	@Override
	public Number getValue() {
		if(value<0) {
			setSign(Sign.NEGATIVE);
			value*=-1;
		}
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RealNode) {
			RealNode other=(RealNode)obj;
			return value==other.value&&sign.equals(other.getSign());
		}else {
			return false;
		}
	}


	@Override
	public int hashCode() {
		return super.hashCode()+getValue().intValue()+sign.hashCode();
	}

}
