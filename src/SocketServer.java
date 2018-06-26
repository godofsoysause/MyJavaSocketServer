import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
	         
	         DataInputStream in = new DataInputStream(s.getInputStream());
	         while(true) {
             String string = in.readUTF();
             System.out.println("client:" + string);
             }
	         
	      } catch (IOException e) {
	         //e.printStackTrace();
	      }
		System.out.println("客户端：end");
	}

}
