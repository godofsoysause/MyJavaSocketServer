import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {
	private String roomName;
	private String roomPassword = "";

	//房主
	private User roomMaster;
	private List<User> roomUser = Collections.synchronizedList(new ArrayList<User>());

	public Room(User roomMaster,String roomName,String password) {
		this.roomName = roomName;
		this.roomMaster = roomMaster;
		if(password!=null) {
			this.roomPassword = password;
		}
	}
	
	public void AddUser(User user) {
		roomUser.add(user);
	}
	public void RemoveUser(User user) {
		roomUser.remove(user);
	}
	
	public boolean ContainUser(String userName) {
		for(int i=0;i<roomUser.size();i++) {
			User u = roomUser.get(i);
			if(u.getUserName().equals(userName)) {
				return true;
			}
		}
		return false;
	}
	public String getAllUserName() {
		String allUserName = "";
		
		for(int i=0;i<roomUser.size();i++) {
			allUserName += roomUser.get(i).getUserName();
			allUserName += ",";
		}
		return allUserName;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomPassword() {
		return roomPassword;
	}

	public void setRoomPassword(String roomPassword) {
		this.roomPassword = roomPassword;
	}

	public List<User> getRoomUser() {
		return roomUser;
	}
	public User getRoomMaster() {
		return roomMaster;
	}

	public void setRoomMaster(User roomMaster) {
		this.roomMaster = roomMaster;
	}
}
