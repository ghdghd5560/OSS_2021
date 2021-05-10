import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DataInqury {
	ServerSocket ss = null;
	static ArrayList<Client> clients = new ArrayList<Client>();
	
	public static void main(String args[]) {
		
		DataInqury server = new DataInqury();
		
		try {
			
			server.ss = new ServerSocket(55555);
			System.out.println("Server > Server Socket is Created...");
			
			
			while(true) {
				Socket socket = server.ss.accept();
				Client c = new Client(socket);
				clients.add(c);
				c.start();
			
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
	
class Client extends Thread{
	Socket socket;
	
	Client(Socket _s){
		this.socket = _s;
	}
	Connection con = null;
	Statement stmt = null;
	String url= "jdbc:mysql://localhost/mydb?serverTimezone=Asia/Seoul";
	String user= "root";
	String passwd = "2more4me";
	
	
	String id;
	String nickName;
	int age;
	String sex;
	String area;
	String hobby;
	String sql = "SELECT * FROM user";
	public void run() {
		
		String uid;			//유저 아이디
		String compared;	//비교할 대상
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, passwd);
			stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
		
			id = rs.getString("id");
			nickName = rs.getString("nickName");
			age = rs.getInt("age");
			sex = rs.getString("sex");
			area = rs.getString("area");
			hobby = rs.getString("hobby");
			
			
			
			OutputStream out = socket.getOutputStream();
			DataOutputStream dout = new DataOutputStream(out);
			InputStream in = socket.getInputStream();
			DataInputStream din = new DataInputStream(in);
			
			String msg = din.readUTF();
			Thread.sleep(100);
			StringTokenizer stk = new StringTokenizer(msg, "///");
			uid = stk.nextToken();
			compared = stk.nextToken();
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
