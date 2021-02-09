package KitchenSink;

import javax.swing.*;

public class main extends JFrame{
	
	
	public static String appNameStr = "Kitchen Sink V0.1";
	public static JLabel ipAdrLabl = new JLabel();
	public static JLabel portNumLabl = new JLabel();
	public static JTextField ipAdrData = new JTextField();
	public static JTextField portNumData = new JTextField();
	
	public static JButton openBtn = new JButton("Open");
	public static JButton closeBtn = new JButton("Close");
	
	
	
	
	
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
		
		openBtn.setBounds(10, 90, 70, 20);
		this.add(openBtn);
		
		closeBtn.setBounds(90, 90, 70, 20);
		this.add(closeBtn);
		
		this.setTitle(appNameStr);
		this.setSize(300,200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new main();
	}

}
