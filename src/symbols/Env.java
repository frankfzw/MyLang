package symbols;
import java.util.*; 

import Parser.Token;


public class Env {

	private Hashtable table;
	protected Env prev;

	public Env(Env n) { table = new Hashtable(); prev = n; }

	public void put(String id, String type) { table.put(id, type); }

	public String get(String id) {
		for( Env e = this; e != null; e = e.prev ) 
		{
			String found = (String)(e.table.get(id));
			if( found != null ) 
				return found;			
		}
		return null;
	}
	public boolean contains(String id)
	{
		return this.table.containsKey(id); 
	}
}
