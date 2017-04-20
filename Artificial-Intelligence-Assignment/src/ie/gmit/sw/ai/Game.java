package ie.gmit.sw.ai;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import ie.gmit.sw.characters.Player;
import ie.gmit.sw.node.Node;
import ie.gmit.sw.node.NodeType;
import ie.gmit.sw.traversor.RandomWalk;
import ie.gmit.sw.traversor.Traversator;
import ie.gmit.sw.weapons.Bomb;
import ie.gmit.sw.weapons.HBomb;
import ie.gmit.sw.weapons.Sword;
public class Game implements KeyListener{
	
	private static final int MAZE_DIMENSION = 50;
	private static final int IMAGE_COUNT = 14;
	
	private GameView gameView;
	private Maze maze;
	public static Player spartan;
	private int currentRow;
	private int currentCol;
	
	public Game() throws Exception
	{
		maze = new Maze(MAZE_DIMENSION);// Generate new Maze.
    	gameView = new GameView(maze); // Create game view from the maze.
    	
    	Sprite[] sprites = getSprites();
    	gameView.setSprites(sprites); // Add image array to the maze view.
    	
    	placePlayer(); // Add the player to the map at a random location.

    	Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	gameView.setPreferredSize(d);
    	gameView.setMinimumSize(d);
    	gameView.setMaximumSize(d);
    	gameView.setCurrentPosition(spartan.getPos_y(),spartan.getPos_x());
    	
    	JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());
        f.add(gameView);
        f.setSize(1000,1000);
        f.setLocation(100,100);
        f.pack();
        f.setVisible(true);
        
        System.out.println(maze.toString());
        
        Node targetNode = new Node(spartan.getPos_y(),spartan.getPos_x(),'p');
        targetNode.setGoalNode(true);
          
        Traversator t = new RandomWalk();
        Node spider2 = new Node(Maze.enemyArray.get(0).getPos_y(), Maze.enemyArray.get(0).getPos_x(), 'm');
        
        t.traverse(maze.getMaze(), spider2);
	}
	
	/*
	 * private void optainedWeapon(){
		Weapon playerWeapon = new Weapon(0, 0, NodeType.Sword);
	}
	 */
	
	private void placePlayer(){   	
		
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	
    	spartan = new Player();
    	spartan.setHealth(100);

    	maze.set(currentRow, currentCol, 'p');
    	updateView();
	}
	
	private void updateView(){
		spartan.setPos(currentCol, currentRow);
		gameView.setCurrentRow(currentRow);
		gameView.setCurrentCol(currentCol);
	}

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow, currentCol + 1)) currentCol++;   		
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) {
        	if (isValidMove(currentRow, currentCol - 1)) currentCol--;	
        }else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) {
        	if (isValidMove(currentRow - 1, currentCol)) currentRow--;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow + 1, currentCol)) currentRow++;        	  	
        }else if (e.getKeyCode() == KeyEvent.VK_Z){
        	gameView.toggleZoom();
        }
        else{
        	return;
        }
        
        updateView();       
    }
    
    
    
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore

    
	private boolean isValidMove(int row, int col){
		if (row <= maze.size() - 1 && col <= maze.size() - 1 && maze.get(row, col).getType() == 'e'){
			maze.set(currentRow, currentCol, 'e');
			maze.set(row, col, 'p');
			return true;
		}else if(maze.get(row, col).getType() == 's'){ // Sword Replace
			System.out.println("Sword Encountered....");// Add the sword to inventory
			
			Sword sword = new Sword(30, NodeType.Sword); // Create the sword.
			spartan.addtoInventory(sword); // Add the sword to the inventory.
			
			maze.set(currentRow, currentCol, 'e');
			maze.set(row, col, 'p'); //Pick Item, Replace with Spartan sprite
			return true;
		}else if(maze.get(row, col).getType() == 'b'){ // Bomb Replace
			System.out.println("Bomb Encountered....");
			
			Bomb bomb = new Bomb(100, NodeType.Bomb); // Create the bomb
			spartan.addtoInventory(bomb);// Add the bomb to inventory
			
			maze.set(currentRow, currentCol, 'e');
			maze.set(row, col, 'p'); //Pick Item, Replace with Spartan sprite
			return true;
		}else if(maze.get(row, col).getType() == 'o'){ //HBomb Replace
			System.out.println("HBomb Encountered....");
			
			HBomb hbomb = new HBomb(100, NodeType.HBomb); // Create the bomb
			spartan.addtoInventory(hbomb);// Add the bomb to inventory
			
			maze.set(currentRow, currentCol, 'e');
			maze.set(row, col, 'p'); //Pick Item, Replace with Spartan sprite
			return true;
		}else if(maze.get(row, col).getType() == 'h'){ //Help encountered
			System.out.println("Help Encountered....");
			maze.set(currentRow, currentCol, 'e');
			maze.set(row, col, 'p'); //Pick Item, Replace with Spartan sprite
			return true;
		}else{
			return false; //Can't move
		}
	}
	
	private Sprite[] getSprites() throws Exception{
		//Read in the images from the resources directory as sprites. Note that each
		//sprite will be referenced by its index in the array, e.g. a 3 implies a Bomb...
		//Ideally, the array should dynamically created from the images... 
		Sprite[] sprites = new Sprite[IMAGE_COUNT];
		sprites[0] = new Sprite("Hedge", "resources/hedge.png");
		sprites[1] = new Sprite("Sword", "resources/sword.png");
		sprites[2] = new Sprite("Help", "resources/help.png");
		sprites[3] = new Sprite("Bomb", "resources/bomb.png");
		sprites[4] = new Sprite("Hydrogen Bomb", "resources/h_bomb.png");
		sprites[5] = new Sprite("Spartan Warrior", "resources/spartan_1.png", "resources/spartan_2.png");
		sprites[6] = new Sprite("Black Spider", "resources/black_spider_1.png", "resources/black_spider_2.png");
		sprites[7] = new Sprite("Blue Spider", "resources/blue_spider_1.png", "resources/blue_spider_2.png");
		sprites[8] = new Sprite("Brown Spider", "resources/brown_spider_1.png", "resources/brown_spider_2.png");
		sprites[9] = new Sprite("Green Spider", "resources/green_spider_1.png", "resources/green_spider_2.png");
		sprites[10] = new Sprite("Grey Spider", "resources/grey_spider_1.png", "resources/grey_spider_2.png");
		sprites[11] = new Sprite("Orange Spider", "resources/orange_spider_1.png", "resources/orange_spider_2.png");
		sprites[12] = new Sprite("Red Spider", "resources/red_spider_1.png", "resources/red_spider_2.png");
		sprites[13] = new Sprite("Yellow Spider", "resources/yellow_spider_1.png", "resources/yellow_spider_2.png");
		return sprites;
	}
	
}