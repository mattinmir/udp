/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import common.MessageInfo;

/**
 * @author bandara
 *
 */
public class UDPServer {

	private DatagramSocket recvSoc;
	private int totalMessages = -1;
	private boolean[] receivedMessages;
	private boolean close;

	private void run() throws IOException {
		int				pacSize;
		byte[]			pacData;
		DatagramPacket 	pac;
		
		
		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever
		pacData = new byte[pacSize];
		pac = new DatagramPacket(pacData, pacData.length);
		recvSoc.setSoTimeout(10000);
		
		while(true)
		{
			try
			{
				recvSoc.receive(pac);
				pacSize = pac.getLength();
				pacData = new byte[pacSize];
			}
			catch(SocketTimeoutException e)
			{
				
			}
			
			if(totalMessages == -1)
			{
				
			}
		}
		
	}

	public void processMessage(String data) throws Exception {

		MessageInfo msg = null;

		// TO-DO: Use the data to construct a new MessageInfo object
		msg = new MessageInfo(data);
		// TO-DO: On receipt of first message, initialise the receive buffer
		receivedMessages = new boolean[];
		// TO-DO: Log receipt of the message
		
		// TO-DO: If this is the last expected message, then identify
		//        any missing messages

	}


	public UDPServer(int rp) throws SocketException {
		// TO-DO: Initialise UDP socket for receiving data
		this.recvSoc = new DatagramSocket(rp);
		
		
		// Done Initialisation
		System.out.println("UDPServer ready on port " + rp + "...");
	}

	public static void main(String args[]) throws IOException {
		int	recvPort;

		// Get the parameters from command line
		if (args.length != 1) {
			System.err.println("Arguments required: recv port");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		// TO-DO: Construct Server object and start it by calling run().
		UDPServer server = new UDPServer(recvPort);
		server.run();
	}

}