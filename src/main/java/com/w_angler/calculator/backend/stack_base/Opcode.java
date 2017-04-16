package com.w_angler.calculator.backend.stack_base;

import static com.w_angler.calculator.backend.stack_base.OpcodeType.*;

import java.util.EnumSet;
/**
 * opcode for stack-based virtual machine
 * @author w-angler
 *
 */
public class Opcode{
    public OpcodeType type;
    public String arg;
    
    public boolean hasArgs() {
        return arg != null;
    }
    
    @Override
    public String toString() {
        return String.valueOf(type.name()) + (hasArgs() ? (" " + arg) : "");
    }
    /*************************************************/
    private Opcode(OpcodeType type, String arg) {
        this.type = type;
        this.arg = arg;
    }
    private Opcode(final OpcodeType type) {
        this.type = type;
        this.arg = null;
    }
    /*************************************************/
    
    public static Opcode push(String arg) {
        return new Opcode(push, arg);
    }
    public static Opcode pop() {
        return new Opcode(pop);
    }
    public static Opcode add() {
        return new Opcode(add);
    }
    public static Opcode sub() {
        return new Opcode(sub);
    }
    public static Opcode mul() {
        return new Opcode(mul);
    }
    public static Opcode mod() {
        return new Opcode(mod);
    }
    public static Opcode div() {
        return new Opcode(div);
    }
    public static Opcode pow() {
        return new Opcode(pow);
    }
    public static Opcode store(String slot) {
        return new Opcode(store, slot);
    }
    public static Opcode load(String slot) {
        return new Opcode(load, slot);
    }
    public static Opcode print() {
        return new Opcode(print);
    }
    public static Opcode slot(String slotNum) {
        return new Opcode(slot,slotNum);
    }
    
    public static Opcode convert(String[] datas){
    	switch (datas[0]) {
		case "push":
			return datas.length==2?push(datas[1]):null;
		case "pop":
			return pop();
		case "add":
			return add();
		case "sub":
			return sub();
		case "mul":
			return mul();
		case "div":
			return div();
		case "mod":
			return mod();
		case "pow":
			return pow();
		case "print":
			return print();
		case "store":
			return datas.length==2?store(datas[1]):null;
		case "load":
			return datas.length==2?load(datas[1]):null;
		case "slot":
			return datas.length==2?slot(datas[1]):null;
		default:
			return null;
		}
    }
    private static EnumSet<OpcodeType> types=EnumSet.allOf(OpcodeType.class);
    public static boolean isValid(String name){
    	for(OpcodeType type:types){
    		if(type.name().equals(name)){
    			return true;
    		}
    	}
    	return false;
    }
}
