package ie.gmit.sw.traversor;

import java.util.ArrayList;
import java.util.PriorityQueue;

import ie.gmit.sw.ai.GameView;
import ie.gmit.sw.node.Node;
public class AStarTraversator implements Traversator{
	private Node goal;
	
	public AStarTraversator(Node goal){
		this.goal = goal;
	}
	
	public void traverse(Node[][] maze, Node node) {
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		PriorityQueue<Node> open = new PriorityQueue<Node>(20, 
				(Node current, Node next) -> (current.getPathCost() + current.getHeuristic(goal)) - 
				(next.getPathCost() + next.getHeuristic(goal)));
		
		java.util.List<Node> closed = new ArrayList<Node>();
    	   	
		Node firstNode = node;
		
		open.offer(node);
		node.setPathCost(0);		
		while(!open.isEmpty()){
			
			Node prevMove = node;
			node = open.poll();		
			closed.add(node);
			node.setVisited(true);	
			visitCount++;
	
			// Goal is found print out status.
			if (node.getCol() == goal.getCol() && node.getRow() == goal.getRow()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(node, time, visitCount);
				break;
			}
			
			try { //Simulate processing each expanded node
				Thread.sleep(1000);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			
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
			
		}// end while
	}
	
	public void enemyMovement(Node node, Node prevMove,Node firstNode)
    {
    	if (node.getRow() <= GameView.maze.size() - 1 && node.getCol() <= GameView.maze.size() - 1 && GameView.maze.get(node.getRow(), node.getCol()).getType() == 'e'){
    		GameView.maze.set(prevMove.getRow(), prevMove.getCol(), 'e');
    		GameView.maze.set(node.getRow(), node.getCol(), '7');	
    	
		}else if(GameView.maze.get(node.getRow(), node.getCol()).getType() == 's' || GameView.maze.get(node.getRow(), node.getCol()).getType() == 'b'
				||GameView.maze.get(node.getRow(), node.getCol()).getType() == 'o' || GameView.maze.get(node.getRow(), node.getCol()).getType() == 'h'){
			GameView.maze.set(prevMove.getRow(), prevMove.getCol(), GameView.maze.get(node.getRow(), node.getCol()).getType());
    		GameView.maze.set(node.getRow(), node.getCol(), '7');
		}
    }

}
