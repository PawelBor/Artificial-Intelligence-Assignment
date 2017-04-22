package ie.gmit.sw.ai;

import ie.gmit.sw.node.Node;
import ie.gmit.sw.traversor.AStarTraversator;
import ie.gmit.sw.traversor.BasicHillClimbingTraversator;
import ie.gmit.sw.traversor.DepthLimitedDFSTraversator;
import ie.gmit.sw.traversor.RandomWalk;
import ie.gmit.sw.traversor.Traversator;

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
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else if (algorithm.equals("aStar")){
	        System.out.println("T2");
	        target.setGoalNode(true);
	        Traversator aStar = new AStarTraversator(target);
	        Node blueSpider = new Node(Maze.enemyArray.get(1).getPos_y(), Maze.enemyArray.get(1).getPos_x(), '7');       
	        try {
				aStar.traverse(maze.getMaze(), Maze.enemyArray.get(1));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        /*
		}else if(algorithm.equals("depthFirst")){
			System.out.println("T3");
	        target.setGoalNode(true);
	        Traversator DepthLimited = new DepthLimitedDFSTraversator(999999);
	        Node brownSpider = new Node(Maze.enemyArray.get(2).getPos_y(), Maze.enemyArray.get(2).getPos_x(), 'm');       
	        try {
	        	DepthLimited.traverse(maze.getMaze(), brownSpider);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(algorithm.equals("hillClimbing")){
			System.out.println("T4");
	        target.setGoalNode(true);
	        Traversator hillClimbing = new BasicHillClimbingTraversator(target);
	        Node greenSpider = new Node(Maze.enemyArray.get(2).getPos_y(), Maze.enemyArray.get(2).getPos_x(), 'm');       
	        try {
	        	hillClimbing.traverse(maze.getMaze(), greenSpider);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}

}
