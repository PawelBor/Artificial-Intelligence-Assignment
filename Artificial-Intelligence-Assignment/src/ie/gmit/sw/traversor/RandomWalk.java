package ie.gmit.sw.traversor;

import ie.gmit.sw.node.Node;

public class RandomWalk implements Traversator{
	
	public void traverse(Node[][] maze, Node node) {
		
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	   	
		int steps = (int) Math.pow(maze.length, 2) * 2;
		System.out.println("Number of steps allowed: " + steps);
		
		boolean complete = false;
		
		while(visitCount <= steps && node != null){		
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
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			//Pick a random adjacent node
        	Node[] children = node.children(maze);
        	node = children[(int)(children.length * Math.random())];	
        	
		}// end while
		
		if (!complete) System.out.println("*** Out of steps....");
	}

}