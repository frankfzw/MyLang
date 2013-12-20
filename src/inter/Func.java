package inter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Parser.Node;

public class Func extends Inter{
	
	public int parse(Node node) throws IOException {		
		if (node.toString().compareTo("FunctionDeclarator") != 0) {
			return -1;
		}
		String content = "";
		
		//get function name
		Node funcName = node.jjtGetChild(0);
		content += funcName.toString().replace(" ", "");
		content += " proc ";
		
		//get parameters if it has
		Node funcParameters = node.jjtGetChild(1);
		if (node.jjtGetNumChildren() == 3) {
			int numOfPara = funcParameters.jjtGetNumChildren();
			for (int i = 0; i < numOfPara; i ++) {
				Node para = funcParameters.jjtGetChild(i).jjtGetChild(0);
				content += para.toString().replace(" ", "");
				content += " ";
			}
		}
		content += "\n";
		
		//write to file
		writeInter(content);
		
		return 0;
	}
}
