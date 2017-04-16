package com.w_angler.calculator.frontend.opg;

/**
 * operator's precedence
 * @author w-angler
 *
 */
public class Precedence {
	/**
	 * operator's association
	 * @author w-angler
	 *
	 */
	public static enum Association{
		/**
		 * left association
		 */
		LEFT,
		/**
		 * right association
		 */
		RIGHT
	}
	/**
	 * precedence's value
	 */
	public int value;
	/**
	 * precedence's association
	 */
	public Association association;
	
	public Precedence(int value, Association association) {
		this.value = value;
		this.association = association;
	}
	@Override
	public String toString(){
		return association.name()+"->"+value;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((association == null) ? 0 : association.hashCode());
		result = prime * result + value;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Precedence other = (Precedence) obj;
		if (association != other.association)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
}
