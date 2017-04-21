package ie.gmit.sw.traversor;

import ie.gmit.sw.ai.GameView;
import ie.gmit.sw.node.Node;

public class BasicHillClimbingTraversator implements Traversator{
	private Node goal;
	
	public BasicHillClimbingTraversator(Node goal){
		this.goal = goal;
	}
	
	public void traverse(Node[][] maze, Node node) {
		Node firstNode = node;
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
    	Node next = null;
		while(node != null){
			Node prevMode = node;
			
			node.setVisited(true);	
			visitCount++;
			
			if (node.isGoalNode()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        //TraversatorStats.printStats(node, time, visitCount);
				break;
			}
			
			try { //Simulate processing each expanded node
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			

			Node[] children = node.children(maze);			
			int fnext = Integer.MAX_VALUE;			
			for (int i = 0; i < children.length; i++) {					
				if (children[i].getHeuristic(goal) < fnext){
					
					next = children[i];
					enemyMovement(children[i], prevMode, firstNode);
					
					fnext = next.getHeuristic(goal);	
				}
			}

				
			/*
			if (fnext >= node.getHeuristic(goal)){
				System.out.println("Cannot improve on current node " + node.toString() + " \nh(n)=" + node.getHeuristic(goal) + " = Local Optimum...");
				break;
			}
			*/
			node = next;	
			next = null;
		}
	}
	
	public void enemyMovement(Node node, Node prevMove,Node firstNode)
    {
		
		
    	if (node.getRow() <= GameView.maze.size() - 1 && node.getCol() <= GameView.maze.size() - 1 && GameView.maze.get(node.getRow(), node.getCol()).getType() == 'e'){
    	
    		GameView.maze.set(prevMove.getRow(), prevMove.getCol(), 'e');
    		GameView.maze.set(node.getRow(), node.getCol(), '9');	
		}else if(GameView.maze.get(node.getRow(), node.getCol()).getType() == 's' || GameView.maze.get(node.getRow(), node.getCol()).getType() == 'b'
				||GameView.maze.get(node.getRow(), node.getCol()).getType() == 'o' || GameView.maze.get(node.getRow(), node.getCol()).getType() == 'h' 
				|| GameView.maze.get(node.getRow(), node.getCol()).getType() == '6' || GameView.maze.get(node.getRow(), node.getCol()).getType() == '7'
				|| GameView.maze.get(node.getRow(), node.getCol()).getType() == '8' || GameView.maze.get(node.getRow(), node.getCol()).getType() == '9'){
			GameView.maze.set(prevMove.getRow(), prevMove.getCol(), GameView.maze.get(node.getRow(), node.getCol()).getType());
    		GameView.maze.set(node.getRow(), node.getCol(), '9');
		}
    }

}