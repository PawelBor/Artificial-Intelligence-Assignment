package ie.gmit.sw.ai;

import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

import ie.gmit.sw.characters.Enemy;
import ie.gmit.sw.node.Node;
import ie.gmit.sw.node.NodeType;
import ie.gmit.sw.node.Weapon;
import ie.gmit.sw.weapons.Sword;

public class Maze {
	private Node[][] maze;
	public static ArrayList<Enemy> enemyArray;
	
	public Maze(int dimension){
		maze = new Node[dimension][dimension];
		init(); // Fills the entire map with hedges.
		buildMaze(); // Overwrites hedges to spaces.
		enemyArray = new ArrayList<>();
		
		int featureNumber = (int)((dimension * dimension) * 0.01);
		addFeature('\u0031', '0', featureNumber); //1 is a sword, 0 is a hedge
		addFeature('\u0032', '0', featureNumber); //2 is help, 0 is a hedge
		addFeature('\u0033', '0', featureNumber); //3 is a bomb, 0 is a hedge
		addFeature('\u0034', '0', featureNumber); //4 is a hydrogen bomb, 0 is a hedge

		addFeature('\u0036', '0', featureNumber); //6 is a Black Spider, 0 is a hedge
		addFeature('\u0037', '0', featureNumber); //7 is a Blue Spider, 0 is a hedge
		addFeature('\u0038', '0', featureNumber); //8 is a Brown Spider, 0 is a hedge
		addFeature('\u0039', '0', featureNumber); //9 is a Green Spider, 0 is a hedge
		addFeature('\u003A', '0', featureNumber); //: is a Grey Spider, 0 is a hedge
		addFeature('\u003B', '0', featureNumber); //; is a Orange Spider, 0 is a hedge
		addFeature('\u003C', '0', featureNumber); //< is a Red Spider, 0 is a hedge
		addFeature('\u003D', '0', featureNumber); //= is a Yellow Spider, 0 is a hedge
	}
	
	private void init(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col] = new Node(row,col, 'w');
			}
		}
	}
	
	private void recursiveAddFeature(char feature){
		
		int feature_n = Character.getNumericValue(feature);
		
		int row = (int) (maze.length * Math.random());
		int col = (int) (maze[0].length * Math.random());

		if(feature_n != 0){// Create the enemy
			maze[row][col].setType('m');
			
			Enemy spuderMan = new Enemy(col, row);
			spuderMan.setHealth(120);	
			
			enemyArray.add(spuderMan);
			maze[row][col].setEnemy(spuderMan);
			
		}else{
			recursiveAddFeature(feature);
		}
	}
	
	private void recursiveAddItem(char feature) {
		
		int feature_n = Character.getNumericValue(feature);
		
		int row = (int) (maze.length * Math.random());
		int col = (int) (maze[0].length * Math.random());

		if(feature_n != 0){// Create the enemy
			maze[row][col].setType('s');
			
			Sword sword = new Sword(30, NodeType.Sword);
		}else{
			recursiveAddFeature(feature);
		}
		
	}
	
	private void addFeature(char feature, char replace, int number){
		int feature_n = Character.getNumericValue(feature);
		
		if(feature_n > 5)
		{
			if(feature_n != 0){
				recursiveAddFeature(feature);
			}			
		}else{
			recursiveAddItem(feature);
		}
	}

	private void buildMaze(){ 
		for (int row = 1; row < maze.length - 1; row++){
			for (int col = 1; col < maze[row].length - 1; col++){
				int num = (int) (Math.random() * 10);
				
				if (num > 5 && col + 1 < maze[row].length - 1){
					maze[row][col + 1].setType('e'); //e = SPACE
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
				sb.append(maze[row][col]);
				if (col < maze[row].length - 1) sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}