/**
 * 
 */
package de.dralle.mathparser;

/**
 * Enum over all functions. Some are parsed from input, some are internal. See also grammar file (Project grammar)
 * @author Nils Dralle
 *
 */
public enum Functions {
	SQRT("sqrt", 1), ROOT("rt", 2), SIN("sin", 1), COS("cos", 1), TAN("tan", 1), ASIN("asin", 1), ACOS("acos", 1),
	ATAN("atan", 1), LD("ld", 1), LN("ln", 1), LG("lg", 1), LOG("log", 2), DEG2RAD("deg2rad", 1), ABSOLUTE("abs", 1),
	SINH("sinh", 1), COSH("cosh", 1), TANH("tanh", 1), COT("cot", 1), ACOT("acot", 1), COTH("coth", 1),
	BINOM("binom", 2), PROD("prod", 3), SUM("sum", 3), DET("det", 1); // not all of these functions are parseable
																		// functions. Refer to MathGrammar2.g4
	// to see which ones can be parsed. Right now everything except DEG2RAD ,
	// ABSOLUTE and NEGATIVE.
	private String name;
	private int paramCnt;

	private Functions(String name, int paramCnt) {
		this.name = name;
		this.paramCnt = paramCnt;
	}

	public String getName() {
		return name;
	}

	public int getParamCnt() {
		return paramCnt;
	}
}
