package inputOutputCheckSum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

public class CheckSumCalculation {
	
	public static void main(String[] args) {
		String dataFilename = args[0];
		String checksumFilename = args[1];
		calculateChecksum(dataFilename, checksumFilename);
	}

	/**
	 * Takes User Input and writes to a file and calculates
	 * checksum for that input and writes to another file
	 * @param dataFilename the file name or full file path for data file
	 * @param checksumFilename the filename or full file path for checksum file
	 */
	private static void calculateChecksum(String dataFilename, String checksumFilename) {
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(new File(dataFilename));
			CheckedOutputStream checkedOutputStream = new CheckedOutputStream(fileOutputStream, new Adler32());
			FileOutputStream checksumFileOutputStream = new FileOutputStream(new File(checksumFilename));
			
			System.out.print("Please enter some text and I will write to file (Use 'x' to terminate): ");
			String completeInput = "";
			String currentLineInput = "";
				
			do{
				currentLineInput = bufferedReader.readLine();
				if(!currentLineInput.equals("x")) {
					completeInput = completeInput + currentLineInput + "\n";
				}
			} while(!(currentLineInput.equalsIgnoreCase("X")));
			
			checkedOutputStream.write(completeInput.getBytes());
			checkedOutputStream.flush();
			
			long checksum = checkedOutputStream.getChecksum().getValue();
			System.out.println("Checksum calculated: " + checksum);
			checksumFileOutputStream.write(String.valueOf(checksum).getBytes());
			
			checksumFileOutputStream.close();
			checkedOutputStream.close();
				
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find the file to write. Try again later " + e);
		}catch (IOException e) {
			System.out.println("Cannot write the input to file at this time. Try again later " + e);
		}
		
	}
}
