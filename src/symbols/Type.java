package symbols;

public class Type{

   public int width = 0;          // width is used for storage allocation
   public String name = "";
   public boolean isFunc = false;
   public boolean isArray = false;
   
   public Type(String s, int w) {name = s; width = w;}

   public static final Type
      Int   = new Type("int", 4 ),
      Real 	= new Type("real", 8 ),
      Char  = new Type("char", 1 ),
      Bool  = new Type("bool", 1 ),
      Void  = new Type("void", 0 );
   
   
   public Type(String s, boolean f) 
   {
	   name = s;
	   isFunc = f;
	   if (s.compareTo("int") == 0)
		   width = 4;
	   else if (s.compareTo("bool") == 0)
		   width = 1;
	   else if (s.compareTo("real") == 0)
		   width = 8;
	   else if (s.compareTo("char") == 0)
		   width = 1;
   }

   public static boolean numeric(Type p) {
      if (p == Type.Char || p == Type.Int || p == Type.Real) return true;
      else return false;
   }

   public static Type max(Type p1, Type p2 ) {
      if ( ! numeric(p1) || ! numeric(p2) ) return null;
      else if ( p1 == Type.Real || p2 == Type.Real ) return Type.Real;
      else if ( p1 == Type.Int   || p2 == Type.Int   ) return Type.Int;
      else return Type.Char;
   }
}
