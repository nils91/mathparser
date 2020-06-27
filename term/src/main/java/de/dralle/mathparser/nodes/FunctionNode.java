/**
 * 
 */
package de.dralle.mathparser.nodes;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.Functions;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * @author Nils Dralle
 *
 */
public abstract class FunctionNode extends ExpressionNode {
	public static FunctionNode buildNode(Functions function) {
		switch (function) {
		case ABSOLUTE:
			return new AbsoluteNode();
		case ACOS:
			return new AcosNode();
		case ASIN:
			return new AsinNode();
		case ATAN:
			return new AtanNode();
		case COS:
			return new CosNode();
		case DEG2RAD:
			return new Degree2RadiansConversionNode();
		case LD:
			return new LdNode();
		case LG:
			return new LgNode();
		case LN:
			return new LnNode();
		case LOG:
			return new LogNode();
		case ROOT:
			return new MathmaticalRootNode();
		case SIN:
			return new SinNode();
		case SQRT:
			return new SqrtNode();
		case TAN:
			return new TanNode();
		case ACOT:
			return new AcotNode();
		case BINOM:
			return new BinomNode();
		case COSH:
			return new CoshNode();
		case COT:
			return new CotNode();
		case COTH:
			return new CothNode();
		case DET:
			return new DeterminantNode();
		case SINH:
			return new SinhNode();
		case TANH:
			return new TanhNode();
		default:
			return null;

		}
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FunctionNode) {
			FunctionNode other = (FunctionNode)obj;
			return function.equals(other.function)&&super.equals(obj);
		}
		return false;
	}

	private Functions function;

	public FunctionNode(Functions function) {
		super();
		this.function = function;
		childs = new ArrayList<>();
	}

	private List<ExpressionNode> childs;


	@Override
	public void clearChilds() {
		childs.clear();		
	}

	public void addChild(ExpressionNode newChild) {
		if (childs.size() < function.getParamCnt()) {
			childs.add(newChild);
		}
	}

	@Override
	public List<IMathParserBaseNode> getChilds() {
		return new ArrayList<IMathParserBaseNode>(childs);
	}

	@Override
	public ExpressionNode getChild(int i) {
		return (ExpressionNode) getChilds().get(i);
	}
	@Override
	public void setChild(IMathParserBaseNode node, int i) {
		childs.set(i, (ExpressionNode)node);
		
	}

	@Override
	public int getChildcount() {
		return getChilds().size();
	}

	@Override
	public FunctionNode clone() throws CloneNotSupportedException {
		FunctionNode node = buildNode(function);
		for (ExpressionNode expressionNode : childs) {
			node.addChild(expressionNode.clone());
		}
		node.sign=sign;
		return node;
	}

	@Override
	public int hashCode() {
		return super.hashCode()+function.hashCode();
	}

	

}
