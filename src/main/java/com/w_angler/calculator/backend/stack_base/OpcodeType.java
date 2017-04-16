package com.w_angler.calculator.backend.stack_base;

/**
 * simple ISA for this stack-based virtual machine, zero address or single address
 * @author w-angler
 *
 */
public enum OpcodeType{
	/**
	 * push a number onto the top of the operand stack.
	 * single address
	 */
    push, 
	/**
	 * pop the top of the operand stack.
	 * zero address
	 */
    pop,
    /*------------------------------------------------------------------*/
    /*********************************************/
    /**
     * do arithmetic operation on the top 2 elements of the operand stack.
	 * zero address
     */
    /*********************************************/
    add, 
    sub, 
    mul, 
    mod, 
    div, 
    pow, 
    /*------------------------------------------------------------------*/
    /**
     * store the top of the operand stack in specified slot
     * for example: {@code store 1}
     */
    store, 
    /**
     * load specified slot into the top of the operand stack
     * for example: {@code load 1}
     */
    load, 
    /**
     * write value to standard output stream.
	 * zero address
     */
    print, 
    /**
     * declare the number of slot, should be the first instruction.
     * for example: {@code slot 3}
     */
    slot;
}
