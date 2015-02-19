/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import common.MessageInfo;

/**
 * @author bandara
 *
 */
public class UDPClient {

	private DatagramSocket sendSoc;

	public static void main(String[] args) throws IOException {
		InetAddress	serverAddr = null;
		int			recvPort;
		int 		countTo;
		String 		message;

		// Get the parameters
		if (args.length != 3) 
		{
			System.err.println("Arguments required: <server name/IP> <recv port> <message count>");
			System.exit(-1);
		}

		try 
		{
			serverAddr = InetAddress.getByName(args[0]);
		} 
		catch (UnknownHostException e) 
		{
			System.out.println("Bad server address in UDPClient, " + args[0] + " caused an unknown host exception " + e);
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[1]);
		countTo = Integer.parseInt(args[2]);


		// TO-DO: Construct UDP client class and try to send messages
		UDPClient client = new UDPClient();
		client.testLoop(serverAddr, recvPort, countTo);
	}

	public UDPClient() throws SocketException {
		// TO-DO: Initialise the UDP socket for sending data
		sendSoc = new DatagramSocket();
	}

	private void testLoop(InetAddress serverAddr, int recvPort, int countTo) throws IOException {
		int				tries = 0;

		// TO-DO: Send the messages to the server
		for (int i = 0; i < countTo; i++) 
		{
			MessageInfo msg = new MessageInfo(countTo, i);
			send(msg.toString(), serverAddr, recvPort);
		}
	}

	private void send(String payload, InetAddress destAddr, int destPort) throws IOException {
		int					pktSize;
		byte[]				pktData;
		DatagramPacket		pkt;

		// TO-DO: build the datagram packet and send it to the server
		pktData = payload.getBytes();
		pktSize = pktData.length;
		pkt = new DatagramPacket(pktData, pktSize, destAddr, destPort);
		
		sendSoc.send(pkt);
	}
}