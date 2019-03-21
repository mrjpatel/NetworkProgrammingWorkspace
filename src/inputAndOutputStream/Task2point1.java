package inputAndOutputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

public class Task2point1 {

	/**
	 * Read the file contents, calculates the checksum and reads the existing checksum
	 * @param args file names as arguments
	 */
	public static void main(String[] args) {
		String filename = "" + args[0];
		String checkSumFilename = "" + args[1];
		
		System.out.println(readFile(filename));
		calculateCheckSum(filename);
		System.out.println("Checksum value read from the file: " + readFile(checkSumFilename));
	}

	/**
	 * Reads the given filename and returns the contents of the file
	 * @param filename the name of the file to read
	 * @return the contents of the file
	 */
	private static String readFile(String filename) {
            FileInputStream fileInputStream;
			try {
				fileInputStream = new FileInputStream(filename);
				
				int character;
	            StringBuffer stringBuffer = new StringBuffer();
	            
	            while ((character = fileInputStream.read()) != -1) {
	                stringBuffer.append((char)character);
	             }
	            fileInputStream.close();
				return stringBuffer.toString();
				
			} 
			catch (FileNotFoundException e) {
				System.out.println("File not found exception. " + e);
			} 
			catch (IOException e) {
				System.out.println("IO Exception. " + e);
			}
			return null;
    }
	
	/**
	 * Calculates the checksum value based on the file contents
	 * @param filename the name of the file
	 */
	private static void calculateCheckSum(String filename) {
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(filename);
			CheckedInputStream checksum = new CheckedInputStream(fileInputStream, new Adler32());
	        byte[] buffer = new byte[1024];
	        
	        while(checksum.read(buffer, 0, buffer.length) >= 0) {
	        	
	        }
	        
	        System.out.println("\nCalculated Checksum: " + checksum.getChecksum().getValue());
	        checksum.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("File not found exception. " + e);
		}
		catch (IOException e) {
			System.out.println("IO Exception. " + e);
		}
	}
	
}
