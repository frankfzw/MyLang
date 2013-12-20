public class ASTAssignmentExpression extends SimpleNode {
	private String leftSide;
	private String op;
	
	public String getLeftSide() {
		return leftSide;
	}

	public void setLeftSide(String leftSide) {
		this.leftSide = leftSide;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public ASTAssignmentExpression(int id) {
	    super(id);
	  }

	public ASTAssignmentExpression(Eg2 p, int id) {
		super(p, id);
	}
	
	public String genInter() {
		String content = "\n" 
				+ leftSide +" "
				+ op + " t"
				+ Integer.toString(para);
		return content;
	}
}
