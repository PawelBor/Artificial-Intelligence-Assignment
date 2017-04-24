package ie.gmit.sw.ai.characters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ie.gmit.sw.ai.Encounter;
import ie.gmit.sw.ai.node.*;

public class Player extends Character implements Inventory{
	
	private List<Item> inventory = new ArrayList<>();
	private int x_pos;
	private int y_pos;
	private boolean run = false;
	
	// Every Player can carry items so it should implement this
	
	@Override
	public void addtoInventory(Item item) {
		inventory.add(item);
	}

	@Override
	public void removeFromInventory(Item item) {
		// Iterate through the inventory and remove the item specified
		
		for(Iterator<Item> i = inventory.iterator(); i.hasNext();) 
		{
			if(i.equals(item)){
				inventory.remove(i);
			}
		}
	}
	
	public int getInventoryCount(NodeType x)
	{
		int counter = 0;
		
		for (Item item : inventory) {
			if(item.getType().equals(x)){
				counter++;
			}
		}
		
		return counter;
	}
	
	public void run(boolean run){
		this.run = run;
	}
	
	public boolean run(){
		return run;
	}
	
	public int encounter(Enemy enemy, int spiderIndex) throws Exception{
		System.out.println("Let's fight!");
		/*
		 * true = win,
		 * false = lose
		 */
		
		Encounter encounter = Encounter.getInstance();
		
		int swordCount = getInventoryCount(NodeType.Sword);
		int swordDamage = 0;
		
		if(swordCount != 0)
			swordDamage = new Random().nextInt(40)+20;
		
		return encounter.getScore(enemy.getDamage(), swordDamage, getHealth(), spiderIndex);
	}

	@Override
	public int getInventorySize(Item item) {
		return inventory.size();
	}
	
	public void setPos(int x, int y)
	{
		this.x_pos = x;
		this.y_pos = y;
	}
	
	public int getPos_x()
	{
		return this.x_pos;
	}
	
	public int getPos_y()
	{
		return this.y_pos;
	}
	
}
