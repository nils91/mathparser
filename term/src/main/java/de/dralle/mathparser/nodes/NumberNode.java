/**
 * 
 */
package de.dralle.mathparser.nodes;

/**
 * @author Nils Dralle
 *
 */
public abstract class NumberNode extends RealValuedNode{
	public abstract Number getValue();
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof NumberNode) {
				NumberNode other=(NumberNode)obj;
				return getValue().equals(other.getValue())&&sign.equals(other.getSign());
			}else {
				return false;
			}
		}
	

}
