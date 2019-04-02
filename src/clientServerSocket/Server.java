package clientServerSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private int port;
	
	private ServerSocket serverSocket;
	
	private Socket socket;
	
	public static void main(String[] args) {
		Server server = new Server(61027);
		server.connectToClient();
	}
	
	public Server(int port) { 
		this.port = port;
	}
	
	public void connectToClient() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server started");
			
			socket = serverSocket.accept();
			System.out.println("Connection Made.");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test() {
		System.out.println("Closing connection");
		try {
			socket.close();
			serverSocket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
