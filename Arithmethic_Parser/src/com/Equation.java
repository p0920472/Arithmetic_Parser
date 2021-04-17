/**
 * 
 */
package com;

/**
 * Parent Class of Parser Class, Intended to contain only the Equation
 * 
 * @author Mohd Saiful Akmal
 *
 */
public class Equation {

	private String eq;
	
	public Equation() {
		// TODO Auto-generated constructor stub
	}

	public Equation(String eq) {
		this.eq = eq;
	}
	
	
	/**
	 * @return the eq
	 */
	public String getEq() {
		return eq;
	}

	/**
	 * @param eq the eq to set
	 */
	public void setEq(String eq) {
		this.eq = eq;
	}

	/**
	 * @return true if Equation has a sign
	 */
	public boolean containSign() {
		boolean l_rtn = false;
		
		Sign s1 = new Sign();
		
		for(int i = 0; i<eq.length();i++) {
			s1.setCharContainer(this.eq.charAt(i));
			if(s1.isSign())
			{
				if(i!=0) {
					return true;
				}
			}
		}
		
		
		return l_rtn;
	}
	

	
	
}
