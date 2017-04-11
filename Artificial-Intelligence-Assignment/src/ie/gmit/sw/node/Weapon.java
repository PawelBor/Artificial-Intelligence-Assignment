package ie.gmit.sw.node;

public class Weapon {
	private int weaponDamage;
	private int weaponCount;
	private NodeType weaponType;
	
	public Weapon(int weaponDamage, int weaponCount, NodeType weaponType){
		setWeaponCount(weaponCount);
		setWeaponDamage(weaponDamage);
		setWeaponType(weaponType);
	}
	
	//Accessor Methods
	
	//get set weaponDamage
	public int getWeaponDamage() {
		return weaponDamage;
	}

	public void setWeaponDamage(int weaponDamage) {
		this.weaponDamage = weaponDamage;
	}
	
	//get set weaponCount
	public int getWeaponCount() {
		return weaponCount;
	}

	public void setWeaponCount(int weaponCount) {
		this.weaponCount = weaponCount;
	}
	
	//get set weaponType
	public NodeType getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(NodeType weaponType) {
		this.weaponType = weaponType;
	}
}
