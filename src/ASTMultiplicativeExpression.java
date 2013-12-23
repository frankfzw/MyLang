import java.io.IOException;

public class ASTMultiplicativeExpression extends SimpleNode {
	private String op;
	
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public ASTMultiplicativeExpression(int id) {
	    super(id);
	}

	public ASTMultiplicativeExpression(Eg2 p, int id) {
		super(p, id);
	}
	
	public int genInter(int paraL, int paraR) throws IOException {
		content = "@t"
				+ Integer.toString(genPara()) + " = "
				+ "@t" + Integer.toString(paraL)
				+ " " + op
				+ " @t" + Integer.toString(paraR)
				+ "\n";
		writeInter();
		return para;
	}
}
