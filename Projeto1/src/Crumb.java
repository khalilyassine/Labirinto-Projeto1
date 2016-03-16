public class Crumb {
	private int a;
	private int b;
	private int passes;

	public Crumb(int a, int b) {
		this.a = a;
		this.b = b;
		this.passes = 0;
	}

	public int getA() {
		return a;
	}
	
	public int getB() {
		return b;
	}

	public int getPasses() {
		return passes;
	}

	public void incrementPasses() {
		passes++;
	}
}
