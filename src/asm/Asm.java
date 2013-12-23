package asm;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Hashtable;

import asm.IFG.REG;

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
	
	private String funcList = "";
	private String uniParaList = "";
	private ArrayList<String> asm = new ArrayList<String>();
	private String prevFunc;
	
	//translate id to reg and memory address
	private static Hashtable idToReg = new Hashtable();
	private static ArrayList<String> local = new ArrayList();
	private static Hashtable localToMem =new Hashtable();
	
	public String getFuncList() {
		return funcList;
	}
	
	//cr: changed here
	public void setFuncList(ArrayList<String> list) {
		for (String s : list) {
			String[] temp = s.split(" ");
			String funcProto = temp[0] + " proto ";
			for (int i = 3; i < temp.length; i ++) {
				String type = temp[i].split(":")[1];
				funcProto += ":" + type + " ";
			}
			funcList += funcProto + "\n";
		}
		//System.out.println(funcList);
	}
	
	public String getUniParaList() {
		return uniParaList;
	}

	//cr: changed here
	public void setUniParaList() {
		
	}

	public void optInter() throws IOException {
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
			//remove this while optimizing
			writer.write(line);
			writer.write("\n");
			line = reader.readLine();
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
		int i = 0;
		while (ins[i].indexOf("@L") != -1)
		{
			length --;
			val += LABEL;
			index ++;
			i ++;
			if (i == ins.length)
				break;
		}
		if (length == 0)
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
				+ ".model flat,stdcall\n"
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
		//optimize intermidate code
		optInter();
		
		//write asm head
		writeHead();
		
		//translate asm body
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
			int i = 0;
			while (type >= LABEL) {
				if (i == ins.length)
					break;
				pos ++;
				asm.add(ins[i]);
				type -= LABEL;
				i ++;
				
			}
			switch(type) {
			case ASSIGNMENT:
				transAssignment(pos, ins);
				break;
			case THREEADDRESS:
				transThreeAddress(pos, ins);
				break;
			case GOTO:
				transGoto(pos, ins);
				break;
			case IF:
				transIf(pos, ins);
				break;
			case FUNC:
				transFunc(pos, ins);
				break;
			case INVOKE:
				transInvoke(pos, ins);
				break;
			case RET:
				transRet(pos, ins);
				break;
			}
		}

		//write asm
		file = new File("ASM.txt");
		if (!file.exists()) {
			throw new IOException("ASM code doesn't exist!\n");
		}
		FileWriter writer = new FileWriter(file, true);
		for(String s : asm) {
			writer.write(s);
			writer.write("\n");
		}
		writer.write(prevFunc + " endp" + "\n\n");
		writer.write("end start");
		writer.close();
	}
	public void transAssignment(int pos, String[] ins) {
		//use move
		if(ins[2 + pos].compareTo("true") == 0)
			ins[2 + pos] = "1";
		if(ins[2 + pos].compareTo("false") == 0)
			ins[2 + pos] = "0";
		String temp = "mov " + transPara(ins[pos]) + ", " +transPara(ins[pos + 2]);
		asm.add(temp);
	}
	public void transThreeAddress(int pos, String[] ins) {
		String op = ins[3 + pos];
		String temp = "mov eax, "  + transPara(ins[pos + 2]);
		asm.add(temp);
		if(op.compareTo("+") == 0) {
			//add
			temp = "add eax, " + transPara(ins[pos + 4]);
			asm.add(temp);
			//return;
		}
		if(op.compareTo("-") == 0) {
			//sub
			temp = "dec eax, " + transPara(ins[pos + 4]);
			asm.add(temp);
			//return;
		}
		if(op.compareTo("*") == 0) {
			//multi
			temp = "mul eax, " + transPara(ins[pos + 4]);
			asm.add(temp);
			//return;
		}
		if(op.compareTo("/") == 0) {
			//div
			temp = "div eax, " + transPara(ins[pos + 4]);
			asm.add(temp);
			//return;
		}
		asm.add("mov " + transPara(ins[pos]) + ", eax");
	}
	public void transGoto(int pos, String[] ins) {
		String temp = "jmp " + ins[1 + pos];
		asm.add(temp);
		
	}
	public void transIf(int pos, String[] ins) {
		//cmp to regs
		String temp = "cmp ";
		temp += transPara(ins[pos + 1]) + ", " + transPara(ins[pos + 3]);
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
		if (prevFunc != null) {
			asm.add(prevFunc + " endp" + "\n");
		}
		prevFunc = ins[pos + 1];
		String temp = transType(pos, ins);
		asm.add(temp);
	}
	public void transInvoke(int pos, String[] ins) {
		String temp = "";
		for(int i = pos; i < ins.length; i ++) {
			//problems
			temp += transPara(ins[i]) + " ";
		}
		asm.add(temp);
	}	
	public void transRet(int pos, String[] ins) {
		String temp;
		if(ins.length > (pos + 1)) {
			//mov ret val to eax
			temp = "mov eax, " + transPara(ins[pos + 1]);
			asm.add(temp);
		}
		temp = "ret ";
		asm.add(temp);
	}
	
	public String transPara(String para) {
		if (Character.isDigit(para.charAt(0)))
			return para;
		String reg = "";
		if (idToReg.containsKey(para)) {
			reg = idToReg.get(para).toString().toLowerCase();
			return reg;
		}
		else {
			//finish later
			return para;
		}
	}
	
	public String transType(int pos, String[] ins) {
		String temp = ins[1 + pos] + " " + ins[2 + pos] + " ";
		for(int i = pos + 3; i < ins.length; i ++) {
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
			temp += para[0] + ":" + para[1] + " ";
		}
		return temp;
	}
	

	public static Hashtable getIdToReg() {
		return idToReg;
	}

	public static void setIdToReg(Hashtable idToReg) {
		Asm.idToReg = idToReg;
	}

	public static ArrayList<String> getLocal() {
		return local;
	}

	public static void setLocal(ArrayList<String> local) {
		Asm.local = local;
		int i = 0;
		for (String s : local) {
			localToMem.put(local, i);
		}
	}

	
}
