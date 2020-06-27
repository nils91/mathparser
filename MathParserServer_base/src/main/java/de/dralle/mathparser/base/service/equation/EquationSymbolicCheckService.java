/**
 * 
 */
package de.dralle.mathparser.base.service.equation;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.ExpressionParser;
import de.dralle.mathparser.NumericalEquationUtil;
import de.dralle.mathparser.SymbolicEqualityUtil;
import de.dralle.mathparser.ValueLookupTable;
import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.nodes.ConstantNode;
import de.dralle.mathparser.nodes.EqualityNode;
import de.dralle.mathparser.nodes.EquationNode;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import io.swagger.model.ConstantValuePreset;
import io.swagger.model.EquationSyntaxCheckRequest;
import io.swagger.model.EquationSyntaxCheckResponse;
import io.swagger.model.IdentifierValuePreset;
import io.swagger.model.NumericalCheckRequest;
import io.swagger.model.NumericalCheckRequestIdentifierTestRange;
import io.swagger.model.NumericalCheckResponse;
import io.swagger.model.SymbolicEquationCheckRequest;
import io.swagger.model.SymbolicEquationCheckResponse;

/**
 * @author Nils Dralle
 *
 */
@Service
public class EquationSymbolicCheckService implements IEquationSymbolicCheckService {

	public EquationSymbolicCheckService() {

	}

	@Override
	public SymbolicEquationCheckResponse handle(SymbolicEquationCheckRequest request,
			HttpServletResponse httpServletResponse) throws MathParserServerException {
		String equationString = request.getEquation();
		EquationNode equationTree = EquationParser.buildEquationTreeFromString(equationString);
		if(equationTree==null) {
			throw new MathParserServerException(1, "Parsing problem.");
		}
		if(!(equationTree instanceof EqualityNode)) {
			throw new MathParserServerException(2, "Equation is of wrong type.");
		}else {
			boolean result=false;
			try {
				result=SymbolicEqualityUtil.checkSymbolicEquality((EqualityNode)equationTree);
			}catch(Exception e) {
				throw new MathParserServerException(e,3, e.getMessage());
			}SymbolicEquationCheckResponse response = new SymbolicEquationCheckResponse();	
			response.setCode(0);
			response.setMessage(String.format("Success evaluating %s.", equationString));
			response.setResult(result);
			return response;
		}		
	}

}
