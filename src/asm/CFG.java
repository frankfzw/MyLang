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

import asm.IFG.ParaNode;

public class CFG {
	public static enum REG {
		INVALID, NULL, EBX, ECX, EDX, ESI, EDI
	}
	
	private ArrayList<GraphNode> nodes;
	private Hashtable jumpTable;
	private ArrayList<String> funcList;
	private Hashtable regTable;
	private IFG ifg;
	private ArrayList<String> variable;
	
	//information for asm
	private static Hashtable idToReg = new Hashtable();
	private static ArrayList<String> local = new ArrayList<String>();
	
	public static Hashtable getIdToReg() {
		return idToReg;
	}

	public static void setIdToReg(Hashtable idToReg) {
		CFG.idToReg = idToReg;
	}

	public static ArrayList<String> getLocal() {
		return local;
	}

	public static void setLocal(ArrayList<String> local) {
		CFG.local = local;
	}

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
		funcList = new ArrayList<String>();
		variable = new ArrayList<String>();
		ifg = new IFG();
		
		File file = new File("optInterCode.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		int num = 0;
		String line;
		
		//set an empty node in 0 pos
		GraphNode empty = new GraphNode();
		nodes.add(empty);
		
		//set use def and jump table
		while ((line = reader.readLine()) != null) {
			num ++;
			GraphNode node = new GraphNode();
			String[] ins = line.split(" ");
			int type = parser.parseLine(ins);
			int pos = 0;
			int i = 0;
			while (type >= LABEL) {
				jumpTable.put(ins[i], num);
				//System.out.println(ins[i] + "\t" + jumpTable.get(ins[i]).toString());
				pos ++;
				type -= LABEL;
				i ++;
				if (i == ins.length)
					break;
			}
			switch (type) {
			case ASSIGNMENT:
				node.addDef(ins[0 + pos]);
				variable.add(ins[0 + pos]);
				if (!Character.isDigit(ins[pos + 2].charAt(0))) {
					node.addUse(ins[2 + pos]);
					variable.add(ins[2 + pos]);
				}
				node.addPred(num - 1);
				node.addSucc(num + 1);
				break;
			case THREEADDRESS:
				node.addDef(ins[0 + pos]);
				variable.add(ins[0 + pos]);
				if (!Character.isDigit(ins[pos + 2].charAt(0))) {
					node.addUse(ins[2 + pos]);
					variable.add(ins[2 + pos]);
				}
				if (!Character.isDigit(ins[pos + 4].charAt(0))) {
					node.addUse(ins[4 + pos]);
					variable.add(ins[4 + pos]);
				}
				node.addPred(num - 1);
				node.addSucc(num + 1);
				break;
			case GOTO:
				node.addPred(num - 1);
				break;
			case IF:
				if (!Character.isDigit(ins[pos + 1].charAt(0))) {
					node.addUse(ins[1 + pos]);
					variable.add(ins[1 + pos]);
				}
				if (!Character.isDigit(ins[pos + 3].charAt(0))) {
					node.addUse(ins[3 + pos]);
					variable.add(ins[3 + pos]);
				}
				node.addPred(num - 1);
				node.addSucc(num + 1);
				break;
			case INVOKE:
				for (int j = 2 + pos; j < ins.length; j ++) {
					if (!Character.isDigit(ins[j].charAt(0))) {
						node.addUse(ins[j]);
						variable.add(ins[j]);
					}
				}
				node.addPred(num - 1);
				break;
			case FUNC:
				funcList.add(parser.transType(pos, ins));
				break;
			case RET:
				if (ins.length > (pos + 1))
					if (!Character.isDigit(ins[pos + 1].charAt(0))) {
						node.addUse(ins[1 + pos]);
						variable.add(ins[1 + pos]);
					}
				node.addPred(num - 1);
				node.addSucc(num + 1);
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
			int i = 0;
			while (type >= LABEL) {
				//int pred = Integer.parseInt(jumpTable.get(ins[i]).toString());
				//nodes.get(num).addPred(pred);
				pos ++;
				type -= LABEL;
				i ++;
				if (i == ins.length)
					break;
			}
			switch(type) {
			case GOTO:
				String temp = jumpTable.get(ins[1 + pos]+":").toString();
				succ = Integer.parseInt(temp);
				nodes.get(num).addSucc(succ);
				nodes.get(succ).addPred(num);
				break;
			case IF:
				String temp1 = jumpTable.get(ins[5 + pos]+":").toString();
				succ = Integer.parseInt(temp1);
				nodes.get(num).addSucc(succ);
				nodes.get(succ).addPred(num);
				break;
			default:
				break;
			}
			
			//parser.setFuncList(funcList);
		}
		
		//test cfg
		buildIFG();
		printCFG();
		
		ifg.initNode(variable);
		for (GraphNode n : nodes) {
			ArrayList<String> inters = new ArrayList<String>();
			for (String id : n.getIn()) {
				inters.add(id);
			}
			ifg.buildInter(inters);
			inters.clear();
			for (String id : n.getOut()) {
				if (!inters.contains(id))
					inters.add(id);
			}
			ifg.buildInter(inters);
		}
		
		ifg.allocReg();
		ifg.printIG();
		idToReg = ifg.getIdToReg();
		local = ifg.getLocal();
		
	}
	
	public ArrayList<String> getFuncList() {
		return funcList;
	}

	public void setFuncList(ArrayList<String> funcList) {
		this.funcList = funcList;
	}

	
	public void printCFG() {
		int i = 0;
		for (GraphNode n : nodes) {
			System.out.println(i + ":");
			System.out.println("\tpred: ");
			for (int j : n.getPred())
				System.out.println("\t\t" + j + ", ");
			System.out.println("\tsucc: ");
			for (int j : n.getSucc())
				System.out.println("\t\t" + j + ", ");
			System.out.println("\tuse: ");
			for (String j : n.getUse())
				System.out.println("\t\t" + j + ", ");
			System.out.println("\tdef: ");
			for (String j : n.getDef())
				System.out.println("\t\t" + j + ", ");
			System.out.println("\tin:");
			System.out.println("\t\t" + n.getIn());
			System.out.println("\tout:");
			System.out.println("\t\t" + n.getOut());
			
			i ++;
		}
	}
	
	
	public void buildIFG() {
		int i = 0;
		while (i < nodes.size() - 1) {
			i = 0;
			for (int j = nodes.size() -1; j > 0; j --) {
				GraphNode n = new GraphNode();
				Set<String> in = new HashSet();
				Set<String> out = new HashSet();
				Set<String> afterIn = new HashSet();
				Set<String> afterOut = new HashSet();
				
				//save in and out set before calculate
				n = nodes.get(j);
				in.clear();
				in.addAll(n.getIn());
				out.clear();
				out.addAll(n.getOut());
				System.out.println(j + ":\n\told in:" + in);
				System.out.println(j + ":\n\told out:" + out);
				
				//calculate in
				Set<String> temp = new HashSet();
				temp.clear();
				temp.addAll(out);
				temp.removeAll(n.getDef());
				temp.addAll(n.getUse());
				n.setIn(temp);
				//System.out.println(j + ":\n\tin:" + n.getIn());
				temp.clear();
				
				//calculate out
				System.out.println(j + ":\n\tin:" + n.getIn());
				for (int succ : n.getSucc()) {
					if (succ >= nodes.size())
						continue;
					//System.out.println(nodes.get(succ).getIn());
					temp.addAll(nodes.get(succ).getIn());
				}
				n.setOut(temp);
				System.out.println(j + ":\n\tout:" + n.getOut());
				nodes.set(j, n);
				
				//compare
				//System.out.println(n.getIn());
				afterIn.clear();
				afterIn.addAll(n.getIn());
				afterOut.clear();
				afterOut.addAll(n.getOut());
				//afterIn.removeAll(in);
				//System.out.println("in:" + in);
				//System.out.println("afterIn:" + afterIn);
				//afterOut.removeAll(out);
				//System.out.println("out:" + out);
				//System.out.println("afterOut:" + afterOut);
				if (afterIn.equals(in) && afterOut.equals(out))
					i ++;
			}
		}
	}
	
	class GraphNode {
		Set<Integer> succ = new HashSet<Integer>();
		Set<Integer> pred = new HashSet<Integer>();
		Set<String> use = new HashSet<String>();
		Set<String> def = new HashSet<String>();
		Set<String> in = new HashSet<String>();
		Set<String> out = new HashSet<String>();
		
		public Set<String> getIn() {
			return in;
		}
		public void setIn(Set<String> in) {
			this.in.clear();
			this.in.addAll(in);
		}
		public Set<String> getOut() {
			return out;
		}
		public void setOut(Set<String> out) {
			this.out.clear();
			this.out.addAll(out);
		}
		public Set<Integer> getSucc() {
			return succ;
		}
		public void setSucc(Set<Integer> succ) {
			this.succ.clear();
			this.succ.addAll(succ);
		}
		public Set<Integer> getPred() {
			return pred;
		}
		public void setPred(Set<Integer> pred) {
			this.pred.clear();
			this.pred.addAll(pred);
		}
		public Set<String> getUse() {
			return use;
		}
		public void setUse(Set<String> use) {
			this.use.clear();
			this.use.addAll(use);
		}
		public Set<String> getDef() {
			return def;
		}
		public void setDef(Set<String> def) {
			this.def.clear();
			this.def.addAll(def);
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
