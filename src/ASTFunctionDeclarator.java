import java.io.IOException;

public class ASTFunctionDeclarator extends SimpleNode {
	private String name;
	private String type;
	
  public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

  public ASTFunctionDeclarator(int id) {
    super(id);
  }

  public ASTFunctionDeclarator(Eg2 p, int id) {
    super(p, id);
  }

  public int genInter() throws IOException {
	  content = "\n"
			  + type + " "
			  + name + " "
			  + "proc ";
	  writeInter();
	  return 0;
  } 
  
  
  
}

