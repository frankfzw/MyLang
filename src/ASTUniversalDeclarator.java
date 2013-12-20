
public class ASTUniversalDeclarator extends SimpleNode {
	private String name;
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

}

