/**
 * Testinterface for Visitor-Pattern. Example for thesis. Not used in actual application.
 * @author Nils Dralle
 *
 * @param <T>
 */
public interface IVisitor<T> {
	T visit(A obj);
}
