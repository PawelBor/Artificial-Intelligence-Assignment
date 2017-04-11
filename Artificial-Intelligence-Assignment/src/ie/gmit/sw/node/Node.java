package ie.gmit.sw.node;

import ie.gmit.sw.characters.*;

public class Node {
	private Player player;
	private Weapon weapon;
	private Enemy enemy;
	
	// Access methods
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
	public Weapon getWeapon() {
		return weapon;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	
	public Enemy getEnemy() {
		return enemy;
	}
	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}
}
