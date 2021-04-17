package com;

public class Sign {

	private static final char ADD = '+';
	private static final char CLOSE_BRAC = ')';
	private static final char DIV = '/';
	private static final char MUL = '*';
	private static final char OPEN_BRAC = '(';
	private static final char SUB = '-';


	private char charContainer;

	private int index;

	public Sign(char charContainer) {
		super();
		this.charContainer = charContainer;
	}

	public Sign() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the charContainer
	 */
	public char getCharContainer() {
		return charContainer;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	public boolean isADD() {
		return this.charContainer == ADD;
	}

	public boolean isCloseBracket() {
		return this.charContainer == CLOSE_BRAC;
	}

	public boolean isDIV() {
		return this.charContainer == DIV;
	}

	public boolean isMUL() {
		return this.charContainer == MUL;
	}

	public boolean isOpenBracket() {
		return this.charContainer == OPEN_BRAC;
	}

	public boolean isSign() {
		return this.isADD() | this.isSUB() | this.isMUL() | this.isDIV() ;
	}

	public boolean isSUB() {
		return this.charContainer == SUB;
	}

	/**
	 * @param charContainer the charContainer to set
	 */
	public void setCharContainer(char charContainer) {
		this.charContainer = charContainer;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 
	 * @return true if char is a number
	 */
	public boolean isNumber() {
		boolean l_rtn = false;

		switch (charContainer) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				l_rtn=true;
				break;

			default:
				break;
		}

		return l_rtn;
	}

}
