package KitchenSink;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.*;
import java.net.*;

public class main extends JFrame{
	
	
	public static String appNameStr = "Kitchen Sink V0.1";
	public static JLabel ipAdrLabl = new JLabel();
	public static JLabel portNumLabl = new JLabel();
	public static JTextField ipAdrData = new JTextField();
	public static JTextField portNumData = new JTextField();
	
	public static volatile JLabel listenPortLabl = new JLabel("Listening on: ");
	public static volatile JTextField listenPortData = new JTextField();
	
	public static JButton openBtn = new JButton("Open");
	public static JButton closeBtn = new JButton("Close");
	
	public static volatile ServerSocket sSo;
	
	
	
	
	public main(){
		ipAdrLabl.setText("Dest. IP: ");
		ipAdrLabl.setBounds(10, 20, 50, 20);
		this.add(ipAdrLabl);
		
		ipAdrData.setBounds(70, 20, 120, 20);
		this.add(ipAdrData);
		
		portNumLabl.setText("Port num#: ");
		portNumLabl.setBounds(10, 50, 70, 20);
		this.add(portNumLabl);
		
		
		portNumData.setBounds(90, 50, 60, 20);
		this.add(portNumData);
		
		listenPortLabl.setBounds(10, 80, 75, 20);
		this.add(listenPortLabl);
		
		listenPortData.setBounds(95, 80, 45, 20);
		this.add(listenPortData);
		listenPortData.setText("50000");
		
		openBtn.setBounds(10, 120, 70, 20);
		this.add(openBtn);
		
		closeBtn.setBounds(90, 120, 70, 20);
		this.add(closeBtn);
		
		this.setTitle(appNameStr);
		this.setSize(300,200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);
		
		
		setSocketPortLabel();
		
		
		openBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					new Thread() {
						
						AudioFormat aFormat = new AudioFormat(8000, 8, 2, false, false);
						DataLine.Info sDli = new DataLine.Info(SourceDataLine.class, aFormat);
						SourceDataLine sDl = (SourceDataLine) AudioSystem.getLine(sDli);
						
						DatagramSocket dSkt = new DatagramSocket();
						byte[] tempBuffer = new byte[65000];
						DatagramPacket dPkt = new DatagramPacket(tempBuffer, tempBuffer.length);
						@Override
						public void run(){
							
						}
					};
				} catch (LineUnavailableException | SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new main();
	}

/////////////////////////////////////////////////	
	private static int initServerSocket()  {
		 int srvPrtNum = 0;
		 try {
			sSo = new ServerSocket(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 srvPrtNum = sSo.getLocalPort();
		return srvPrtNum;
	}
/////////////////////////////////////////////////
	
	
	
	
	
	
	
	
	
//////////////////////////////////////////////////////////////////////
	private static void setSocketPortLabel() {
		listenPortData.setText(Integer.toString(initServerSocket()));
	}
///////////////////////////////////////////////////////////////////////	
	
	
	
	
	//end main class
}
