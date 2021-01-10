package Entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import sun.util.logging.resources.logging;

public class Menu extends JFrame{
	String title;
	JButton registerButton = new JButton("Register");
	JButton loginButton = new JButton("Login");		
	JPanel jPanel = new JPanel();
	public Menu(String title){
		this.title = title;
		this.setTitle(title);
		this.setLocationRelativeTo(null);
	}
	public void Init(){
		
		this.setSize(300, 100);
		registerButton.setSize(30, 10);
		loginButton.setSize(30, 10);
		registerButton.setLocation(20, 150);
		loginButton.setLocation(200, 150);
		
		JFrame jf=this;
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {  
					new Login(jf);
			}
		});
		 
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {  
					new Register(jf);
			}
		});
		jPanel.add(loginButton);
		jPanel.add(registerButton);  
		jPanel.setSize(300,300);
		this.add(jPanel); 
	}
	
	
}
