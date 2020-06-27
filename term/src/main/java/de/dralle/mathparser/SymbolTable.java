/**
 * 
 */
package de.dralle.mathparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.dralle.mathparser.nodes.interfaces.IMathParserBaseNode;
import de.dralle.mathparser.visitors.ExpressionTreeSymbolTableVisitor;

/**
 * The symbol table. Contains all vars and constant (and var counts) that are in a given expression.
 * 
 * @author Nils Dralle
 *
 */
public class SymbolTable {
	private Map<String, List<String>> identifiers;
	private Map<Constants, List<String>> constants;

	private Map<String, Map<String, Integer>> identifierCounts;

	public SymbolTable() {
		identifiers = new HashMap<String, List<String>>();
		constants = new HashMap<Constants, List<String>>();

		identifierCounts = new HashMap<String, Map<String, Integer>>();
	}

	public List<SymbolIdentifier> getIdentifiers() {
		List<SymbolIdentifier> identifierList = new ArrayList<SymbolIdentifier>();
		for (String key : identifiers.keySet()) {
			List<String> indices = identifiers.get(key);
			for (String index : indices) {
				SymbolIdentifier identifier = new SymbolIdentifier(key, null);
				if (index != null) {
					identifier.setIndex(index);
				}
				identifierList.add(identifier);
			}
		}
		return identifierList;
	}

	public Collection<SymbolConstant> getConstants() {
		List<SymbolConstant> constantList = new ArrayList<SymbolConstant>();
		for (Constants key : constants.keySet()) {
			List<String> indices = constants.get(key);
			for (String index : indices) {
				SymbolConstant constant = new SymbolConstant(key, null);
				if (index != null) {
					constant.setIdentifier(index);
				}
				constantList.add(constant);
			}
		}
		return constantList;
	}

	/**
	 * Gets all identifiers that are stored in this symbol table as an array,
	 * pre-sorted
	 * 
	 * @return
	 */
	public SymbolIdentifier[] getIdentifiersAsArray() {
		SymbolIdentifier[] array = getIdentifiers().toArray(new SymbolIdentifier[getIdentifiers().size()]);
		Arrays.sort(array);
		return array;
	}

	public static SymbolTable fromTree(IMathParserBaseNode tree) {
		return tree.accept(new ExpressionTreeSymbolTableVisitor());
	}

	public boolean addIdentifier(String identifier) {
		return addIdentifier(identifier, null);
	}

	public boolean addIdentifier(String identifier, String indice) {
		boolean known = false;
		List<String> indiceList = null;
		if (!identifiers.containsKey(identifier)) {
			indiceList = new ArrayList<String>();
			identifiers.put(identifier, indiceList);
		} else {
			indiceList = identifiers.get(identifier);
		}
		if (!indiceList.contains(indice)) {
			indiceList.add(indice);
		} else {
			known = true;
		}
		increaseIdentiferCount(identifier, indice);
		return !known;
	}

	private void increaseIdentiferCount(String identifier, String indice) {
		Map<String, Integer> countMap = null;
		if (identifierCounts.containsKey(identifier)) {
			countMap = identifierCounts.get(identifier);
		} else {
			countMap = new HashMap<String, Integer>();
			identifierCounts.put(identifier, countMap);
		}
		Integer count = null;
		if (countMap.containsKey(indice)) {
			count = countMap.get(indice);
		} else {
			count = new Integer(0);
		}
		count++;
		countMap.put(indice, count);
	}

	public int getIdentifierCount(String identifier, String indice) {
		int value = 0;
		Map<String, Integer> identifierIndiceMap = identifierCounts.get(identifier);
		if (identifierIndiceMap != null) {
			Integer identifierCount = identifierIndiceMap.get(indice);
			if (identifierCount != null) {
				value = identifierCount.intValue();
			}
		}
		return value;
	}

	public boolean addConstant(Constants constant) {
		return addConstant(constant, null);
	}

	public boolean addConstant(Constants constant, String indice) {
		boolean known = false;
		List<String> indiceList = null;
		if (!constants.containsKey(constant)) {
			indiceList = new ArrayList<String>();
			constants.put(constant, indiceList);
		} else {
			indiceList = constants.get(constant);
		}
		if (!indiceList.contains(indice)) {
			indiceList.add(indice);
		} else {
			known = true;
		}
		return !known;
	}

	/**
	 * Combines another SymbolTable into this one
	 * 
	 * @param other
	 */
	public void combineInto(SymbolTable other) {
		// merge identifiers
		for (String otherIdentfier : other.identifiers.keySet()) {
			List<String> otherIndiceList = other.identifiers.get(otherIdentfier);
			for (String otherIndex : otherIndiceList) {
				boolean newIdentifer = false;
				if (getIdentifierCount(otherIdentfier, otherIndex) <= 0) {
					newIdentifer = addIdentifier(otherIdentfier, otherIndex);
				}
				int otherIdentifierCount = other.getIdentifierCount(otherIdentfier, otherIndex);
				if (newIdentifer) {
					otherIdentifierCount--;
				}
				for (int i = 0; i < otherIdentifierCount; i++) {
					increaseIdentiferCount(otherIdentfier, otherIndex);
				}
			}
		}
		// merge constants
		for (Constants otherConstant : other.constants.keySet()) {
			List<String> otherIndiceList = other.constants.get(otherConstant);
			for (String otherIndex : otherIndiceList) {
				addConstant(otherConstant, otherIndex);
			}
		}
	}

}
