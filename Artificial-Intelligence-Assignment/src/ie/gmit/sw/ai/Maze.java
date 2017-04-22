package ie.gmit.sw.ai;

import java.util.ArrayList;
import java.util.Random;

import ie.gmit.sw.characters.Enemy;
import ie.gmit.sw.node.Node;

public class Maze {
	private Node[][] maze;
	public static ArrayList<Enemy> enemyArray = new ArrayList<>();
	
	public Maze(int dimension){
		maze = new Node[dimension][dimension];
		init(); // Fills the entire map with hedges.
		buildMaze(); // Overwrites hedges to spaces on random location.
		
		
		addFeature('\u0031', '0', 45); //1 is a sword, 0 is a hedge
		addFeature('\u0032', '0', 3); //2 is help, 0 is a hedge
		addFeature('\u0033', '0', 5); //3 is a bomb, 0 is a hedge
		addFeature('\u0034', '0', 5); //4 is a hydrogen bomb, 0 is a hedge

		
		addFeature('\u0036', '0', 1); //6 is a Black Spider, 0 is a hedge
		addFeature('\u0037', '0', 1); //7 is a Blue Spider, 0 is a hedge
		//addFeature('\u0038', '0', 1); //8 is a Brown Spider, 0 is a hedge
		//addFeature('\u0039', '0', 1); //9 is a Green Spider, 0 is a hedge
		/*addFeature('\u003A', '0', 1); //: is a Grey Spider, 0 is a hedge
		 * 
		addFeature('\u003B', '0', 1); //; is a Orange Spider, 0 is a hedge
		addFeature('\u003C', '0', 1); //< is a Red Spider, 0 is a hedge
		addFeature('\u003D', '0', 1); //= is a Yellow Spider, 0 is a hedge */
	}
	
	private void init(){// Populates the map with hedges/walls 
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col] = new Node(row,col, 'w');
			}
		}
	}
	
	// Add enemies to the map.
	private void recursiveAddFeature(int feature_n, int number, int counter){
		
		if(counter < number){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());

			if(feature_n == 6)
				maze[row][col].setType('6');
			else if(feature_n == 7)
				maze[row][col].setType('7');
			else if(feature_n == 8)
				maze[row][col].setType('8');
			else if (feature_n == 9)
				maze[row][col].setType('9');
				
			Enemy spuderMan = new Enemy(col, row);
			spuderMan.setHealth(100);
		
			spuderMan.setDamage(1+(int)(Math.random() *((10 - 1) + 1)));
			
			enemyArray.add(spuderMan);
			maze[row][col].setEnemy(spuderMan);
			
			counter++;
			
			recursiveAddFeature(feature_n, number,counter);
		}
	}
	
	private void recursiveAddItem(int feature_n, char replace, int number, int counter) {

		// Generate a random location on the map.
		int row = (int) (maze.length * Math.random());
		int col = (int) (maze[0].length * Math.random());

		// Create the sword if not a hedge, is replace and 
		//the number created is less than needed.
		if(feature_n != 0 && replace == '0' && counter < number){
			if(feature_n == 1)//sword
				maze[row][col].setType('s');
			else if(feature_n == 2)//help
				maze[row][col].setType('h');
			else if(feature_n == 3)//bomb
				maze[row][col].setType('b');
			else if(feature_n == 4)//h bomb
				maze[row][col].setType('o');
			
			counter++;
			
			recursiveAddItem(feature_n, '0', number, counter);
		}
	}
	
	private void addFeature(char feature, char replace, int number){
		int feature_n = Character.getNumericValue(feature);
		
		if(feature_n > 5){// If its not an item
			if(feature_n != 0 && replace == '0'){ // If not a hedge & overwrite
				recursiveAddFeature(feature_n, number, 0);
			}			
		}else{// It is an item
			recursiveAddItem(feature_n, replace, number, 0);
		}
	}

	private void buildMaze(){ // Overwrite hedges with a path using empty spaces.
		for (int row = 1; row < maze.length - 1; row++){
			for (int col = 1; col < maze[row].length - 1; col++){
				int num = (int) (Math.random() * 10);
				
				if (num > 5 && col + 1 < maze[row].length - 1){
					maze[row][col + 1].setType('e'); //e = empty block
				}else{
					if (row + 1 < maze.length - 1)maze[row + 1][col].setType('e');
				}
				
			}
		}		
	}
	
	public Node[][] getMaze(){
		return this.maze;
	}
	
	public Node get(int row, int col){
		return this.maze[row][col];
	}
	
	public void set(int row, int col, char c){
		this.maze[row][col].setType(c);
	}
	
	public int size(){
		return this.maze.length;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				sb.append(maze[row][col].getType());
				if (col < maze[row].length - 1) sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}