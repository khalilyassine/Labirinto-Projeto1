import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Screen extends JPanel implements ActionListener, KeyListener {
	

	
	private static final long serialVersionUID = 1L;

	private final static int CELL_SIZE = 25;
	private final static int INICIAL_POSITION = 0;
	
	private static int width;
	private static int height;
	
	private int x_bloco;
	private int y_bloco;
	
	private int xTaco;
	private int yTaco;
	
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
	
	private static boolean[][] labyrinth;
	private static boolean[][] caminho;
	
	private static String[][] labirintoR;

	public Screen() {
		
		try {
			Screen.readFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		y_bloco= INICIAL_POSITION;
		x_bloco= INICIAL_POSITION;
		
		boneco.setX(INICIAL_POSITION);
		boneco.setY(INICIAL_POSITION);
		
		yLimiteDown= CELL_SIZE;
		yLimiteUp= this.width - y_bloco;
		xLimiteRight =this.width- x_bloco;
		xLimiteLeft =x_bloco;
		
		this.width = this.labyrinth[0].length;
		this.height = this.labyrinth.length;
		
		while(true) {
			xTaco = bolo.nextInt(this.width);
			yTaco = bolo.nextInt(this.height);
						
			if(labyrinth[yTaco][xTaco]) {
				break;
			}
		}
		
		setPreferredSize(new Dimension(this.width * CELL_SIZE, this.height * CELL_SIZE));
		
		image = new ImageIcon(getClass().getResource("/img/Lanterna.png")).getImage();
		image1 = new ImageIcon(getClass().getResource("/img/Deadpool.png")).getImage();
		image2 = new ImageIcon(getClass().getResource("/img/taco.png")).getImage();
		
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
		g.drawImage(image2, xTaco*CELL_SIZE, yTaco*CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
    
		getToolkit().sync();
    }
	
	public void keyPressed(KeyEvent e) {
    	
		int key = e.getKeyCode();

		if (x_bloco == (xTaco*CELL_SIZE) && y_bloco == (yTaco*CELL_SIZE) ) {
			System.out.println("VOCE GANHOU!");
			timer.stop();
		}
		
		else {
				if (key == KeyEvent.VK_LEFT) {
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
		
	}
	
	
	
	public void keyReleased(KeyEvent event){	
	}
	
	public void keyTyped(KeyEvent event){	
	}
	
	public void actionPerformed(ActionEvent e) {
	
		Crumb crumb = stack.peek();
		
		if (boneco.x == (xTaco*CELL_SIZE) && boneco.y == (yTaco*CELL_SIZE) ) {
			System.out.println("VOCE GANHOU!");
			timer.stop();
		}
		
		else {
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
			}
		
	
	private static void readFiles() throws IOException{
		
		List<String> argumentos = Files.lines(Paths.get("labyrinth.txt"))
	    .collect(Collectors.toList());
		//System.out.println(argumentos);
		
		int linha = 0;
		labirintoR = new String[argumentos.size()][];
		for(String i : argumentos){
			String[] temp = i.split("");
			labirintoR[linha] = temp;
			//System.out.println(temp);
			//System.out.println(labirintoR);
			linha++;
		}
		
		labyrinth = new boolean[labirintoR.length][labirintoR[0].length];
		caminho = new boolean[labirintoR.length][labirintoR[0].length];
		
		for(int j = 0; j != labirintoR.length; j++){
			for(int i = 0; i != labirintoR[0].length; i++){
				
				if(labirintoR[j][i].startsWith("#")){
					labyrinth[j][i] = false;
					caminho[j][i] = false;
				}
				else{
					labyrinth[j][i] = true;
					caminho[j][i] = true;
				}
			}
		}


	}
}
		



