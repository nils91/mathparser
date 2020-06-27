/**
 * 
 */
package de.dralle.mathparser.nodes.interfaces;

import de.dralle.mathparser.RelationOperator;

/**
 * @author Nils Dralle
 *
 */
public interface IEquationNode extends IMathParserBaseNode,IBinaryNode,IOperatorNode{	
	RelationOperator getOperator();
}
