public class SendTool {
	public static void LoginReturn(User user,String succeedOrNot,String error) {
		if (error==null)error="";
		user.SendMessage(succeedOrNot, error, 1);
	}
	
	public static void BuildRoomReturn(User user,String succeedOrNot,String roomName) {
		user.SendMessage(succeedOrNot, roomName, 2);
	}
	
	public static void UserJoinRoomReturn(User user,String succeedOrNot,String userNameInRoom) {
		user.SendMessage(succeedOrNot, userNameInRoom, 3);
	}
	
	public static void UserSendMessageInRoomReturn(User user,String userName,String message) {
		user.SendMessage(userName, message, 4);
	}
	
	public static void UserLeaveRoomReturn(User user,String allNoLockRoomName,String allLockedRoomName) {
		user.SendMessage(allNoLockRoomName, allLockedRoomName,5);
	}
	public static void FindAllRoomsReturn(User user,String allNoLockRoomName,String allLockedRoomName) {
		user.SendMessage(allNoLockRoomName,allLockedRoomName, 7);
	}
}
