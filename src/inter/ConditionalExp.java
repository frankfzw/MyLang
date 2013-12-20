package inter;

import java.io.IOException;

import Parser.Node;

public class ConditionalExp extends Inter{
	
	public int parse(Node node) throws IOException {
		if (node.toString().compareTo("ConditionalExpression") != 0) {
			return -1;
		}
		//write a jump label
		String content = genLabel(getLineNum());
		writeInter(content);
		
		//deal with conditional or exp
		ConditionalOrExp condOrExp = new ConditionalOrExp();
		return condOrExp.parse(node) + 1;
	}
}
