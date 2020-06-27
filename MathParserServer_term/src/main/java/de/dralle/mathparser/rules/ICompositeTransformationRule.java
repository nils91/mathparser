/**
 * 
 */
package de.dralle.mathparser.rules;

/**
 * @author Nils Dralle
 *
 */
public interface ICompositeTransformationRule extends ITransformationRule{
	void addChildRule(AbstractTransformationRule childRule);

	boolean removeChildRule(AbstractTransformationRule childRule);
	/**
	 * True by default. If false, every child rule needs to be possible for this
	 * rule to be possible, if true only one child rule needs to be possible.
	 */
	boolean isIgnoreNotPossibleRules();

	/**
	 * True by default. If false, every child rule needs to be possible for this
	 * rule to be possible, if true only one child rule needs to be possible.
	 */
	void setIgnoreNotPossibleRules(boolean ignoreNotPossibleRules);
}
