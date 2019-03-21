package inputAndOutputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

public class Task2 {
	
	private static String FILENAME = "src/inputAndOutputStream/";
	
	private static String CHECKSUM_FILENAME = "src/inputAndOutputStream/";
	
	private static long checksum = 0;
	
	private static String userInput = "";
	
	/**
	 * Takes User input and write the contents to a file
	 * @param args filenames
	 */
	public static void main(String[] args) {
		CHECKSUM_FILENAME = CHECKSUM_FILENAME + args[0];
		FILENAME = FILENAME + args[1];
		
		InputStream inputStream = System.in;
		processInputAndCalculateChecksum(inputStream);
		
		writeToFile(userInput, FILENAME); //writes user input to data file		
	}
	
	/**
	 * Process User Input and write the checksum to the file
	 * @param inputStream the input stream to read keyboard input
	 */
	private static void processInputAndCalculateChecksum(InputStream inputStream) {
		CheckedInputStream checkedInputStream = null;        
		
        try {
            checkedInputStream = new CheckedInputStream(inputStream, new Adler32());
            int inputChars;
    		StringBuffer stringBuffer = new StringBuffer();
    		System.out.print("Please enter some text and I will write to file (Use 'x' to terminate): ");
    		
            while((inputChars = checkedInputStream.read()) != -1) {
				char character = (char)inputChars;

				if(character == 'x') {
					checkedInputStream.close();
					inputStream.close();
					userInput = stringBuffer.toString();
					System.out.println("Checksum "+ checksum);
					writeToFile(String.valueOf(checksum), CHECKSUM_FILENAME);
				}
				stringBuffer.append(character);
				checksum = checkedInputStream.getChecksum().getValue();
			}
            
        }
        catch (IOException e) {
            checksum = 0;
        }
	}

	/**
	 * Write User input to a file
	 * @param fileContents the user input
	 * @param filename the name of the file with filepath
	 */
	private static void writeToFile(String fileContents, String filename) {
		OutputStream outputStream;
		
		try {
			outputStream = new FileOutputStream(filename);
			byte[] strToBytes = fileContents.getBytes();
		    outputStream.write(strToBytes);
		    outputStream.close();
		} 
		catch (FileNotFoundException e) {
			
			System.out.println("Cannot find the file to write. Try again later " + e);
		} 
		catch (IOException e) {
			
			System.out.println("Cannot write the input to file at this time. Try again later " + e);
		}
	}
}
