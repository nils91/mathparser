/**
 * 
 */
package de.dralle.mathparser;

/**
 * Container class for variable names. 
 * 
 * @author Nils Dralle
 *
 */
public class SymbolIdentifier implements Comparable<SymbolIdentifier> {
	private String name;

	public SymbolIdentifier(String name) {
		super();
		this.name = name;
		this.index = null;
	}
	
	public SymbolIdentifier(String name, String index) {
		super();
		this.name = name;
		this.index = index;
	}

	private String index;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	@Override
	public int compareTo(SymbolIdentifier o) {
		if (o == null) {
			return Integer.MIN_VALUE;
		}
		if (name.equals(o.getName())) {
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
		return name.compareTo(o.getName());
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SymbolIdentifier) {
			return this.compareTo((SymbolIdentifier) obj)==0;
		}else if(obj instanceof String){
			if(index==null) {
				return name.equals((String)obj);
			}
		}
			return false;				
	}

	@Override
	public String toString() {
		if (index==null) {
			return name;
		}
		else {
			return String.format("%s_%s", name,index);
		}
	}
}
