package clientServerSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * A TCP server that connects to the
 * server and receives, data from the
 * server and closes the connection.
 * @author Japan Patel
 *
 */
public class Client {

	/**
	 * The server Address
	 */
	private String address;
	
	/**
	 * The port number that server is running
	 */
	private int port;
	
	/**
	 * Client Socket that connects to server
	 */
	private Socket clientSocket;
	
	/**
	 * Buffered Reader reads user input
	 */
	private BufferedReader bufferReader;
	
	/**
	 * Runs the server
	 * @param args None.
	 */
	public static void main(String args[]) 
    { 
        Client client = new Client("localhost", 61027); 
        client.getServerAddress();
        client.connectToServer();
        client.closeConnection();
    }
	
	/**
	 * Initialises the server address and port
	 * @param address The server address
	 * @param port The server port number
	 */
	public Client(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	/**
	 * Reads User input for a desired server address
	 * and the port numbers
	 */
	public void getServerAddress() {
		System.out.print("Please input the server address (e.g. 127.0.0.1:61027) - ");
		BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in)); 
		try {
			String userInput = reader.readLine();
			this.address = userInput.split(":")[0];
			this.port = Integer.parseInt(userInput.split(":")[1]);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Connects to the Server and
	 * Reads the data sent from the server.
	 */
	public void connectToServer(){
		try {
			System.out.println("Attempting to connect to server on: " + this.address + ":" + this.port);
			clientSocket = new Socket(address, port);
			System.out.println("Server Connected! Waiting for a message...");
			
			bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String message = bufferReader.readLine();
			System.out.println("Message received from server: " + message);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * Closes the connection
	 */
	public void closeConnection() {
		System.out.println("Closing connection...");
		try {
			bufferReader.close();
			clientSocket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
