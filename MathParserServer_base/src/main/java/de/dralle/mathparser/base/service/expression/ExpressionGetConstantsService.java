/**
 * 
 */
package de.dralle.mathparser.base.service.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import de.dralle.mathparser.Constants;
import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.ExpressionUtil;
import de.dralle.mathparser.SymbolConstant;
import de.dralle.mathparser.SymbolTable;
import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.base.service.expression.IExpressionSyntaxCheckService;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.visitors.CalculateNumericalValueVisitor;
import io.swagger.model.Constant;
import io.swagger.model.ExpressionConstantsRequest;
import io.swagger.model.ExpressionConstantsResponse;
import io.swagger.model.ExpressionSyntaxCheckRequest;
import io.swagger.model.ExpressionSyntaxCheckResponse;
import io.swagger.model.NumericalCheckRequest;
import io.swagger.model.NumericalCheckResponse;
import io.swagger.model.NumericalResultRequest;
import io.swagger.model.NumericalResultResponse;

/**
 * @author Nils Dralle
 *
 */
@Service
public class ExpressionGetConstantsService implements IExpressionGetConstantsService{	
	
	public ExpressionGetConstantsService() {
	
	}

	@Override
	public ExpressionConstantsResponse handle(ExpressionConstantsRequest request,
			HttpServletResponse httpServletResponse) throws MathParserServerException {
		ExpressionConstantsResponse response = new ExpressionConstantsResponse();
		ExpressionNode expression=null;
		try {
			expression=ExpressionParser.buildExpressionTreeFromString(request.getExpression());
		}catch(Exception e) {
			throw new MathParserServerException(e, 1, e.getMessage());
		}
		if(expression==null) {
			throw new MathParserServerException(1, "");
		}
		SymbolTable st=SymbolTable.fromTree(expression);
		Collection<SymbolConstant> constants = st.getConstants();
		List<Constant> swaggerConstantList=new ArrayList<Constant>();
		for (SymbolConstant symbolConstant : constants) {
			Constant swaggerConstant=new Constant();
			swaggerConstant.setName(symbolConstant.getConstant().getName());
			swaggerConstant.setIndex(symbolConstant.getIndex());
			swaggerConstantList.add(swaggerConstant);
		}
		response.setConstants(swaggerConstantList);
		response.setCode(0);
		return response;
	}
	
	

}
