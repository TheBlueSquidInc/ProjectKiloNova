package KitchenSink;

import java.net.*;

import javax.sound.sampled.*;

import java.io.*;



public class TcpThread extends Thread{

	public static volatile Socket s_2;
	public static volatile ServerSocket sSo_2;
	public static volatile boolean isRecording;
	public static byte[] tempBuffer;
	public static volatile ByteArrayOutputStream bOs;
	public static volatile String rcvData = "empty";
	public static volatile DataInputStream dIs;
	public static volatile DataOutputStream dOs;
	public static volatile TargetDataLine tDl;
	public static volatile AudioFormat aFormat;
	public static volatile DataLine.Info tDli;
	public static volatile DatagramPacket dgPckt;
	public static volatile DatagramSocket dgSkt;
	public static volatile int udpHostPort = 0;
	public static volatile int numOfBytesRead = 0;
	public static volatile InetAddress hostInetAdr;
	
	
	public TcpThread(ServerSocket sSobj) {
		sSo_2 = sSobj;
		
	}
	
	@Override
	public void run() {
		
	while(true) {
		
		try {
			this.s_2 = sSo_2 .accept();
			dIs = new DataInputStream(this.s_2.getInputStream());
			dOs = new DataOutputStream(this.s_2.getOutputStream());
			this.rcvData = dIs.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		switch(this.rcvData) {
		
			
		case "Test":
			System.out.println("Test successful");
			break;
			
		case "Start":
			
			tempBuffer = new byte[tDl.getBufferSize()/5];
			
			try {
				dgSkt = new DatagramSocket();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				
				dOs.writeUTF("START_ACK" + "|" + dgSkt.getPort());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				udpHostPort = Integer.parseInt(dIs.readUTF());
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			System.out.println("Start command received");
			runMic();
			break;
			
		case "Stop":
			
			try {
				dOs.writeUTF("STOP_ACK");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				sSo_2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break;

		}
		
		
	}
	
	// End of Run method
	}
	
	
	
	
	public static void runMic() {
		isRecording = true;
		hostInetAdr = s_2.getInetAddress();
		dgPckt = new DatagramPacket(tempBuffer,0,tempBuffer.length,hostInetAdr,udpHostPort);
		aFormat = new AudioFormat(44100, 8, 2, false, false);
		tDli = new DataLine.Info(TargetDataLine.class, aFormat);
		try {
			tDl = (TargetDataLine) AudioSystem.getLine(tDli);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(isRecording) {
			numOfBytesRead = tDl.read(tempBuffer, 0, tempBuffer.length);
			try {
				dgSkt.send(dgPckt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
}
