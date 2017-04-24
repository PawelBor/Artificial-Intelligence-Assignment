package ie.gmit.sw.ai.traversor;

import java.util.ArrayList;
import java.util.PriorityQueue;

import ie.gmit.sw.ai.Game;
import ie.gmit.sw.ai.GameView;
import ie.gmit.sw.ai.characters.Player;
import ie.gmit.sw.ai.node.Node;
public class PlayerTraversor{
	private Node goal;
	
	public PlayerTraversor(Node goal){
		this.goal = goal;
	}
	
	public void traverse(Node[][] maze, Player enemy) throws InterruptedException {
		
		Node node = new Node(enemy.getPos_y(),enemy.getPos_x(), 'p');
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		PriorityQueue<Node> open = new PriorityQueue<Node>(20, 
				(Node current, Node next) -> (current.getPathCost() + current.getHeuristic(goal)) - 
				(next.getPathCost() + next.getHeuristic(goal)));
		
		java.util.List<Node> closed = new ArrayList<Node>();
    	   	
		Node firstNode = node;
		Node prevMove = null;

		open.offer(node);
		node.setPathCost(0);		
		while(!open.isEmpty()){

			prevMove = node;
			node = open.poll();		
			closed.add(node);
			node.setVisited(true);	
			visitCount++;

			//Simulate processing each expanded node
			Thread.sleep(100);
			
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
			
			// Goal is found print out status.
			if (node.getCol() == goal.getCol() && node.getRow() == goal.getRow()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(node, time, visitCount);
		        
		        // Clear the previous position of the spider.
		        clear(prevMove.getRow(), prevMove.getCol(), 'e');
				clear(node.getRow(), node.getCol(), 'e');
				
				
	    		GameView.maze.set(goal.getRow(), goal.getCol(), 'k');
	    		Game.spartan.setPos(goal.getCol(), goal.getRow());
	    		Game.gameView.setCurrentRow(goal.getRow());
	    		Game.gameView.setCurrentCol(goal.getCol());
				
		        Game.gameOver("You Win!");
				break;
			}
			
			enemyMovement(node, prevMove, firstNode);
		
			
			
		}// end while
		
		clear(Game.spartan.getPos_y(), Game.spartan.getPos_x(), 'p');
	}

	private void clear(int pos_y, int pos_x, char x){
		GameView.maze.set(pos_y,pos_x, x);
	}

	
	public void enemyMovement(Node node, Node prevMove,Node firstNode)
    {
		if(node.getRow() <= GameView.maze.size() - 1 && node.getCol() <= GameView.maze.size() - 1 && GameView.maze.get(node.getRow(), node.getCol()).getType() == 'e' 
				||GameView.maze.get(node.getRow(), node.getCol()).getType() == 's' || GameView.maze.get(node.getRow(), node.getCol()).getType() == 'b'
				||GameView.maze.get(node.getRow(), node.getCol()).getType() == 'o' || GameView.maze.get(node.getRow(), node.getCol()).getType() == 'h' 
				|| GameView.maze.get(node.getRow(), node.getCol()).getType() == '6' || GameView.maze.get(node.getRow(), node.getCol()).getType() == '7'
				|| GameView.maze.get(node.getRow(), node.getCol()).getType() == '8' || GameView.maze.get(node.getRow(), node.getCol()).getType() == '9'){
			GameView.maze.set(prevMove.getRow(), prevMove.getCol(), GameView.maze.get(node.getRow(), node.getCol()).getType());
    		GameView.maze.set(node.getRow(), node.getCol(), 'p');
    		Game.spartan.setPos(node.getCol(), node.getRow());
    		Game.gameView.setCurrentRow(node.getRow());
    		Game.gameView.setCurrentCol(node.getCol());
		}
    }

}
