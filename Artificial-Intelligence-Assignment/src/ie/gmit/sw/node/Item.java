package ie.gmit.sw.node;

public class Item {

	private NodeType type;
	private String name;
	private int power;
	
	public Item(String name, NodeType type){
		this.name = name;
		this.type = type;
	}
	
	public Item(String name, NodeType type, int power){
		this.name = name;
		this.type = type;
		this.power = power;
	}
	
	public Item(NodeType itemType){
		this.name = null;
		this.type = itemType;
	}
	
	// Accessor methods
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public NodeType getType(){
		return type;
	}
	
	public void setType(NodeType type){
		this.type = type;
	}
	
}
