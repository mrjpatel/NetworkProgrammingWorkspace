package udpUnicast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Client responsible for connecting to Server.
 * @author japan
 *
 */
public class Client {

	/**
	 * Sends messages to Server
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader inFromUser = new BufferedReader(
				new InputStreamReader(System.in)); 
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("localhost");
			
			byte[] sendData = new byte[1024];
			String sentence = inFromUser.readLine();
			sendData = sentence.getBytes();
			
			DatagramPacket sendPacket = new DatagramPacket(
					sendData,sendData.length, IPAddress, 61027);
			
			int counter = 0;
			while(counter < 3) {
				clientSocket.send(sendPacket);
				counter++;
			}
			clientSocket.close();
			
		}
		catch (SocketException e) {
			System.out.println("Socket Exception");
		}
		catch (UnknownHostException e) {
			System.out.println("Unknown Exception");
		}
		catch (IOException e) {
			System.out.println("IO Exception");
		} 
		
	}
}
