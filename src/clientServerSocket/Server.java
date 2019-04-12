package clientServerSocket;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
	 * The output file
	 */
	private static final String OUTPUT_FILE = "outputFile.txt";
	
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
	 * Output stream for file
	 */
	private FileOutputStream fileOutputStream;
	
	/**
	 * Writes data to the file
	 */
	private BufferedOutputStream bufferedOutputStream;
	
	/**
	 * Reads buffer received from Client
	 */
	private InputStream inputStream = null;;
	
	/**
	 * Runs the server
	 * @param args The desired port number.
	 */
	public static void main(String[] args) {		
		Server server = new Server(Integer.parseInt(args[0]));
		server.connectToClient();
//		server.closeConnection();
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
			System.out.println("Connection Successful. Waiting for data...");
			inputStream = socket.getInputStream();
			buildFileFromStream();
		} 
		catch (IOException e) {
			System.out.println("IO exception when connecting to client. " + e);
		}
	}
	
	/**
	 * Reads and write the byte stream from 
	 * the client onto the server disk
	 */
	private void buildFileFromStream() {
		File file = new File(OUTPUT_FILE);
		try {
			
			fileOutputStream = new FileOutputStream(file);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			byte [] buffer = new byte[1024];
            int bytesRead;
			do {
				bytesRead = inputStream.read(buffer, 0, buffer.length);
				if(bytesRead <= 0) {
					break;
				}
                bufferedOutputStream.write(buffer, 0, bytesRead);
                bufferedOutputStream.flush();
            }
            while (true);
			System.out.println("File contents successfully and saved to: " + OUTPUT_FILE);
			
			bufferedOutputStream.close();
			inputStream.close();
			System.out.println("CLosed the stream");
			closeConnection();
		}
		catch (IOException e) {
			System.out.println("Cannot create the file." + e);
			closeConnection();
		}
	}
	
	/**
	 * closes the connection
	 */
	public void closeConnection() {
		System.out.println("Closing connection now...");
		try {
			socket.close();
			serverSocket.close();
		} 
		catch (IOException e) {
			System.out.println("IO exception when closing the socket. " + e);
		}
	}
}
