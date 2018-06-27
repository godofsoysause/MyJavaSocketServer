import java.util.ArrayList;

public class Room {
	private String roomName;
	private ArrayList<User> roomUser =  new ArrayList<User>();
	
	public Room() {
		// TODO Auto-generated constructor stub
	}
	
	public void AddUser(User user) {
		
	}
	public void RemoveUser(User user) {
		
	}
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
}
