package multithreading;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client implements Runnable{
	
	private static ConcurrentLinkedQueue<Integer> messageQueue;
	private Socket clientSocket;
	
	
	public static void main(String args[]) { 
		Client client = new Client();
		client.createThreads(client);
    }
	
	public Client() {
		messageQueue = new ConcurrentLinkedQueue<Integer>();
	}
	
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
	
	public void queueMessages(int message) {
		messageQueue.add(message);
	}
	
	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName() + ": Attempting to connect to server on: " + "localhost" + ":" + "61207");
			clientSocket = new Socket("localhost", 61207);
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
	
	private class FileReader implements Runnable{
		
		private static final String FILE_NAME = "100_megabyte_file.txt";
		private Client client;
		
		public FileReader(Client client) {
			this.client = client;
		}

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
