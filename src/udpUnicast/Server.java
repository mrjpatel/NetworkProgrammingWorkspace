package udpUnicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Server responsible for starting udp connection
 * @author japan
 *
 */
public class Server implements Runnable{

	private int port;
	private DatagramSocket socket;
	
	private Thread process;
	
	public Server(int port) {
		this.port = port;
	}
	
	public void initialise() {
		try {
			this.socket = new DatagramSocket();
			process = new Thread(this);
			process.start();
		} 
		catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void run() {
		System.out.println("Server started on port " + port);
		String message = "UDP Server. This is a message from Server.";
		byte[] data = new byte[1024];
		data = message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(data, data.length, new InetSocketAddress("localhost", this.port));
		try {
			 while (true) {
				 socket.send(sendPacket);
				 Thread.sleep(3*1000);
			 }
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server(62017);
		server.initialise();
	}
}
