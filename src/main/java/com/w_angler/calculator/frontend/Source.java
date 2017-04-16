package com.w_angler.calculator.frontend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * source file
 * @author w-angler
 *
 */
public class Source implements AutoCloseable{
	public static final char EOF = (char) 0;  //end of file
	public static final char EOL='\n';        //end of line
	private BufferedReader source;            //reader for source file
	private String line;                      //line
	private int lineNum=0;                    //line number
	private int currentPosition;              //current position

	/**
	 * 
	 * @param path source file's path
	 */
	public Source(String path){
		try {
			this.source=new BufferedReader(new FileReader(path));
			line=readNextLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get line number
	 * @return line number
	 */
	public int getLineNum() {
		return lineNum;
	}
	/**
	 * get current position
	 * @return current position
	 */
	public int getCurrentPosition(){
		return currentPosition;
	}

	/**
	 * read next char
	 * @return next char
	 */
	public char nextChar(){
		currentPosition++;
		if(line==null){
			return Source.EOF;
		}
		if(line.charAt(currentPosition)==Source.EOL||line.length()==0){
			line=readNextLine();
			return nextChar();
		}
		return line.charAt(currentPosition);
	}
	/**
	 * get char at specified offset (default 1).
	 * if offset is too big or too small, returns end of line 
	 * @param offset offset
	 * @return
	 */
	public char peekChar(int offset){
		if(line==null||"".equals(line)){
			return nextChar();
		}
		int index=currentPosition+offset;
		if(index>=line.length()||index<=-1){
			return Source.EOL;
		}
		return line.charAt(index);
	}
	public char peekChar(){
		return this.peekChar(1);
	}
	/**
	 * read next line
	 * @return
	 */
	private String readNextLine(){
		String s=null;
		try {
			s = source.readLine();
		} catch (IOException e) {
			System.out.println(e);
		}
		currentPosition = -1;

		if (s != null) {
			lineNum=getLineNum() + 1;
			s=s+Source.EOL;
		}
		return s;
	}
	/**
	 * close all resources
	 * @throws Exception 
	 */
	@Override
	public void close() throws IOException {
		if (source != null) {
			source.close();
		}
	}
}
