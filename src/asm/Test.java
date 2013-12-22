package asm;

import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		Asm asm = new Asm();
		try {
			asm.translate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
