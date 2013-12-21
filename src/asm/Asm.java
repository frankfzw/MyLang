package asm;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class Asm {

	public static final int INVALID = -1;
	public static final int ASSIGNMENT = 0;
	public static final int THREEADDRESS = 1;
	public static final int GOTO = 2;
	public static final int IF = 3;
	public static final int FUNC = 4;
	public static final int INVOKE = 5;
	public static final int RET = 6;
	public static final int LABEL = 7;
	
	private String funcList;
	private String uniParaList;
	private ArrayList<String> asm = new ArrayList<String>();
	
	public String getFuncList() {
		return funcList;
	}
	
	//cr: changed here
	public void setFuncList() {
		
	}
	
	public String getUniParaList() {
		return uniParaList;
	}

	//cr: changed here
	public void setUniParaList() {
		
	}

	public void parseInter() throws IOException {
		//from file
		File file = new File("interCode.txt");
		if (!file.exists()) {
			throw new IOException("intermediate code doesn't exist!\n");
		}
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		
		//target file
		file = new File("optInterCode.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file);
		
		//parse here cr
		String line = reader.readLine();
		while(line != null) {
			
		}
		
		//close file
		reader.close();
		in.close();
		writer.close();
	}
	
	public int parseLine(String[] ins) {
		int length = ins.length;
		int val = 0;
		int index = 0;
		if (ins[0].indexOf("@L") != -1)
		{
			length --;
			val += LABEL;
			index ++;
		}
		if (length == 1)
			return LABEL + val;
		if ((length == 3) && (ins[1 + index].compareTo("=") == 0))
			return ASSIGNMENT + val;
		if ((length == 5) && (ins[1 + index].compareTo("=") == 0))
			return THREEADDRESS + val;
		if (ins[0 + index].compareTo("goto") == 0)
			return GOTO + val;
		if (ins[0 + index].compareTo("if") == 0)
			return IF + val;
		if (ins[0 + index].compareTo("ret") == 0)
			return RET + val;
		if (ins[0 + index].compareTo("invoke") == 0)
			return INVOKE + val;
		if (ins[2 + index].compareTo("proc") == 0)
			return FUNC + val;
		return INVALID;
	}
	
	//remained 
	public void writeHead() throws IOException {
		File file = new File("ASM.txt");
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file);
		
		String content = ".386\n"
				+ ".model flat,stdcall"
				+ "option casemap:none\n\n"
				+ funcList
				+ "\n\n"
				+ ".data\n"
				+ uniParaList
				+ "\n\n"
				+ ".code\n\n"
				+ "start:\n\n";
		writer.write(content);
		writer.close();
				
	}
	public void translate() throws IOException {
		
		File file = new File("optInterCode.txt");
		if (!file.exists()) {
			throw new IOException("intermediate code doesn't exist!\n");
		}
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		String content;
		while ((content = reader.readLine()) != null) {
			String ins[] = content.split(" ");
			int type = parseLine(ins);
			int pos = 0;
			if (type >= LABEL) {
				pos ++;
				asm.add(ins[0]);
				type -= LABEL;
			}
			switch(type) {
			case ASSIGNMENT:
				transAssignment(pos, ins);
			case THREEADDRESS:
				transThreeAddress(pos, ins);
			case GOTO:
				transGoto(pos, ins);
			case IF:
				transIf(pos, ins);
			case FUNC:
				transFunc(pos, ins);
			case INVOKE:
				transInvoke(pos, ins);
			case RET:
				transRet(pos, ins);
			}
		}
		
		//write asm
		file = new File("ASM.txt");
		if (!file.exists()) {
			throw new IOException("ASM code doesn't exist!\n");
		}
		FileWriter writer = new FileWriter(file);
		for(String s : asm) {
			writer.write(s);
		}
		writer.close();
	}
	public void transAssignment(int pos, String[] ins) {
		//use move
	}
	public void transThreeAddress(int pos, String[] ins) {
		String op = ins[2 + pos];
		if(op.compareTo("+") == 0) {
			//add
		}
		if(op.compareTo("-") == 0) {
			//sub
		}
		if(op.compareTo("*") == 0) {
			//muti
		}
		if(op.compareTo("/") == 0) {
			//div
		}
	}
	public void transGoto(int pos, String[] ins) {
		String temp = "jmp " + ins[1 + pos];
		asm.add(temp);
		
	}
	public void transIf(int pos, String[] ins) {
		//cmp to regs
		String temp = "cmp ";
		asm.add(temp);
		String op = ins[2 + pos];
		if(op.compareTo("==") == 0) {
			temp = "je ";
		}
		if(op.compareTo("!=") == 0) {
			temp = "jne ";
		}
		if(op.compareTo(">") == 0) {
			temp = "jg ";
		}
		if(op.compareTo("<") == 0) {
			temp = "jl ";
		}
		if(op.compareTo(">=") == 0) {
			temp = "jge ";
		}
		if(op.compareTo("<=") == 0) {
			temp = "jle ";
		}
		temp += ins[pos + 5];
		asm.add(temp);
	}
	public void transFunc(int pos, String[] ins) {
		String temp = ins[0 + pos] + " " + ins[1 + pos] + " ";
		for(int i = pos + 1; i < ins.length; i ++) {
			String para[] = ins[i].split(":");
			if(para[1].compareTo("int") == 0) {
				para[1] = "dword";
			}
			else if(para[1].compareTo("real") == 0) {
				para[1] = "real8";
			}
			else {
				para[1] = "byte";
			}
			temp += para[0] + para[1] + " ";
		}
		asm.add(temp);
	}
	public void transInvoke(int pos, String[] ins) {
		String temp = "";
		for(int i = pos; i < ins.length; i ++) {
			//problems
			temp += ins[i];
		}
		asm.add(temp);
	}	
	public void transRet(int pos, String[] ins) {
		String temp = "ret ";
		if(ins.length > (pos + 1))
			temp += ins[pos + 1];
		asm.add(temp);
	}
}
