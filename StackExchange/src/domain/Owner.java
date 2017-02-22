package domain;

public class Owner {
	
	private int reputation;
	private int user_id;
	private String user_type;
	
	public int getReputation() {
		return reputation;
	}
	public void setReputation(int reputation) {
		this.reputation = reputation;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	@Override
	public String toString() {
		return "Owner [reputation=" + reputation + ",\n user_id=" + user_id + ",\n user_type=" + user_type + "]";
	}
	
	
	

}
