package KitchenSink;

import java.io.*;
import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;

import java.net.*;

public class AudioStreamThread extends Thread{

	public static volatile String tempString;
	public static volatile boolean isPlaying;
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
	public static volatile String tcpDataString;
	public static volatile String[] tcpDataStringArray;
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
		this.sDli = new DataLine.Info(SourceDataLine.class, aFormat);
		try {
			this.sDl = (SourceDataLine) AudioSystem.getLine(sDli);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.tempAudioBuffer = new byte[sDl.getBufferSize()/5];
		this.tempDataBuffer = new byte[tempAudioBuffer.length];
		
		this.tcpCskt = new Socket(destIpStr, destPortInt);

		this.dOs = new DataOutputStream(this.tcpCskt.getOutputStream());
		
		this.dIs = new DataInputStream(this.tcpCskt.getInputStream());
		
		sendPlayCmd();
		initSound();
		initPacket();
	}
	
	@Override
	public void run(){
		isPlaying = true;
		while(isPlaying) {
			
			try {
				dgSkt.receive(dgPkt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sDl.write(dgPkt.getData(), 0, dgPkt.getLength());
			System.out.println(dgPkt.getData().toString());
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
			dOs.writeUTF("Start" + " " + dgSkt.getLocalPort());
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
	
	
	public void initPacket() {
		dgPkt = new DatagramPacket(tempAudioBuffer, 0, tempAudioBuffer.length);
	}
	
	
	public void initSound() {
		try {
			this.sDl.open(aFormat);
			this.sDl.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
