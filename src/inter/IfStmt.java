package inter;

import java.io.IOException;

import Parser.Node;

public class IfStmt extends Inter{
	
	public int parse(Node node) throws IOException {
		if (node.toString().compareTo("IfStatement") != 0) {
			return -1;
		}
		
		//generate expression
		Expression exp = new Expression();
		int totalLineNum = exp.parse(node);
		int totalCodeLine = getLineNum();
		
		//generate if satement
		String content = "if "
				+ genParaLabe(getLineNum() - 1)
				+ " goto"
				+ genLabel(totalCodeLine + 1)
				+ "\n";
		
		//generate block label;
		content += genLabel(totalCodeLine + 1);
		writeInter(content);
		
		totalLineNum += 2;
		
		return totalLineNum;
	}
}
