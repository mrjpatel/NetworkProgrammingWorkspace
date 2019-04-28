package multithreading;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Client Class that reads files asynchronously
 * and sends to Server
 * @author Japan Patel
 */
public class Client implements Runnable{
	
	private static ConcurrentLinkedQueue<Integer> messageQueue;
	private Socket clientSocket;
	
	private static String address;
	private static int port;
	
	/**
	 * Initialise Client
	 * @param args args[0] address args[1] port
	 */
	public static void main(String args[]) {
		
		Client client = new Client();
		address = args[0];
		port = Integer.parseInt(args[1]);
		client.createThreads(client);
    }
	
	/**
	 * Constructor. Initialises Concurrent message queue
	 */
	public Client() {
		messageQueue = new ConcurrentLinkedQueue<Integer>();
	}
	
	/**
	 * Creates File reader threds and Connection Threads
	 * @param client Client Instance
	 */
	private void createThreads(Client client) {
		//Starts File reading thread which is asynchronous
		FileReader reader = new FileReader(client);
		Thread fileReaderThread = new Thread(reader);
		fileReaderThread.start();
		System.out.println(fileReaderThread.getName() + ": Started file reading thread");
        
		//Starts the Connection to Server Thread
		Thread connectionThread = new Thread(client);
        connectionThread.start();
        System.out.println(connectionThread.getName() + ": Started Connection to Server Thread");
	}
	
	/**
	 * Queues Messages read from file.
	 * @param message Messsage in integer format
	 */
	public void queueMessages(int message) {
		messageQueue.add(message);
	}
	
	/**
	 * Connects to Server and sends data from message queue
	 */
	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName() + ": Attempting to connect to server on: " + address + ":" + port);
			clientSocket = new Socket(address, port);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream());
			System.out.println(Thread.currentThread().getName() + ": Successfully Connected to Server! on port: " + clientSocket.getLocalPort());
			System.out.println(Thread.currentThread().getName() + ": Started reading from queue...");
			
			while(!messageQueue.isEmpty()) {
				bufferedOutputStream.write(messageQueue.poll());
				bufferedOutputStream.flush();
			}
			System.out.print(Thread.currentThread().getName() + ": Finished sending data");
			
			clientSocket.close();
			System.out.print("Successfully closed socket.");
		} 
		catch (IOException e) {
			System.out.print("Cannot connect to Server " + e);
		}
	}
	
	/**
	 * File Reader Class.
	 * Reads file data and gives it to concurrent queue.
	 * @author Japan Patel
	 *
	 */
	private class FileReader implements Runnable{
		
		private static final String FILE_NAME = "100_megabyte_file.txt";
		private Client client;
		
		/**
		 * Constructor
		 * @param client Client Instance
		 */
		public FileReader(Client client) {
			this.client = client;
		}

		/**
		 * Reads file contents
		 */
		@Override
		public void run() {
			FileInputStream fileInputStream;
			try {
				fileInputStream = new FileInputStream(new File(FILE_NAME));
				InputStreamReader inputStream = new InputStreamReader(fileInputStream);
		        int bytesRead;
		        System.out.println(Thread.currentThread().getName() + ": Reading file...");
		        do {
					bytesRead = inputStream.read();
					if(bytesRead <= 0) {
						break;
					}
					client.queueMessages(bytesRead);
		        }
		        while (true);
		        System.out.println(Thread.currentThread().getName() + ": File contents read successfully, and added to the queue...");
				inputStream.close();
			}
			catch (FileNotFoundException e) {
				System.out.println("File cannot be found: " + e);
			}
			catch (IOException e) {
				System.out.println("Cannot send data to server: " + e);
			}
		}
	}
	
}
