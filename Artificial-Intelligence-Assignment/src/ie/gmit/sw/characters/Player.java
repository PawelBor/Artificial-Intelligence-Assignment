package ie.gmit.sw.characters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ie.gmit.sw.node.*;

public class Player extends Character implements Inventory{
	
	private List<Item> inventory = new ArrayList<>();
	private int x_pos;
	private int y_pos;
	
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
