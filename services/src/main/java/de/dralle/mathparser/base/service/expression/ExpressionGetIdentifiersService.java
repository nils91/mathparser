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
import de.dralle.mathparser.SymbolIdentifier;
import de.dralle.mathparser.SymbolTable;
import de.dralle.mathparser.base.common.MathParserServerException;
import de.dralle.mathparser.base.service.expression.IExpressionSyntaxCheckService;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.parser.latexmath.LatexMathExpressionParser;
import de.dralle.mathparser.visitors.CalculateNumericalValueVisitor;
import io.swagger.model.ExpressionConstantsResponse;
import io.swagger.model.ExpressionIdentifiersRequest;
import io.swagger.model.ExpressionIdentifiersResponse;
import io.swagger.model.ExpressionSyntaxCheckRequest;
import io.swagger.model.ExpressionSyntaxCheckResponse;
import io.swagger.model.Identifier;
import io.swagger.model.NumericalCheckRequest;
import io.swagger.model.NumericalCheckResponse;
import io.swagger.model.NumericalResultRequest;
import io.swagger.model.NumericalResultResponse;

/**
 * @author Nils Dralle
 *
 */
@Service
public class ExpressionGetIdentifiersService implements IExpressionGetIdentifiersService {

	public ExpressionGetIdentifiersService() {

	}

	@Override
	public ExpressionIdentifiersResponse handle(ExpressionIdentifiersRequest request,
			HttpServletResponse httpServletResponse) throws MathParserServerException {
		ExpressionIdentifiersResponse response = new ExpressionIdentifiersResponse();
		ExpressionNode expression = null;
		try {
			expression = ExpressionParser.buildExpressionTreeFromString(request.getExpression());
		} catch (Exception e) {
			throw new MathParserServerException(e, 1, e.getMessage());
		}
		if (expression == null) {
			throw new MathParserServerException(1, "");
		}
		SymbolTable st = SymbolTable.fromTree(expression);
		SymbolIdentifier[] identifierNames = st.getIdentifiersAsArray();
		List<Identifier> fiers = new ArrayList<Identifier>();
		for (int i = 0; i < identifierNames.length; i++) {
			Identifier identifier = new Identifier();
			identifier.setName(identifierNames[i].getName());
			identifier.setIndex(identifierNames[i].getIndex());
			fiers.add(identifier);
		}
		response.setCode(0);
		response.setIdentifiers(fiers);
		return response;
	}
}
