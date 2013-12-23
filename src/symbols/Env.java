package symbols;
import java.util.*; 

import Parser.Token;


public class Env {

	private Hashtable table;
	private int breakLabel;
	private int continueLabel;
	private Type funType; //use only in function env
	private boolean isReturned;
	
	public boolean isReturned() {
		return isReturned;
	}

	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}
	protected Env prev;

	public int getBreakLabel() {
		return breakLabel;
	}

	public Type getFunType() {
		return funType;
	}

	public void setFunType(Type funType) {
		this.funType = funType;
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

	public void put(String id, Type type) { table.put(id, type); }

	public Type get(String id) {
		for( Env e = this; e != null; e = e.prev ) 
		{
			Type found = (Type)(e.table.get(id));
			if( found != null ) 
				return found;			
		}
		return null;
	}
	
	public Type getFuncType(String id) 
	{
		Env e = this;
		while (e.prev != null)
			e = e.prev;
		Type type = e.get(id);
		if (type == null)
			return null;
		if (!type.isFunc)
			return null;
		String s = type.name;
		if (s.compareTo("int") == 0)
			return Type.Int;
		else if (s.compareTo("bool") == 0)
			return Type.Bool;
		else if (s.compareTo("real") == 0)
			return Type.Real;
		else if (s.compareTo("char") == 0)
			return Type.Char;
		else if (s.compareTo("void") == 0)
			return Type.Void;
		else
			return null;
	}
	
	public boolean contains(String id)
	{
		return this.table.containsKey(id); 
	}
}
