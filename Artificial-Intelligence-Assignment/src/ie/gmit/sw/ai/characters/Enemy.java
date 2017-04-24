package ie.gmit.sw.ai.characters;

public class Enemy extends Character{

	private int x_pos;
	private int y_pos;
	private int damage;
	
	public Enemy(int x, int y){
		this.x_pos = x;
		this.y_pos = y;
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

	public int getDamage(){
		return this.damage;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
	
}