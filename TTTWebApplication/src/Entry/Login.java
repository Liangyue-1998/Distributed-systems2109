package Entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ttt.james.server.TTTWebService;
//  names surname username password register
public class Login extends JFrame {
    private JFrame frame = new JFrame("Welcome");
    private JPanel panel = new JPanel(); 
    private JButton loginButton = new JButton("Login");       // 创建登录按钮  
 
    private JLabel usernamejJLabel = new JLabel("Username:");           // 创建username 
    private JTextField usernameJTextField = new JTextField();           // 获取登录名

    private JLabel passwordjJLabel = new JLabel("Password:");           // 创建password  
    private JPasswordField passwordJTextField = new JPasswordField();  //密码框隐藏 
    
    private static TTTWebService service=new TTTWebService();
    
    private JFrame pjf;
    
    static String uid;
    static String uname;
    

   

	public Login(JFrame jfp) {
		this.setTitle("TTT Login");
		this.setLocationRelativeTo(null);         
    	this.pjf=jfp;
        //设置窗体的位置及大小
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);                     //在屏幕中居中显示
        frame.add(panel);                                      // 添加面板 
        placeComponents(panel);                                //往窗体里放其他控件
        frame.setVisible(true);                                //设置窗体可见
    }
    
    /**
     * 面板具体布局
     * @param panel
     */
    private void placeComponents(JPanel panel) {

        panel.setLayout(null);  //设置布局为 null 
        
        // 创建 usernameJLabel
        usernamejJLabel.setBounds(30, 90, 80, 25);
        panel.add(usernamejJLabel);
        // 创建文本域用于用户输入
        usernameJTextField.setBounds(105, 90, 165, 25);
        panel.add(usernameJTextField);
        
        
        // 创建 passwordJLabel
        passwordjJLabel.setBounds(30, 120, 80, 25);
        panel.add(passwordjJLabel);
        // 创建文本域用于用户输入
        passwordJTextField.setBounds(105, 120, 165, 25);
        panel.add(passwordJTextField);
         
        // 创建登录按钮
        loginButton.setBounds(100, 150, 80, 25);

		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {   
				int key = service.login(usernameJTextField.getText().toString(), String.valueOf( passwordJTextField.getPassword() ) );
				if(key == -1) {      
			 		JOptionPane.showMessageDialog(panel, "Password Mistake", "Prompt！！",JOptionPane.WARNING_MESSAGE);  
			 		frame.dispose();
					new Login(pjf);
				}else{
			 		JOptionPane.showMessageDialog(panel, "Login successful", "Prompt！！",JOptionPane.WARNING_MESSAGE);  
			 		frame.dispose();
			 		pjf.dispose();
			 		uid=""+key;
			 		uname=""+usernameJTextField.getText().toString();
				 	new Games();
				} 
			}
		});  
        panel.add(loginButton); 
    }  
}


