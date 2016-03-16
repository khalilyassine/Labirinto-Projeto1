import java.util.Stack;

public class DFS {
	
	private final static int CELL_SIZE = 25;;
	
	Boneco boneco = new Boneco();


	public void matriz() {
		int i, j;
	
    	Node[][] nodes = new Node[10][15];

		for(i = 0; i < 10; i++) {
			for(j = 0; j < 15; j++) {
				nodes[i][j] = new Node(i, j);
			}			
		}
			
    	/*
		nodes[boneco.x][boneco.y].setLeft(nodes[boneco.x+CELL_SIZE][boneco.y]);
		nodes[boneco.x][boneco.y].setRight(nodes[boneco.x-CELL_SIZE][boneco.y]);
		nodes[boneco.x][boneco.y].setUp(nodes[boneco.x][boneco.y-CELL_SIZE]);
		nodes[boneco.x][boneco.y].setDown(nodes[boneco.x][boneco.y+CELL_SIZE]);

		Stack<Crumb> stack = new Stack<Crumb>();
		stack.push(new Crumb(nodes[boneco.x][boneco.y]));
		System.out.println(nodes[boneco.x][boneco.y].getI());
		System.out.println(nodes[boneco.x][boneco.y].getJ());

		while (!stack.empty()) {
		Crumb crumb = new Crumb(stack.peek().getNode());

		if (crumb.getNode().getLeft() != null) {
		Crumb crumb2 = new Crumb(crumb.getNode().getLeft());
		crumb.getNode().setLeft(null);
		stack.push(crumb2);
		System.out.println(crumb2.getNode().getI());
		System.out.println(crumb2.getNode().getJ());


		} else if (crumb.getNode().getRight() != null) {
		Crumb crumb2 = new Crumb(crumb.getNode().getRight());
		crumb.getNode().setRight(null);
		stack.push(crumb2);
		System.out.println(crumb2.getNode().getI());
		System.out.println(crumb2.getNode().getJ());
		
		} else if (crumb.getNode().getUp() != null) {
		Crumb crumb2 = new Crumb(crumb.getNode().getUp());
		crumb.getNode().setUp(null);
		stack.push(crumb2);
		System.out.println(crumb2.getNode().getI());
		System.out.println(crumb2.getNode().getJ());
		
		} else if (crumb.getNode().getDown() != null) {
			Crumb crumb2 = new Crumb(crumb.getNode().getDown());
			crumb.getNode().setDown(null);
			stack.push(crumb2);
			System.out.println(crumb2.getNode().getI());
			System.out.println(crumb2.getNode().getJ());

		} else {
		stack.pop();
		}
		}

*/
		System.out.println("FIM");
	}
}
