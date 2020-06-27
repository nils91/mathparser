/**
 * 
 */
package de.dralle.mathparser;

/**
 * @author Nils Dralle
 *
 */
public enum Operator implements IOperator {
	ADD("+","add"),SUBTRACT("-","subtract"), MULTIPLY("*","multiply"),DIV("/","div"),MODULO("%","mod"), POW("^","pow");
	private String operator;
	private String functionForm;
	
	private Operator(String operator,String functionForm) {
		this.operator=operator;
		this.functionForm=functionForm;
	}
	@Override
	public String getOperator() {
		return operator;
	}

	@Override
	public String getFunctionForm() {
		return functionForm;
	}

}
