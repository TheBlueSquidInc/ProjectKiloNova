package KitchenSink;

import java.net.*;
import java.io.*;

public class TcpThread extends Thread{

	public static volatile Socket s_2;
	public static volatile ServerSocket sSo_2;
	public static byte[] tempBuffer = new byte[65000];
	public static volatile ByteArrayOutputStream bOs;
	
	public TcpThread(ServerSocket sSobj) {
		sSo_2 = sSobj;
		
	}
	
	@Override
	public void run() {
	try {
		s_2 = sSo_2 .accept();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
}
