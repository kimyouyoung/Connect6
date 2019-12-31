package project.YookMok;


import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Frame extends JFrame{
	
	int start_n = 0;
	int count = 0;
	int p_count = 0;
	int co = 1;
	
	int gu_x, gu_y, g_width, g_height;
	int su_x, su_y, s_width, s_height;
	int player = 0;
	
	JPanel panel;
	JPanel panel_1;
	JPanel panel_2;
	JPanel panel_3;
	
	
	Checkbox cb;
	Checkbox cb2;
	
	JLabel ch;
	JLabel jang;
	
	String str = "";
	String str2 = "";
	
	int m_1 = 0;
	int m_2 = 0;
	
	boolean checkBlack = true;
	
	BufferedImage player_1;
	BufferedImage player_2;
	
	int murugi_1 = 0;
	int murugi_2 = 0;
	int black = 0;
	int re = 0;
	
	ArrayList<Yook> yook = new ArrayList<Yook>();
	
	int[][] map = new int[19][19];
	
	public Frame() {
		
		this.setSize(1100, 1100);
		this.setTitle("YOOKMOK");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.getContentPane().setBackground(Color.WHITE);
		this.add(explanation());
		
		panel = new JPanel();
		panel.setBounds(80, 30, 920, 70);
		panel.setBackground(Color.WHITE);
		
		panel_2 = new Cheolsu();
		panel_2.setBounds(30, 100, 200, 700);
		this.add(panel_2);
		
		panel_3 = new Janggu();
		panel_3.setBounds(870, 100, 200, 700);
		this.add(panel_3);
		
		cb = new Checkbox("CHEOLSU");
		cb.setForeground(Color.BLACK);
		
		JButton restart = new JButton("RESTART");
		restart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				restart();
				re = 1;
			}
			
		});
		
		JButton murugi = new JButton("MURUGI");
		murugi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				murugi_1 = 1;
				if(player == -1)
					JOptionPane.showMessageDialog(null, "자신의 차례가 아닐 경우 무르기를 할 수 없습니다.");
				else
					check();
			}
			
		});
		panel.add(murugi);
		
		cb2 = new Checkbox("JANGGU");
		cb2.setForeground(Color.BLACK);
		
		cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				player = 1;
				black = 1;
				cb2.setState(false);
			}
		});
		panel.add(cb);
		
		panel.add(restart);
		JButton start = new JButton("START");
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!cb.getState() && !cb2.getState())
					JOptionPane.showMessageDialog(null, "선공할 플레이어를 고르시오.");
				else {
					start_n = 1;
					re = 0;
					if(player == 1) {
						str = "백돌";
						str2 = "흑돌";
						
					}else {
						str = "흑돌";
						str2 = "백돌";
						
					}
					panel_2.repaint();
					panel_3.repaint();
				}
				repaint();
			}
			
		});
		panel.add(start);
		
		cb2.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				player = -1;
				black = 2;
				cb.setState(false);
			}
		});
		
		JButton murugi2 = new JButton("MURUGI");
		murugi2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				murugi_2 = 1;
				if(player == 1) {
					JOptionPane.showMessageDialog(null, "자신의 차례가 아닐 경우 무르기를 할 수 없습니다.");
				}else
					check();
			}
			
		});
		
		panel.add(cb2);
		panel.add(murugi2);
		
		panel_1 = new Pan();
		panel_1.setBounds(270, 100, 570, 570);
		panel_1.setBackground(new Color(244, 203, 134));
		
		this.setVisible(true);
	}
	
	public JPanel explanation() {
		JPanel ex_panel = new JPanel();
		ex_panel.setBounds(250, 60, 600, 600);
		ex_panel.setBackground(Color.WHITE);
		
		
		JLabel label = new JLabel("***EXPLANATION***");
		label.setBounds(0, 0, 600, 200);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Britannic", Font.BOLD, 50));
		label.setForeground(Color.RED);
		ex_panel.add(label);
		
		JLabel label1 = new JLabel("1. 선공을 하는 사람이 흰돌을 가지게 된다.");
		label1.setBounds(0, 100, 600, 150);
		label1.setHorizontalAlignment(JLabel.CENTER);
		label1.setFont(new Font("Britannic", Font.PLAIN, 27));
		label1.setForeground(Color.BLACK);
		ex_panel.add(label1);
		
		JLabel label2 = new JLabel("2. 상대방이 둔 돌 위에 둘 수 없다.");
		label2.setBounds(0, 200, 600, 150);
		label2.setHorizontalAlignment(JLabel.CENTER);
		label2.setFont(new Font("Britannic", Font.PLAIN, 27));
		label2.setForeground(Color.BLACK);
		ex_panel.add(label2);
		
		JLabel label3 = new JLabel("3. 무르기는 한 게임당 2번만 가능하다.");
		label3.setBounds(0, 300, 600, 150);
		label3.setHorizontalAlignment(JLabel.CENTER);
		label3.setFont(new Font("Britannic", Font.PLAIN, 27));
		label3.setForeground(Color.BLACK);
		ex_panel.add(label3);
		
		ex_panel.setLayout(null);
		JButton btn = new JButton("게임시작");
		btn.setFont(new Font("Britannic", Font.PLAIN, 40));
		btn.setBounds(200, 500, 200, 100);
		btn.setHorizontalAlignment(JButton.CENTER);
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().removeAll();
				getContentPane().add(panel);
				getContentPane().add(panel_1);
				getContentPane().add(panel_2);
				getContentPane().add(panel_3);
				revalidate();
				repaint();
			}
			
		});
		ex_panel.add(btn);
		
		return ex_panel;
	}
	
	public void restart() {
		yook.clear();
		player = 0;
		start_n = 0;
		cb.setState(false);
		cb2.setState(false);
		map = new int[19][19];
		black = 0;
		murugi_1 = 0;
		murugi_2 = 0;
		m_1 = 0;
		m_2 = 0;
		count = 0;
		p_count = 0;
		co = 1;
		re = 1;
		str = "";
		str2 = "";
		repaint();
	}

	
	class Pan extends JPanel{
		
		
		public Pan() {
			MyMouseListener ml = new MyMouseListener();
			this.addMouseListener(ml);
			this.addMouseMotionListener(ml);
		}
		
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g2);
			
			// Line
			int x = 15, y = 15;
			for(int i = 0; i < 19; i++) {	
				g2.drawLine(x, y, x, 554);
				x += 30;
			}
			x = 15; y = 15;
			for(int j = 0; j < 19; j++) {
				g2.drawLine(x, y, 554, y);
				y += 30;
			}
			
			// Oval
			int e_x = 105, e_y = 105;
			
			for(int k = 0; k < 3; k++) {
				for(int l = 0; l < 3; l++) {
					g2.fillOval((e_x+180*k)-4, (e_y+180*l)-4, 8, 8);
					
				}
			}
			
			if(start_n == 1) {
				Yook s = new Yook(new Ellipse2D.Double(9*30+6, 9*30+6, 20, 20), 1, 9, 9);
				yook.add(s);
				map[9][9] = 1;
				co = -1;
			}
			
			for(Yook yo : yook) {
				if(yo.color == 1) {
					g2.setColor(Color.BLACK);
					g2.fill(yo.shape);
				}else {
					g2.setColor(Color.WHITE);
					g2.fill(yo.shape);
				}
			}
		}
	}
	
	public void check() {
		if(yook != null) {
			if(murugi_1 == 1 && player == 1) {
				if(m_1 == 2) {
					JOptionPane.showMessageDialog(null, "무르기 2번을 모두 사용하셨습니다.");
				}
				else {
					if(black == 1 && m_1 < 2) {
						int i;
						for(i = yook.size()-1; i >= 0; i--) {
							if(yook.get(i).color == -1) {
								map[yook.get(i).x][yook.get(i).y] = 0;
								yook.remove(i);
								m_1++;
								break;
							}
						}
						if(i < 0) {
							JOptionPane.showMessageDialog(null, "더 이상 무를 수 있는 돌이 없습니다.");
						}
					}else {
						int i;
						for(i = yook.size()-1; i >= 0; i--) {
							if(yook.get(i).color == 1) {
								if(yook.get(i).x != 9 && yook.get(i).y != 9) {
									map[yook.get(i).x][yook.get(i).y] = 0;
									yook.remove(i);
									m_1++;
									break;
								}
							}
						}
						if(i < 0) {
							JOptionPane.showMessageDialog(null, "더 이상 무를 수 있는 돌이 없습니다.");
						}
					}
				}
			}
			
			else if(murugi_2 == 1 && player == -1) {
				if(m_2 == 2) {
					JOptionPane.showMessageDialog(null, "무르기 2번을 모두 사용하셨습니다.");
				}
				else {
					if(black == 1 && m_2 < 2) {
						int i;
						for(i = yook.size()-1; i >= 0; i--) {
							if(yook.get(i).color == 1) {
								if(yook.get(i).x != 9 && yook.get(i).y != 9) {
									map[yook.get(i).x][yook.get(i).y] = 0;
									yook.remove(i);
									m_2++;
									break;
								}
							}
						}
						if(i < 0)
							JOptionPane.showMessageDialog(null, "더 이상 무를 수 있는 돌이 없습니다.");
					}else {
						int i;
						for(i = yook.size()-1; i >= 0; i--) {
							if(yook.get(i).color == -1) {
								map[yook.get(i).x][yook.get(i).y] = 0;
								yook.remove(i);
								m_2++;
								break;
							}
						}
						if(i < 0)
							JOptionPane.showMessageDialog(null, "더 이상 무를 수 있는 돌이 없습니다.");
					}
				}
			}
		}
		repaint();
	}
	
	public void Win(int who) {
		if(who == 1) {
			JOptionPane.showMessageDialog(null, "흑돌 승!");
		}else if(who == -1) {
			JOptionPane.showMessageDialog(null, "백돌 승!");
		}else if(who == 0) {
			JOptionPane.showMessageDialog(null, "게임 끝!");
		}
		
		
		repaint();
		restart();
	}
	
	class MyMouseListener extends MouseAdapter implements MouseListener{
		
		public void mousePressed(MouseEvent e) {
			
			if(start_n == 0 && player == 0) {
				JOptionPane.showMessageDialog(null, "시작버튼을 눌러주십시오.");
				return;
			}
			
			if(re == 1 && start_n == 0) {
				JOptionPane.showMessageDialog(null, "시작버튼을 눌러주십시오.");
				return;
			}
			int x = e.getX();
			int y = e.getY();
			
			if(count == 2) {
				co *= -1;
				count = 0;
				p_count = 0;
			}
			if(p_count == 1) {
				player *= -1;
				
			}
			
			int yo_x = 0, yo_y = 0;
			if(x <= 555 && y <= 555) {
				yo_x = x/30;
				yo_y = y/30;
				
				if(map[yo_x][yo_y] == 0) {
					if(co == 1)
						map[yo_x][yo_y] = 1;
					else
						map[yo_x][yo_y] = -1;
					
					Yook yo = new Yook(new Ellipse2D.Double(yo_x*30+5, yo_y*30+5, 20, 20), co, yo_x, yo_y);
					yook.add(yo);
					count++;
					p_count++;
				}else {
					JOptionPane.showMessageDialog(null, "같은 곳에 둘 수 없습니다.");
				}
				
			}
			
			start_n = 0;
			
			repaint();
			Row();	
			Column();
			Diagonal_Line();
			Diagonal_Line2();
			All();
		}
	}
	
	class Cheolsu extends JPanel{
		public Cheolsu() {
			try {
				player_1 = ImageIO.read(new File("/Users/youyoungkim/Downloads/player_1.png"));
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		public void paintComponent(Graphics g) {
			
			if(ch != null) {
				panel_2.remove(ch);
			}
			ch = new JLabel(str);
			
			if(str.equals("백돌"))
				ch.setForeground(new Color(200, 200, 200));
			else
				ch.setForeground(Color.BLACK);
			
			ch.setFont(new Font("나눔손글씨 붓", Font.BOLD, 50));
			ch.setBounds(60, 50, 100, 50);
			panel_2.add(ch);
			
			if(player == 1) {
				su_x = -50;
				su_y = 120;
				s_width = 330;
				s_height = 540;
			}else if(player == -1){
				su_x = 50;
				su_y = 400;
				s_width = 150;
				s_height = 200;
			}else {
				su_x = 0;
				su_y = 150;
				s_width = 230;
				s_height = 430;
			}
			
			g.drawImage(player_1, su_x, su_y, s_width, s_height, null);
		}
		
		
	}
	
	class Janggu extends JPanel{
		public Janggu() {
			try {
				player_2 = ImageIO.read(new File("/Users/youyoungkim/Downloads/player_2.png"));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void paintComponent(Graphics g) {
			
			if(jang != null) {
				panel_3.remove(jang);
			}
			
			jang = new JLabel(str2);
			
			if(str2.equals("백돌"))
				jang.setForeground(new Color(200, 200, 200));
			else
				jang.setForeground(Color.BLACK);
			jang.setFont(new Font("나눔손글씨 붓", Font.BOLD, 50));
			jang.setBounds(60, 50, 100, 50);
			panel_3.add(jang);
			
			if(player == -1) {
				gu_x = -65;
				gu_y = 150;
				g_width = 300;
				g_height = 500;
				
			}else if(player == 1){
				gu_x = 50;
				gu_y = 400;
				g_width = 150;
				g_height = 200;
			}else {
				gu_x = 20;
				gu_y = 200;
				g_width = 170;
				g_height = 370;
			}
			
			
			g.drawImage(player_2, gu_x, gu_y, g_width, g_height, null);
		}
		
	}
	
	public void Row() {
		
		for(int i = 0; i < 14; i++) {
			for(int j = 0; j < 19; j++) {
				if(map[i][j] == 1 && map[i+1][j] == 1 && map[i+2][j] == 1 && map[i+3][j] == 1 && map[i+4][j] == 1 && map[i+5][j] == 1) {
					Win(1);
				}
				else if(map[i][j] == -1 && map[i+1][j] == -1 && map[i+2][j] == -1 && map[i+3][j] == -1 && map[i+4][j] == -1 && map[i+5][j] == -1){
					Win(-1);
				}
			}
		}
	}
	
	public void Column() {
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 14; j++) {
				if(map[j][i] == 1 && map[j][i+1] == 1 && map[j][i+2] == 1 && map[j][i+3] == 1 && map[j][i+4] == 1 && map[j][i+5] == 1) {
					Win(1);
				}
				else if(map[j][i] == -1 && map[j][i+1] == -1 && map[j][i+2] == -1 && map[j][i+3] == -1 && map[j][i+4] == -1 && map[j][i+5] == -1){
					Win(-1);
				}
			}
		}
	}
	
	
	public void Diagonal_Line() {
		for(int i = 0; i < 14; i++) {
			for(int j = 0; j < 14; j++) {
				if(map[i][j] == 1 && map[i+1][j+1] == 1 && map[i+2][j+2] == 1 && map[i+3][j+3] == 1 && map[i+4][j+4] == 1 && map[i+5][j+5] == 1) {
					Win(1);
				}
				else if(map[i][j] == -1 && map[i+1][j+1] == -1 && map[i+2][j+2] == -1 && map[i+3][j+3] == -1 && map[i+4][j+4] == -1 && map[i+5][j+5] == -1){
					Win(-1);
				}
			}
		}
	}
	
	public void Diagonal_Line2() {
		for(int i = 0; i < 14; i++) {
			for(int j = 18; j > 4; j--) {
				if(map[i][j] == 1 && map[i+1][j-1] == 1 && map[i+2][j-2] == 1 && map[i+3][j-3] == 1 && map[i+4][j-4] == 1 && map[i+5][j-5] == 1) {
					Win(1);
				}
				else if(map[i][j] == -1 && map[i+1][j-1] == -1 && map[i+2][j-2] == -1 && map[i+3][j-3] == -1 && map[i+4][j-4] == -1 && map[i+5][j-5] == -1){
					Win(-1);
				}
			}
		}
	}

	public void All() {
		for(int i = 0; i < 18; i++) {
			for(int j = 0; j < 18; j++) {
				if(map[i][j] ==0)
					return;
			}
		}
		Win(0);
	}
	
}
