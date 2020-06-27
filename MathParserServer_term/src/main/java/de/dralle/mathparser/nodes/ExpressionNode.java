/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.ExpressionUtil;
import de.dralle.mathparser.Sign;
import de.dralle.mathparser.nodes.interfaces.IExpressionNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;
import de.dralle.mathparser.visitors.ExpressionTreeInfixFormatter;

/**
 * @author Nils Dralle
 *
 */
public abstract class ExpressionNode extends AbstractMathParserBaseNode implements IExpressionNode{
	protected Sign sign;
	@Override
	public Sign getSign() {
		return sign;
	}

	@Override
	public void setSign(Sign sign) {
		this.sign=sign;
	}
	public ExpressionNode() {
		sign=Sign.POSITIVE;
	}

	public ExpressionNode(Sign sign) {
		super();
		this.sign = sign;
	}

	@Override
	public abstract ExpressionNode clone() throws CloneNotSupportedException;

	@Override
	public String toString() {
		return accept(new ExpressionTreeInfixFormatter());
	}

	@Override
	public abstract ExpressionNode getChild(int i);
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IMathParserBaseNode) {
			return ExpressionUtil.isEqual(this, (IMathParserBaseNode) obj);
		}else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode=this.getClass().getName().hashCode()+sign.hashCode();
		for (int i = 0; i < getChildcount(); i++) {
			hashCode+=getChild(i).hashCode();
		}
		return hashCode;
	}
}
