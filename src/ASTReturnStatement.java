import java.io.IOException;

public
class ASTReturnStatement extends SimpleNode {
  public ASTReturnStatement(int id) {
    super(id);
  }

  public ASTReturnStatement(Eg2 p, int id) {
    super(p, id);
  }

  public int genInter(int paraL, int paraR) throws IOException {
	  content = "ret ";
	  if (paraL != 0)
		  content += "@t" + Integer.toString(paraL);
	  content += "\n";
	  writeInter();
	  return para;
  }
}
