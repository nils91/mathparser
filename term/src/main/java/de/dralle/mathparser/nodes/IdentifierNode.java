/**
 * 
 */
package de.dralle.mathparser.nodes;

import java.util.ArrayList;
import java.util.List;

import de.dralle.mathparser.SymbolIdentifier;
import de.dralle.mathparser.nodes.interfaces.IIndiceNode;
import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;

/**
 * @author Nils Dralle
 *
 */
public class IdentifierNode extends AtomNode implements IIndiceNode {
	private String name;
	private ExpressionNode indice;

	public IdentifierNode(String name) {
		super();
		this.name = name;
	}

	public IdentifierNode(SymbolIdentifier identifierName) {
		super();
		this.name=identifierName.getName();
		if(identifierName.getIndex()!=null) {
			indice=new TextNode(identifierName.getIndex());
		}
	}

	@Override
	public List<IMathParserBaseNode> getChilds() {
		List<IMathParserBaseNode> tmp = new ArrayList<IMathParserBaseNode>();
		tmp.add(indice);
		return tmp;
	}

	@Override
	public ExpressionNode getChild(int i) {
		if(i==0) {
			return indice;
		}
		return null;
	}

	@Override
	public int getChildcount() {
		return hasIndice()?1:0;
	}

	@Override
	public void setChild(IMathParserBaseNode node, int i) {
		if(i==0) {
			indice=(ExpressionNode) node;
		}else {
			super.setChild(node, i);
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public IdentifierNode clone() throws CloneNotSupportedException {
		IdentifierNode node = new IdentifierNode(name);
		if (this.hasIndice()) {
			node.indice = this.indice.clone();
		}
		node.sign=sign;
		return node;
	}

	@Override
	public ExpressionNode getIndice() {
		return indice;
	}

	@Override
	public void setIndice(ExpressionNode indice) {
		if(indice instanceof IntegerNode) {
			this.indice=new TextNode(((IntegerNode)indice).getValue()+"");
		}else {
			this.indice=indice;
		}
	}

	@Override
	public boolean hasSimpleTextualNumericIndice() {
		return indice instanceof IntegerNode || indice instanceof TextNode;
	}

	public boolean hasIndice() {
		return indice != null;
	}

	@Override
	public boolean hasAdvancedIndice() {
		return hasIndice()&&!hasSimpleTextualNumericIndice();
	}

	@Override
	public int hashCode() {
		int value = super.hashCode();
		value+=name.hashCode();
		if(hasSimpleTextualNumericIndice()) {
			value+=indice.hashCode();
		}
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IdentifierNode) {
			IdentifierNode other=(IdentifierNode)obj;
			if(sign.equals(other.sign)) {
				if(hasIndice()) {
					if (hasSimpleTextualNumericIndice()) {
						return name.equals(other.name)&&indice.equals(other.indice);
					}else {
						return name.equals(other.name)&&super.equals(obj);
					}
				}else {
					return name.equals(other.name);
				}
			}else {
				return false;
			}
		}else {
			return false;
		}		
	}

	public SymbolIdentifier getNameAsSymbolIdentifier() {
		SymbolIdentifier si = new SymbolIdentifier(name, null);
		if(hasIndice()&&hasSimpleTextualNumericIndice()) {
			String index=null;
			if(indice instanceof TextNode) {
				index=((TextNode)indice).getContent();
			}else if(indice instanceof IntegerNode) {
				index=((IntegerNode)indice).getValue().toString();
			}
			if(index!=null) {
				si.setIndex(index);
			}
		}
		return si;
	}
}
