import java.io.IOException;

public class ASTPrimary extends SimpleNode {
  private String name;

  /**
   * Constructor.
   * @param id the id
   */
  public ASTPrimary(int id) {
    super(id);
  }


  /**
   * Set the name.
   * @param n the name
   */
  public void setName(String n) {
    name = n;
  }

  /**
   * {@inheritDoc}
   * @see org.javacc.examples.jjtree.eg2.SimpleNode#toString()
   */
  public String toString() {
	  if (name != null)
		  return "Primary Literal: " + name;
	return "Primary";
  }
  
  public int genInter() throws IOException {
		content = "\n@t"
				+ Integer.toString(genPara()) + " = "
				+ name;
		writeInter();
		return para;
	}
  
  public int genFuncInvoke(String id, int[] args) throws IOException {
	  content = "invoke " + id;
	  for (int i = 0; i < args.length; i ++) {
		  content += " @t" + Integer.toString(args[i]);
	  }
	  content += "\n";
	  writeInter();
	  return para;
  }

}
