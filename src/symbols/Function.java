package symbols;

import java.util.ArrayList;


public class Function 
{
	private String id;
	
	private ArrayList<Type> paras;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Type> getParas() {
		return paras;
	}

	public void setParas(ArrayList<Type> paras) {
		this.paras = paras;
	}

	
	
	public Function(String id, ArrayList<Type> paras)
	{
		this.id = id;
		this.paras = paras;
	}
	
	
	
}
