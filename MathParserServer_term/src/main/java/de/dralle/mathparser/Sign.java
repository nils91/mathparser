/**
 * 
 */
package de.dralle.mathparser;

/**
 * Mathmatical sign.
 * 
 * @author Nils Dralle
 *
 */
public enum Sign {
	NEGATIVE, POSITIVE;

	/**
	 * Creates the opposite sign as new instance to the sign given as parameter.
	 * @param sign
	 * @return
	 */
	public static Sign createOpposite(Sign sign) {
		switch(sign) {
		case NEGATIVE:
			return POSITIVE;
		case POSITIVE:
			return NEGATIVE;
		default:
			break;		
		}
		return null;
	}
}
