
public class ASTFunctionParameter extends SimpleNode 
{
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

	
  public ASTFunctionParameter(int id) {
    super(id);
  }

  public ASTFunctionParameter(Eg2 p, int id) {
    super(p, id);
  }

  public String genInter() {
	  String content = name
			  + ":"
			  + type
			  + " ";
	  return content;
  }
  
}

