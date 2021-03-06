/**
 * 
 */
package de.dralle.mathparser.visitors;

import de.dralle.mathparser.Sign;
import de.dralle.mathparser.nodes.AbsoluteNode;
import de.dralle.mathparser.nodes.AcosNode;
import de.dralle.mathparser.nodes.AsinNode;
import de.dralle.mathparser.nodes.AtanNode;
import de.dralle.mathparser.nodes.ConstantNode;
import de.dralle.mathparser.nodes.CosNode;
import de.dralle.mathparser.nodes.Degree2RadiansConversionNode;
import de.dralle.mathparser.nodes.DivisionNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.FactorialNode;
import de.dralle.mathparser.nodes.IdentifierNode;
import de.dralle.mathparser.nodes.IntegerNode;
import de.dralle.mathparser.nodes.LdNode;
import de.dralle.mathparser.nodes.LgNode;
import de.dralle.mathparser.nodes.LnNode;
import de.dralle.mathparser.nodes.LogNode;
import de.dralle.mathparser.nodes.MathmaticalRootNode;
import de.dralle.mathparser.nodes.ModuloNode;
import de.dralle.mathparser.nodes.MultiAddNode;
import de.dralle.mathparser.nodes.MultiMultiplyNode;
import de.dralle.mathparser.nodes.PowNode;
import de.dralle.mathparser.nodes.RealNode;
import de.dralle.mathparser.nodes.SinNode;
import de.dralle.mathparser.nodes.SqrtNode;
import de.dralle.mathparser.nodes.SubtractNode;
import de.dralle.mathparser.nodes.TanNode;

/**
 * @author Nils Dralle
 *
 */
public class ExpressionTreeFRIMFormatter extends ExpressionTreeStringVisitor {
	@Override
	protected String visitNode(AcosNode node) {
		return String.format("acos(%s)", node.getChild(0).accept(this));
	}

	@Override
	protected String visitNode(MultiAddNode node) {
		String tmp = "add(";
		for (int i = 0; i < node.getChildcount(); i++) {
			String nodestring = node.getChild(i).accept(this);
			tmp += nodestring;
			if (i < node.getChildcount() - 1) {
				tmp += ",";
			}
		}
		return tmp + ")";
	}

	@Override
	protected String visitNode(MultiMultiplyNode node) {
		String tmp = "mul(";
		for (int i = 0; i < node.getChildcount(); i++) {
			String nodestring = node.getChild(i).accept(this);
			tmp += nodestring;
			if (i < node.getChildcount() - 1) {
				tmp += ",";
			}
		}
		return tmp + ")";
	}

	@Override
	protected String visitNode(AsinNode node) {
		return String.format("asin(%s)", node.getChild(0).accept(this));
	}

	@Override
	protected String visitNode(AtanNode node) {
		return String.format("atan(%s)", node.getChild(0).accept(this));
	}

	@Override
	protected String visitNode(CosNode node) {
		return String.format("cos(%s)", node.getChild(0).accept(this));
	}

	@Override
	protected String visitNode(Degree2RadiansConversionNode node) {
		return String.format("deg2rad(%s)", node.getChild(0).accept(this));
	}

	@Override
	protected String visitNode(DivisionNode node) {
		String left = node.getLeftExpression().accept(this);
		String right = node.getRightExpression().accept(this);
		return String.format("frac(%s,%s)", left, right);
	}

	@Override
	protected String visitNode(FactorialNode node) {
		return String.format("factorial(%s)", node.getChild(0).accept(this));
	}

	@Override
	protected String visitNode(IdentifierNode node) {
		return String.format("id(%s)", node.getName());
	}

	@Override
	protected String visitNode(ConstantNode node) {
		switch (node.getConstant()) {
		case EPSILON:
			return "const(E)";
		case PI:
			return "const(PI)";
		default:
			break;
		}
		return null;
	}

	@Override
	protected String visitNode(IntegerNode node) {
		return String.format("int(%s)", node.getValue());
	}

	@Override
	protected String visitNode(RealNode node) {
		return String.format("real(%s)", node.getValue());
	}

	@Override
	protected String visitNode(LdNode node) {
		return String.format("ld(%s)", node.getChild(0).accept(this));
	}

	@Override
	protected String visitNode(LnNode node) {
		return String.format("ln(%s)", node.getChild(0).accept(this));
	}

	@Override
	protected String visitNode(LgNode node) {
		return String.format("lg(%s)", node.getChild(0).accept(this));
	}

	@Override
	protected String visitNode(LogNode node) {
		String left = node.getLeftExpression().accept(this);
		String right = node.getRightExpression().accept(this);
		return String.format("log(%s,%s)", left, right);
	}

	@Override
	protected String visitNode(ModuloNode node) {
		String left = node.getLeftExpression().accept(this);
		String right = node.getRightExpression().accept(this);
		return String.format("mod(%s,%s)", left, right);
	}

	@Override
	protected String visitNode(PowNode node) {
		String left = node.getLeftExpression().accept(this);
		String right = node.getRightExpression().accept(this);
		return String.format("pow(%s,%s)", left, right);
	}

	@Override
	protected String visitNode(SinNode node) {
		return String.format("sin(%s)", node.getChild(0).accept(this));
	}

	@Override
	protected String visitNode(SqrtNode node) {
		return String.format("sqrt(%s)", node.getChild(0).accept(this));
	}

	@Override
	protected String visitNode(MathmaticalRootNode node) {
		String left = node.getLeftExpression().accept(this);
		String right = node.getRightExpression().accept(this);
		return String.format("root(%s,%s)", left, right);
	}

	@Override
	protected String visitNode(SubtractNode node) {
		String left = node.getLeftExpression().accept(this);
		String right = node.getRightExpression().accept(this);
		return String.format("sub(%s,%s)", left, right);
	}

	@Override
	protected String handleSign(String value, ExpressionNode node) {
		if (node.getSign().equals(Sign.NEGATIVE)) {
			return String.format("neg(%s)", value);
		}
		return value;
	}

	@Override
	protected String visitNode(TanNode node) {
		return String.format("tan(%s)", node.getChild(0).accept(this));
	}

	@Override
	protected String visitNode(AbsoluteNode node) {
		return String.format("abs(%s)", node.getChild(0).accept(this));
	}
}
