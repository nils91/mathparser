/**
 * 
 */
package de.dralle.mathparser.rules;

/**
 * @author Nils Dralle
 *
 */
public interface IRepeatingTransformationRule extends ITransformationRule{
	/**
	 * @return the maxRepeats
	 */
	int getMaxRepeats();

	/**
	 * @param maxRepeats the maxRepeats to set
	 */
	void setMaxRepeats(int maxRepeats);

}
