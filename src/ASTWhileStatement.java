import java.io.IOException;

/* Generated By:JJTree: Do not edit this line. ASTWhileStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTWhileStatement extends SimpleNode {
	private int headLabel;
	private int tailLabel;
	
  public ASTWhileStatement(int id) {
    super(id);
  }

  public ASTWhileStatement(Eg2 p, int id) {
    super(p, id);
  }
  
  public void writeHeadLabel() throws IOException {
	  headLabel = genLabel();
	  continueLabel = headLabel;
	  content = "\nL" + Integer.toString(headLabel) + ":";
	  writeInter();
  }
  
  public int genInter(int paraL, int paraR) throws IOException {
	  tailLabel = genLabel();
	  breakLabel = tailLabel;
	  content = "\n@t" + Integer.toString(genPara()) + " = @t" + Integer.toString(paraR)
			  + "\nif @t" + Integer.toString(para) + " == 0 goto L" + Integer.toString(tailLabel);
	  writeInter();
	  return para;
  }
  
  public void writeTailLabel() {
	  content = "\ngoto L" + Integer.toString(headLabel)
			  + "\nL" + Integer.toString(tailLabel) + ":";
  }
}
/* JavaCC - OriginalChecksum=295e09f0e4126e4e0f72780a27183f4d (do not edit this line) */
