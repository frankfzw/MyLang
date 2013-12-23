package asm;

import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		Asm asm = new Asm();
		try {
			CFG cfg = new CFG();
			asm.setFuncList(cfg.getFuncList());
			asm.setIdToReg(cfg.getIdToReg());
			asm.setLocal(cfg.getLocal());
			asm.translate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
