public class ASTPrimary2 extends SimpleNode 
{
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ASTPrimary2(int id) {
	    super(id);
	}

	public ASTPrimary2(Eg2 p, int id) {
		super(p, id);
	}
	

}
