import java.util.ArrayList;

public class Room {
	private String roomName;
	//房主
	private User roomMaster;
	private ArrayList<User> roomUser =  new ArrayList<User>();
	
	public Room(User roomMaster,String roomName) {
		this.roomName = roomName;
		this.roomMaster = roomMaster;
	}
	
	public void AddUser(User user) {
		roomUser.add(user);
	}
	public void RemoveUser(User user) {
		roomUser.remove(user);
	}
	
	public boolean ContainUser(String userName) {
		return false;
	}
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
}
