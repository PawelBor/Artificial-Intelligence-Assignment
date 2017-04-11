package ie.gmit.sw.node;

public class Item {

	private NodeType type;
	private String name;
	
	public Item(String name, NodeType type){
		this.name = name;
		this.type = type;
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
