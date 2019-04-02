package clientServerSocket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * A TCP server that runs on user desired port
 * When a client connects, it sends the
 * client the current date and time, then
 * closes the connection with that client.
 * 
 * @author Japan Patel
 */
public class Server {
	
	/**
	 * Port number
	 */
	private int port;
	
	/**
	 * Server Socket
	 */
	private ServerSocket serverSocket;
	
	/**
	 * Socket that accepts client connection
	 */
	private Socket socket;
	
	/**
	 * Writes data on a given stream
	 */
	private PrintWriter printWriter;
	
	/**
	 * Runs the server
	 * @param args The desired port number.
	 */
	public static void main(String[] args) {		
		Server server = new Server(Integer.parseInt(args[0]));
		server.connectToClient();
		server.closeConnection();
	}
	
	/**
	 * Initialises Port number
	 * @param port The desired port number
	 */
	public Server(int port) { 
		this.port = port;
	}
	
	/**
	 * Connects to the client
	 * and sends the date.
	 */
	public void connectToClient() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server started on port: " + port);
			
			socket = serverSocket.accept();
			System.out.println("Connection Successful. Sending data...");
			
			printWriter = new PrintWriter(socket.getOutputStream(), true);
			printWriter.println(new Date().toString());
			System.out.println("Successfully sent data to client.");
			
		} 
		catch (IOException e) {
			System.out.println("IO exception when connecting to client. " + e);
		}
	}
	
	/**
	 * closes the connection
	 */
	public void closeConnection() {
		System.out.println("Closing connection now...");
		try {
			printWriter.close();
			socket.close();
			serverSocket.close();
		} 
		catch (IOException e) {
			System.out.println("IO exception when closing the socket. " + e);
		}
	}
}
