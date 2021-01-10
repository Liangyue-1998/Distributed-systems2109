package Entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ttt.james.server.TTTWebService;
//  names surname username password register
public class Games extends JFrame {
    private JFrame frame = new JFrame("Tic-Tac-Toe");
    private JPanel panel = new JPanel(); 
    private JButton showBoard = new JButton("My Score");       // 创建计分板按钮  
    private JButton showAllBoard = new JButton("All Score");       // 创建总排行榜按钮  
    private JButton newGames = new JButton("New Game");       // 创建创建新游戏按钮  
    private JButton joinGames = new JButton("Join Game");       // 创建加入游戏按钮   
    private JButton joinGame = new JButton("Join");       // 创建加入指定游戏
    
	private static TTTWebService service=new TTTWebService();
    
 

    public Games() {
    	frame.setTitle("Welcome："+Login.uid);
    	//定时执行 //如果有正在玩的游戏 不能重复创建
    	new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				frame.setTitle("Welcome："+Login.uid);
		    	String rst = service.showMyOpenGames(Integer.parseInt(Login.uid));
		    	if(!rst.equals("ERROR-NOGAMES")) {
		    		newGames.setEnabled(false);
		    	}else {
		    		newGames.setEnabled(true);
		    	}
				
			}
		}, 1000, 1000);
    	
    	
        //设置窗体的位置及大小
        frame.setSize(300, 500);
        frame.setLocationRelativeTo(null);                     //在屏幕中居中显示
        frame.add(panel);                                      // 添加面板 
        placeComponents(panel);                                //往窗体里放其他控件
        frame.setVisible(true);                                //设置窗体可见
    }
  //用于判定指字符串 在第N此出现的位置
    public static int tt(String str,String thisstr,int ii) {
     Pattern pattern = Pattern.compile(thisstr);  
        Matcher findMatcher = pattern.matcher(str);  
        int number = 0;  
        while(findMatcher.find()) {  
            number++;  
           if(number == ii){
              break;  
           }  
        }  
        int i = findMatcher.start();
        return i;
    }
    
    /**
     * 面板具体布局
     * @param panel
     */
    private void placeComponents(JPanel panel) {

        panel.setLayout(null);  //设置布局为 null 
        showBoard.setBounds(90, 100, 120, 30);
        showAllBoard.setBounds(90, 150, 120, 30);
        newGames.setBounds(90, 200, 120, 30);
        joinGames.setBounds(90, 250, 120, 30); 
        
        
        showBoard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				/**
				 * 自己的比赛记录 实现思路与下方排行发相似
				 */
				
				//下面三个map分别存玩家赢得记录 输的记录 平的记录 key=玩家 value=输赢平number
				Map<String, Integer> okmap=new HashMap<String, Integer>();
				Map<String, Integer> nomap=new HashMap<String, Integer>();
				Map<String, Integer> drawmap=new HashMap<String, Integer>();
				
				JFrame jf = new JFrame("My league");
				JTable table;
				//定义一维数据作为列标题
				Object[] columnTitle = {"user" , "victory" , "failure" , "draw"};
				
				//获取接口原始数据 如果报错直接返回
				String Dbrest=service.leagueTable();
				 if(Dbrest.startsWith("ERROR")) {
					 JOptionPane.showMessageDialog(panel, Dbrest, "Prompt！！",JOptionPane.WARNING_MESSAGE); 
					 return;
				 }
				 
				//截取每一行
				String[] rests=Dbrest.split("\n");
				
				//循环每一行数据
				for (int i = 0; i < rests.length; i++) {
					String rest = rests[i];
					String states=rest.substring(tt( rest,",",3)+1,tt( rest,",",3)+2);//游戏状态
					String p1=rest.substring(tt( rest,",",1)+1, tt( rest,",",1)+2);//玩家1
					String p2=rest.substring(tt( rest,",",2)+1, tt( rest,",",2)+2);//玩家2
					if(states.equals("0")) continue;
					if(!p1.equals(Login.uname)&&!p2.equals(Login.uname)) continue;
					
					//如果状态为1当前用户也等于用户1胜利加1 或者状态2用户为2胜也加1
					if(states.equals("1")&&p1.equals(Login.uname)||states.equals("2")&&p2.equals(Login.uname)) okmap.put(Login.uname, okmap.get(Login.uname)==null?1:okmap.get(Login.uname)+1);
					
					//否则为输的情况
					else if(states.equals("1")&&p2.equals(Login.uname)||states.equals("2")&&p1.equals(Login.uname)) nomap.put(Login.uname, nomap.get(Login.uname)==null?1:nomap.get(Login.uname)+1);
					
					//如果状态为3就是平局
					if(states.equals("3")) {
						drawmap.put(Login.uname, drawmap.get(Login.uname)==null?1:drawmap.get(Login.uname)+1);
					}
				}
				
				
				//下面排行榜监听方法 有具体的注释
				List<String> listUser=new ArrayList<String>();
				listUser.addAll(okmap.keySet());
				listUser.addAll(nomap.keySet());
				listUser.addAll(drawmap.keySet());
				Set<String> allUser=new HashSet<>(listUser);
				
				//定义二维数组作为表格数据 长度为1 查询自己的记录 只需要一行 
				Object[][] tableData = new String[1][];
				int i=0;
				for (String string : allUser) {//循环set集合 中所有的用户
					int ok;
					int no;
					int draw;
					ok=okmap.get(string)==null?0:okmap.get(string);
					no=nomap.get(string)==null?0:nomap.get(string);
					draw=drawmap.get(string)==null?0:drawmap.get(string);
					tableData[i]= new String(string+","+ok+","+no+","+draw).split(",");
					i++;
				}
				i=0;
				table = new JTable(tableData , columnTitle);
				 jf.add(new JScrollPane(table));
				 jf.setVisible(true);
				 jf.setSize(400, 300);
			}
		});
        
        
        //排行榜按钮监听
        showAllBoard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				/**
				 *	排行榜实现思路
				 *	每局游戏结束修改游戏状态 如果p1赢状态改为1 p2赢改为2 平局改为3
				 *	创建三个map 存玩家输赢平记录
				 *	leagueTable接口拿到所有游戏记录 然后通过字符串截取 获取玩家输赢接口 存入map 然后再获取map中输赢平总量 拼接为String展示在界面
				 */
				
				//下面三个map分别存玩家赢得记录 输的记录 平的记录 key=玩家 value=输赢平number
				Map<String, Integer> okmap=new HashMap<String, Integer>();
				Map<String, Integer> nomap=new HashMap<String, Integer>();
				Map<String, Integer> drawmap=new HashMap<String, Integer>();
				
				JFrame jf = new JFrame("league");
				JTable table;
				//定义一维数据作为列标题
				Object[] columnTitle = {"user" , "victory" , "failure" , "draw"};
				
				//获取接口原始数据 如果报错直接返回
				String Dbrest=service.leagueTable();
				 if(Dbrest.startsWith("ERROR")) {
					 JOptionPane.showMessageDialog(panel, Dbrest, "Prompt！！",JOptionPane.WARNING_MESSAGE); 
					 return;
				 }
				 
				//截取每一行
				String[] rests=Dbrest.split("\n");
				
				//循环每一行数据
				for (int i = 0; i < rests.length; i++) {
					String rest = rests[i];
					String states=rest.substring(tt( rest,",",3)+1,tt( rest,",",3)+2);//游戏状态
					String p1=rest.substring(tt( rest,",",1)+1, tt( rest,",",1)+2);//玩家1
					String p2=rest.substring(tt( rest,",",2)+1, tt( rest,",",2)+2);//玩家2
					if(states.equals("0")) continue;
					//状态为1的话 说明玩家1胜利 存入map key=玩家 value=胜利加1 反之玩家2输加1  下面三个if相同含义
					if(states.equals("1")) {
						okmap.put(p1, okmap.get(p1)==null?1:okmap.get(p1)+1);
						nomap.put(p2, nomap.get(p2)==null?1:nomap.get(p2)+1);
					}
					if(states.equals("2")) {
						okmap.put(p2, okmap.get(p2)==null?1:okmap.get(p2)+1);
						nomap.put(p1, nomap.get(p1)==null?1:nomap.get(p1)+1);
					}
					if(states.equals("3")) {
						drawmap.put(p2, drawmap.get(p2)==null?1:drawmap.get(p2)+1);
						drawmap.put(p1, drawmap.get(p1)==null?1:drawmap.get(p1)+1);
					}
				}
				
				//将输赢平中的key 都加到list集合中
				List<String> listUser=new ArrayList<String>();
				listUser.addAll(okmap.keySet());
				listUser.addAll(nomap.keySet());
				listUser.addAll(drawmap.keySet());
				
				//再加入到set集合中去重 之后就拿到了所有用户
				Set<String> allUser=new HashSet<>(listUser);
				
				//定义二维数组作为表格数据
				Object[][] tableData = new String[allUser.size()][];
				int i=0;
				for (String string : allUser) {//循环set集合 中所有的用户
					int ok;
					int no;
					int draw;
					ok=okmap.get(string)==null?0:okmap.get(string);//这个用户赢的数量
					no=nomap.get(string)==null?0:nomap.get(string);//这个用户输的数量
					draw=drawmap.get(string)==null?0:drawmap.get(string);//这个用户平局的数量
					tableData[i]= new String(string+","+ok+","+no+","+draw).split(",");//拼接为字符串数组 显示页面
					i++;
				}
				i=0;
				table = new JTable(tableData , columnTitle);//创建表格 放入拼接好的数据 和表头
				 jf.add(new JScrollPane(table));//将表格添加至页面
				 jf.setVisible(true);
				 jf.setSize(400, 300);
			}
		});
		
		
        //新建游戏
        newGames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String resGameId=service.newGame(Integer.parseInt(Login.uid));
				Board.isUser="X";//新建游戏者为p1 规定p1使用X
				new Board(Integer.parseInt(resGameId), Integer.parseInt(Login.uid));
				
			}
		});
		//监听joingame
        joinGames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame jf = new JFrame("openGames");
				JTable table;
				//定义一维数据作为列标题
				 Object[] columnTitle = {"game" , "user" , "date" , "work"};
				 String Dbrest=service.showOpenGames();
				 if(Dbrest.startsWith("ERROR")) {
					 JOptionPane.showMessageDialog(panel, Dbrest, "Prompt！！",JOptionPane.WARNING_MESSAGE); 
					 return;
				 }
				 String[] rest=Dbrest.split("\n");
				//定义二维数组作为表格数据
				Object[][] tableData = new String[rest.length][];
				for(int i=0; i < rest.length; i++) {//拼接表格数据
				 rest[i]+=", ";
				  tableData[i] =rest[i].toString().split(",");
				}
				 table = new JTable(tableData , columnTitle);
				 //将JTable对象放在JScrollPane中，并将该JScrollPane放在窗口中显示出来
				 jf.add(new JScrollPane(table));
				 jf.setVisible(true);
				 jf.setSize(400, 300);
				 
				 //监听表格点击时间
				 table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int row = table.getSelectedRow(); 
					    String gameId = table.getValueAt(row, 0).toString();
					    service.joinGame(Integer.parseInt(Login.uid),Integer.parseInt(gameId));
						Board.isUser="O";
						new Board(Integer.parseInt(gameId), Integer.parseInt(Login.uid));
					    jf.dispose();
					}
				 });
				 table.getColumnModel().getColumn(3).setCellRenderer(new MyRender(table,joinGame));//添加表格按钮
			}
		});
        
        panel.add(showBoard);
        panel.add(showAllBoard);
        panel.add(newGames);
        panel.add(joinGames);  
    }  
    
}


