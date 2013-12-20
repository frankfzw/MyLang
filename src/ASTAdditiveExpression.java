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
	
	public String genInter() {
		String content = "\nt"
				+ Integer.toString(genPara()) + " = "
				+ " t" + Integer.toString(para - 1)
				+ " " + op
				+ " t" + Integer.toString(para - 2);
		return content;
	}

}
