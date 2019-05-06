package udpUnicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Client responsible for connecting to Server.
 * @author japan
 *
 */
public class Client {

	public static void main(String[] args) {
		String message = "";
		
		try {
			DatagramSocket socket = new DatagramSocket(62017);
			while(true) {
				byte[] data = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(data, data.length);
				socket.receive(receivePacket);
				message = new String(receivePacket.getData());
				System.out.println(message.trim());
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
