package com.w_angler.calculator.frontend.opg;

/**
 * Operator
 * @author w-angler
 *
 */
public class Operator {
	public static enum ARY{
		/**
		 * unary operator
		 */
		UNARY,
		/**
		 * binary operator
		 */
		BINARY
	}
	public ARY ary;
	public String op;
	
	public Operator(String op,ARY ary){
		this.op=op;
		this.ary=ary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ary == null) ? 0 : ary.hashCode());
		result = prime * result + ((op == null) ? 0 : op.hashCode());
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
		Operator other = (Operator) obj;
		if (ary != other.ary)
			return false;
		if (op == null) {
			if (other.op != null)
				return false;
		} else if (!op.equals(other.op))
			return false;
		return true;
	}

}
