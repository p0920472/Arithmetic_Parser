/**
 * 
 */
package com;

/**
 * @author Mohd Saiful Akmal
 *
 */
public class Operand {
	
	private int startIndex;
	private int endIndex;
	private int Operand;
	/**
	 * 
	 */
	public Operand() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * @param endIndex the endIndex to set
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	/**
	 * @return the operand
	 */
	public int getOperand() {
		return Operand;
	}

	/**
	 * @param operand the operand to set
	 */
	public void setOperand(int operand) {
		Operand = operand;
	}

	public Operand(int startIndex, int endIndex, int operand) {
		super();
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		Operand = operand;
	}

}
