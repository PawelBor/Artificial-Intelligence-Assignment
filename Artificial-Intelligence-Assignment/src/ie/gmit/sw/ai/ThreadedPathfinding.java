package ie.gmit.sw.ai;

import ie.gmit.sw.node.Node;
import ie.gmit.sw.traversor.AStarTraversator;
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
			Traversator randomWalk = new RandomWalk();
			try {
				randomWalk.traverse(maze.getMaze(), spider);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (algorithm.equals("aStar")){
	        System.out.println("T2");
	        target.setGoalNode(true);
	        Traversator aStar = new AStarTraversator(target);
	        Node blueSpider = new Node(Maze.enemyArray.get(1).getPos_y(), Maze.enemyArray.get(1).getPos_x(), 'm');       
	        try {
				aStar.traverse(maze.getMaze(), blueSpider);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
