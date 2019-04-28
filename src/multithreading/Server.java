package multithreading;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A server that accepts multiple clients simultaneously
 * and stores data to a file
 * @author Japan Patel
 */
public class Server implements Runnable{
	
	private int port = 0;
	private ServerSocket serverSocket;

	/**
	 * Intialise Server object and starts 
	 * server socket thread
	 * @param args takes int for port
	 */
    public static void main(String [] args){
    	int port = Integer.parseInt(args[0]);
        Server server = new Server(port);
    	
        Thread connectionThread = new Thread(server);
    	connectionThread.start();
    }
    
    /**
     * Server Constructor
     * @param port port to start server on
     */
    public Server(int port) {
    	this.port = port;
    }
    
    /**
     * Checks if the current thread is Interrupted.
     * @return true if the thread is interrupted
     */
    private boolean isThreadInterrupted(){
		return Thread.currentThread().isInterrupted();
	}
    
    /**
     * Starts Server and once client accepts the connection
     * It gives the client socket to the data receiver
     */
    @Override
	public void run() {
    	try {
			serverSocket = new ServerSocket(port);
			System.out.println("Started a Server Socket on port: " + serverSocket.getLocalPort());
			
			while(!isThreadInterrupted()){
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client connected from: " + clientSocket.getInetAddress() + ": " + clientSocket.getPort());
				ServerFileReceiver fileReceiver = new ServerFileReceiver(clientSocket);
				Thread fileReceiverThread = new Thread(fileReceiver);
				fileReceiverThread.start();
			}
			serverSocket.close();
		}
    	catch (IOException e) {
    		System.out.println("Cannot create a server socket socket on: " + port + "\n" + e);
		}
	}
    
    /**
     * A file receiver Class that receives data from client
     * and stores it in file.
     * @author Japan Patel
     */
    private class ServerFileReceiver implements Runnable{

    	private Socket clientSocket;
	    private InputStream inputStream = null;
	    private FileOutputStream fileOutputStream;
	    private BufferedOutputStream bufferedOutputStream = null;
	    private String ONE_HUNDRED_MEGABYTE_FILE = "100_megabyte_file_out_";
    	
	    /**
	     * Constructor for the File Receiver
	     * @param clientSocket Client Socket that the server has bind to.
	     */
		public ServerFileReceiver(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		/**
		 * Generates a file based on client data.
		 */
		private void readFileData() {
			try {
				inputStream = clientSocket.getInputStream();
				byte [] buffer = new byte[1024];
				int bytesRead;
				
				File file = new File(ONE_HUNDRED_MEGABYTE_FILE + clientSocket.getPort() + ".txt");
				fileOutputStream = new FileOutputStream(file);
				bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
				System.out.println("Receiving file contents from client: " + clientSocket.getInetAddress() + ": " + clientSocket.getPort());
				do {
					bytesRead = inputStream.read(buffer, 0, buffer.length);
					if(bytesRead <= 0)
						break;
					bufferedOutputStream.write(buffer, 0, bytesRead);
					bufferedOutputStream.flush();
				} while (true);
				System.out.println("Finished writing to a file for: " + clientSocket.getInetAddress() + ": " + clientSocket.getPort());
				System.out.println("Exported file: " + file.getName());
			}
			catch (IOException e) {
				System.out.println("Client socket closed on port: " + clientSocket.getPort());
				System.out.println("Closing the port...");
				
			}
			finally {
				try {
					bufferedOutputStream.close();
					inputStream.close();
					clientSocket.close();
					System.out.println("Successfully closed the socket on port." + clientSocket.getPort());
				}
				catch (IOException e) {
					System.out.println("Cannot close stream and the cient socket: " + e);
				}
			}
		}

		@Override
		public void run() {
			readFileData();
		}	
    }
}
