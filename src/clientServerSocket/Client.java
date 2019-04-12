package clientServerSocket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	 * Input stream to read file
	 */
	private InputStreamReader inputStream;
	
	/**
	 * Output stream to send file contents
	 */
	private BufferedOutputStream bufferedOutputStream;
	
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
		System.out.print("Please input the server address (e.g. 127.0.0.1:61207) - ");
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
	 * Reads User input for a desired server address
	 * and the port numbers
	 */
	public String getFilename() {
		System.out.print("Please enter name of the file that you want to receive - ");
		BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in)); 
		try {
			String userInput = reader.readLine();
			return userInput;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Connects to the Server and
	 * Reads the data sent from the server.
	 */
	private void connectToServer(){
		try {
			System.out.println("Attempting to connect to server on: " + this.address + ":" + this.port);
			clientSocket = new Socket(address, port);
			System.out.println("Server Connected! Waiting for a message...");
			bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream());
			
			readAndSendFile();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * Reads files and sends to server
	 */
	private void readAndSendFile() {
		System.out.println("Reading file...");
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(new File("readingFile.txt"));
			inputStream = new InputStreamReader(fileInputStream);
	        int bytesRead;
	        System.out.println("About to start sending file...");
			do {
				bytesRead = inputStream.read();
				if(bytesRead <= 0) {
					break;
				}
				bufferedOutputStream.write(bytesRead);
				bufferedOutputStream.flush();
	        }
	        while (true);
			System.out.println("File contents sent successfully...");
			inputStream.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("File cannot be found: " + e);
		}
		catch (IOException e) {
			System.out.println("Cannot send data to server: " + e);
		}
		
	}
	
	/**
	 * Closes the connection
	 */
	public void closeConnection() {
		System.out.println("Closing connection...");
		try {
			clientSocket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
