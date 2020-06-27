/**
 * 
 */
package de.dralle.mathparser.antlr4.visitors.latexmath;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.math3.util.FastMath;

import de.dralle.generated.antlr4.latexmathBaseVisitor;
import de.dralle.generated.antlr4.latexmathParser.AtomContext;
import de.dralle.generated.antlr4.latexmathParser.ConstantContext;
import de.dralle.generated.antlr4.latexmathParser.IdentifierContext;
import de.dralle.generated.antlr4.latexmathParser.IntegerContext;
import de.dralle.generated.antlr4.latexmathParser.NumberContext;
import de.dralle.generated.antlr4.latexmathParser.RealContext;
import de.dralle.generated.antlr4.latexmathParser.ShortAtomContext;
import de.dralle.generated.antlr4.latexmathParser.ShortNumberContext;
import de.dralle.mathparser.Constants;
import de.dralle.mathparser.nodes.ConstantNode;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.IdentifierNode;
import de.dralle.mathparser.nodes.IntegerNode;
import de.dralle.mathparser.nodes.RealNode;

/**
 * @author Nils Dralle
 *
 */
public class AtomVisitor extends latexmathBaseVisitor<ExpressionNode> {

	@Override
	public ExpressionNode visitNumber(NumberContext ctx) {
		if (ctx.getChildCount() == 0) {
			return null;
		} else if (ctx.getChildCount() == 1) {
			if (ctx.integer() != null) {
				return ctx.integer().accept(this);
			} else if (ctx.real() != null) {
				return ctx.real().accept(this);
			}
		}
		return null;
	}

	@Override
	public ExpressionNode visitShortNumber(ShortNumberContext ctx) {
		if(ctx.DIGIT()!=null) {
			return new IntegerNode(Integer.parseInt(ctx.DIGIT().getText()));
		}return null;
	}

	@Override
	public ExpressionNode visitReal(RealContext ctx) {
		double val = Double.parseDouble(ctx.getText());
		
			return new RealNode(val);
		
//		if(ctx.integer().size()==1) {//only fraction
//			if(ctx.integer(0)!=null) {
//				IntegerNode intnode = (IntegerNode) ctx.integer(0).accept(this);
//				if(intnode.getValue().intValue()==0) {
//					return intnode;
//				}
//			}
//		}

	}

	@Override
	public ExpressionNode visitInteger(IntegerContext ctx) {
		List<TerminalNode> digits = ctx.DIGIT();
		long val = 0;
		for (int i = 0; i < digits.size(); i++) {
			val += Integer.parseInt(digits.get(i).getText()) * FastMath.pow(10, digits.size() - i - 1);
		}
		return new IntegerNode(val);
	}

	@Override
	public ExpressionNode visitConstant(ConstantContext ctx) {
		ConstantNode node = null;
		if (ctx.ALPHA() != null) {
			node = new ConstantNode(Constants.ALPHA);
		} else if (ctx.AALPHA() != null) {
			node = new ConstantNode(Constants.AALPHA);
		} else if (ctx.BETA() != null) {
			node = new ConstantNode(Constants.BETA);
		} else if (ctx.BBETA() != null) {
			node = new ConstantNode(Constants.BBETA);
		} else if (ctx.GAMMA() != null) {
			node = new ConstantNode(Constants.GAMMA);
		} else if (ctx.GGAMMA() != null) {
			node = new ConstantNode(Constants.GGAMMA);
		} else if (ctx.DELTA() != null) {
			node = new ConstantNode(Constants.DELTA);
		} else if (ctx.DDELTA() != null) {
			node = new ConstantNode(Constants.DDELTA);
		} else if (ctx.EPSILON() != null) {
			node = new ConstantNode(Constants.EPSILON);
		} else if (ctx.EEPSILON() != null) {
			node = new ConstantNode(Constants.EEPSILON);
		} else if (ctx.VAREPSILON() != null) {
			node = new ConstantNode(Constants.VAREPSILON);
		} else if (ctx.ETA() != null) {
			node = new ConstantNode(Constants.ETA);
		} else if (ctx.EETA() != null) {
			node = new ConstantNode(Constants.EETA);
		} else if (ctx.ZETA() != null) {
			node = new ConstantNode(Constants.ZETA);
		} else if (ctx.ZZETA() != null) {
			node = new ConstantNode(Constants.ZZETA);
		} else if (ctx.THETA() != null) {
			node = new ConstantNode(Constants.THETA);
		} else if (ctx.TTHETA() != null) {
			node = new ConstantNode(Constants.TTHETA);
		} else if (ctx.VARTHETA() != null) {
			node = new ConstantNode(Constants.VARTHETA);
		} else if (ctx.IOTA() != null) {
			node = new ConstantNode(Constants.IOTA);
		} else if (ctx.IIOTA() != null) {
			node = new ConstantNode(Constants.IIOTA);
		} else if (ctx.KAPPA() != null) {
			node = new ConstantNode(Constants.KAPPA);
		} else if (ctx.KKAPPA() != null) {
			node = new ConstantNode(Constants.KKAPPA);
		} else if (ctx.VARKAPPA() != null) {
			node = new ConstantNode(Constants.VARKAPPA);
		} else if (ctx.LAMBDA() != null) {
			node = new ConstantNode(Constants.LAMBDA);
		} else if (ctx.LLAMBDA() != null) {
			node = new ConstantNode(Constants.LLAMBDA);
		} else if (ctx.MU() != null) {
			node = new ConstantNode(Constants.MU);
		} else if (ctx.MMU() != null) {
			node = new ConstantNode(Constants.MMU);
		} else if (ctx.NU() != null) {
			node = new ConstantNode(Constants.NU);
		} else if (ctx.NNU() != null) {
			node = new ConstantNode(Constants.NNU);
		} else if (ctx.XI() != null) {
			node = new ConstantNode(Constants.XI);
		} else if (ctx.XXI() != null) {
			node = new ConstantNode(Constants.XXI);
		} else if (ctx.PI() != null) {
			node = new ConstantNode(Constants.PI);
		} else if (ctx.PPI() != null) {
			node = new ConstantNode(Constants.PPI);
		} else if (ctx.PHI() != null) {
			node = new ConstantNode(Constants.PHI);
		} else if (ctx.VARPHI() != null) {
			node = new ConstantNode(Constants.VARPHI);
		} else if (ctx.PPHI() != null) {
			node = new ConstantNode(Constants.PPHI);
		} else if (ctx.RHO() != null) {
			node = new ConstantNode(Constants.RHO);
		} else if (ctx.RRHO() != null) {
			node = new ConstantNode(Constants.RRHO);
		} else if (ctx.VARRHO() != null) {
			node = new ConstantNode(Constants.VARRHO);
		} else if (ctx.SIGMA() != null) {
			node = new ConstantNode(Constants.SIGMA);
		} else if (ctx.SSIGMA() != null) {
			node = new ConstantNode(Constants.SSIGMA);
		} else if (ctx.VARSIGMA() != null) {
			node = new ConstantNode(Constants.VARSIGMA);
		} else if (ctx.TAU() != null) {
			node = new ConstantNode(Constants.TAU);
		} else if (ctx.TTAU() != null) {
			node = new ConstantNode(Constants.TTAU);
		} else if (ctx.UPSILON() != null) {
			node = new ConstantNode(Constants.UPSILON);
		} else if (ctx.UUPSILON() != null) {
			node = new ConstantNode(Constants.UUPSILON);
		} else if (ctx.CHI() != null) {
			node = new ConstantNode(Constants.CHI);
		} else if (ctx.CCHI() != null) {
			node = new ConstantNode(Constants.CCHI);
		} else if (ctx.PSI() != null) {
			node = new ConstantNode(Constants.PSI);
		} else if (ctx.PPSI() != null) {
			node = new ConstantNode(Constants.PPSI);
		} else if (ctx.OMEGA() != null) {
			node = new ConstantNode(Constants.OMEGA);
		} else if (ctx.OOMEGA() != null) {
			node = new ConstantNode(Constants.OOMEGA);
		} else if (ctx.OMICRON() != null) {
			node = new ConstantNode(Constants.OMICRON);
		} else if (ctx.OOMICRON() != null) {
			node = new ConstantNode(Constants.OOMICRON);
		}
		if(ctx.indice()!=null) {
			ExpressionNode indicenode = ctx.indice().accept(new IndiceVisitor());
			node.setIndice(indicenode);
		}
		return node;
	}

	@Override
	public ExpressionNode visitIdentifier(IdentifierContext ctx) { // TODO: seperate into name and indice
		if(ctx.CHAR()!=null) {
			IdentifierNode node = new IdentifierNode(ctx.CHAR().getText());
			if(ctx.indice()!=null) {
				ExpressionNode indicenode = ctx.indice().accept(new IndiceVisitor());
				node.setIndice(indicenode);
			}
			return node;
		}
		return null;
	}

	@Override
	public ExpressionNode visitAtom(AtomContext ctx) {
		if(ctx.number()!=null) {
			return ctx.number().accept(this);
		}if(ctx.shortAtom()!=null) {
			return ctx.shortAtom().accept(this);
		}return null;
	}

	@Override
	public ExpressionNode visitShortAtom(ShortAtomContext ctx) {
		if(ctx.shortNumber()!=null) {
			return ctx.shortNumber().accept(this);
		}if(ctx.constant()!=null) {
			return ctx.constant().accept(this);
		}if(ctx.identifier()!=null) {
			return ctx.identifier().accept(this);
		}return null;
	}

}
