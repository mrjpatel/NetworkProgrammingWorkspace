package udpUnicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Server responsible for starting udp connection
 * @author japan
 *
 */
public class Server implements Runnable{

	private int port;
	private DatagramSocket socket;
	
	
	/**
	 * Constructor.
	 * @param port Port number for udp socket
	 */
	public Server(int port) {
		this.port = port;
	}
	

	/**
	 * Tread execution that receives message from Client
	 */
	@Override
	public void run() {
		try {
			socket = new DatagramSocket(this.port);
			System.out.println(Thread.currentThread().getName() + " Started a datagram socket on port: " + this.port);
			
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				socket.receive(receivePacket);
				PacketReceiver receiver = new PacketReceiver(receivePacket);
				Thread receiverThread = new Thread(receiver);
				receiverThread.start();
				
				socket.close();
				run();
			} catch (IOException e) {
				System.out.println("IO Exception");
			}
		} 
		catch (SocketException e) {
			System.out.println("Cannot create Datagram Socket.");
		}
		
	}

	/**
	 * Main Method
	 * @param args None.
	 */
	public static void main(String[] args) {
		
		int port = Integer.parseInt(args[0]);
        Server server = new Server(port);
        
        Thread connectionThread = new Thread(server);
    	connectionThread.start();
	}
	
	private class PacketReceiver implements Runnable{
		
		DatagramPacket receiverPacket;
		public PacketReceiver(DatagramPacket receiverPacket){
			this.receiverPacket = receiverPacket;
		}

		/**
		 * Receives Client message
		 */
		@Override
		public void run() {
			try {
				Thread.sleep(5*1000);
				String sentence = new String(receiverPacket.getData());
				System.out.println(Thread.currentThread().getName() + " Message received: " + sentence);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
	}
}
