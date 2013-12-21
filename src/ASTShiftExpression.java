import java.io.IOException;

public class ASTShiftExpression extends SimpleNode {
	private String op;
	
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public ASTShiftExpression(int id) {
		super(id);
	}

	public ASTShiftExpression(Eg2 p, int id) {
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
