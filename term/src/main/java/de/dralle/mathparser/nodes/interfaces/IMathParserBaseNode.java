/**
 * 
 */
package de.dralle.mathparser.nodes.interfaces;

import java.util.List;

import de.dralle.mathparser.visitors.MathParserBaseTreeVisitor;

/**
 * @author Nils Dralle
 *
 */
public interface IMathParserBaseNode extends Cloneable {
	List<IMathParserBaseNode> getChilds();

	IMathParserBaseNode getChild(int i);

	void setChild(IMathParserBaseNode node, int i);
	
	void clearChilds();

	int getChildcount();

	IMathParserBaseNode clone() throws CloneNotSupportedException;
	
	<T> T accept(MathParserBaseTreeVisitor<T> visitor);

	@Override
	boolean equals(Object obj);

	@Override
	int hashCode();

}
