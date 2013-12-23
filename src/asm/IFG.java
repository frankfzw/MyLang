package asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class IFG {
	public static enum REG {
		INVALID, NULL, EBX, ECX, EDX, ESI, EDI
	}
	
	private static HashSet<REG> allReg = new HashSet<REG>();
	private static Hashtable nodes;
	private static Hashtable idToReg;
	private static ArrayList<String> local;
	
	
	public IFG (){
		allReg.add(REG.EBX);
		allReg.add(REG.ECX);
		allReg.add(REG.EDX);
		allReg.add(REG.ESI);
		allReg.add(REG.EDI);
		nodes = new Hashtable();
		idToReg = new Hashtable();
		local = new ArrayList<String>();
		
	}
	

	
	public static Hashtable getIdToReg() {
		return idToReg;
	}



	public static void setIdToReg(Hashtable idToReg) {
		IFG.idToReg = idToReg;
	}



	public static ArrayList<String> getLocal() {
		return local;
	}



	public static void setLocal(ArrayList<String> local) {
		IFG.local = local;
	}



	public void initNode(ArrayList<String> id) {
		for (String s : id) {
			ParaNode pn = new ParaNode(s);
			if (!nodes.containsKey(s)) {
				nodes.put(s, pn);
				System.out.println(s);
			}
		}
	}
	
	public void buildInter(ArrayList<String> id) {
		for (String s : id) {
			ParaNode pn = (ParaNode)nodes.get(s);
			for (String t : id) {
				if (t.compareTo(s) == 0)
					continue;
				ParaNode p = (ParaNode)nodes.get(t);
				if (!pn.getInter().contains(p))
					pn.addInter(p);
				if (!p.getInter().contains(pn))
					p.addInter(pn);
			}
		}
		
		//test
	}
	public void allocReg() {
		for (Iterator itr = nodes.keySet().iterator(); itr.hasNext();) {
			String key = (String)itr.next();
			ParaNode p = (ParaNode)nodes.get(key);
			recursAlloc(p);
		}
	}
	
	public void recursAlloc(ParaNode pn) {
		if (pn.getReg() != REG.INVALID)
			return;
		
		ArrayList<ParaNode> inter = pn.getInter();
		HashSet<REG> used = new HashSet<REG>();
		for (ParaNode p : inter) {
			if ((p.getReg() != REG.NULL) && (p.getReg() != REG.INVALID))
				used.add(p.getReg());
		}
		HashSet<REG> remain = new HashSet<REG>();
		remain.addAll(allReg);
		remain.removeAll(used);
		if (remain.size() == 0) {
			pn.setReg(REG.NULL);
			idToReg.put(pn.getId(), REG.NULL);
			local.add(pn.getId());
		}
		else {
			for (REG r : remain) {
				pn.setReg(r);
				idToReg.put(pn.getId(), r);
				break;
			}
		}
		
		for (ParaNode p : inter) {
			recursAlloc(p);
		}
	}
	
	public class ParaNode {
		private ArrayList<ParaNode> inter = new ArrayList<ParaNode>();
		private REG reg = REG.INVALID;
		private String id;
		
		public ParaNode(String id) {
			this.id = id;
		}
		public REG getReg() {
			return reg;
		}

		public void setReg(REG reg) {
			this.reg = reg;
		}

		
		public ArrayList<ParaNode> getInter() {
			return inter;
		}
		
		public void addInter(ParaNode node) {
			inter.add(node);
		}
		
		public String getId() {
			return id;
		}
	}
	
	public void printIG() {
		for(Iterator itr = nodes.keySet().iterator(); itr.hasNext();) {
			String key = (String)itr.next();
			System.out.println(key + ":");
			ParaNode p = (ParaNode)nodes.get(key);
			for (ParaNode n : p.getInter())
				System.out.println("\t" + n.getId());
		}
		for(Iterator itr = idToReg.keySet().iterator(); itr.hasNext();) {
			String key = (String)itr.next();
			System.out.println(key + ":");
			System.out.println("\t" + idToReg.get(key));
		}
	}
	
	}
