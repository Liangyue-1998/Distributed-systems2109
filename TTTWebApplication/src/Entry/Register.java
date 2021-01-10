package Entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import jdk.nashorn.internal.ir.WhileNode;


import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import ttt.james.server.TTTWebService;
//  names surname username password register
public class Register extends JFrame {
    private JFrame frame = new JFrame("Welcome");
    private JPanel panel = new JPanel();  
    private JButton registerButton = new JButton("Register"); // 创建注册按钮
    

    private JLabel nameJLabel = new JLabel("Name:");           // 创建name 
    private JTextField nameJTextField = new JTextField();           // 获取登录名
    

    private JLabel surnameJLabel = new JLabel("Surname:");           // 创建surname
    private JTextField surnameJTextField = new JTextField();           // 获取登录名

    private JLabel usernamejJLabel = new JLabel("Username:");           // 创建username 
    private JTextField usernameJTextField = new JTextField();           // 获取登录名

    private JLabel passwordjJLabel = new JLabel("Password:");           // 创建password  
    private JPasswordField passwordJTextField = new JPasswordField(); //密码框隐藏 
    private JFrame pjf;
    
    private static TTTWebService service=new TTTWebService();

    public Register(JFrame jfp) {
    	this.setTitle("TTT Register");
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

        // 创建 nameJLabel
        nameJLabel.setBounds(30, 30, 80, 25);
        panel.add(nameJLabel);
        // 创建文本域用于用户输入
        nameJTextField.setBounds(105, 30, 165, 25);
        panel.add(nameJTextField);

        // 创建 surnameJLabel
        surnameJLabel.setBounds(30, 60, 80, 25);
        panel.add(surnameJLabel);
        // 创建文本域用于用户输入
        surnameJTextField.setBounds(105, 60, 165, 25);
        panel.add(surnameJTextField);

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
         
        registerButton.setBounds(150, 150, 80, 25);
        

        //监听注册按钮
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub  
				String string = service.register(usernameJTextField.getText().toString(),
						String.valueOf( passwordJTextField.getPassword() ),nameJTextField.getText().toString(),
							surnameJTextField.getText().toString());
				if(string.equals("")) {
					JOptionPane.showMessageDialog(panel, "Registered Successful", "Hint",JOptionPane.WARNING_MESSAGE);  
			 		frame.dispose();
			 		new Login(pjf);
				}
				else if(string.charAt(0) == 'E' ){ 
					 		JOptionPane.showMessageDialog(panel, "Duplicate username", "Hint！！",JOptionPane.WARNING_MESSAGE);  
							frame.dispose();
					 		new Register(pjf);
				 }else{
				 		JOptionPane.showMessageDialog(panel, "Registered successful", "Hint",JOptionPane.WARNING_MESSAGE);  
				 		frame.dispose();
				 		new Login(pjf);
				 }  
			}

			private void Text_clear() {
				// TODO Auto-generated method stub 
				passwordJTextField.setText(null);
				usernameJTextField.setText(null);
				surnameJTextField.setText(null);
				nameJTextField.setText(null);
			}  
		});
        panel.add(registerButton);
    } 
}


