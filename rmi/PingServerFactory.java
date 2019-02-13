package rmi;

import java.io.IOException;
import java.net.InetSocketAddress;

public class PingServerFactory {
	
	public static PingPongServer makePingServer() {
		
		PingPongServer pingServer = new PingPongServer();
		
		return pingServer;
	}
}

