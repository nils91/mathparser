/**
 * 
 */
package de.dralle.mathparser;

/**
 * Container class to represent constants.
 * @author Nils Dralle
 *
 */
public class SymbolConstant implements Comparable<SymbolConstant> {
	@Override
	public int hashCode() {
		return constant.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SymbolConstant) {
			return this.compareTo((SymbolConstant) obj) == 0;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		if (index == null) {
			return constant.getName();
		} else {
			return String.format("%s_%s", constant.getName(), index);
		}
	}

	private Constants constant;
	private String index;

	public SymbolConstant(Constants constant, String identifier) {
		super();
		this.constant = constant;
		this.index = identifier;
	}

	/**
	 * @return the constant
	 */
	public Constants getConstant() {
		return constant;
	}

	/**
	 * @param constant the constant to set
	 */
	public void setConstant(Constants constant) {
		this.constant = constant;
	}

	/**
	 * @return the identifier
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.index = identifier;
	}

	@Override
	public int compareTo(SymbolConstant o) {
		if (o == null) {
			return Integer.MIN_VALUE;
		}
		if (constant.equals(o.getConstant())) {
			if (index == null) {
				if (o.getIndex() == null) {
					return 0;
				} else {
					return 1;
				}
			} else {
				if (o.getIndex() == null) {
					return -1;
				} else {
					return index.compareTo(o.getIndex());
				}
			}
		}
		return constant.compareTo(o.getConstant());
	}
}
