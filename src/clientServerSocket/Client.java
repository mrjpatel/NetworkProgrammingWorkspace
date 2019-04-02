package clientServerSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

	private String address;
	
	private int port;
	
	private Socket clientSocket;
	
	private BufferedReader bufferReader;
	
	public static void main(String args[]) 
    { 
        Client client = new Client("localhost", 61027); 
        client.getServerAddress();
        client.connectToServer();
    }
	
	public Client(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
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
	
	public void connectToServer(){
		try {
			System.out.println("Attempting to connect to server on: " + this.address + ":" + this.port);
			clientSocket = new Socket(address, port);
			System.out.println("Server Connected! Waiting for a message...");
			
			bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String message = bufferReader.readLine();
			System.out.println("Message received from server: " + message);
			
			closeConnection();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }
	
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
