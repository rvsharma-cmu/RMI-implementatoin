package rmi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner; 

public class PingPongServer implements PingPongInterface{
	
	public String ping(int idNumber) throws RMIException{
		
		return "Pong " + idNumber; 
	}
	
	public static void main(String[] args) {
		
		int portNumber; 
		
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter the port number for the server to bind: ");
		
		portNumber = 55790;
				//Integer.parseInt(reader.nextLine());
		PingPongInterface remoteInterface = PingServerFactory.makePingServer();
		InetSocketAddress address = new InetSocketAddress(portNumber);
		
		System.out.println("Server port number is " + portNumber);
		
		Skeleton<PingPongInterface> skeletonServer = 
				new Skeleton<PingPongInterface>
		(PingPongInterface.class, remoteInterface, address); 
		
		try {
			skeletonServer.start();
			System.out.println("Server has started");
		} catch (RMIException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
