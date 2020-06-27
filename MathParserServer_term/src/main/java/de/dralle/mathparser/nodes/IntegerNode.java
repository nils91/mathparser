/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.Sign;

/**
 * @author Nils Dralle
 *
 */
public class IntegerNode extends NumberNode {
	private long value;
	public IntegerNode(int value) {
		super();
		if(value<0) {
			setSign(Sign.NEGATIVE);
			value*=-1;
		}
		this.value = value;
	}


	public IntegerNode(long value) {
		super();
		if(value<0) {
			setSign(Sign.NEGATIVE);
			value*=-1;
		}
		this.value = value;
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IntegerNode) {
			IntegerNode other=(IntegerNode)obj;
			return value==other.value&&sign.equals(other.getSign());
		}else {
			return false;
		}
	}


	@Override
	public IntegerNode clone() throws CloneNotSupportedException {
		IntegerNode node = new IntegerNode(value);
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
	public int hashCode() {
		return (int) (super.hashCode()+value);
	}

}
