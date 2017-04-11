package ie.gmit.sw.characters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ie.gmit.sw.node.*;

public class Player extends Character implements Inventory{
	
	private List<Item> inventory = new ArrayList<>();
	
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
	
}
