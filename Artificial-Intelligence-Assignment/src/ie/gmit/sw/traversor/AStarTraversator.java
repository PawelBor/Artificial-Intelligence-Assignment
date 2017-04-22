package ie.gmit.sw.traversor;

import java.util.ArrayList;
import java.util.PriorityQueue;

import ie.gmit.sw.ai.Encounter;
import ie.gmit.sw.ai.Game;
import ie.gmit.sw.ai.GameView;
import ie.gmit.sw.ai.Maze;
import ie.gmit.sw.characters.Enemy;
import ie.gmit.sw.node.Node;
public class AStarTraversator implements Traversator{
	private Node goal;
	
	public AStarTraversator(Node goal){
		this.goal = goal;
	}
	
	public void traverse(Node[][] maze, Enemy enemy) throws InterruptedException {
		
		Node node = new Node(enemy.getPos_x(),enemy.getPos_y(), 'e');
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		PriorityQueue<Node> open = new PriorityQueue<Node>(20, 
				(Node current, Node next) -> (current.getPathCost() + current.getHeuristic(goal)) - 
				(next.getPathCost() + next.getHeuristic(goal)));
		
		java.util.List<Node> closed = new ArrayList<Node>();
    	   	
		Node firstNode = node;
		Node prevMove = null;

		boolean isKilled = false;
		
		open.offer(node);
		node.setPathCost(0);		
		while(!open.isEmpty()){
			
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

			// Keep the goal node updated from the game class.
			goal = new Node(Game.spartan.getPos_y(),Game.spartan.getPos_x(), 'p');
			
			prevMove = node;
			node = open.poll();		
			closed.add(node);
			node.setVisited(true);	
			visitCount++;

			//Simulate processing each expanded node
			Thread.sleep(1000);
			
			// Process adjacent nodes
			Node[] children = node.children(maze);
			
			for (int i = 0; i < children.length; i++) {
				Node child = children[i];
				int score = node.getPathCost() + 1 + child.getHeuristic(goal);
				int existing = child.getPathCost() + child.getHeuristic(goal);
				
				if ((open.contains(child) || closed.contains(child)) && existing < score){
					continue;
				}else{
					open.remove(child);
					closed.remove(child);
					child.setParent(node);
					child.setPathCost(node.getPathCost() + 1);
					open.add(child);
				}
				
			}//end for	
			
			enemyMovement(node, prevMove, firstNode);
			
			// Goal is found print out status.
			if (node.getCol() == goal.getCol() && node.getRow() == goal.getRow()){
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
