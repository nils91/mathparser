/**
 * 
 */
package de.dralle.mathparser.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import de.dralle.mathparser.ExpressionUtil;
import de.dralle.mathparser.NodeUtils;
import de.dralle.mathparser.Sign;
import de.dralle.mathparser.nodes.ExpressionNode;
import de.dralle.mathparser.nodes.IntegerNode;
import de.dralle.mathparser.nodes.MultiAddNode;
import de.dralle.mathparser.nodes.MultiMultiplyNode;
import de.dralle.mathparser.nodes.RealNode;

/**
 * Combine factors in sums together. Could be written as an+bn -> (a+b)n, but works on sums of every lengths.
 * @author Nils Dralle
 *
 */
public class CommonFactorCombinationRule2 extends AbstractTransformationRule {
	private boolean onlyForConstantValues = true;
	private boolean onlyForNonConstantCommonFactors = true;

	public boolean isOnlyForNonConstantCommonFactors() {
		return onlyForNonConstantCommonFactors;
	}

	public void setOnlyForNonConstantCommonFactors(boolean onlyForNonConstantCommonFactors) {
		this.onlyForNonConstantCommonFactors = onlyForNonConstantCommonFactors;
	}

	private static Logger log = Logger.getLogger(CommonFactorCombinationRule2.class);

	/**
	 * @return the onlyForConstantValues
	 */
	public boolean isOnlyForConstantValues() {
		return onlyForConstantValues;
	}

	/**
	 * @param onlyForConstantValues the onlyForConstantValues to set
	 */
	public void setOnlyForConstantValues(boolean onlyForConstantValues) {
		this.onlyForConstantValues = onlyForConstantValues;
	}

	@Override
	public boolean isPossible(ExpressionNode expression) {
		return NodeUtils.isAnyNodeTypeInTree(expression, MultiAddNode.class);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression) throws Exception {
		return apply(expression, true);
	}

	@Override
	public ExpressionNode apply(ExpressionNode expression, boolean transformAll) throws Exception {
		ExpressionNode original = expression.clone();
		int transformed = 0;
		if (NodeUtils.isAnyNodeType(expression, MultiAddNode.class)) {
			ExpressionNode[][] commonFactorMatrix = new ExpressionNode[expression.getChildcount()][expression
					.getChildcount()];
			ExpressionNode[][][] remainderMatrix = new ExpressionNode[expression.getChildcount()][expression
					.getChildcount()][2];
			int commonFactorCount = buildCommonFactorMatrix(expression, commonFactorMatrix, remainderMatrix);
			if (commonFactorCount > 0) {
				List<ExpressionNode> newExpressionChilds = new ArrayList<ExpressionNode>();
				List<int[]> commonFactorIndices = new ArrayList<int[]>();
				int[][] commonFactorWeightMatrix = new int[expression.getChildcount()][expression.getChildcount()];
				for (int i = 0; i < commonFactorMatrix.length; i++) {
					for (int j = 0; j < commonFactorMatrix[i].length; j++) {
						ExpressionNode commonFactor = commonFactorMatrix[i][j];
						if (commonFactor != null) {
							commonFactorWeightMatrix[i][j] = commonFactor.getChildcount();
							commonFactorIndices.add(new int[] { i, j });
						} else {
							commonFactorWeightMatrix[i][j] = Integer.MIN_VALUE;
						}
					}
				}
				boolean commonFactorLeft = false;
				Set<Integer> usedRemainderIndices = new HashSet<Integer>();
				do {
					commonFactorLeft = false;
					ExpressionNode nxtCommonFactor = null;
					int nxtCommonFactorWeight = Integer.MIN_VALUE;
					int nxtCommonFactorIndexi = -1;
					int nxtCommonFactorIndexj = -1;
					for (int[] ia : commonFactorIndices) {
						int i = ia[0];
						int j = ia[1];
						ExpressionNode commonFactor = commonFactorMatrix[i][j];
						int factorWeight = commonFactorWeightMatrix[i][j];
						if (factorWeight > nxtCommonFactorWeight) {
							nxtCommonFactorWeight = factorWeight;
							nxtCommonFactor = commonFactor;
							nxtCommonFactorIndexi = i;
							nxtCommonFactorIndexj = j;
						} else {
							if (commonFactor != null) {
								commonFactorLeft = true;
							}
						}
					}
					if (nxtCommonFactor != null) {
						int commonFactorOccuranceCount = 0; // how often does nxtCommonFactor occur?
						Set<Integer> remainderIndices = new HashSet<Integer>();
						List<ExpressionNode> remaindersForCommonFactor = new ArrayList<ExpressionNode>();
						for (int[] ia : commonFactorIndices) {
							int i = ia[0];
							int j = ia[1];
							ExpressionNode commonFactor = commonFactorMatrix[i][j];
							if (commonFactor != null) {
								if (ExpressionUtil.isEqual(commonFactor, nxtCommonFactor)) {
									if (!remainderIndices.contains(i) && !usedRemainderIndices.contains(i)) {
										commonFactorOccuranceCount++;
										remainderIndices.add(i);
										usedRemainderIndices.add(i);
										ExpressionNode remainderToAdd = remainderMatrix[i][j][0];
										if (remainderToAdd != null) {
											remaindersForCommonFactor.add(remainderToAdd);
										}
										expression.setChild(null, i);
									}
									if (!remainderIndices.contains(j) && !usedRemainderIndices.contains(j)) {
										commonFactorOccuranceCount++;
										remainderIndices.add(j);
										usedRemainderIndices.add(j);
										ExpressionNode remainderToAdd = remainderMatrix[i][j][1];
										if (remainderToAdd != null) {
											remaindersForCommonFactor.add(remainderToAdd);
										}
										expression.setChild(null, j);
									}
									commonFactorMatrix[i][j] = null;
									commonFactorWeightMatrix[i][j] = Integer.MIN_VALUE;
								}
							}
						}
						ExpressionNode remainderTerm = null;
						int commonFactorOccuranceCountNotAccountedFor = commonFactorOccuranceCount
								- remaindersForCommonFactor.size();
						if (remaindersForCommonFactor.size() == 0) {
							remainderTerm = new IntegerNode(commonFactorOccuranceCount);
						} else if (remaindersForCommonFactor.size() == 1) {
							if (commonFactorOccuranceCountNotAccountedFor > 0) {
								MultiAddNode tmp = new MultiAddNode();
								tmp.setChild(remaindersForCommonFactor.get(0), -1);
								tmp.setChild(new IntegerNode(commonFactorOccuranceCountNotAccountedFor), -1);
								remainderTerm = tmp;
							} else {
								remainderTerm = remaindersForCommonFactor.get(0);
								log.error("Shouldnt happen");
								// TODO: not supposed to happen, so log maybe?
							}
						} else if (remaindersForCommonFactor.size() == 2) {
							if (commonFactorOccuranceCountNotAccountedFor > 0) {
								MultiAddNode tmp = new MultiAddNode();
								tmp.setChild(remaindersForCommonFactor.get(0), -1);
								tmp.setChild(remaindersForCommonFactor.get(1), -1);
								tmp.setChild(new IntegerNode(commonFactorOccuranceCountNotAccountedFor), -1);
								remainderTerm = tmp;
							} else {
								MultiAddNode tmp = new MultiAddNode();
								tmp.setChild(remaindersForCommonFactor.get(0), -1);
								tmp.setChild(remaindersForCommonFactor.get(1), -1);
								remainderTerm = tmp;
							}
						} else {
							MultiAddNode tmp = new MultiAddNode();
							for (ExpressionNode expressionNode : remaindersForCommonFactor) {
								tmp.setChild(expressionNode, -1);
							}
							if (commonFactorOccuranceCountNotAccountedFor > 0) {
								tmp.setChild(new IntegerNode(commonFactorOccuranceCountNotAccountedFor), -1);
							}
							remainderTerm = tmp;
						}
						MultiMultiplyNode newChild = new MultiMultiplyNode();
						newChild.setChild(remainderTerm, -1);
						newChild.setChild(nxtCommonFactor, -1);
						newExpressionChilds.add(newChild);

						transformed++;

						expression.setChild(null, nxtCommonFactorIndexi);
						expression.setChild(null, nxtCommonFactorIndexj);
					}
				} while (commonFactorLeft && transformAll);
				// rebuild expression
				ArrayList<ExpressionNode> notCommonFactorExpressionChild = new ArrayList<ExpressionNode>();
				for (int i = 0; i < expression.getChildcount(); i++) {
					ExpressionNode child = expression.getChild(i);
					if (child != null) {
						notCommonFactorExpressionChild.add(child);
					}
				}
				newExpressionChilds.addAll(notCommonFactorExpressionChild);
				expression = NodeUtils.buildAdditiveExpressionFromList(newExpressionChilds,expression.getSign());
				if (!transformAll) {
					return transformed > 0 ? expression : null;
				}
			}
			if (!transformAll) {
				return null;
			}
		}
		for (int i = 0; i < expression.getChildcount(); i++) {
			ExpressionNode transformedChild = apply(expression.getChild(i), transformAll);
			if (transformedChild != null) {
				expression.setChild(transformedChild, i);
				transformed++;
				if (!transformAll) {
					return transformedChild;
				}
			}
		}
		ExpressionNode rval = transformed > 0 ? expression : null;
		log.debug(String.format("%s transformed to %s", original, rval));
		return rval;
	}

	private int buildCommonFactorMatrix(ExpressionNode expression, ExpressionNode[][] commonFactorMatrix,
			ExpressionNode[][][] remainderMatrix) {
		int commonFactorCnt = 0;
		for (int i = 0; i < expression.getChildcount(); i++) {
			for (int j = i + 1; j < expression.getChildcount(); j++) {
				ExpressionNode term1 = expression.getChild(i);
				ExpressionNode term2 = expression.getChild(j);
				List<ExpressionNode> t1CfCandidatesList = new ArrayList<ExpressionNode>();
				List<ExpressionNode> t2CfCandidatesList = new ArrayList<ExpressionNode>();
				if (!NodeUtils.isAnyNodeType(term1, MultiMultiplyNode.class)) {
					t1CfCandidatesList.add(term1);
				} else {
					for (int k = 0; k < term1.getChildcount(); k++) {
						t1CfCandidatesList.add(term1.getChild(k));
					}
				}
				if (!NodeUtils.isAnyNodeType(term2, MultiMultiplyNode.class)) {
					t2CfCandidatesList.add(term2);
				} else {
					for (int k = 0; k < term2.getChildcount(); k++) {
						t2CfCandidatesList.add(term2.getChild(k));
					}
				}
				Sign t1Sign = term1.getSign();
				Sign t2Sign = term2.getSign();
				List<ExpressionNode> commonFactorList = new ArrayList<ExpressionNode>();
				for (int k = 0; k < t1CfCandidatesList.size(); k++) {
					for (int k2 = 0; k2 < t2CfCandidatesList.size(); k2++) {
						ExpressionNode term1Child = t1CfCandidatesList.get(k);
						ExpressionNode term2Child = t2CfCandidatesList.get(k2);
						boolean isCommon = ExpressionUtil.isEqual(term1Child, term2Child);
						if (isCommon) {
							commonFactorList.add(term1Child);

							t1CfCandidatesList.set(k, null);
							t2CfCandidatesList.set(k2, null);
						}
					}
				}
				List<ExpressionNode> remainderT1List = new ArrayList<ExpressionNode>();
				List<ExpressionNode> remainderT2List = new ArrayList<ExpressionNode>();
				for (int k = 0; k < t1CfCandidatesList.size(); k++) {
					ExpressionNode term1Child = t1CfCandidatesList.get(k);
					if (term1Child != null) {
						NodeUtils.addExpressionToListIfNotInList(term1Child, remainderT1List);
					}
				}
				for (int k = 0; k < t2CfCandidatesList.size(); k++) {
					ExpressionNode term2Child = t2CfCandidatesList.get(k);
					if (term2Child != null) {
						NodeUtils.addExpressionToListIfNotInList(term2Child, remainderT2List);
					}
				}
				ExpressionNode commonFactor = null;
				if(commonFactorList.size()>0) {
					commonFactor = NodeUtils.buildMultiplicativeExpressionFromList(commonFactorList);
				}				
				ExpressionNode remainderT1 = NodeUtils.buildMultiplicativeExpressionFromList(remainderT1List,t1Sign);
				ExpressionNode remainderT2 = NodeUtils.buildMultiplicativeExpressionFromList(remainderT2List,t2Sign);
				if (writeCommonFactorMatrix(i, j, commonFactorMatrix, remainderMatrix, commonFactor, remainderT1,
						remainderT2)) {
					commonFactorCnt++;
				}

			}
		}
		return commonFactorCnt;
	}

	private boolean writeCommonFactorMatrix(int i, int j, ExpressionNode[][] commonFactorMatrix,
			ExpressionNode[][][] remainderMatrix, ExpressionNode commonFactor, ExpressionNode remainder1,
			ExpressionNode remainder2) {
		boolean remainderConstant = NodeUtils.isEachNodeAnyTypeOrNull(remainder1, IntegerNode.class)
				&& NodeUtils.isEachNodeAnyTypeOrNull(remainder2, IntegerNode.class);
		boolean commonFactorVar = !NodeUtils.isAllNodesInTreeOfAnyType(commonFactor, IntegerNode.class, RealNode.class);
		boolean writeRemainder = onlyForConstantValues ? remainderConstant : true;
		boolean writeCommonFactor = onlyForNonConstantCommonFactors ? commonFactorVar : true;
		boolean write = writeRemainder && writeCommonFactor;
		if (write) {
			writeCommonFactorMatrixNoCheck(i, j, commonFactorMatrix, commonFactor);
			writeRemainderMatrixNoCheck(i, j, remainderMatrix, remainder1, remainder2);
		}
		return write;
	}

	private void writeCommonFactorMatrixNoCheck(int i, int j, ExpressionNode[][] commonFactorMatrix,
			ExpressionNode commonFactor) {
		commonFactorMatrix[i][j] = commonFactor;
	}

	private void writeRemainderMatrixNoCheck(int i, int j, ExpressionNode[][][] remainderMatrix,
			ExpressionNode remainder1, ExpressionNode remainder2) {
		remainderMatrix[i][j][0] = remainder1;
		remainderMatrix[i][j][1] = remainder2;
	}

}
