/**
 * 
 */
package de.dralle.mathparser;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.util.FastMath;
import org.apache.log4j.Logger;

/**
 * Lookup table for numeric values during numeric expression evaluation.
 * @author Nils Dralle
 *
 */
public class ValueLookupTable implements Cloneable{
	

	private static Logger log = Logger.getLogger(ValueLookupTable.class);
	
	private Map<String,Map<String,Double>> identifierValueMap;
	private Map<Constants,Map<String,Double>> constantsValueMap;
	private double constantsDefaultValue;
	
	/**
	 * @return the constantsDefaultValue
	 */
	public double getConstantsDefaultValue() {
		return constantsDefaultValue;
	}

	/**
	 * @param constantsDefaultValue the constantsDefaultValue to set
	 */
	public void setConstantsDefaultValue(double constantsDefaultValue) {
		this.constantsDefaultValue = constantsDefaultValue;
	}

	public ValueLookupTable() {
		identifierValueMap=new HashMap<String, Map<String,Double>>();
		constantsValueMap=new HashMap<Constants, Map<String,Double>>();
		constantsDefaultValue=0;
		setupConstantsDefaults();
	}
	
	private void setupConstantsDefaults() {
		setValueFor(Constants.PI, FastMath.PI);
		setValueFor(Constants.EPSILON, FastMath.E);
		
	}

	public void setValueFor(String identifier,double value) {
		setValueFor(identifier, null, value);
	}
	public void setValueFor(String identifier,String indice,double value) {
		Map<String,Double> indiceMap;
		if(identifierValueMap.containsKey(identifier)) {
			indiceMap=identifierValueMap.get(identifier);
		}else {
			indiceMap=new HashMap<String, Double>();
			identifierValueMap.put(identifier, indiceMap);
		}
		indiceMap.put(indice, value);
	}
	public double getValueFor(String identifier) {
		return getValueFor(identifier, null);
	}
	public double getValueFor(String identifier,String indice) {
		Map<String,Double> indiceMap;
		if(identifierValueMap.containsKey(identifier)) {
			indiceMap=identifierValueMap.get(identifier);
		}else {
			indiceMap=new HashMap<String, Double>();
			identifierValueMap.put(identifier, indiceMap);
		}
		if(indiceMap.containsKey(indice)) {
			return indiceMap.get(indice);
		}else {
			log.error(String.format("No value found for identifier %s_%s", identifier,indice));
			throw new RuntimeException(String.format("No value found for identifier %s_%s", identifier,indice));
		}
	}
	public void setValueFor(Constants constant,double value) {
		setValueFor(constant, null, value);
	}
	public void setValueFor(Constants constant,String indice,double value) {
		Map<String,Double> indiceMap;
		if(constantsValueMap.containsKey(constant)) {
			indiceMap=constantsValueMap.get(constant);
		}else {
			indiceMap=new HashMap<String, Double>();
			constantsValueMap.put(constant, indiceMap);
		}
		indiceMap.put(indice, value);
	}
	public double getValueFor(Constants constant) {
		return getValueFor(constant, null);
	}
	public double getValueFor(Constants constant,String indice) {
		Map<String,Double> indiceMap;
		if(constantsValueMap.containsKey(constant)) {
			indiceMap=constantsValueMap.get(constant);
		}else {
			indiceMap=new HashMap<String, Double>();
			constantsValueMap.put(constant, indiceMap);
		}
		if(indiceMap.containsKey(indice)) {
			return indiceMap.get(indice);
		}else {
			log.warn(String.format("No value found for constant %s_%s. Returning default value", constant.name(),indice));
			return constantsDefaultValue;
		}
	}

	@Override
	public ValueLookupTable clone() throws CloneNotSupportedException {
		ValueLookupTable clone = new ValueLookupTable();
		for (String identifier : identifierValueMap.keySet()) {
			Map<String, Double> indiceMap = identifierValueMap.get(identifier);
			for (String indice : indiceMap.keySet()) {
				Double val = indiceMap.get(indice);
				clone.setValueFor(identifier, indice, val.doubleValue());
			}
		}
		for (Constants constant : constantsValueMap.keySet()) {
			Map<String, Double> indiceMap = constantsValueMap.get(constant);
			for (String indice : indiceMap.keySet()) {
				Double val = indiceMap.get(indice);
				clone.setValueFor(constant, indice, val.doubleValue());
			}
		}
		return clone;
	}
}
