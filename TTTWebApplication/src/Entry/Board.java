package Entry;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ttt.james.server.TTTWebService;

public  class  Board  extends JFrame{ 
	private int thisGameId;
	private int thisUserId;
	static boolean is=true;
	static boolean is2=true;
	
	//初始指定X先走
	private char whoseturn='X';
	//创建井字棋棋格【3*3】
	private Cell[][] cell=new Cell[3][3];
	//创建标签用于显示当前对局状态
	private JLabel jlblStatus=new JLabel("X go game start");
	
	private static TTTWebService service=new TTTWebService();
	
	static String isUser;//存放当前用户用的是X还是O 默认P1用X P2用O
	
	JFrame thisJ=this;

	public Board() throws HeadlessException {
		init(); 
		this.setSize(400,400);
		this.setVisible(true);
		this.setLocation(500,300);
	}
	public Board(int thisGameId,int thisUserId){
		this.thisGameId=thisGameId;
		this.thisUserId=thisUserId;
		this.setTitle("Welcome Player："+Login.uid+" Use the pieces："+isUser);
		init(); 
		this.setSize(400,400);
		this.setVisible(true);
	}
	public  void init()//初始化各组件
	{
		
		//创建显示面板并设置布局、边界
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(3,3,0,8));
		p.setBorder(new LineBorder(Color.black,1));
		
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				p.add(cell[i][j]=new Cell());
		
	    jlblStatus.setBorder(new LineBorder(Color.red,1));
	    //将状态标签和显示面板加入内容面板并设置其方位
		this.getContentPane().add(p,BorderLayout.CENTER);
		this.getContentPane().add(jlblStatus,BorderLayout.SOUTH);
	}
 
 
	//判断棋盘是否下满
		public  boolean isFull()
		{
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
					if(cell[i][j].getToken()==' ') {
						//如果还有空的 说明没下满 就返回false
						return false;
					}
			return true;
		}
	//判断胜利的几种的状态
	public  boolean isWon(char token)
	{
		//某行连续三个
		for(int i=0;i<3;i++)
			if((cell[i][0].getToken()==token)
				&&(cell[i][1].getToken()==token)
				&&(cell[i][2].getToken()==token))
				return true;
		//某列连续三个
		for(int j=0;j<3;j++)
			if((cell[0][j].getToken()==token)
				&&(cell[1][j].getToken()==token)
				&&(cell[2][j].getToken()==token))
				return true;
		//主对角线三个
		if((cell[0][0].getToken()==token)
				&&(cell[1][1].getToken()==token)
				&&(cell[2][2].getToken()==token))
				return true;
		//副对角线三个
		if((cell[0][2].getToken()==token)
				&&(cell[1][1].getToken()==token)
				&&(cell[2][0].getToken()==token))
				return true;
 
		return false;
			
	}
	
	class Cell extends JPanel implements MouseListener
	{
		JPanel cellP;
		private char token=' ';
		//为棋格添加监听器
		public  Cell()
		{
			this.cellP=this;
			setBorder(new LineBorder(Color.black));
			addMouseListener(this);
		}
		public  char getToken()
		{
			return token;
		}
		public synchronized void setToken(char c)
		{
			token=c;
			repaint();//重画棋盘，刷新显示
		}
		
		//对于空棋格，点哪就在哪画对应的X或O
		public  void paintComponent(Graphics g)
		{
			if(is) {
				is=false;
				if(isUser.equals("O")) {
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							String dbRest=service.getBoard(thisGameId);
							if(!dbRest.equals("ERROR-NOMOVES")) {//说明有下棋记录
								String[] ss = dbRest.split("\n");
								String rest=ss[ss.length-1];
								int thisX=Integer.parseInt(rest.substring(rest.indexOf(",",1)+1, rest.indexOf(",",1)+2));
								int thisY=Integer.parseInt(rest.substring(rest.indexOf(",",3)+1, rest.indexOf(",",3)+2));
								cell[thisY][thisX].setToken(whoseturn);
								if(cell[thisY][thisX].getToken()=='X') whoseturn='O';
								else whoseturn='X';
								jlblStatus.setText(whoseturn+" Go");
								this.cancel();
							}
						}
					}, 500, 1000);
				}
			}
			super.paintComponent(g);
			if(token=='X')
			{
				g.drawLine(10,10,
						getSize().width-10,
						getSize().height-10);
				g.drawLine(getSize().width-10,
						10,10,
						getSize().height-10);
			}
			else if(token=='O')
				g.drawOval(10,10,
						getSize().width-20,
						getSize().height-20);
		}
		
		
		//鼠标个个监听事件 用不到
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		
		
		
		//鼠标点击监听事件 只需重写该方法即可
		public  void mouseClicked(MouseEvent e)
		{
			Timer t = null;
			int XX = 0;
			int YY = 0;
			for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++) {
                    if (cell[i][j] == e.getSource()) {
                    	XX=j;
                    	YY=i;
                    }
                }
			if(is2) {
				if(isUser.equals("X"))
				service.takeSquare(XX,YY,thisGameId,thisUserId);
				is2=false;
			}
			if(token==' ')//只能在空棋格下棋
			{
				if(whoseturn=='X')//若当前为X走
				{
					if(!isUser.equals("X")) {//若当前用户用的不是X 则不能下棋 弹出提示
						JOptionPane.showMessageDialog(this, "This X！", "Prompt！！",JOptionPane.WARNING_MESSAGE);  
						if(t!=null) {
							t.cancel();
						}
						return;
					}
					service.takeSquare(XX,YY,thisGameId,thisUserId);//添加最新的下棋记录
					
					setToken('X');//设置说要画的形状为X
					whoseturn='O';//并更新下一步为O走
					//在状态标签显示
					jlblStatus.setText(whoseturn+" Go");
					if(isWon('X')) {//这里判断O是否三连（胜利）
						if(t!=null) {
							t.cancel();//如果下面那个定时器没有关闭 关闭它
						}
						JOptionPane.showMessageDialog(this, "X victory！", "Prompt！！",JOptionPane.WARNING_MESSAGE);//弹出提示
						thisJ.dispose();//关闭下棋界面
						service.setGameState(thisGameId,1);//修改游戏状态 1代表玩家1赢
						
					}
					//棋盘下满了仍未分出胜负，平局
					else if(isFull()) {
						if(t!=null) {
							t.cancel();//如果下面那个定时器没有关闭 关闭它
						}	
						JOptionPane.showMessageDialog(this, "Draw！", "Prompt！！",JOptionPane.WARNING_MESSAGE);  
						thisJ.dispose();
						service.setGameState(thisGameId,3);//修改当前游戏状态为3 平局
					}
				}
				//若当前为O走 基本同上
				else if(whoseturn=='O')
				{
					if(!isUser.equals("O")) {//如果当前用户用的不是O 弹出提示
						JOptionPane.showMessageDialog(this, "This O！", "Prompt！！",JOptionPane.WARNING_MESSAGE);  
						if(t!=null) {
							t.cancel();//如果下面那个定时器没有关闭 关闭它
						}
						return;
					}
					service.takeSquare(XX,YY,thisGameId,thisUserId);//添加最新走的记录到数据库
					setToken('O');
					whoseturn='X';
					jlblStatus.setText(whoseturn+" Go");
					if(isWon('O')) {
						if(t!=null) {
							t.cancel();
						}				
						JOptionPane.showMessageDialog(this, "O victory！", "Prompt！！",JOptionPane.WARNING_MESSAGE);  
						thisJ.dispose();//关闭下棋界面
						service.setGameState(thisGameId,2);//修改游戏状态
					}
					else if(isFull()) {
						if(t!=null) {
							t.cancel();
						}	
						JOptionPane.showMessageDialog(this, "Draw！", "Prompt！！",JOptionPane.WARNING_MESSAGE);  
						thisJ.dispose();
						service.setGameState(thisGameId,3);
					}
						
				}
				
				//定时扫描数据库最新棋子位置
				t=new Timer();
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						String dbRest=service.getBoard(thisGameId);
							if(!dbRest.startsWith("ERROR")) {
								String[] ss = dbRest.split("\n");
								String rest=ss[ss.length-1];
								int thisX=Integer.parseInt(rest.substring(rest.indexOf(",",1)+1, rest.indexOf(",",1)+2));
								int thisY=Integer.parseInt(rest.substring(rest.indexOf(",",3)+1, rest.indexOf(",",3)+2));
								if(cell[thisY][thisX].getToken()!='X'&&cell[thisY][thisX].getToken()!='O') {
									if(isUser.equals("X")) {
										cell[thisY][thisX].setToken('O');
										whoseturn=isUser.charAt(0);
									}else {
										cell[thisY][thisX].setToken('X');
										whoseturn=isUser.charAt(0);										
									}
									this.cancel();
								}
								if(isWon('O')) {//判断o是否胜利
									this.cancel();//停止定时
									JOptionPane.showMessageDialog(cellP, "O victory！", "Prompt！！",JOptionPane.WARNING_MESSAGE); //弹出提示
									thisJ.dispose();//关闭下棋面板
									service.setGameState(thisGameId,2);//设置状态为2 O赢
								}
								
								//下面同上
								else if(isWon('X')) {//判断X是否胜利
									this.cancel();
									JOptionPane.showMessageDialog(cellP, "X victory！", "Prompt！！",JOptionPane.WARNING_MESSAGE);  
									thisJ.dispose();
									service.setGameState(thisGameId,1);
								}
								else if(isFull()) {//判断是否平局
									this.cancel();
									JOptionPane.showMessageDialog(cellP, "Draw！", "Prompt！！",JOptionPane.WARNING_MESSAGE);  
									thisJ.dispose();
									service.setGameState(thisGameId,3);
								}
								jlblStatus.setText(whoseturn+" Go");
							}else JOptionPane.showMessageDialog(cellP, dbRest, "Prompt！！",JOptionPane.WARNING_MESSAGE);  
					}
				}, 500, 1000);//1000毫秒执行一次 执行器延迟500毫秒
				
			}
		}
	}
	public int getThisGameId() {
		return thisGameId;
	}
	public void setThisGameId(int thisGameId) {
		this.thisGameId = thisGameId;
	}
	public int getThisUserId() {
		return thisUserId;
	}
	public void setThisUserId(int thisUserId) {
		this.thisUserId = thisUserId;
	}
	
	
}
