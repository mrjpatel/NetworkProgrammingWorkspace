package clientServerSocket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
	
	private int port;
	
	private ServerSocket serverSocket;
	
	private Socket socket;
	
	private PrintWriter printWriter;
	
	public static void main(String[] args) {		
		Server server = new Server(Integer.parseInt(args[0]));
		server.connectToClient();
	}
	
	public Server(int port) { 
		this.port = port;
	}
	
	public void connectToClient() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server started on port: " + port);
			
			socket = serverSocket.accept();
			System.out.println("Connection Successful. Sending data...");
			
			printWriter = new PrintWriter(socket.getOutputStream(), true);
			printWriter.println(new Date().toString());
			System.out.println("Successfully sent data to client.");
			
			closeConnection();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		System.out.println("Closing connection now...");
		try {
			printWriter.close();
			socket.close();
			serverSocket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
