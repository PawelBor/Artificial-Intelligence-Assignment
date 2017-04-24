package ie.gmit.sw.ai;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ie.gmit.sw.ai.characters.Player;
import ie.gmit.sw.ai.nn.EncounterNN;
import ie.gmit.sw.ai.node.Node;
import ie.gmit.sw.ai.node.NodeType;
import ie.gmit.sw.ai.traversor.PlayerTraversor;
import ie.gmit.sw.ai.weapons.Bomb;
import ie.gmit.sw.ai.weapons.HBomb;
import ie.gmit.sw.ai.weapons.Sword;
public class Game implements KeyListener{
	
	private static final int MAZE_DIMENSION = 50;
	private static final int IMAGE_COUNT = 16;
	
	public static GameView gameView;
	public static Maze maze;
	public static Player spartan;
	public static int currentRow;
	public static int currentCol;
	public static String found = "";
	public static JFrame f;
	public static JLabel hp;
	public static EncounterNN combatNet = new EncounterNN();
	public static boolean toggleNN = true;
	private JLabel nn;
	
	public Game() throws Exception
	{
		
		maze = new Maze(MAZE_DIMENSION);// Generate new Maze.
		
    	gameView = new GameView(maze); // Create game view from the maze.
    	
    	gameView.setSprites(getSprites()); // Add image array to the maze view.
    	
    	Node exitNode = placeExitNode(); // Place the exit node (Helmet).
    	
    	placePlayer(); // Add the player to the map at a random location.
    	
    	setGameViewPrefrences(); // Create the game world.
    	
    	createJframe(); // Create the JFrame containing the gameView.
    	
        aiChoice(exitNode); // JOptionPane choose the hero control AI or not.
        
        initSpiderMovement(); // Create a thread pool and start up the worker threads.
        
	}
	
	private void createJframe() 
	{
		int rgb = new Color(255, 12, 45).getRGB();
		int white = new Color(255, 255, 255).getRGB();
		
		Color x = new Color(rgb);
		Color whites = new Color(white);
		
		JLabel health = new JLabel("HEALTH");
		nn = new JLabel("NN: "+ toggleNN);
		nn.setForeground(whites);
		hp = new JLabel("100");
		hp.setForeground(whites);
		health.setForeground(whites);
		f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());
        f.add(gameView);
        
        f.getContentPane().setBackground(x);
        f.getContentPane().setForeground(whites);
        f.getContentPane().add(health); f.getContentPane().add(hp);
        f.getContentPane().add(nn);
        f.setSize(1000,1000);
        f.setLocation(100,100);
        f.pack();
        f.setVisible(true);
	}

	private void setGameViewPrefrences() {
		Dimension dimension = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	gameView.setPreferredSize(dimension);
    	gameView.setMinimumSize(dimension);
    	gameView.setMaximumSize(dimension);
    	gameView.setCurrentPosition(spartan.getPos_y(),spartan.getPos_x());
	}

	private Node placeExitNode() {
		currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	
    	// Check if the random position is an empty square.
    	if(maze.get(currentRow, currentCol).getType() != 'e')
    		placeExitNode(); // Use recursion if the square is not empty.
    	
    	maze.set(currentRow, currentCol, 'k');
    	
    	return new Node(currentCol, currentRow, 'k');
	}

	private void initSpiderMovement() throws InterruptedException {
		
		Node targetNode = new Node(spartan.getPos_y(),spartan.getPos_x(),'p');
        targetNode.setGoalNode(true);
        
        // Create a thread pool of a static size
        ExecutorService executor = Executors.newFixedThreadPool(3); 
        
        // Create the spider and pass it to the worker thread.
        Node blackSpider = new Node(Maze.enemyArray.get(0).getPos_y(), Maze.enemyArray.get(0).getPos_x(), '6'); 
        Runnable worker = new ThreadedPathfinding("randomWalk", blackSpider, maze, targetNode);  
        executor.execute(worker); // Calling execute method of ExecutorService

        // Create the spider and pass it to the worker thread.
        Node blueSpider = new Node(Maze.enemyArray.get(1).getPos_y(), Maze.enemyArray.get(1).getPos_x(), '7');
        Runnable secondWorker = new ThreadedPathfinding("aStar", blueSpider, maze, targetNode);  
        executor.execute(secondWorker); // Calling execute method of ExecutorService
        
        /*
        Node brownSpider = new Node(Maze.enemyArray.get(2).getPos_y(), Maze.enemyArray.get(2).getPos_x(), 'm');
        Runnable thirdWorker = new ThreadedPathfinding("depthFirst", brownSpider, maze, targetNode);  
        executor.execute(thirdWorker);//calling execute method of ExecutorService
        
        
        Node greenSpider = new Node(Maze.enemyArray.get(3).getPos_y(), Maze.enemyArray.get(3).getPos_x(), 'm');
        Runnable FourthWorker = new ThreadedPathfinding("hillClimbing", greenSpider, maze, targetNode);  
        executor.execute(FourthWorker);//calling execute method of ExecutorService
        */
        
        executor.shutdown();  
        
        while (!executor.isTerminated()) {   } 
	}

	/*
	private void optainedWeapon(){
		Weapon playerWeapon = new Weapon(0, 0, NodeType.Sword);
	}
    */
	
	private void placePlayer()
	{   	
		
		// Generate a random position on the map
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	
    	// Check if the random position is an empty square.
    	if(maze.get(currentRow, currentCol).getType() != 'e')
    		placePlayer(); // Use recursion if the square is not empty.
    	
    	spartan = new Player(); // Create the player
    	spartan.setHealth(100); // Set health to 100
    	
    	// Move the current position to the player spawn
    	maze.set(currentRow, currentCol, 'p');
    	
    	// Update the position of the player and the game view.
    	updateView();
    	
	}
	
	private void updateView()
	{
		
		spartan.setPos(currentCol, currentRow);
		gameView.setCurrentRow(currentRow);
		gameView.setCurrentCol(currentCol);
		
	}

    public void keyPressed(KeyEvent e) 
    {
    	
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
        }else if (e.getKeyCode() == KeyEvent.VK_X){
        	toggleNN = !toggleNN;
        	System.out.println("NN TOGGLED TO: "+ toggleNN);
        	nn.setText("NN: " + toggleNN);
        }
        else{
        	return;
        }
        
        updateView();       
    }
    
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore

    
	private boolean isValidMove(int row, int col){
		
		if (row <= maze.size() - 1 && col <= maze.size() - 1 && maze.get(row, col).getType() == 'e')
		{
			moveCharacter(row, col); // Update player position
			return true;
		}else if(maze.get(row, col).getType() == 's'){ // Sword
			System.out.println("Sword added to inventory!");
			Sword sword = new Sword( 3+(int)(Math.random() *((7 - 3) + 1)), NodeType.Sword); // Create the sword.
			spartan.addtoInventory(sword); // Add the sword to the inventory.
			moveCharacter(row, col); // Update player position
			return true;
		}else if(maze.get(row, col).getType() == 'b'){ // Bomb Replace
			System.out.println("Bomb added to inventory!");
			Bomb bomb = new Bomb(100, NodeType.Bomb); // Create the bomb
			spartan.addtoInventory(bomb);// Add the bomb to inventory
			moveCharacter(row, col); // Update player position
			return true;
		}else if(maze.get(row, col).getType() == 'o'){ // HBomb Replace
			System.out.println("Hydrogen Bomb added to inventory!");
			HBomb hbomb = new HBomb(100, NodeType.HBomb); // Create the bomb
			spartan.addtoInventory(hbomb);// Add the bomb to inventory
			moveCharacter(row, col); // Update player position
			return true;
		}else if(maze.get(row, col).getType() == 'h'){ // Help encountered
			System.out.println("Help Encountered....");
			moveCharacter(row, col); // Update player position
			return true;
		}else if(maze.get(row, col).getType() == 'k'){ // Exit node Encountered
			Game.spartan.setPos(Game.spartan.getPos_x(), Game.spartan.getPos_y());
			Game.maze.set(Game.spartan.getPos_x(), Game.spartan.getPos_y(), 'p');
			Game.gameView.setCurrentPosition(Game.spartan.getPos_x(),Game.spartan.getPos_y());
			gameOver("You Win! Play Again?"); // Show the win message
			return true;
		}else if(maze.get(row, col).getType() == '7'){ 
			System.out.println("Spider Encountered!");
			found = "7"; // Flag to encounter
			maze.set(currentRow, currentCol, 'e'); maze.set(row, col, 'x');
			return true;
		}else if(maze.get(row, col).getType() == '6'){ 
			System.out.println("Spider Encountered!");
			found = "6"; // Flag to encounter
			maze.set(currentRow, currentCol, 'e'); maze.set(row, col, 'x');
			return true;
		}
		else{
			return false; //Can't move
		}
	}
	
	private void moveCharacter(int row, int col) {
		maze.set(currentRow, currentCol, 'e');
		maze.set(row, col, 'p');
		spartan.setPos(col, row);
	}

	public static void aiChoice(Node target) throws InterruptedException
    {
		Object[] options = {"YES", "NO"};
        int n = JOptionPane.showOptionDialog(Game.f,
                        "Use the 'x' key to toggle the NN.\nThis will make the hero run when health < 40 or and has no weapon. \n"
                        + "Use AI controlled character?",
                        "Choose",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
        if (n == JOptionPane.YES_OPTION) {
    		// Move the player towards the exit node using A*
        	PlayerTraversor pt = new PlayerTraversor(target);
        	pt.traverse(maze.getMaze(), spartan);
        } else if (n == JOptionPane.NO_OPTION) {
    		//Game.f.dispose();
        } else {

        }
    }
	
	public static void gameOver(String message)
    {
		Object[] options = {"EXIT"};
        int n = JOptionPane.showOptionDialog(Game.f,
        		message,
                        "Play Again?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
        if (n == JOptionPane.YES_OPTION) {
        	System.exit(0);
        } else if (n == JOptionPane.NO_OPTION) {
        	System.exit(0);
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
		sprites[14] = new Sprite("Fight Cloud", "resources/fight.png", "resources/fight_2.png");
		sprites[15] = new Sprite("Exit node", "resources/exit.png", "resources/exit_2.png");
		return sprites;
	}
	
}