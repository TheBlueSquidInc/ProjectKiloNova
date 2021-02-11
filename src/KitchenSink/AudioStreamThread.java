package KitchenSink;

import java.io.*;
import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;

import java.net.*;

public class AudioStreamThread extends Thread{

	public static volatile String destIpStr = "placeholder";
	public static volatile int destPortInt = 0;
	public static volatile DatagramPacket dgPkt;
	public static volatile DatagramSocket dgSkt;
	public static volatile Socket tcpCskt;
	public static volatile AudioFormat aFormat;
	public static volatile DataLine.Info sDli;
	public static volatile SourceDataLine sDl;
	public static volatile byte[] tempAudioBuffer;
	public static volatile byte[] tempDataBuffer;
	public static  enum tcpCommand{
		Start,
		Stop,
	}
	
	public static volatile DataOutputStream dOs;
	public static volatile DataInputStream dIs;
	
	
	public AudioStreamThread (String destIp, int destPort) throws UnknownHostException, IOException {
		
	
		
		this.destIpStr = destIp;
		this.destPortInt = destPort;
		this.aFormat = new AudioFormat(8000, 8, 2, false, false);
		this.tempAudioBuffer = new byte[65000];
		this.tempDataBuffer = new byte[65000];
		this.sDli = new DataLine.Info(SourceDataLine.class, aFormat);
		try {
			this.sDl = (SourceDataLine) AudioSystem.getLine(sDli);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.tcpCskt = new Socket(destIpStr, destPortInt);
		
		//this.oS = new PrintWriter(tcpCskt.getOutputStream(), true);
		
		this.dOs = new DataOutputStream(this.tcpCskt.getOutputStream());
		
		this.dIs = new DataInputStream(this.tcpCskt.getInputStream());
		
		//this.iS = new BufferedReader(new InputStreamReader(tcpCskt.getInputStream()));
		
		sendPlayCmd();
		
	}
	
	@Override
	public void run(){
		while(true) {
			
		}
	}
	
	
	
	public static void sendPlayCmd() {
		
		try {
			dgSkt = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			dOs.writeUTF("Start");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			System.out.print(dIs.readUTF());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			dOs.writeUTF(Integer.toString(dgSkt.getLocalPort()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void stopPlaying() {
		try {
			dOs.writeUTF("Stop");
			tcpCskt.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(dIs.readUTF());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
