package ie.gmit.sw.ai.characters;

import ie.gmit.sw.ai.node.Item;

public interface Inventory {
	public void addtoInventory(Item item);
	public void removeFromInventory(Item item);
	public int getInventorySize(Item item);
}