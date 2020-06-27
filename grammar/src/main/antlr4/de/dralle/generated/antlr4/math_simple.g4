grammar math_simple; //Simple grammar as example for thesis.

expr : additiveExpr;
additiveExpr : multiplicativeExpr | additiveExpr (PLUS | MINUS) multiplicativeExpr;
multiplicativeExpr : powerExpr | multiplicativeExpr (TIMES | DIV | MODULO) powerExpr;
powerExpr : primaryExpr | primaryExpr CARET powerExpr;
primaryExpr : LPAREN expr RPAREN | INT;

PLUS : '+';
MINUS : '-';
TIMES : '\\times' | '\\cdot' | '*';
DIV : '\\div' | '/';
MODULO : '\\bmod' | '%';
CARET : '^';
LPAREN : '\\left(' | '(';
RPAREN : '\\right)' | ')';
INT : [0-9]+;

WS : [ \r\n\t]+ -> skip;