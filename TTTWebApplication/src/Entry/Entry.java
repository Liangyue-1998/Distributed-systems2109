package Entry;

import javax.swing.JFrame;


/**
 * 项目启动类 运行此类启动项目
 *
 */
public class Entry {
	public static void main(String[] args) throws InterruptedException {
		JFrame frame=new JFrame("Tic-Tac-Toe");   
		Menu menu = new Menu("TTT welcome");   
	 	menu.Init();
	 	menu.setVisible(true);  
		  
	}
}