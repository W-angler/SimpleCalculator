import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.w_angler.calculator.backend.Context;
import com.w_angler.calculator.backend.Environment;
import com.w_angler.calculator.backend.ast.AST;
import com.w_angler.calculator.backend.stack_base.Opcode;
import com.w_angler.calculator.backend.stack_base.VirtualMachine;
import com.w_angler.calculator.exception.LexemeException;
import com.w_angler.calculator.exception.SyntaxException;
import com.w_angler.calculator.frontend.Lexer;
import com.w_angler.calculator.frontend.Parser;
import com.w_angler.calculator.frontend.Source;

/** 
 * calculator entry
 * @author w-angler
 *
 */
public class Calculator {
	enum Option{
		SHOW_TREE,
		INTERPRET,
		COMPILE,
		EXECUTE,
		VM;
	}
	public static void main(String[] args) {
		if(args.length==0||(args.length==1&&args[0].equals("-h"))){
			help();
		}
		else if(args.length==1||(args.length==2&&args[0].equals("-i"))){
			whatCanIDoForYou(args.length==1?args[0]:args[1],Option.INTERPRET);
		}
		else if(args.length==2&&args[0].equals("-c")){
			whatCanIDoForYou(args[1],Option.COMPILE);
		}
		else if(args.length==2&&args[0].equals("-vm")){
			whatCanIDoForYou(args[1],Option.VM);
		}
		else if(args.length==2&&args[0].equals("-tree")){
			whatCanIDoForYou(args[1],Option.SHOW_TREE);
		}
		else if(args.length==2&&args[0].equals("-exe")){
			whatCanIDoForYou(args[1],Option.EXECUTE);
		}
		else{
			System.out.printf("Arguments %s are invalid!\n\n",Arrays.deepToString(args));
			help();
		}
	}
	/**
	 * show help information
	 */
	private static void help() {
		System.out.println("Usage: calculator [options] sourceFile");
		System.out.println("options:");
		System.out.println("\t-i, interprets source file(default option)");
		System.out.println("\t-c, compiles source file to opcodes");
		System.out.println("\t-vm, interprets source file by a stack-based virtual machine");
		System.out.println("\t-tree, shows the syntax tree");
		System.out.println("\t-exe, executes opcodes");
		System.out.println("\t-h, help");
	}
	/**
	 * what can I do for you?
	 * @param path
	 */
	private static void whatCanIDoForYou(String path,Option option){
		if(option.equals(Option.EXECUTE)){
			try {
				VirtualMachine.execute(readOpcodes(path));
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
			return;
		}
		try(Source source=new Source(path)){
			Lexer lexer=new Lexer(source);
			Parser parser=new Parser(lexer);
			AST ast=parser.parse();
			switch(option){
			case SHOW_TREE:{
				ast.show("AST");
				break;
			}
			case INTERPRET:{
				ast.eval(new Environment());
				break;
			}
			case COMPILE:{
				FileWriter out=new FileWriter(path+".s",false);
				ast.generate(new Context()).forEach(code->{
					try {
						out.write(code.toString()+System.getProperty("line.separator"));
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
				});
				out.flush();
				out.close();
				break;
			}
			case VM:{
				VirtualMachine.execute(ast.generate(new Context()));
				break;
			}
			case EXECUTE:{
				VirtualMachine.execute(readOpcodes(path));
				break;
			}
			}
		} catch (SyntaxException | IllegalArgumentException | LexemeException | IOException e) {
			System.err.println(e.getMessage());
		}
	}
	/**
	 * read opcode from provided file path
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private static List<Opcode> readOpcodes(String path) throws IOException {
		int lineNum=0;
		List<Opcode> opcodes=new ArrayList<>();
		for(String line:Files.lines(Paths.get(path)).collect(Collectors.toList())){
			lineNum++;
			String[] code=line.trim().split("\\s+");
			Opcode opcode=null;
			if(code.length>2
					||(code.length>0&&!Opcode.isValid(code[0]))
					||(opcode=Opcode.convert(code))==null){
				System.err.println("unsupported instruction or wrong formation: line "+lineNum+", <"+line+">");
				continue;
			}
			opcodes.add(opcode);
		}
		return opcodes;
	}
}
