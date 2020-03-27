
// package example;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;

// server is single-threaded, so at most one client can connect at a time.
// based on: https://introcs.cs.princeton.edu/java/84network/EchoServer.java.html
// see also: https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip

public class EchoServer {
	private static final StringBuffer messageBuffer = new StringBuffer();
	private static int port;
	private static String payload;

	public static void main(String[] args) throws Exception {

		try {
			port = Integer.parseInt(System.getenv("SERVICE_PORT"));
		} catch (NumberFormatException e) {
			port = 10000;
		}
		ServerSocket serverSocket = new ServerSocket(port);
		System.err.println("Started server on port " + port);
		boolean done = false;
		InputStream inputStream = null;
		BufferedReader reader = null;
		OutputStream outputStream = null;
		PrintWriter writer = null;
		Socket clientSocket = null;
		read: while (!done) {
			try {
				clientSocket = serverSocket.accept();
				System.err.println("Accepted connection from " + clientSocket.getInetAddress().getHostAddress());

				inputStream = clientSocket.getInputStream();

				reader = new BufferedReader(new InputStreamReader(inputStream));
				outputStream = clientSocket.getOutputStream();
				writer = new PrintWriter(outputStream, true);

				while ((payload = reader.readLine()) != null) {
					writer.println(payload);
					messageBuffer.append(payload);
				}
				String message = messageBuffer.toString();
				System.err.println("Received " + message);
				if (message.matches("QUIT")) {
					System.err.println("Stopping accepting the connections");
					done = true;
					break read;
				}
			} catch (java.net.SocketException e) {
				// Exception in thread "main" java.net.SocketException: Connection reset

			} finally {
				messageBuffer.delete(0, messageBuffer.length());
				outputStream.close();
				reader.close();
				inputStream.close();
				clientSocket.close();
			}
		}
		System.err.println("Closing server");
		serverSocket.close();
	}
}
