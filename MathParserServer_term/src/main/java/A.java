/**
 * Example-Visitor-Implementation. Example for thesis. Not used in actual application.
 * @author Nils Dralle
 *
 * @param <T>
 */
public class A {

	public <T> T accept(IVisitor<T> visitor) {
		if(visitor!=null) {
			return visitor.visit(this);
		}
		return null;
	}

}
