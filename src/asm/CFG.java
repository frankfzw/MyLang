package asm;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class CFG {
	private ArrayList<GraphNode> nodes;
	private Hashtable jumpTable;
	
	public static final int INVALID = -1;
	public static final int ASSIGNMENT = 0;
	public static final int THREEADDRESS = 1;
	public static final int GOTO = 2;
	public static final int IF = 3;
	public static final int FUNC = 4;
	public static final int INVOKE = 5;
	public static final int RET = 6;
	public static final int LABEL = 7;
	
	public CFG() throws IOException {
		Asm parser = new Asm();
		nodes = new ArrayList<GraphNode>();
		jumpTable = new Hashtable();
		
		File file = new File("optInterCode.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		int num = 0;
		String line;
		
		//set use def and jump table
		while ((line = reader.readLine()) != null) {
			num ++;
			GraphNode node = new GraphNode();
			String[] ins = line.split(" ");
			int type = parser.parseLine(ins);
			int pos = 0;
			if (type >= LABEL) {
				jumpTable.put(ins[0], num);
				pos ++;
				type -= LABEL;
			}
			switch (type) {
			case ASSIGNMENT:
				node.addDef(ins[0 + pos]);
				node.addUse(ins[2 + pos]);
				node.addPred(num - 1);
				node.addSucc(num + 1);
				break;
			case THREEADDRESS:
				node.addDef(ins[0 + pos]);
				node.addUse(ins[2 + pos]);
				node.addUse(ins[4 + pos]);
				node.addPred(num - 1);
				node.addSucc(num + 1);
				break;
			case GOTO:
				node.addPred(num - 1);
				break;
			case IF:
				node.addUse(ins[1]);
				node.addUse(ins[3]);
				node.addPred(num - 1);
				node.addSucc(num + 1);
				break;
			case INVOKE:
				for (int i = 2; i < ins.length; i ++)
					node.addUse(ins[i]);
				node.addPred(num - 1);
				break;
			case FUNC:
				break;
			case INVALID:
				break;
			default:
				break;
			}
			nodes.add(node);
		}
		reader.close();
		in.close();
		
		//set succ pred
		num = 0;
		in = new FileReader(file);
		reader = new LineNumberReader(in);
		while ((line = reader.readLine()) != null) {
			num ++;
			String[] ins = line.split(" ");
			int type = parser.parseLine(ins);
			int pos = 0;
			int succ = 0;
			if (type >= LABEL) {
				int pred = Integer.parseInt(jumpTable.get(ins[0]).toString());
				nodes.get(num).addPred(pred);
				pos ++;
				type -= LABEL;
			}
			switch(type) {
			case GOTO:
				succ = Integer.parseInt(jumpTable.get(ins[1 + pos]).toString());
				nodes.get(num).addSucc(succ);
				break;
			case IF:
				succ = Integer.parseInt(jumpTable.get(ins[5 + pos]).toString());
				nodes.get(num).addSucc(succ);
				break;
			default:
				break;
			}
		}
	}
	
	class GraphNode {
		Set<Integer> succ = new HashSet<Integer>();
		Set<Integer> pred = new HashSet<Integer>();
		Set<String> use = new HashSet<String>();
		Set<String> def = new HashSet<String>();
		
		public Set<Integer> getSucc() {
			return succ;
		}
		public void setSucc(Set<Integer> succ) {
			this.succ = succ;
		}
		public Set<Integer> getPred() {
			return pred;
		}
		public void setPred(Set<Integer> pred) {
			this.pred = pred;
		}
		public Set<String> getUse() {
			return use;
		}
		public void setUse(Set<String> use) {
			this.use = use;
		}
		public Set<String> getDef() {
			return def;
		}
		public void setDef(Set<String> def) {
			this.def = def;
		}
		
	
		public void addDef(String def) {
			this.def.add(def);
		}
		public void addUse(String use) {
			this.use.add(use);
		}
		public void addSucc(int succ) {
			this.succ.add(succ);
		}
		public void addPred(int pred) {
			this.pred.add(pred);
		}
		
	}
	
	
}
