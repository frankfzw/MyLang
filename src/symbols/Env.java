package symbols;
import java.util.*; 

import Parser.Token;


public class Env {

	private Hashtable table;
	private int breakLabel;
	private int continueLabel;
	
	protected Env prev;

	public int getBreakLabel() {
		return breakLabel;
	}

	public void setBreakLabel(int breakLabel) {
		this.breakLabel = breakLabel;
	}

	public int getContinueLabel() {
		return continueLabel;
	}

	public void setContinueLabel(int continueLabel) {
		this.continueLabel = continueLabel;
	}

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
