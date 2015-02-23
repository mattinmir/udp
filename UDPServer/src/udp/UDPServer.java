/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package udp;

import java.io.IOException;
import java.io.PrintWriter;
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
public class UDPServer 
{

	private DatagramSocket recvSoc;
	private int totalMessages = -1;
	private boolean[] receivedMessages = null;
	
	private void run() throws Exception 
	{
		int				pacSize = 256;
		byte[]			pacData;
		DatagramPacket 	pac;
		
		
		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever
		pacData = new byte[pacSize];
		pac = new DatagramPacket(pacData, pacData.length);
		recvSoc.setSoTimeout(10000);
		
		boolean finished = false;
		while(!finished)
		{
			try
			{
				recvSoc.receive(pac);
				//System.out.println(new String(pac.getData(), "UTF-8"));
				processMessage(new String(pac.getData()).trim());
			}
			catch(SocketTimeoutException e)
			{
				finished = true;
				System.out.println("Timeout reached.");
				
				// Check lost messages after timeout, as last message may not be 
				// received, or may be received in wrong order
				int lostCount = 0;
				int receivedCount = 0;
				PrintWriter lost = new PrintWriter("UDP_lost.txt", 
						"UTF-8");
				
				for (int i = 0; i < receivedMessages.length; i++) 
				{
					if(receivedMessages[i] == false)
					{
						lostCount++;
						lost.println(i);
					}
					else
						receivedCount++;
				}
				lost.close();
				System.out.println("Messages Received: " + receivedCount);
				System.out.println("Messages Lost: " + lostCount);
				System.out.println("See UDP_lost.txt for lost messages.");
				
			}				
		}		
	}

	public void processMessage(String data) throws Exception  {

		MessageInfo msg = null;

		// TO-DO: Use the data to construct a new MessageInfo object
		msg = new MessageInfo(data);
		// TO-DO: On receipt of first message, initialise the receive buffer
		if (receivedMessages == null)
		{
			receivedMessages = new boolean[msg.totalMessages];
			totalMessages = msg.totalMessages;
			System.out.println("Processing messages...");
		}
		// TO-DO: Log receipt of the message
		receivedMessages[msg.messageNum] = true;
	}


	public UDPServer(int rp) throws SocketException {
		// TO-DO: Initialise UDP socket for receiving data
		this.recvSoc = new DatagramSocket(rp);
		
		
		// Done Initialisation
		System.out.println("UDPServer ready on port " + rp + "...");
	}

	public static void main(String args[]) throws Exception {
		int	recvPort;

		// Get the parameters from command line
		if (args.length != 1) {
			System.err.println("Arguments required: <recv port>");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		// TO-DO: Construct Server object and start it by calling run().
		UDPServer server = new UDPServer(recvPort);
		server.run();
	}

}
