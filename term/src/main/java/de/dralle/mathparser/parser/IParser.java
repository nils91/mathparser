/**
 * 
 */
package de.dralle.mathparser.parser;

/**
 * Base helper interface for all used ANTLR4-based parsers. Helps when quickly switching between parsers.
 * @author Nils Dralle
 *
 */
public interface IParser<T> {
	T fromString(String str);
	
	String checkSyntaxThrowException(String str) throws Exception;
	
	boolean checkSyntax(String str);
}
