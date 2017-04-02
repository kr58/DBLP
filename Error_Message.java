/**
 * This class handles the Gui Part of Error Messages
 * It has one constructor of the type Error_Meassage(String)
 * The Error text needs to be passed in constructor
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Error_Message extends JFrame {
	JLabel msg; JButton ok;
	Error_Message(String show)
	{
		msg = new JLabel(show,JLabel.CENTER); ok = new JButton();

		setTitle("Error Message"); setSize(220,120);
		getContentPane().setBackground(new Color(128, 212, 255));

		msg.setMaximumSize(new Dimension(200,60));
		msg.setAlignmentX(Component.CENTER_ALIGNMENT);
		msg.setFont(new Font("SansSerif",Font.BOLD+Font.ITALIC,15));

		ok.setText("OK");
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		ok.setBackground(new Color(0, 170, 255));
		ok.setMaximumSize(new Dimension(60,20));
		ok.setActionCommand("ok");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String cmd = e.getActionCommand();
				if(cmd.equals("ok")) dispose();
			}
		});
		add(Box.createRigidArea(new Dimension(0,20))); add(msg); add(Box.createRigidArea(new Dimension(0,20))); 
		add(ok); add(Box.createRigidArea(new Dimension(0,20)));
		
		int screen_width = getToolkit().getScreenSize().width, screen_height = getToolkit().getScreenSize().height;
		setLocation((screen_width-getWidth())/2,(screen_height-getHeight())/2);
		setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS)); setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
