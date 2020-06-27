/**
 * Example-Visitor-Implementation. Example for thesis. Not used in actual application.
 * @author Nils Dralle
 *
 * @param <T>
 */
public class B implements IVisitor<String> {

	public String visit(A obj) {
		if(obj!=null) {
			return obj.toString();
		}
		return null;		
	}

}
