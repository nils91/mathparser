/**
 * 
 */
package de.dralle.mathparser.base.service.equation;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import de.dralle.mathparser.EquationParser;
import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.parser.latexmath.LatexMathEquationParser;
import io.swagger.model.EquationSyntaxCheckRequest;
import io.swagger.model.EquationSyntaxCheckResponse;

/**
 * @author Nils Dralle
 *
 */
@Service
public class EquationSyntaxCheckService implements IEquationSyntaxCheckService{

	
	public EquationSyntaxCheckService() {
		
	}
	
	@Override
	public EquationSyntaxCheckResponse handle(EquationSyntaxCheckRequest request,
			HttpServletResponse httpServletResponse) throws MathParserServerException {
		EquationParser.setParser(new LatexMathEquationParser());
		EquationSyntaxCheckResponse response = new EquationSyntaxCheckResponse();
		try{
			response.setEquation(EquationParser.checkEquationSyntaxThrowException(request.getEquation()));
			response.setCode(0);
		}catch(Exception e) {
			throw new MathParserServerException(e, 1, e.getMessage());
		}
		return response;
	}

}
