/**
 * 
 */
package de.dralle.mathparser.nodes;

import de.dralle.mathparser.ExpressionUtil;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;
import de.dralle.mathparser.visitors.MathParserBaseTreeVisitor;

/**
 * @author Nils Dralle
 *
 */
public abstract class AbstractMathParserBaseNode implements IMathParserBaseNode {

	@Override
	public abstract AbstractMathParserBaseNode clone() throws CloneNotSupportedException;

	@Override
	public <T> T accept(MathParserBaseTreeVisitor<T> visitor) {
		if(visitor!=null) {
			return visitor.visit(this);
		}
		return null;
	}

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
		int value=this.getClass().getName().hashCode();
		for (int i = 0; i < getChildcount(); i++) {
			value+=getChild(i).hashCode();
		}
		return value;
	}

}
