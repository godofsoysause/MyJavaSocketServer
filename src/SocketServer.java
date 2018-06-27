import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer implements Runnable{
	private Thread t;
	private String threadName;
	private ArrayList<User> noRoomUser =  new ArrayList<User>();
	private ArrayList<Room> Rooms =  new ArrayList<Room>();
	
	public SocketServer() {
		this.threadName = "SocketServer";
	}
	public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	}
	public void run() {
		try {
	         ServerSocket ss = new ServerSocket(8888);
	         System.out.println("启动服务器....");
	         System.out.println("服务器:"+ss.getInetAddress().getLocalHost()+"已ok");
	         
	         while(true) {
	        	 Socket c = ss.accept();
	        	 System.out.println("客户端:"+c.getInetAddress().getLocalHost()+"已连接到服务器");
	        	 User user = new User(c,this);
	        	 user.start();
	        	 noRoomUser.add(user);
	         }
	         
	      } catch (IOException e) {
	         //e.printStackTrace();
	    	  System.out.println(e);
	      }
		catch (Exception e) {
	    	  System.out.println(e);
	      }
		System.out.println("服务器：GG");
	}
	public Room UserEnterRoom(User user,String roomName) {
		for(int i=0;i<Rooms.size();i++) {
			Room t_room = Rooms.get(i);
			if(t_room.getRoomName().equals(roomName)) {
				t_room.AddUser(user);
				noRoomUser.remove(user);
				return t_room; 
			}
		}
		return null;
	}
	public void UserLeaveRoom(User user) {
		noRoomUser.add(user);
	}
	
	//getter and setter
	public ArrayList<User> getNoRoomUser() {
		return noRoomUser;
	}
}
