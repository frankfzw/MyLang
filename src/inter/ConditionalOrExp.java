package inter;

import java.io.IOException;

import Parser.Node;

public class ConditionalOrExp extends Inter{

	public int parse(Node node) throws IOException {
		if (node.toString().compareTo("ConditionalOrExpression") != 0) {
			return -1;
		}
		
		//deal with conditional and
		int totalLineNum = 0;
		String content = "";
		int num = node.jjtGetNumChildren();
		
		//one expression
		if (num == 1) {
			ConditionalAndExp condAndExp = new ConditionalAndExp();
			totalLineNum += condAndExp.parse(node);
			return totalLineNum;
		}
		
		//multi expression
		for (int i = 0; i < num - 1; i ++) {
			Node left = node.jjtGetChild(i);
			Node right = node.jjtGetChild(i + 1);
			
		}
		return totalLineNum;
	}
}
