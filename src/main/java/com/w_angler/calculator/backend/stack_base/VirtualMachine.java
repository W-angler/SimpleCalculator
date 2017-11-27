package com.w_angler.calculator.backend.stack_base;

import java.util.List;
import java.util.Stack;

import static com.w_angler.calculator.util.Converter.*;
/**
 * A stack-based virtual machine
 * @author w-angler
 *
 */
public final class VirtualMachine
{
	/**
	 * operand stack
	 */
    private static Stack<Double> operandStack= new Stack<>();
    /**
     * slots for variables
     */
    private static double[] slots;
    
    private static List<Opcode> opcodes;
    
    private VirtualMachine() {
        throw new IllegalAccessError();
    }
    
    public static void execute(final List<Opcode> codes){
    	opcodes=codes;
    	Opcode slot=null;
    	if(codes.size()==0
    			||!(slot=fetch()).type.equals(OpcodeType.slot)
    			||!slot.hasArgs()){
    		System.err.println("unformatted opcodes:should start with a slot instruction");
    		return;
    	}
    	Result<Integer> slotNumber=tryParseInt(slot.arg);
    	if(!slotNumber.success){
    		System.err.println("slot number is invalid:"+slot.arg);
    		return;
    	}
    	slots=new double[slotNumber.value];
    	while(hasMoreOpcode()){
    		Opcode code=fetch();
    		switch (code.type) {
			case push:{
				if(!code.hasArgs()){
					System.err.println("can't do push operation:slot number should be provided");
					return;
				}
				Result<Double> operand=tryParseOperand(code.arg);
				if(!operand.success){
		    		System.err.println("operand is invalid:"+code.arg);
					return;
				}
				operandStack.push(operand.value);
				break;
			}
			case pop:{
				if(operandStack.isEmpty()){
					System.err.println("can't do pop operation: operand stack is empty");
					return;
				}
				operandStack.pop();
				break;
			}
			case add:{
				if(!isOperandStackValid()){
					System.err.println("can't do add operation: operand stack is invalid, two or more elements should be contained");
					return;
				}
				double right=operandStack.pop();
				double left=operandStack.pop();
				operandStack.push(left+right);
				break;
			}
			case sub:{
				if(!isOperandStackValid()){
					System.err.println("can't do subtract operation: operand stack is invalid, two or more elements should be contained");
					return;
				}
				double right=operandStack.pop();
				double left=operandStack.pop();
				operandStack.push(left-right);
				break;
			}
			case mul:{
				if(!isOperandStackValid()){
					System.err.println("can't do multiply operation: operand stack is invalid, two or more elements should be contained");
					return;
				}
				double right=operandStack.pop();
				double left=operandStack.pop();
				operandStack.push(left*right);
				break;
			}
			case mod:{
				if(!isOperandStackValid()){
					System.err.println("can't do modulus operation: operand stack is invalid, two or more elements should be contained");
					return;
				}
				double right=operandStack.pop();
				double left=operandStack.pop();
				operandStack.push(left%right);
				break;
			}
			case div:{
				if(!isOperandStackValid()){
					System.err.println("can't do divide operation: operand stack is invalid, two or more elements should be contained");
					return;
				}
				double right=operandStack.pop();
				double left=operandStack.pop();
	            if (right==0){
	                System.out.println("divided by zero");
	            }
				operandStack.push(left/right);
				break;
			}
			case pow:{
				if(!isOperandStackValid()){
					System.err.println("can't do power operation: operand stack is invalid, two or more elements should be contained");
					return;
				}
				double right=operandStack.pop();
				double left=operandStack.pop();
				operandStack.push(Math.pow(left, right));
				break;
			}
			case store:{
				if(!code.hasArgs()){
					System.err.println("can't do store operation:slot number should be provided");
					return;
				}
				slotNumber=tryParseInt(code.arg);
				if(!slotNumber.success){
		    		System.err.println("slot number is invalid:"+code.arg);
					return;
				}
				slots[slotNumber.value]=operandStack.peek();
				break;
			}
			case load:{
				if(!code.hasArgs()){
					System.err.println("can't do load operation:slot number should be provided");
					return;
				}
				slotNumber=tryParseInt(code.arg);
				if(!slotNumber.success){
		    		System.err.println("slot number is invalid:"+code.arg);
					return;
				}
				operandStack.push(slots[slotNumber.value]);
				break;
			}
			case print:{
				if(operandStack.isEmpty()){
					System.err.println("can't do print operation:operand stack is empty");
					return;
				}
				System.out.println(operandStack.peek());
				break;
			}
			default:
				System.out.println("unsupported instruction: "+code);
				break;
			}
    	}
    }
    
    private static boolean isOperandStackValid() {
        return VirtualMachine.operandStack.size() >= 2;
    }
    private static boolean hasMoreOpcode(){
    	return !opcodes.isEmpty();
    }
    /**
     * fecth opcode
     * @return
     */
    private static Opcode fetch(){
    	return opcodes.remove(0);
    }
}
