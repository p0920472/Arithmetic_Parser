package com;

import java.util.logging.Logger;

/**
 * This class is designed to Parse a String and output the resulting parse The
 * parsing is based on BODMAS logic, hence, it will not be as per proper
 * calculation priority
 * 
 * @author Mohd Saiful Akmal
 *
 */
public class Parser extends Equation {
	
    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private String parseStr;
	private Sign s1;
	private Sign s2;
	private Operand leftOperand;
	private Operand rightOperand;
	private Sign currentOperator;

	/**
	 * @return the outPut
	 */
	public int getOutPut() {
		return outPut;
	}

	/**
	 * @param outPut the outPut to set
	 */
	public void setOutPut(int outPut) {
		this.outPut = outPut;
	}

	private int outPut;

	public Parser(String parseStr) throws RuntimeException {
		super();
		LOG.info("-------------------------------------------------\r\n"
				+"-------------------------------------------------\r\n");
		this.parseStr = parseStr;
		this.outPut = parse(this.parseStr);
	}

	/**
	 * @return the parseStr
	 */
	public String getParseStr() {
		return parseStr;
	}

	/**
	 * @param parseStr the parseStr to set
	 */
	public void setParseStr(String parseStr) {
		this.parseStr = parseStr;
	}

	/**
	 * Method to find if Equation has brackets
	 * 
	 * @return true if Equation has brackets
	 */
	public boolean hasInnerBrac() {
		boolean rtn = false;
		this.s1 = new Sign();
		this.s2 = new Sign();

		for (int i = 0; i < super.getEq().length(); i++) {
			s1.setCharContainer(super.getEq().charAt(i));

			if (s1.isOpenBracket()) {
				s1.setIndex(i);
			} else if (s1.isCloseBracket()) {
				s2.setIndex(i);
				return true;
			} else {

			}
		}

		return rtn;
	}

	/**
	 * Parse the string equation into the resulting Integer
	 * 
	 * @param s
	 * @return
	 * @throws RuntimeException
	 */
	public int parse(String s) throws RuntimeException {
		int result = 0;
		int l_temp = 0;

		super.setEq(s);
		if(!this.validEquation()){
			//throw new RuntimeException("Invalid Equation");
			LOG.severe("Invalid Equation, returning null");
			return 0x00;
		}

		if (this.hasInnerBrac()) {
			result = innerBracketParsing(s);

		} else if (!containSign()) {
			result = Integer.parseInt(s);
		} else {
			// find Highest priority sign
			this.currentOperator = this.getHighestSign();

			// Get Operands
			this.leftOperand = this.getLeftOperand();
			this.rightOperand = this.getRightOperand();

			// Calculate Operation
			if (this.currentOperator.getIndex() != 0) {
				l_temp = this.calculateOperation();
				s = this.insertResultToEq(l_temp);
			} else {

			}
			super.setEq(s);

			if (!this.containSign()) {
				LOG.info("Sign not found: " + s);
				result = Integer.parseInt(s);
			} else if (this.currentOperator.getIndex() == 0) {

				LOG.info("Negative found: " + s);
				result = Integer.parseInt(s);
			} else {
				LOG.info("Sign found: " + s);
				result = result + this.parse(s);
			}
		}

		return result;
	}

	/**
	 * Parsing Logic of Inner Brackets
	 * 
	 * @param s the string Equation
	 * @return
	 * @throws RuntimeException
	 */
	private int innerBracketParsing(String s) throws RuntimeException {
		int result = 0;
		int l_temp;
		int l_startIndex = s1.getIndex();
		int l_endIndex = s2.getIndex();
		String backupEq = super.getEq();
		// Get inner equation
		LOG.info("Has Inner Bracket: " + s);

		LOG.info("Inner Bracket: " + s.substring(l_startIndex + 1, l_endIndex));

		l_temp = this.parse(s.substring(l_startIndex + 1, l_endIndex));
		LOG.info("Resulting Inner Bracket"+ l_temp);
		super.setEq(backupEq);
		LOG.info("InnerBeforeBracketsInserted: " + s);

		LOG.info("s1.index:" + l_startIndex);
		LOG.info("s2.index:" + l_endIndex);

		if (this.leftOperand == null) {
			LOG.info("Created new Operand:Left");
			this.leftOperand = new Operand();
			// 	Not sure why this is here in the first place
			//	if(this.containSign()){
			// 	//l_endIndex++;
			// }
			// else{
			// 	LOG.info("endIndexNotIncremented");
			// }

		}

		if (this.rightOperand == null) {
			LOG.info("Created new Operand:Right");
			this.rightOperand = new Operand();
			l_startIndex++;
		}

		if (l_startIndex != 0)
			this.leftOperand.setStartIndex(l_startIndex - 1);
		else
			this.leftOperand.setStartIndex(0);

			this.rightOperand.setEndIndex(l_endIndex + 1);
		// Insert back into String
		s = this.insertResultToEq(l_temp);
		LOG.info("InnerBracketsInserted: " + s);
		super.setEq(s);

		if (!this.containSign()) {
			LOG.info("Sign not found: " + s);
			result = Integer.parseInt(s);
		}  else if (this.currentOperator!=null && this.currentOperator.getIndex() == 0) {

			LOG.info("Negative found: " + s);
			result = Integer.parseInt(s);
		}else
		{
			LOG.info("Sign found: " + s);
			result = result + this.parse(s);
		}
		return result;
	}

	private String insertResultToEq(int a_result) {
		String l_rtn = "";
		String l_eq = super.getEq();
		Sign l_sg = new Sign();
		Sign l_sg_peek = new Sign();

		LOG.info("insertResultToEq().l_eq received: " + l_eq);

		l_sg.setCharContainer(l_eq.charAt(this.leftOperand.getStartIndex()));

		if(this.leftOperand.getStartIndex() != 0){
			l_sg_peek.setCharContainer(l_eq.charAt(this.leftOperand.getStartIndex()-1));
		}

		// Something is very wrong here (Need to implement peek functionality)
		if (l_sg.isSign() && !l_sg_peek.isSign() && this.leftOperand.getStartIndex() != 0) {
			l_rtn = l_eq.substring(0, this.leftOperand.getStartIndex() + 1);
		} else {
			l_rtn = l_eq.substring(0, this.leftOperand.getStartIndex());
		}
		LOG.info("insertResultToEq(). getStartIndex: " + this.leftOperand.getStartIndex());
		LOG.info("insertResultToEq(). After LeftOperand: " + l_rtn);
		l_rtn = l_rtn + a_result;

		LOG.info("insertResultToEq(). After Append Result: " + l_rtn);
		LOG.info("insertResultToEq(). l_eq.length():" + l_eq.length());
		LOG.info("insertResultToEq(). End index of right operand: " + this.rightOperand.getEndIndex());

		if (l_eq.length() - 1 > this.rightOperand.getEndIndex()) {
			LOG.info("Right Operand: "+  l_eq.substring(this.rightOperand.getEndIndex()));
			l_rtn = l_rtn + l_eq.substring(this.rightOperand.getEndIndex());
		} else {
			// l_rtn = l_rtn + l_eq.substring(this.rightOperand.getEndIndex() + 1);
		}

		LOG.info("insertResultToEq(). Returning: " + l_rtn);
		return l_rtn;
	}

	/**
	 * Get Highest Sign
	 * 
	 * @return
	 */
	public Sign getHighestSign() {
		Sign rtn = null;
		Sign l_sg = new Sign();
		String l_eq = super.getEq();
		int l_priority = 0;

		for (int i = 1; i < l_eq.length(); i++) {
			l_sg.setCharContainer(l_eq.charAt(i));
			if (l_sg.isSign()) {

				if (l_sg.isSUB()) {
					if (l_priority < 1) {

						l_priority = 1;
						rtn = new Sign(l_sg.getCharContainer());
						rtn.setIndex(i);
					}
				} else if (l_sg.isADD()) {
					if (l_priority < 2) {

						l_priority = 2;
						rtn = new Sign(l_sg.getCharContainer());
						rtn.setIndex(i);
					}
				} else if (l_sg.isMUL()) {
					if (l_priority < 3) {

						l_priority = 3;
						rtn = new Sign(l_sg.getCharContainer());
						rtn.setIndex(i);
					}

				} else if (l_sg.isDIV()) {
					if (l_priority < 4) {

						l_priority = 4;
						rtn = new Sign(l_sg.getCharContainer());
						rtn.setIndex(i);
					}

				} else {
					// theres still Brakets?
				}

			}
		}
		return rtn;
	}

	/**
	 * Method to get the Left Operand of the highest Sign
	 * 
	 * @return
	 */
	private Operand getLeftOperand() {
		int l_int = 0;
		Operand l_op = null;
		Sign l_sg = new Sign();
		Sign l_sg_peek = new Sign();
		String l_eq = super.getEq();

		LOG.info("getLeftOperand(). l_eq.getLeftOperand():" + l_eq);
		LOG.info("");

		for (int i = this.currentOperator.getIndex() - 1; i >= 0; i--) {
			l_sg.setCharContainer(l_eq.charAt(i));
			LOG.info("getLeftOperand(). l_sg.getLeftOperand().ArrayPointer: " + l_sg.getCharContainer());
			if (l_sg.isSign() || i == 0) {
				if (i == 0) {
					l_int = Integer.parseInt(l_eq.substring(i, currentOperator.getIndex()));
					LOG.info("getLeftOperand(). l_int.getLeftOperand(): " + l_int);
					l_op = new Operand(i, currentOperator.getIndex(), l_int);
					break;
				} else {
					/* Peek to check if next charater is sign */
					l_sg_peek.setCharContainer(l_eq.charAt(i - 1));
					if (l_sg_peek.isSign() && l_sg.isSUB()) {
						LOG.info("getLeftOperand(). multiple signs detected");
						l_int = Integer.parseInt(l_eq.substring(i, currentOperator.getIndex()));
						LOG.info("getLeftOperand(). l_int.getLeftOperand(): " + l_int);
						l_op = new Operand(i, currentOperator.getIndex(), l_int);
						break;
					} else {
						l_int = Integer.parseInt(l_eq.substring(i + 1, currentOperator.getIndex()));
						LOG.info("getLeftOperand(). l_int.getLeftOperand(): " + l_int);
						l_op = new Operand(i + 1, currentOperator.getIndex(), l_int);
						break;
					}
				}
			}
		}

		return l_op;
	}

	/**
	 * Method to get the Right Operand of the highest Sign
	 * 
	 * @return
	 */
	private Operand getRightOperand() {
		int l_int = 0;
		Operand l_op = null;
		Sign l_sg = new Sign();
		String l_eq = super.getEq();

		LOG.info("getRightOperand(). l_eq.getRightOperand(): " + l_eq);

		for (int i = this.currentOperator.getIndex() + 1; i < l_eq.length(); i++) {
			l_sg.setCharContainer(l_eq.charAt(i));
			LOG.info("getRightOperand(). ArrayPointer: " + l_sg.getCharContainer());

			if (l_sg.isSign() || i == l_eq.length() - 1) {
				if (l_sg.isSign() && i == this.currentOperator.getIndex() + 1) {
					LOG.info("getRightOperand(). detected sign");

				} else if (i == l_eq.length() - 1) {
					LOG.info(
							"getRightOperand(). Substring: " + l_eq.substring(currentOperator.getIndex() + 1, i + 1));
					l_int = Integer.parseInt(l_eq.substring(currentOperator.getIndex() + 1, i + 1));
					LOG.info("getRightOperand(). l_int.getRightOperand(): " + l_int);
					l_op = new Operand(currentOperator.getIndex() + 1, i, l_int);
					break;
				} else {
					LOG.info(
							"getRightOperand(). Substring: " + l_eq.substring(currentOperator.getIndex() + 1, i));
					l_int = Integer.parseInt(l_eq.substring(currentOperator.getIndex() + 1, i));
					LOG.info("getRightOperand(). l_int.getRightOperand(): " + l_int);
					l_op = new Operand(currentOperator.getIndex() + 1, i, l_int);
					break;
				}
			}
		}

		return l_op;

	}

	/**
	 * Method to calculate left sign right operation
	 * 
	 * @return result of left sign right operation
	 */
	private int calculateOperation() {
		int l_rtn = 0;
		Sign l_sg = this.currentOperator;

		if (l_sg.isSUB()) {
			l_rtn = this.leftOperand.getOperand() - this.rightOperand.getOperand();
		} else if (l_sg.isADD()) {

			l_rtn = this.leftOperand.getOperand() + this.rightOperand.getOperand();

		} else if (l_sg.isMUL()) {

			l_rtn = this.leftOperand.getOperand() * this.rightOperand.getOperand();

		} else if (l_sg.isDIV()) {

			l_rtn = this.leftOperand.getOperand() / this.rightOperand.getOperand();

		}

		LOG.info("calculateOperation() Equation: " + this.leftOperand.getOperand() + " "
				+ l_sg.getCharContainer() + " " + this.rightOperand.getOperand());
		LOG.info("calculateOperation(): " + l_rtn);

		return l_rtn;

	}

	/**
	 * To Check if equation contains invalid characters
	 * @return
	 */
	private boolean validEquation(){
		boolean l_rtn=true;
		String l_eq = super.getEq();
		Sign l_sg = new Sign();

		for (int i = 0; i < l_eq.length(); i++) {
			l_sg.setCharContainer(l_eq.charAt(i));
			if(!l_sg.isSign()&&!l_sg.isNumber()&&!l_sg.isOpenBracket()&&!l_sg.isCloseBracket()){
				LOG.severe("validEquation(). Invalid Equation");
				return false;
			}
		}

		return l_rtn;
	}

}
