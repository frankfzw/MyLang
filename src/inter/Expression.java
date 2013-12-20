package inter;

import java.io.IOException;

import Parser.Node;

public class Expression extends Inter{
	
	public int parse(Node node) throws IOException {
		if (node.toString().compareTo("Expression") != 0) {
			return -1;
		}
		Node child = node.jjtGetChild(0);
		
		//deal with conditional expression
		if (child.toString().compareTo("ConditonalExpression") == 0) {
			ConditionalExp condExp = new ConditionalExp();
			return condExp.parse(node);
		}
		return 0;
	}
}
