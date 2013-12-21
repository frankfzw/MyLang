import java.io.IOException;

public class ASTAdditiveExpression extends SimpleNode {
	private String op;
	
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public ASTAdditiveExpression(int id) {
	    super(id);
	}

	public ASTAdditiveExpression(Eg2 p, int id) {
		super(p, id);
	}
	
	public int genInter(int paraL, int paraR) throws IOException {
		content = "\n@t"
				+ Integer.toString(genPara()) + " = "
				+ " @t" + Integer.toString(paraL)
				+ " " + op
				+ " @t" + Integer.toString(paraR);
		writeInter();
		return para;
	}

}
