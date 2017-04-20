package ie.gmit.sw.traversor;

import ie.gmit.sw.ai.GameView;
import ie.gmit.sw.node.Node;

public class RandomWalk implements Traversator{
	
	public void traverse(Node[][] maze, Node node) {
		
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	   	
		int steps = (int) Math.pow(maze.length, 2) * 2;
		System.out.println("Number of steps allowed: " + steps);
		
		boolean complete = false;
		
		while(visitCount <= steps && node != null){		
			
			Node prevMove = node;
			
			node.setVisited(true);	
			visitCount++;
		
			if (node.isGoalNode()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        //TraversatorStats.printStats(node, time, visitCount);
		        complete = true;
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
        	
		}// end while
		
		if (!complete) System.out.println("*** Out of steps....");
	}
	
	public void enemyMovement(Node node, Node prevMove)
    {
    	if (node.getRow() <= GameView.maze.size() - 1 && node.getCol() <= GameView.maze.size() - 1 && GameView.maze.get(node.getRow(), node.getCol()).getType() == 'e'){
    		GameView.maze.set(prevMove.getRow(), prevMove.getCol(), 'e');
    		GameView.maze.set(node.getRow(), node.getCol(), '6');		
		}
    }

}