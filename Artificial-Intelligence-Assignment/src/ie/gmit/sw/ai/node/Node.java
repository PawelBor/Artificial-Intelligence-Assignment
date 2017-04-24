package ie.gmit.sw.ai.node;

import java.awt.Color;

import ie.gmit.sw.ai.characters.*;

public class Node {
	public enum Direction {North, South, East, West};
	private Node parent;
	private Color color = Color.BLACK;
	private Direction[] paths = null;
	public boolean visited =  false;
	public boolean goal;
	private int row = -1;
	private int col = -1;
	private int distance;
	private Enemy enemy = null;
	char type;
	
	public Node(int row, int col, char type) {
		this.row = row;
		this.col = col;
		this.type = type;
	}

	public void setType(char type){
		this.type = type;
	}

	public char getType()
	{
		return this.type;
	}
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	
	public boolean hasDirection(Direction direction){	
		for (int i = 0; i < paths.length; i++) {
			if (paths[i] == direction) return true;
		}
		return false;
	}
	
	public Node[] children(Node[][] maze){		
		// Gets all four children available.
				Node[] children = new Node[4];
				if (col - 1 >= 0 && maze[row][col - 1].getType() != 'w' && maze[row][col - 1].getType() != 's')
					children[0] = maze[row][col - 1]; // A West edge
				if (col + 1 < maze[row].length && maze[row][col + 1].getType() != 'w' && maze[row][col + 1].getType() != 's')
					children[1] = maze[row][col + 1]; // An East Edge
				if (row - 1 >= 0 && maze[row - 1][col].getType() != 'w' && maze[row - 1][col].getType() != 's')
					children[2] = maze[row - 1][col]; // A North edge
				if (row + 1 < maze.length && maze[row + 1][col].getType() != 'w' && maze[row + 1][col].getType() != 's')
					children[3] = maze[row + 1][col]; // An South Edge

				int counter = 0;
				for (int i = 0; i < children.length; i++) { // goes through each child
					if (children[i] != null) // if child is not empty
						counter++; // increment counter
				}

				// this makes sure the array length is correct (2, 3 or 4)
				Node[] tmp = new Node[counter]; // array with length of 2, 3 or 4
				int index = 0;
				for (int i = 0; i < children.length; i++) {// goes through each child
					if (children[i] != null) { // copy all children into tmp
						tmp[index] = children[i];
						index++;
					}
				}

				return tmp;
	}

	public Node[] adjacentNodes(Node[][] maze){
		java.util.List<Node> adjacents = new java.util.ArrayList<Node>();
		
		if (row > 0) adjacents.add(maze[row - 1][col]); //Add North
		if (row < maze.length - 1) adjacents.add(maze[row + 1][col]); //Add South
		if (col > 0) adjacents.add(maze[row][col - 1]); //Add West
		if (col < maze[row].length - 1) adjacents.add(maze[row][col + 1]); //Add East
		
		return (Node[]) adjacents.toArray(new Node[adjacents.size()]);
	}
	
	public Direction[] getPaths() {
		return paths;
	}

	public void addPath(Direction direction) {
		int index = 0;
		if (paths == null){
			paths = new Direction[index + 1];		
		}else{
			index = paths.length;
			Direction[] temp = new Direction[index + 1];
			for (int i = 0; i < paths.length; i++) temp[i] = paths[i];
			paths = temp;
		}
		
		paths[index] = direction;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.color = Color.BLUE;
		this.visited = visited;
	}

	public boolean isGoalNode() {
		return goal;
	}

	public void setGoalNode(boolean goal) {
		this.goal = goal;
	}
	
	public int getHeuristic(Node goal){
		double x1 = this.col;
		double y1 = this.row;
		double x2 = goal.getCol();
		double y2 = goal.getRow();
		return (int) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}
	
	public int getPathCost() {
		return distance;
	}

	public void setPathCost(int distance) {
		this.distance = distance;
	}

	public String toString() {
		return "[" + row + "/" + col + "]";
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}
	
	public boolean isEnemy()
	{
		if(enemy == null)
			return false;
		
		return true;
	}
}