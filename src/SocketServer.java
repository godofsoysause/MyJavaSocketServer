import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	public static void main(String[] args) {
		try {
	         ServerSocket ss = new ServerSocket(8888);
	         System.out.println("启动服务器....");
	         System.out.println("服务器:"+ss.getInetAddress().getLocalHost()+"已ok");
	         Socket s = ss.accept();
	         System.out.println("客户端:"+s.getInetAddress().getLocalHost()+"已连接到服务器");
	         
	         /*DataInputStream in = new DataInputStream(s.getInputStream());
	         while(true) {
             String string = in.readUTF();
             System.out.println("client:" + string);
             }*/
	         DataInputStream in = new DataInputStream(s.getInputStream());
		     while(true) {
		        messageLength = in.read(buffer,0,buffer.length);
		        ReadMessage();
	         }
	         
	      } catch (IOException e) {
	         //e.printStackTrace();
	    	  System.out.println(e);
	      }
		catch (Exception e) {
	    	  System.out.println(e);
	      }
		System.out.println("客户端：end");
	}
	
	
	static byte[] buffer = new byte[2048];
	static int readOffset = 0;
	//信息总长度（可能不止一条信息）
	static int messageLength = 0;
	public static void ReadMessage() throws UnsupportedEncodingException {
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

}
