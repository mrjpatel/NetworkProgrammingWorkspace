package clientServerSocket;

import java.io.IOException;
import java.net.Socket;

public class Client {

	private String address;
	
	private int port;
	
	private Socket clientSocket;
	
	public static void main(String args[]) 
    { 
        Client client = new Client("localhost", 61027); 
        client.connectToServer();
    } 
	
	public Client(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public void connectToServer(){
		try {
			System.out.println("Attempting to connect to server");
			clientSocket = new Socket(address, port);
			System.out.println("Server Connected!");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public void closeConnection() {
		System.out.println("Closing connection");
		try {
			clientSocket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
