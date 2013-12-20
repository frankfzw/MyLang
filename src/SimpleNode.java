import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SimpleNode implements Node {

  protected Node parent;
  protected Node[] children;
  protected int id;
  protected Object value;
  protected Eg2 parser;
  
  protected static int label = 0;
  protected static int para = 0;
  protected static int oldLabel = 0;
  protected String content;
  

  public SimpleNode(int i) {
    id = i;
  }

  public SimpleNode(Eg2 p, int i) {
    this(i);
    parser = p;
  }

  public void jjtOpen() {
  }

  public void jjtClose() {
  }

  public void jjtSetParent(Node n) { parent = n; }
  public Node jjtGetParent() { return parent; }

  public void jjtAddChild(Node n, int i) {
    if (children == null) {
      children = new Node[i + 1];
    } else if (i >= children.length) {
      Node c[] = new Node[i + 1];
      System.arraycopy(children, 0, c, 0, children.length);
      children = c;
    }
    children[i] = n;
  }

  public Node jjtGetChild(int i) {
    return children[i];
  }

  public int jjtGetNumChildren() {
    return (children == null) ? 0 : children.length;
  }

  public void jjtSetValue(Object value) { this.value = value; }
  public Object jjtGetValue() { return value; }

  /* You can override these two methods in subclasses of SimpleNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. */

  public String toString() { return Eg2TreeConstants.jjtNodeName[id]; }
  public String toString(String prefix) { return prefix + toString(); }

  /* Override this method if you want to customize how the node dumps
     out its children. */

  public void dump(String prefix) {
    System.out.println(toString(prefix));
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        SimpleNode n = (SimpleNode)children[i];
        if (n != null) {
          n.dump(prefix + " ");
        }
      }
    }
  }
  
  
  
  
  
  
	@Override
	public int genLabel() {
		// TODO Auto-generated method stub
		return ++label;
	}
	
	@Override
	public int genPara() {
		// TODO Auto-generated method stub
		return ++para;
	}
	
	@Override
	public void writeInter() throws IOException {
		// TODO Auto-generated method stub
		//write to file
		File file = new File("interCode.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file.getName(), true);
		writer.write(content);
		writer.close();
	}

	@Override
	public int genOldLabel() {
		// TODO Auto-generated method stub
		if (oldLabel < label)
			return ++ oldLabel;
		return -1;
	}
  
  

  
  
}


