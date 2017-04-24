package ie.gmit.sw.ai.traversor;

import java.util.Random;

import ie.gmit.sw.ai.Encounter;
import ie.gmit.sw.ai.Game;
import ie.gmit.sw.ai.GameView;
import ie.gmit.sw.ai.Maze;
import ie.gmit.sw.ai.characters.Enemy;
import ie.gmit.sw.ai.node.Node;

public class RandomWalk implements Traversator{
	
	Node target = null;
	
	public RandomWalk(Node target){
		this.target = target;
	}
	public void traverse(Node[][] maze, Enemy enemy) throws Exception {
		
		Node node = new Node(enemy.getPos_x(), enemy.getPos_y(), 'e');
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	   	
		int steps = (int) Math.pow(maze.length, 2) * 2;

		Node firstNode = node;
		boolean isKilled = false;
		Node prevMove = null;
		
		while(visitCount <= steps && node != null){		
			
			prevMove = node;
			
			target = new Node(Game.spartan.getPos_y(),Game.spartan.getPos_x(), 'p');
			
			node.setVisited(true);	
			visitCount++;
		
			// Has the player found the spider
			if(Game.found == "6")
			{
				System.out.println("You attacked");
				Game.found = "";
				
				// Clear the previous position of the spider.
		        clear(prevMove.getRow(), prevMove.getCol(), 'e');
				clear(node.getRow(), node.getCol(), 'e');
				
		        while(Game.spartan.isAlive() && !isKilled || enemy.isAlive() && !isKilled){
		        	int weaponDamage = encounterSpartan();
		        	isKilled = encounterSpider(enemy, weaponDamage);
		        	Thread.sleep(1000);
		        	
		        	if(Game.spartan.run())
		        	{// Run away 1-2 steps from the spider
		        		run(maze, enemy);
		        		Game.spartan.run(false);
		        		break;
		        	}
		        		
		        }
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
				
				System.out.println("They attacked");
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(node, time, visitCount);
		        
		        // Clear the previous position of the spider.
		        clear(prevMove.getRow(), prevMove.getCol(), 'e');
				clear(node.getRow(), node.getCol(), 'e');
				
		        while(Game.spartan.isAlive() && !isKilled || enemy.isAlive() && !isKilled){
		        	int weaponDamage = encounterSpartan();
		        	isKilled = encounterSpider(enemy, weaponDamage);
		        	Thread.sleep(1000);
		        	
		        	if(Game.spartan.run())
		        	{// Run away 1-2 steps from the spider
		        		run(maze, enemy);
		        		Game.spartan.run(false);
		        		break;
		        	}
		        		
		        }
			}
			
			if(Encounter.spiderDead == 0)
			{
				clear(node.getCol(), node.getRow(), 'p');
				clear(prevMove.getCol(), prevMove.getRow(), 'e');
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
	
	private int encounterSpartan() throws Exception{
		Game.maze.set(Game.spartan.getPos_y(), Game.spartan.getPos_x(), 'x');
        return Game.spartan.encounter(Maze.enemyArray.get(0), 0);
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
			Game.maze.set(Game.spartan.getPos_x()+runSteps, Game.spartan.getPos_y()+runSteps, 'p');
			Game.gameView.setCurrentPosition(Game.spartan.getPos_x()+runSteps,Game.spartan.getPos_y()+runSteps);
			Game.currentCol = Game.spartan.getPos_y()+runSteps;
    		Game.currentRow = Game.spartan.getPos_x()+runSteps;
		}else{
			run(maze, enemy);
		}
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