public class Node {
	private int i,j;
	private Node left;
	private Node right;
	private Node up;
	private Node down;
	

	public Node (int i, int j) {
		this.i = i;
		this.j = j;
		this.left = null;
		this.right = null;
		this.up =null;
		this.down=null;
	}

	public int getI() {
		return i;
	}
	
	public int getJ() {
		return j;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}
	
	public Node getUp() {
		return up;
	}

	public void setUp(Node up) {
		this.up = up;
	}
	
	public Node getDown() {
		return down;
	}

	public void setDown(Node down) {
		this.down = down;
	}

}
