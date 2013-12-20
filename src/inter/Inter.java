package inter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class Inter {
	protected static final String labelHead = "L";
	protected static final String paraHead = "_t"; 
	
	public void writeInter(String content) throws IOException {
		//write to file
		File file = new File("interCode.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file.getName(), true);
		writer.write(content);
		writer.close();
	}
	
	public int getLineNum() throws IOException {
		File file = new File("interCode.txt");
		if (!file.exists()) {
			file.createNewFile();
			return 0;
		}
		FileReader in = new FileReader(file.getName());
		LineNumberReader reader = new LineNumberReader(in);
		String line = reader.readLine();
		int num = 0;
		while (line != null) {
			num ++;
			line = reader.readLine();
		}
		reader.close();
		in.close();
		return num;
	}
	
	public String genLabel(int num) {
		return labelHead + Integer.toString(num) + ":\n";
	}
	
	public String genParaLabe(int num) {
		return paraHead + Integer.toString(num);
	}
}
