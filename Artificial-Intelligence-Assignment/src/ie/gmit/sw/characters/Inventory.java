package ie.gmit.sw.characters;

import ie.gmit.sw.node.Item;

public interface Inventory {
	public void addtoInventory(Item item);
	public void removeFromInventory(Item item);
	public int getInventorySize(Item item);
}