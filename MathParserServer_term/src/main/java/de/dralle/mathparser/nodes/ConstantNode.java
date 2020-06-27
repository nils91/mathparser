/**
 * 
 */
package de.dralle.mathparser.nodes;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.Constants;
import de.dralle.mathparser.nodes.interfaces.IIndiceNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * @author Nils Dralle
 *
 */
public class ConstantNode extends RealValuedNode implements IIndiceNode {
	private Constants constant;
	private ExpressionNode indice;

	public ConstantNode(Constants constant) {
		super();
		this.constant = constant;
	}

	public Constants getConstant() {
		return constant;
	}

	@Override
	public ConstantNode clone() throws CloneNotSupportedException {
		ConstantNode node = new ConstantNode(constant);
		if (hasIndice()) {
			node.indice = this.indice.clone();
		}
		node.sign=sign;
		return node;
	}

	@Override
	public List<IMathParserBaseNode> getChilds() {
		List<IMathParserBaseNode> tmp = new ArrayList<IMathParserBaseNode>();
		tmp.add(indice);
		return tmp;
	}

	@Override
	public ExpressionNode getChild(int i) {
		if (i == 0) {
			return indice;
		}
		return null;
	}

	@Override
	public int getChildcount() {
		return hasIndice() ? 1 : 0;
	}

	@Override
	public void setChild(IMathParserBaseNode node, int i) {
		if (i == 0) {
			indice = (ExpressionNode) node;
		} else {
			super.setChild(node, i);
		}
	}

	@Override
	public void setIndice(ExpressionNode indice) {
		if (indice instanceof IntegerNode) {
			this.indice = new TextNode(((IntegerNode) indice).getValue() + "");
		} else {
			this.indice = indice;
		}
	}

	@Override
	public ExpressionNode getIndice() {
		return indice;
	}

	public boolean hasIndice() {
		return indice != null;
	}

	@Override
	public boolean hasSimpleTextualNumericIndice() {
		return indice instanceof IntegerNode || indice instanceof TextNode;
	}

	@Override
	public boolean hasAdvancedIndice() {
		return hasIndice() && !hasSimpleTextualNumericIndice();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ConstantNode) {
			ConstantNode other=(ConstantNode)obj;
			if(sign.equals(other.sign)) {
				if(hasIndice()) {
					if (hasSimpleTextualNumericIndice()) {
						return constant.equals(other.constant)&&indice.equals(other.indice);
					}else {
						return constant.equals(other.constant)&&super.equals(obj);
					}
				}else {
					return constant.equals(other.constant);
				}
			}else {
				return false;
			}
		}else {
			return false;
		}		
	}

	@Override
	public int hashCode() {
		int value = super.hashCode() + constant.hashCode();
		if (hasSimpleTextualNumericIndice()) {
			value += indice.hashCode();
		}
		return value;
	}

	@Override
	public void clearChilds() {
		
		
	}

}
