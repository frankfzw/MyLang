public class ASTRelationalExpression extends SimpleNode {
	private String leftExp;
	private String rightExp;
	private String op;
	
	public String getLeftExp() {
		return leftExp;
	}

	public void setLeftExp(String leftExp) {
		this.leftExp = leftExp;
	}

	public String getRightExp() {
		return rightExp;
	}

	public void setRightExp(String rightExp) {
		this.rightExp = rightExp;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public ASTRelationalExpression(int id) {
	    super(id);
	}

	public ASTRelationalExpression(Eg2 p, int id) {
		super(p, id);
	}
	
	public String genInter() {
		String content = "\nif t"
				+ Integer.toString(para - 1)
				+ " " + op
				+ " t" + Integer.toString(para)
				+ " goto L" + Integer.toString(genLabel())
				+ "\ngoto L" + Integer.toString(genLabel());
		content += "\nL"
				+ Integer.toString(genOldLabel())
				+ ":\nt" + Integer.toString(genPara())
				+ " = 1\n"
				+ "L"
				+ Integer.toString(genOldLabel())
				+ ":\nt" + Integer.toString(para)
				+ " = 0";
		return content;
	}

}
