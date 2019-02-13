package rmi;
import java.net.*;
import java.util.Scanner;

public class PingPongClient {

	public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter the IP address of the server");
		String IP = reader.nextLine(); 
		String result = "";
		
		System.out.println("Enter the port of the server");
		
		int port = Integer.parseInt(reader.nextLine()); 
		
		InetSocketAddress address = new InetSocketAddress(IP, port);
		
		PingPongInterface server = Stub.create(PingPongInterface.class,
											   address);
		
		System.out.println("Enter the idNumber");
		int i = Integer.parseInt(reader.nextLine());
		
		if(server != null)
			
			try {
				result = server.ping(i);
			} catch (RMIException e) {
				e.printStackTrace();
			}
			System.out.println("Output: " + result);
		
	}

}
