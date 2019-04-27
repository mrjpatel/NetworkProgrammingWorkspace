package multithreading;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static final String ONE_HUNDRED_MEGABYTE_FILE = "100_megabyte_file_out.txt";

    public static void main(String [] args)
    {
        new Server();
    }

    public Server()
    {
        System.out.println("Server running");
        ServerSocket serverSocket = null;
        Socket connection = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            serverSocket = new ServerSocket(61174); // Binds to the server port

            while(true)
            {
                connection = serverSocket.accept(); // Create a connection between server and client
                System.out.println("Accepted Client");
                inputStream = connection.getInputStream(); // Access the read stream
                byte [] buffer = new byte[1024];
                int bytesRead;

                File file = new File(ONE_HUNDRED_MEGABYTE_FILE);
                fileOutputStream = new FileOutputStream(file);
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

                /* Reads and write the byte stream from the client onto the server disk */
                do
                {
                    bytesRead = inputStream.read(buffer, 0, buffer.length);

                    if(bytesRead <= 0)
                        break;

                    bufferedOutputStream.write(buffer, 0, bytesRead);
                    bufferedOutputStream.flush();
                }
                while (true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                bufferedOutputStream.close();
                inputStream.close();
                connection.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
