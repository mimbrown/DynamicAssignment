
public class Node {
	private String s;
	private int i;
	private Node parent;
	private String removed;
	
	public Node() {
		super();
	}
	
	public Node(String s, int i, Node n, String removed) {
		super();
		this.s = s;
		this.i = i;
		this.parent = n;
		this.removed = removed;
	}
	
	public String getS() {
		return s;
	}
	
	public int getI() {
		return i;
	}
	
	public void setParent(Node n) {
		parent = n;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public String getRemoved() {
		return removed;
	}

}
