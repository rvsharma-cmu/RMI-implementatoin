package rmi;

import java.io.IOException;
import java.net.InetSocketAddress;

public class PingPongServer implements PingPongInterface{
	
	public String ping(int idNumber) throws RMIException{
		
		return "Pong " + idNumber; 
	}
	
	public static void main(String[] args) {
		
		int portNumber = 52521; 
		System.out.println("Port number is " + portNumber);
		PingPongInterface remoteInterface = PingServerFactory.makePingServer();
		
		InetSocketAddress address = new InetSocketAddress(portNumber);
		
		Skeleton<PingPongInterface> skeletonServer = 
				new Skeleton<PingPongInterface>
		(PingPongInterface.class, remoteInterface, address); 
		
		try {
			skeletonServer.start();
		} catch (RMIException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
