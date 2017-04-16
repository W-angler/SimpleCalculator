package com.w_angler.calculator.util;

/**
 * A converter likes {@code C#}'s {@code Int32.tryParse()}
 * @author w-angler
 *
 */
public final class Converter {
	private Converter(){
		throw new IllegalAccessError();
	}
	/**
	 * try to parse integer
	 * @param value
	 * @param result
	 * @return
	 */
	public static Result<Integer> tryParseInt(String value){
		Result<Integer> result = new Result<>();
		try {
			result.value=Integer.parseInt(value);
			result.success=true;
		} catch (Throwable t) {
			result.success=false;
		}
		return result;
	}
	/**
	 * try to parse double
	 * @param value
	 * @param result
	 * @return
	 */
	public static Result<Double> tryParseDouble(String value){
		Result<Double> result = new Result<>();
		try {
			result.value=Double.parseDouble(value);
			result.success=true;
		} catch (Throwable t) {
			result.success=false;
		}
		return result;
	}
	/**
	 * try to parse operand
	 * @param value
	 * @param result
	 * @return
	 */
	public static Result<Double> tryParseOperand(String value){
		Result<Double> result = new Result<>();
		try {
            if (value.startsWith("0x") || value.startsWith("0X") || (value.contains("_") && !value.contains("."))) {
            	result.value=Double.valueOf(Long.decode(value.replaceAll("_", "")));
            }
            else{
                result.value=Double.parseDouble(value.replaceAll("_", ""));
            }
			result.success=true;
		} catch (Throwable t) {
			result.success=false;
		}
		return result;
	}
	
	public static class Result<T>{
		public boolean success;
		public T value;
	}

}
