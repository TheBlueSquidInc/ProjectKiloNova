package KitchenSink;

import java.net.*;
import java.io.*;

public class TcpThread extends Thread{

	public static volatile Socket s_2;
	public static volatile ServerSocket sSo_2;
	public static byte[] tempBuffer = new byte[65000];
	public static volatile ByteArrayOutputStream bOs;
	public static volatile String rcvData = "empty";
	public static volatile DataInputStream dIs;
	public static volatile DataOutputStream dOs;
	
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
			try {
				dOs.writeUTF("START_ACK");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Start command received");
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
	
	
}
