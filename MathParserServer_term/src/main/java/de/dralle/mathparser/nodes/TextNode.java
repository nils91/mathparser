/**
 * 
 */
package de.dralle.mathparser.nodes;

/**
 * @author Nils Dralle
 * Placeholder Node for operations not implemented yet and debugging
 */
public class TextNode extends AtomNode {
	private String content;
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}



	public TextNode(String content) {
		super();
		this.content = content;
	}



	@Override
	public String toString() {
		return content;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TextNode) {
			return content.equals(((TextNode)obj).content);
		}
		return false;
	}



	@Override
	public ExpressionNode clone() throws CloneNotSupportedException {
		TextNode nóde = new TextNode(content);
		nóde.sign=sign;
		return nóde;
	}



	@Override
	public int hashCode() {
		return super.hashCode()+content.hashCode();
	}
	

}
