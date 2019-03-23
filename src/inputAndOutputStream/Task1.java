/**
 * Input And Output Stream Example
 */
package inputAndOutputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A Class that converts User input with space characters 
 * with '_' characters.
 * <p>Warnings: <b>None</b></p>
 * @author Japan Patel
 */
public class Task1 {

	public static void main(String[] args) {
		String userInput = readingUserInput();
		System.out.println(userInput);
	}
	
	/**
	 * Reads User Input from keyboard using Input Stream.
	 * And converts space characters to '_' characters.
	 * @return updated user input.
	 */
	private static String readingUserInput() {
		InputStream inputStream = System.in;
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		
		int inputChars;
		StringBuffer stringBuffer = new StringBuffer();
		
		System.out.print("Please enter some text: ");
		try {
			while((inputChars = bufferedReader.read()) != -1) {
				char character = (char)inputChars;
				if(character != '\n') {
					if(character == ' ') {
						character = '_';
					}
					stringBuffer.append(character);
				}
				else {
					break;
				}
			}
			bufferedReader.close();
			inputStream.close();
			return stringBuffer.toString();
		} 
		catch (IOException e) {
			System.out.println("Unable to take input. Please try again later. " + e);
		}
		return null;
	}

}
