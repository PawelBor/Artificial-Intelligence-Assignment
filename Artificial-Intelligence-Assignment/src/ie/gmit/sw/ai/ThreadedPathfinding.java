package ie.gmit.sw.ai;

import ie.gmit.sw.ai.node.Node;
import ie.gmit.sw.ai.traversor.AStarTraversator;
import ie.gmit.sw.ai.traversor.RandomWalk;
import ie.gmit.sw.ai.traversor.Traversator;

public class ThreadedPathfinding implements Runnable{

	String algorithm;
	Node spider;
	Maze maze;
	Node target;
	
	public ThreadedPathfinding(String algorithm, Node spider, Maze maze, Node target) throws InterruptedException{
		this.algorithm = algorithm;
		this.spider = spider;
		this.maze = maze;
		this.target = target;
	}
	
	@Override
	public void run() {
		if(algorithm.equals("randomWalk")){
			System.out.println("T1");
			Traversator randomWalk = new RandomWalk(target);
			try {
				randomWalk.traverse(maze.getMaze(), Maze.enemyArray.get(0));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (algorithm.equals("aStar")){
	        System.out.println("T2");
	        target.setGoalNode(true);
	        Traversator aStar = new AStarTraversator(target);   
	        try {
				aStar.traverse(maze.getMaze(), Maze.enemyArray.get(1));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*	
		else if(algorithm.equals("depthFirst")){
			System.out.println("T3");
	        target.setGoalNode(true);
	        Traversator DepthLimited = new DepthLimitedDFSTraversator(999999);      
	        try {
	        	DepthLimited.traverse(maze.getMaze(), Maze.enemyArray.get(2));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
			else if(algorithm.equals("hillClimbing")){
		System.out.println("T4");
        target.setGoalNode(true);
        Traversator hillClimbing = new BasicHillClimbingTraversator(target);
        Node greenSpider = new Node(Maze.enemyArray.get(2).getPos_y(), Maze.enemyArray.get(2).getPos_x(), 'm');       
        try {
        	hillClimbing.traverse(maze.getMaze(), greenSpider);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
