package ie.gmit.sw.traversor;

import ie.gmit.sw.ai.Game;
import ie.gmit.sw.ai.GameView;
import ie.gmit.sw.ai.Maze;
import ie.gmit.sw.characters.Enemy;
import ie.gmit.sw.node.Node;

public class RandomWalk implements Traversator{
	
	Node target = null;
	
	public RandomWalk(Node target){
		this.target = target;
	}
	public void traverse(Node[][] maze, Enemy enemy) throws InterruptedException {
		
		Node node = new Node(enemy.getPos_x(), enemy.getPos_y(), 'e');
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	   	
		int steps = (int) Math.pow(maze.length, 2) * 2;
		System.out.println("Number of steps allowed: " + steps);
		
		boolean complete = false;
		boolean isKilled = false;
		
		while(visitCount <= steps && node != null){		
			
			Node prevMove = node;
			
			node.setVisited(true);	
			visitCount++;
		
			// Has the player found the spider
			if(Game.found)
			{
		        // Clear the previous position of the spider.
		        clear(prevMove.getRow(), prevMove.getCol(), 'e');
				clear(node.getRow(), node.getCol(), 'e');
				
		        while(Game.spartan.isAlive() && !isKilled || enemy.isAlive() && !isKilled){
		        	int weaponDamage = encounterSpartan();
		        	isKilled = encounterSpider(enemy, weaponDamage);
		        	Thread.sleep(1000);
		        }
	    		
				break;
			}
			
			//Simulate processing each expanded node
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			//Pick a random adjacent node
        	Node[] children = node.children(maze);
        	node = children[(int)(children.length * Math.random())];
        	
        	enemyMovement(node,prevMove);
        	
        	// Goal is found print out status.
			if (node.getCol() == target.getCol() && node.getRow() == target.getRow()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(node, time, visitCount);
		        
		        // Clear the previous position of the spider.
		        clear(prevMove.getRow(), prevMove.getCol(), 'e');
				clear(node.getRow(), node.getCol(), 'e');
				
		        while(Game.spartan.isAlive() && !isKilled || enemy.isAlive() && !isKilled){
		        	int weaponDamage = encounterSpartan();
		        	isKilled = encounterSpider(enemy, weaponDamage);
		        	Thread.sleep(1000);
		        }
	    		
				break;
			}
        	
		}// end while
		
		clear(Game.spartan.getPos_y(), Game.spartan.getPos_x(), 'p');
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
	
	private int encounterSpartan(){
		Game.maze.set(Game.spartan.getPos_y(), Game.spartan.getPos_x(), 'x');
        return Game.spartan.encounter(Maze.enemyArray.get(1));
	}
	
	public void enemyMovement(Node node, Node prevMove)
    {
    	if (node.getRow() <= GameView.maze.size() - 1 && node.getCol() <= GameView.maze.size() - 1 && GameView.maze.get(node.getRow(), node.getCol()).getType() == 'e'){
    		GameView.maze.set(prevMove.getRow(), prevMove.getCol(), 'e');
    		GameView.maze.set(node.getRow(), node.getCol(), '6');		
		}else if(GameView.maze.get(node.getRow(), node.getCol()).getType() == 's' || GameView.maze.get(node.getRow(), node.getCol()).getType() == 'b'
				||GameView.maze.get(node.getRow(), node.getCol()).getType() == 'o' || GameView.maze.get(node.getRow(), node.getCol()).getType() == 'h'){
			GameView.maze.set(prevMove.getRow(), prevMove.getCol(), GameView.maze.get(node.getRow(), node.getCol()).getType());
    		GameView.maze.set(node.getRow(), node.getCol(), '6');
		}
    }

}