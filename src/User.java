import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class User implements Runnable{
	private String userName = "";
	private Thread t;
	private String threadName;
	private Socket client;
	private SocketServer server;
	DataOutputStream out;
	private Room room;
	public User(Socket client,SocketServer server) {
		this.client = client;
		this.server = server;
		threadName = "User";
		try {
			if(this.client!=null) {
				out = new DataOutputStream(client.getOutputStream());
			}
		}catch(Exception e) { }
		
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
			if(client!=null) {
		         DataInputStream in = new DataInputStream(client.getInputStream());
			     while(true) {
			        messageLength = in.read(buffer,0,buffer.length);
			        ReadMessage();
		         }
			}
	    }catch (IOException e) {
	        //e.printStackTrace();
	    	//System.out.println(e);
	    }catch (Exception e) {
		    //e.printStackTrace();
	    	//System.out.println(e);
		}
		if(room!=null) {
			room.RemoveUser(this);
		}else {
			server.getNoRoomUser().remove(this);
		}
		System.out.println("客户端： "+ client +" End");
	}
	
	private byte[] buffer = new byte[2048];
	private int readOffset = 0;
	//信息总长度（可能不止一条信息）
	int messageLength = 0;
	public void ReadMessage() throws UnsupportedEncodingException {
		readOffset = 0;
		byte[] b1_length = new byte[4];
		byte[] type_byte = new byte[4]; 
		int length = 0;
		int type = 0;
		while(messageLength>8) {
			System.arraycopy(buffer,readOffset,b1_length,0,b1_length.length);
			length = (int) ((b1_length[0] & 0xff) | ((b1_length[1] & 0xff) << 8) 
			| ((b1_length[2] & 0xff) << 16) | ((b1_length[3] & 0xff) << 24)); 
			if(length>messageLength-4||length<=4)return;

			readOffset += 4;
			System.arraycopy(buffer,readOffset,type_byte,0,type_byte.length);
			type = (int) ((type_byte[0] & 0xff) | ((type_byte[1] & 0xff) << 8) 
					| ((type_byte[2] & 0xff) << 16) | ((type_byte[3] & 0xff) << 24));
			readOffset += 4;
			
			//不同type后续处理不同
			switch(type) {
				case 0:
					byte[] message = new byte[length-4];
					System.arraycopy(buffer,readOffset,message,0,message.length);
					String s = new String(message,"UTF-8");
					System.out.println(s);
					readOffset += length-4;
					break;
			}
			messageLength -= length+4;
		}
	}

	public void SendMessage(String message,int Type) {
		try {
			BuildMessage(message,Type);
			out.write(buffer,0,messageLength);
		}catch(Exception e) { }
	}
	
	public void SendMessage(String message,String message2,int Type) {
		try {
			BuildMessage(message,message2,Type);
			out.write(buffer,0,messageLength);
		}catch(Exception e) { }
	}
	
	private byte[] buffer2 = new byte[2048];
	private int messageLength2 = 0;
	//type什么类型的消息，0是命令行输入  可以外部调用
	public int BuildMessage(String s,int type) {
		try {
		byte[] b1 = s.getBytes("UTF-8");
		if(b1.length > buffer2.length-40||b1.length==0)return 0;
		int i = 0;

		messageLength2 = b1.length+8;
		//开头4字节是整个有效信息的长度
		i = messageLength2-4;
		
		byte[] b1_length = TurnIntToBytes(i); 
		
		byte[] type_byte = TurnIntToBytes(type); 
		
		System.arraycopy(b1_length,0,buffer2,0,b1_length.length);
		System.arraycopy(type_byte,0,buffer2,b1_length.length,4);
		System.arraycopy(b1,0,buffer2,b1_length.length+4,b1.length);

		}catch(Exception e) {
			
		}
		return messageLength2;
	}
	
	//传两个值的BuildMessage
	public int BuildMessage(String s,String s2,int type) {
		try {
		byte[] b1 = s.getBytes("UTF-8");
		byte[] b2 = s2.getBytes("UTF-8");
		if((b2.length==0 && b1.length==0)||b2.length+b1.length>buffer2.length-40)return 0;
		int i = 0;
		int b1_l = 0;
		int b2_l = 0;
		
		messageLength2 = b1.length + b2.length + 16;
		b1_l = b1.length;
		b2_l = b2.length;
		i = messageLength2-4;
		
		byte[] b_length = TurnIntToBytes(i); 	
		byte[] type_byte = TurnIntToBytes(type); 
		byte[] b1_length = TurnIntToBytes(b1_l);
		byte[] b2_length = TurnIntToBytes(b2_l);
		
		System.arraycopy(b_length,0,buffer2,0,b_length.length);
		System.arraycopy(type_byte,0,buffer2,
				b_length.length,type_byte.length);
		System.arraycopy(b1_length,0,buffer2,
				b_length.length+type_byte.length,b1_length.length);
		System.arraycopy(b1,0,buffer2,
				b_length.length+type_byte.length+b1_length.length,b1.length);
		System.arraycopy(b2_length,0,buffer2,
				b_length.length+type_byte.length+b1_length.length+b1.length,b2_length.length);
		System.arraycopy(b2,0,buffer2,
				b_length.length+type_byte.length+b1_length.length+b1.length+b2_length.length,b2.length);
		
		}catch(Exception e) {
			
		}
		return messageLength2;
	}
	private byte[] TurnIntToBytes(int i) {
		byte[] b = new byte[4]; 
		b[0] = (byte) (i & 0xff); 
		b[1] = (byte) ((i >> 8) & 0xff); 
		b[2] = (byte) ((i >> 16) & 0xff); 
		b[3] = (byte) ((i >> 24) & 0xff); 
		return b;
	}
	
	//内部调用
	public void EnterRoom(String roomName) {
		this.room = server.UserEnterRoom(this, roomName);
	}
	private void LeaveRoom() {
		server.UserLeaveRoom(this);
		if(room!=null) {
			room.RemoveUser(this);
			this.room = null;
		}
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
