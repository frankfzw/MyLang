package symbols;

import java.util.Hashtable;

public class FuncTable 
{
	
	private Hashtable<String, Function> table;
	
	public FuncTable()
	{
		table = new Hashtable<String, Function>();
	}
	
	public void put(String id, Function func) { table.put(id, func); }

	public Function get(String id) 
	{		
		Function found = (Function)(this.table.get(id));
		if( found != null ) 
			return found;			
		return null;
	}
}
