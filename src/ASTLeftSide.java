
public class ASTLeftSide extends SimpleNode {
  private String name;


  public ASTLeftSide(int id) {
    super(id);
  }
  
  public ASTLeftSide(Eg2 p, int id) {
	  super(p, id);
  }


  public void setName(String n) {
    name = n;
  }


  public String toString() {
    return "Leftside Identifier: " + name;
  }

}
