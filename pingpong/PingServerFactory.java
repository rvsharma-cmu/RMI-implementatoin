package pingpong;

import java.io.IOException;
import java.net.InetSocketAddress;
import rmi.*;

public class PingServerFactory {
	
	public static PingPongServer makePingServer() {
		
		PingPongServer pingServer = new PingPongServer();
		
		return pingServer;
	}
}