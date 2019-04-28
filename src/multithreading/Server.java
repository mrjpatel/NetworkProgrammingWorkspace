package multithreading;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
	
	private int port = 0;
	
	private ServerSocket serverSocket;

    public static void main(String [] args){
    	
    	int port = Integer.parseInt(args[0]);
        Server server = new Server(port);
    	
        Thread connectionThread = new Thread(server);
    	connectionThread.start();
    }
    
    public Server(int port) {
    	this.port = port;
    }
    
    private boolean isThreadInterrupted(){
		return Thread.currentThread().isInterrupted();
	}
    
    @Override
	public void run() {
    	try {
			serverSocket = new ServerSocket(port);
			System.out.println("Started a Server Socket on port: " + serverSocket.getLocalPort());
			
			while(!isThreadInterrupted()){
				Socket clientSocket = serverSocket.accept();
				new ServerFileReceiver(clientSocket);
			}
			serverSocket.close();
		}
    	catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    private class ServerFileReceiver{

    	private Socket clientSocket;
	    private InputStream inputStream = null;
	    private FileOutputStream fileOutputStream;
	    private BufferedOutputStream bufferedOutputStream = null;
	    private String ONE_HUNDRED_MEGABYTE_FILE = "100_megabyte_file_out_";
    	
		public ServerFileReceiver(Socket clientSocket) {
			this.clientSocket = clientSocket;
			this.readFileData();
//			System.out.println("Inside the file receiver: " + clientSocket.getLocalPort());
		}
		
		private void readFileData() {
			try {
				inputStream = clientSocket.getInputStream();
				byte [] buffer = new byte[1024];
				int bytesRead;
				
				File file = new File(ONE_HUNDRED_MEGABYTE_FILE + clientSocket.getPort() + ".txt");
				fileOutputStream = new FileOutputStream(file);
				bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
				
				do {
					bytesRead = inputStream.read(buffer, 0, buffer.length);
					if(bytesRead <= 0)
						break;
					bufferedOutputStream.write(buffer, 0, bytesRead);
					bufferedOutputStream.flush();
				} while (true);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					bufferedOutputStream.close();
					inputStream.close();
					clientSocket.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}	
    }
}
