grammar latexmath;

expressionRuleStart
:
	(
		(
			latex_inline
			| tex_inline
			| latex_ex
			| tex_ex
			| comment
		) EOL?
	)+ EOF
;

comment
:
	'%' .*? EOL
;

latex_inline
:
	LATEX_INLINE_START
	(
		expr
		| equation
		| transformation_rule
	) LATEX_INLINE_END
;

latex_ex
:
	LATEX_EX_START
	(
		expr
		| equation
		| transformation_rule
	) LATEX_EX_END
;

tex_inline
:
	TEX_INLINE_START_END
	(
		expr
		| equation
		| transformation_rule
	) TEX_INLINE_START_END
;

tex_ex
:
	TEX_EX_START_END
	(
		expr
		| equation
		| transformation_rule
	) TEX_EX_START_END
;

relop
:
	EQ
	| NE
	| LE
	| GE
	| LT
	| GT
;

transformation_rule
:
	expr TRANSFORMATION_ARROW expr
;

equation
:
	expr relop expr
;

expr
:
	additiveExpr
;

primary
:
	parenthesisExpr
	| braceExpr
	| absoluteExpr
	| funcExpr
	| atom
;

additiveExpr
:
	multiplicativeExpr
	| additiveExpr
	(
		PLUS
		| MINUS
	) multiplicativeExpr
;

multiplicativeExpr
:
	shortFracExpr
	| multiplicativeExpr
	(
		TIMES
		| DIV
		| MODULO
	) shortFracExpr
;

shortFracExpr
:
	impMulExpr
	| shortFrac
;

impMulExpr
:
	signedFactorialExpr
	| impMulExpr factorialExpr
;

signedFactorialExpr
:
	factorialExpr
	|
	(
		PLUS
		| MINUS
	) signedFactorialExpr
;

factorialExpr
:
	powerExpr
	| factorialExpr FACTORIAL
;

powerExpr
:
	primary caretSubExpr? //for some reason doesn´t work when put as alternative

;

caretSubExpr
:
	CARET functionParam
;

funcExpr
:
	sqrtFunction
	| rtFunction
	| trigFunction
	| lbFunction
	| lnFunction
	| lgFunction
	| logFunction
	| fractionFunction
	| binomFunction
	| sumFunction
	| prodFunction
	| detFunction
;

absoluteExpr
:
	LPIPE expr RPIPE
	| PIPE expr PIPE
;

parenthesisExpr
:
	LPAREN expr RPAREN
	| LBRACKET expr RBRACKET
;

braceExpr
:
	LBRACE expr RBRACE
;

atom
:
	number
	| shortAtom
;

shortAtom
:
	shortNumber
	| identifier
	| constant
;

matrix
:
	MATRIX_BEGIN
	(
		matrixLine '\\\\'
	)+ matrixLine MATRIX_END
;

matrixLine
:
	(
		expr '&'
	)+ expr
;

constant
:
	(
		ALPHA
		| AALPHA
		| BETA
		| BBETA
		| GAMMA
		| GGAMMA
		| DELTA
		| DDELTA
		| EPSILON
		| EEPSILON
		| VAREPSILON
		| ETA
		| EETA
		| ZETA
		| ZZETA
		| THETA
		| TTHETA
		| VARTHETA
		| IOTA
		| IIOTA
		| KAPPA
		| KKAPPA
		| VARKAPPA
		| LAMBDA
		| LLAMBDA
		| MU
		| MMU
		| NU
		| NNU
		| XI
		| XXI
		| PI
		| PPI
		| PHI
		| VARPHI
		| PPHI
		| RHO
		| RRHO
		| VARRHO
		| SIGMA
		| SSIGMA
		| VARSIGMA
		| TAU
		| TTAU
		| UPSILON
		| UUPSILON
		| CHI
		| CCHI
		| PSI
		| PPSI
		| OMEGA
		| OOMEGA
		| OMICRON
		| OOMICRON
	) indice?
;

indice
:
	simpleIndice
	| advancedIndice
;

simpleIndice
:
	numericIndice
;

numericIndice
:
	UNDERSCORE shortNumber
	| UNDERSCORE LBRACE integer RBRACE
;

advancedIndice
:
	UNDERSCORE shortAtom
	| UNDERSCORE LBRACE expr RBRACE
;

identifierAssignParam
:
	UNDERSCORE LBRACE identifierAssign RBRACE
;

identifierAssign
:
	identifier EQ expr
;

identifier
:
	CHAR indice?
;

shortNumber
:
	DIGIT
;

number
:
	real
	| integer
;

//rules for supported functions

functionParam
:
	shortAtom
	| braceExpr
;

sqrtFunction
:
	SQRT functionParam
;

rtFunction
:
	SQRT LBRACKET expr RBRACKET functionParam
;
//trigonometry functions

trigFunctionParam
:
	shortAtom
	| LBRACE expr DEGREE? RBRACE
;

trigFunction
:
	(
		SIN
		| COS
		| TAN
		| COT
		| ASIN
		| ACOS
		| ATAN
		| ACOT
		| SINH
		| COSH
		| TANH
		| COTH
	) caretSubExpr? trigFunctionParam
;
//logarithmic functions

lbFunction
:
	LB functionParam
;

lnFunction
:
	LN functionParam
;

lgFunction
:
	LG functionParam
;

logFunction
:
	LOG advancedIndice functionParam
;

fractionFunction
:
	FRACTION functionParam functionParam
;

binomFunction
:
	BINOM functionParam functionParam
;

normalFrac
:
	FRACTION functionParam functionParam
;

shortFrac
:
	CARET functionParam '/_' functionParam
;

sumFunction
:
	SUM sumFunctionParam
;

prodFunction
:
	PROD sumFunctionParam
;

sumFunctionParam
:
	identifierAssignParam caretSubExpr functionParam
;

detFunction
:
	DET matrix
	| DET LBRACE matrix RBRACE
;

real
:
	integer? DOT integer
;

integer
:
	DIGIT+
;

//latex start/end

LATEX_INLINE_START
:
	'\\\\('
;

LATEX_INLINE_END
:
	'\\\\)'
;

LATEX_EX_START
:
	'\\\\['
;

LATEX_EX_END
:
	'\\\\]'
;

TEX_INLINE_START_END
:
	'$'
;

TEX_EX_START_END
:
	'$$'
;

//rewrite arrow for rewrite rules

TRANSFORMATION_ARROW
:
	'->'
	| '\\to'
	| '\\rightarrow'
;

//Token for equations

EQ
:
	'='
;

NE
:
	'\\neq'
;

LE
:
	'\\leq'
;

GE
:
	'\\geq'
;

LT
:
	'<'
;

GT
:
	'>'
;

//Token for numbers

DIGIT
:
	[0-9]
;

//Token for supported functions

SQRT
:
	'\\sqrt'
;

SIN
:
	'\\sin'
;

COS
:
	'\\cos'
;

TAN
:
	'\\tan'
;

COT
:
	'\\cot'
;

ASIN
:
	'\\arcsin'
;

ACOS
:
	'\\arccos'
;

ATAN
:
	'\\arctan'
;

ACOT
:
	'\\arccot'
;

SINH
:
	'\\sinh'
;

COSH
:
	'\\cosh'
;

TANH
:
	'\\tanh'
;

COTH
:
	'\\coth'
;

LB
:
	'\\log_2'
;

LG
:
	'\\lg'
;

LN
:
	'\\ln'
;

LOG
:
	'\\log'
;

FRACTION
:
	'\\frac'
	| '\\sfrac'
	| '\\cfrac'
;

BINOM
:
	'\\binom'
;

SUM
:
	'\\sum'
;

PROD
:
	'\\prod'
;

DET
:
	'\\det'
;

//matrices

MATRIX_BEGIN
:
	BEGIN LBRACE 'matrix' RBRACE
;

MATRIX_END
:
	END LBRACE 'matrix' RBRACE
;

//greek  letters

ALPHA
:
	'\\alpha'
;

AALPHA
:
	'\\Alpha'
	| 'A'
;

BETA
:
	'\\beta'
;

BBETA
:
	'\\Beta'
	| 'B'
;

GAMMA
:
	'\\gamma'
;

GGAMMA
:
	'\\Gamma'
;

DELTA
:
	'\\delta'
;

DDELTA
:
	'\\Delta'
;

EPSILON
:
	'\\epsilon'
;

EEPSILON
:
	'\\Epsilon'
	| 'E'
;

VAREPSILON
:
	'\\varepsilon'
;

ETA
:
	'\\eta'
	| 'H'
;

EETA
:
	'\\Eta'
;

ZETA
:
	'\\zeta'
;

ZZETA
:
	'\\Zeta'
	| 'Z'
;

THETA
:
	'\\theta'
;

TTHETA
:
	'\\Theta'
;

VARTHETA
:
	'\\vartheta'
;

IOTA
:
	'\\iota'
;

IIOTA
:
	'\\Iota'
	| 'I'
;

KAPPA
:
	'\\kappa'
;

KKAPPA
:
	'\\Kappa'
	| 'K'
;

VARKAPPA
:
	'\\varkappa'
;

LAMBDA
:
	'\\lambda'
;

LLAMBDA
:
	'\\Lambda'
;

MU
:
	'\\mu'
;

MMU
:
	'\\Mu'
	| 'M'
;

NU
:
	'\\nu'
;

NNU
:
	'\\Nu'
	| 'N'
;

XI
:
	'\\xi'
;

XXI
:
	'\\Xi'
;

PI
:
	'\\pi'
;

PPI
:
	'\\Pi'
;

PHI
:
	'\\phi'
;

VARPHI
:
	'\\varphi'
;

PPHI
:
	'\\Phi'
;

RHO
:
	'\\rho'
;

RRHO
:
	'\\Rho'
	| 'P'
;

VARRHO
:
	'\\varrho'
;

SIGMA
:
	'\\sigma'
;

SSIGMA
:
	'\\Sigma'
;

VARSIGMA
:
	'\\varsigma'
;

TAU
:
	'\\tau'
;

TTAU
:
	'\\Tau'
	| 'T'
;

UPSILON
:
	'\\upsilon'
;

UUPSILON
:
	'\\Upsilon'
;

CHI
:
	'\\chi'
;

CCHI
:
	'\\Chi'
	| 'X'
;

PSI
:
	'\\psi'
;

PPSI
:
	'\\Psi'
;

OMEGA
:
	'\\omega'
;

OOMEGA
:
	'\\Omega'
;

OMICRON
:
	'\\omicron'
	| 'o'
;

OOMICRON
:
	'\\Omicron'
	| 'O'
;

PLUS
:
	'+'
;

MINUS
:
	'-'
;

TIMES
:
	'*'
	| '\\times'
	| '\\cdot'
;

DIV
:
	'/'
	| '\\div'
;

MODULO
:
	'\\bmod'
;

UNDERSCORE
:
	'_'
;

CARET
:
	'^'
;

LPAREN
:
	'('
	| '\\left('
;

RPAREN
:
	')'
	| '\\right)'
;

LBRACKET
:
	'['
	| '\\left['
;

RBRACKET
:
	']'
	| '\\right]'
;

LBRACE
:
	'{'
;

RBRACE
:
	'}'
;

FACTORIAL
:
	'!'
;

PIPE
:
	'|'
;

LPIPE
:
	'\\left|'
;

RPIPE
:
	'\\right|'
;

COMMA
:
	','
;

DOT
:
	'.'
;

SEMICOLON
:
	';'
;

DEGREE
:
	'°'
	| '^\\circ'
;

CHAR
:
	[A-Za-z]
;

BEGIN
:
	'\\begin'
;

END
:
	'\\end'
;

EOL
:
	[\r\n]+
;

WS
:
	[ \r\n\t]+ -> skip
;