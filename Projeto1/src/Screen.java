import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Screen extends JPanel implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;

	private final static int CELL_SIZE = 25;
	private final static int INICIAL_POSITION = 0;
	
	private int width;
	private int height;
	
	private int x_bloco;
	private int y_bloco;
	
	private int xBolo;
	private int yBolo;
	
	public int xLimiteRight;
	public int xLimiteLeft;
	public int yLimiteDown;
	public int yLimiteUp;
	
	private Image image;
	private Image image1;
	private Image image2;
	
	Timer timer = new Timer(50, this);
	
	Random bolo = new Random();
	
	Stack<Crumb> stack = new Stack<Crumb>();
	Boneco boneco = new Boneco();
	
	private boolean[][] labyrinth;
	private boolean[][] caminho;

	public Screen(boolean[][] labyrinth) {
				
		y_bloco= INICIAL_POSITION;
		x_bloco= INICIAL_POSITION;
		
		boneco.setX(INICIAL_POSITION);
		boneco.setY(INICIAL_POSITION);
		
		yLimiteDown= CELL_SIZE;
		yLimiteUp= this.width - y_bloco;
		xLimiteRight =this.width- x_bloco;
		xLimiteLeft =x_bloco;
	
		this.labyrinth = labyrinth;
		caminho = deepCopy(labyrinth);
		
		this.width = this.labyrinth[0].length;
		this.height = this.labyrinth.length;
		
		while(true) {
			xBolo = bolo.nextInt(this.width);
			yBolo = bolo.nextInt(this.height);
						
			if(labyrinth[yBolo][xBolo]) {
				break;
			}
		}
		
		setPreferredSize(new Dimension(this.width * CELL_SIZE, this.height * CELL_SIZE));
		
		image = new ImageIcon(getClass().getResource("/img/example.png")).getImage();
		image1 = new ImageIcon(getClass().getResource("/img/superman.png")).getImage();
		image2 = new ImageIcon(getClass().getResource("/img/bolo.png")).getImage();
		
		stack.push(new Crumb (boneco.x, boneco.y));
		
		caminho[boneco.y][boneco.x] = false;



		timer.start();
	}

	public void paintComponent(Graphics g) {
		
		for(int i = 0; i < this.height; i++) {
			int y = i * CELL_SIZE;

			for(int j = 0; j < this.width; j++) {
				int x = j * CELL_SIZE;
				
			if(!caminho[i][j] && labyrinth[i][j]) {
				g.setColor(Color.BLUE);
			}

				if(labyrinth[i][j]) {
					g.setColor(Color.WHITE);
					
				}
				else {
					g.setColor(Color.BLACK);
				}

				g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
				
			}

			
		}
    	
		g.drawImage(image, boneco.x, boneco.y, CELL_SIZE, CELL_SIZE, null);
		g.drawImage(image1, x_bloco, y_bloco, CELL_SIZE, CELL_SIZE, null);
		g.drawImage(image2, xBolo*CELL_SIZE, yBolo*CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
    
		getToolkit().sync();
    }
	
	public void keyPressed(KeyEvent e) {
    	int key = e.getKeyCode();


    	if(key == KeyEvent.VK_LEFT) {
    		if(x_bloco == xLimiteLeft - CELL_SIZE) {
    		}
    		
    		else if(labyrinth[y_bloco/CELL_SIZE][(x_bloco/CELL_SIZE)-1]) {
    			x_bloco = x_bloco - CELL_SIZE;
    		}
    		
    		repaint();
    	}

    	if(key == KeyEvent.VK_RIGHT) {
    		if(x_bloco == width-1) {
     		}

    		else if(labyrinth[y_bloco/CELL_SIZE][(x_bloco/CELL_SIZE)+1]){
    			x_bloco = x_bloco + CELL_SIZE;    				
    		}
    		
    		repaint();
    	}
    	
    	if(key == KeyEvent.VK_UP) {
    		if(y_bloco == INICIAL_POSITION){
    		}
    		else if(labyrinth[(y_bloco/CELL_SIZE)-1][x_bloco/CELL_SIZE]){
    			y_bloco = y_bloco - CELL_SIZE;
    		}
    		repaint();

    	}
    	
    	if(key == KeyEvent.VK_DOWN) {
    		if(y_bloco == height){
    		}
    		
    		else if(labyrinth[(y_bloco/CELL_SIZE)+1][x_bloco/CELL_SIZE]){
    			y_bloco = y_bloco + CELL_SIZE;
    		}
    		
    	repaint();
    	}
    }
	
	public void keyReleased(KeyEvent event){	
	}
	
	public void keyTyped(KeyEvent event){	
	}
	
	public void actionPerformed(ActionEvent e) {
	
		Crumb crumb = stack.peek();
		
		if (boneco.x == (xBolo*CELL_SIZE) && boneco.y == (yBolo*CELL_SIZE)) {
			timer.stop();
		}
		
		else if(x_bloco == (xBolo*CELL_SIZE) && y_bloco == (yBolo*CELL_SIZE)) {
			timer.stop();
		} 
		
		if (crumb.getPasses() == 0){
			if(boneco.x >0 && caminho[boneco.y/CELL_SIZE][(boneco.x/CELL_SIZE)-1]){
				caminho [boneco.y/CELL_SIZE][boneco.x/CELL_SIZE] =false;	
				boneco.x = boneco.x- CELL_SIZE;
				System.out.println(boneco.x);
				stack.push(new Crumb(boneco.x, boneco.y));
				repaint();
			}
				crumb.incrementPasses();	
		}
					
		else if(crumb.getPasses() == 1){
			if(boneco.x/CELL_SIZE < this.width - 1 && caminho[boneco.y/CELL_SIZE][(boneco.x/CELL_SIZE)+1]){
				caminho [boneco.y/CELL_SIZE][boneco.x/CELL_SIZE] =false;	
				boneco.x = boneco.x + CELL_SIZE;
				System.out.println(boneco.x);
				stack.push(new Crumb(boneco.x, boneco.y));
				repaint();
			}
				crumb.incrementPasses();		
		}
		
		else if(crumb.getPasses()==2){
			if(boneco.y/CELL_SIZE < this.height - 1 && caminho[(boneco.y/CELL_SIZE)+1][boneco.x/CELL_SIZE]){
				caminho [boneco.y/CELL_SIZE][boneco.x/CELL_SIZE] =false;	
				boneco.y = boneco.y + CELL_SIZE;
				stack.push(new Crumb(boneco.x, boneco.y));
				repaint();
			}
				crumb.incrementPasses();			
		}
		
		else if(crumb.getPasses()==3) {
			if(boneco.y> 0 && caminho[(boneco.y/CELL_SIZE)-1][boneco.x/CELL_SIZE]){
				caminho [boneco.y/CELL_SIZE][boneco.x/CELL_SIZE] =false;	
				boneco.y = boneco.y - CELL_SIZE;
				stack.push(new Crumb(boneco.x, boneco.y));
				repaint();
			}
				crumb.incrementPasses();
			
		}
		
		else{
			caminho [boneco.y/CELL_SIZE][boneco.x/CELL_SIZE] =false;
			stack.pop();
			boneco.x= stack.peek().getA();
			boneco.y= stack.peek().getB();
		}
			repaint();
		}

	public static boolean[][] deepCopy(boolean[][] original) {
				
		if (original == null) {
	        return null;
	    }

	    final boolean[][] result = new boolean[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        result[i] = Arrays.copyOf(original[i], original[i].length);
	    }
	    return result;
	}
}
		



