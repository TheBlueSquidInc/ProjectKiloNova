package KitchenSink;

import java.net.*;
import java.io.*;

public class TcpThread extends Thread{

	public static volatile Socket s_2;
	public static volatile ServerSocket sSo_2;
	public static byte[] tempBuffer = new byte[65000];
	public static volatile ByteArrayOutputStream bOs;
	public static volatile String rcvData = "empty";
	public static volatile PrintWriter out;
	public static volatile BufferedReader in;
	
	
	public TcpThread(ServerSocket sSobj) {
		sSo_2 = sSobj;
		
	}
	
	@Override
	public void run() {
	while(true) {
		try {
			s_2 = sSo_2 .accept();
			out = new PrintWriter(s_2.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s_2.getInputStream()));
			rcvData = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(rcvData) {
		
		case "empty":
			System.out.println("ServerSocket did not run correctly");
			break;
			
		case "test":
			System.out.println("Test successful");
			
		}
		
		/* try {
			sSo_2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
	}
	
	// End of Run method
	}
	
	
}
