import java.io.IOException;

/* Generated By:JJTree: Do not edit this line. ASTForStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTForStatement extends SimpleNode {
	private int headLabel;
	private int tailLabel;
	
  public ASTForStatement(int id) {
    super(id);
  }

  public ASTForStatement(Eg2 p, int id) {
    super(p, id);
  }

  public void writeHeadLabel() throws IOException {
	  headLabel = genLabel();
	  continueLabel = headLabel;
	  content = "\nL" + Integer.toString(headLabel) + ":";
	  writeInter();
  }
  
  public int genInter(String id, int paraL, int paraR) throws IOException {
	  tailLabel = genLabel();
	  breakLabel = tailLabel;
	  content = "\n" + id + " = @t" + Integer.toString(paraL)
			  + "\n@t" + Integer.toString(genPara()) + " = @t" + Integer.toString(paraR)
			  + "\nif @t" + Integer.toString(para) + " < " + "@t" + id
			  + " goto L" + Integer.toString(tailLabel);
	  writeInter();
	  return para;
  }
  
  public void writeTailLabel() {
	  content = "\ngoto L" + Integer.toString(headLabel)
			  + "\nL" + Integer.toString(tailLabel) + ":";
  }
}
/* JavaCC - OriginalChecksum=ca57cc63ce44c379797e0b21d4047f63 (do not edit this line) */
