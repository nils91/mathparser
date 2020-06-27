package de.dralle.mathparser.base.common;

public interface RestCalls {
	public static final String TEST_ECHO = "/test/echo";
	public static final String TEST_PING = "/test/ping";
	public static final String EQUATION_SYNTAX = "/equation/syntax";
	public static final String EXP_SYNTAX = "/expression/syntax";
	public static final String EXP_CONST = "/expression/constants";
	public static final String EXP_ID = "/expression/identifiers";
	public static final String EXP_NUM_RESULT = "/expression/get_numerical_result";
	public static final String NUM_EQUATION_CORRECTNESS = "/equation/get_numerical_correctness";
	public static final String SYM_EQUATION_CORRECTNESS = "/equation/check_symbolic_equality";
	public static final String SYM_EXPRESSION_CORRECTNESS = "/expression/check_symbolic_equality";
	public static final String EQUATION_APPLY_TRANSFORM="/equation/apply_transform";
	public static final String EQUATION_CHECK_TRANSFORM="/equation/apply_transform_check";
}
