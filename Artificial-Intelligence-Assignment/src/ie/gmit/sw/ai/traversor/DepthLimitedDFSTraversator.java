package ie.gmit.sw.ai.traversor;

import java.util.Random;

import ie.gmit.sw.ai.Game;
import ie.gmit.sw.ai.GameView;
import ie.gmit.sw.ai.Maze;
import ie.gmit.sw.ai.Maze.*;
import ie.gmit.sw.ai.characters.Enemy;
import ie.gmit.sw.ai.node.Node;
public class DepthLimitedDFSTraversator implements Traversator{
	private Node[][] maze;
	private int limit;
	private boolean keepRunning = true;
	private long time = System.currentTimeMillis();
	private int visitCount = 0;
	private Node firstNode;
	
	public DepthLimitedDFSTraversator(int limit){
		this.limit = limit;
	}
	
	public void traverse(Node[][] maze, Enemy enemy) {
		
		Node node = new Node(enemy.getPos_x(),enemy.getPos_y(), 'e');
		
		this.firstNode = node;
		this.maze = maze;
		System.out.println("Search with limit " + limit);
		dfs(node, 1);
	}
	
	private void dfs(Node node, int depth){
		if (!keepRunning || depth > limit) return;

		Node prevMode = node;
		
		node.setVisited(true);	
		visitCount++;

		
		try { //Simulate processing each expanded node
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Node[] children = node.children(maze);
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].isVisited()){
				enemyMovement(children[i], prevMode, firstNode);
				children[i].setParent(node);
				dfs(children[i], depth + 1);
			}
		}
	}
	
	private int randomRunSteps(){
		Random r = new Random();
		int Low = 1;
		int High = 3;
		int result = r.nextInt(High-Low) + Low;
		return result;
	}
	
	private void run(Node[][] maze, Enemy enemy) throws Exception {

		clear(Game.spartan.getPos_y(),Game.spartan.getPos_x(), 'e');
		
		int runSteps = randomRunSteps();

		if(Game.maze.get((Game.spartan.getPos_x()+runSteps), (Game.spartan.getPos_y()+runSteps)).getType() == 'e')
		{
			Game.spartan.setPos(Game.spartan.getPos_y()+runSteps, Game.spartan.getPos_x()+runSteps);
			//Game.maze.set(Game.spartan.getPos_x()+runSteps, Game.spartan.getPos_y()+runSteps, 'p');
			Game.gameView.setCurrentPosition(Game.spartan.getPos_x()+runSteps,Game.spartan.getPos_y()+runSteps);
			Game.currentCol = Game.spartan.getPos_y()+runSteps;
    		Game.currentRow = Game.spartan.getPos_x()+runSteps;
		}else{
			run(maze, enemy);
		}
	}
	
	private boolean encounterSpider(Enemy enemy, int damage) {
		enemy.takeDamage(damage);
		if(!enemy.isAlive()){
			return true;
		}
		return false;
	}

	private void clear(int pos_y, int pos_x, char x){
		GameView.maze.set(pos_y,pos_x, x);
	}
	
	private int encounterSpartan() throws Exception{
		Game.maze.set(Game.spartan.getPos_y(), Game.spartan.getPos_x(), 'x');
        return Game.spartan.encounter(Maze.enemyArray.get(1) , 1);
	}
	
	public void enemyMovement(Node node, Node prevMove,Node firstNode)
    {
		if(node.getRow() <= GameView.maze.size() - 1 && node.getCol() <= GameView.maze.size() - 1 && GameView.maze.get(node.getRow(), node.getCol()).getType() == 'e' 
				||GameView.maze.get(node.getRow(), node.getCol()).getType() == 's' || GameView.maze.get(node.getRow(), node.getCol()).getType() == 'b'
				||GameView.maze.get(node.getRow(), node.getCol()).getType() == 'o' || GameView.maze.get(node.getRow(), node.getCol()).getType() == 'h' 
				|| GameView.maze.get(node.getRow(), node.getCol()).getType() == '6' || GameView.maze.get(node.getRow(), node.getCol()).getType() == '7'
				|| GameView.maze.get(node.getRow(), node.getCol()).getType() == '8' || GameView.maze.get(node.getRow(), node.getCol()).getType() == '9'){
			GameView.maze.set(prevMove.getRow(), prevMove.getCol(), GameView.maze.get(node.getRow(), node.getCol()).getType());
    		GameView.maze.set(node.getRow(), node.getCol(), '7');
		}
    }

}