import java.io.IOException;

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
	
	public int genInter(int paraL, int paraR) throws IOException {
		content = "if @t"
				+ Integer.toString(paraL)
				+ " " + op
				+ " @t" + Integer.toString(paraR)
				+ " goto @L" + Integer.toString(genLabel());
		content += "\n@t" + Integer.toString(genPara())
				+ " = 0\n"
				+ "goto @L"
				+ Integer.toString(genLabel())
				+ "\n@L"
				+ Integer.toString(genOldLabel())
				+ ": @t" + Integer.toString(para)
				+ " = 1\n@L" + Integer.toString(genOldLabel())
				+ ": ";
		writeInter();
		return para;
	}

}
