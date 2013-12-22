import java.io.IOException;


public class ASTUniversalDeclarator extends SimpleNode {
	private String name;
	private String id;
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public ASTUniversalDeclarator(int id) 
	{
		super(id);
	}

	
	public void setName(String n) 
	{
		name = n;
	}
	
	
	public String toString() 
	{
		if (name != "")
			return name;
		else 
			return "UniversalDeclarator";		
	}
	
	public int genInter(int paraL, int paraR) throws IOException {
		content = id +" = "
				+ "@t"
				+ Integer.toString(paraR)
				+ "\n";
		writeInter();
		return para;
	}

}

