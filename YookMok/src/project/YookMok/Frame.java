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
import javax.swing.ImageIcon;
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
	int b_count = 0;
	
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
	String com = "";
	
	int m_1 = 0;
	int m_2 = 0;
	
	boolean brain = false;
	boolean first = true;
	boolean second = true;
	
	BufferedImage player_1;
	BufferedImage player_2;
	
	int murugi_1 = 0;
	int murugi_2 = 0;
	int black = 0;
	int re = 0;
	
	ArrayList<Yook> yook = new ArrayList<Yook>();
	
	int[][] map = new int[19][19];
	int[][] b_map = new int[19][19];
	
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
				co = -1;
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
				co = -1;
				player = -1;
				black = 2;
				cb.setState(false);
				b_map[9][9] = -1;
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
		JButton btn = new JButton("같이놀기");
		btn.setFont(new Font("Britannic", Font.PLAIN, 40));
		btn.setBounds(50, 500, 200, 100);
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
		
		JButton btn2 = new JButton("혼자놀기");
		btn2.setFont(new Font("Britannic", Font.PLAIN, 40));
		btn2.setBounds(350, 500, 200, 100);
		btn2.setHorizontalAlignment(JButton.CENTER);
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				brain = true;
				getContentPane().removeAll();
				getContentPane().add(ChooseCharacter());
				revalidate();
				repaint();
			}
			
		});
		ex_panel.add(btn2);
		
		return ex_panel;
	}
	
	public JPanel ChooseCharacter() {
		JPanel c_panel = new JPanel();
		c_panel.setBounds(0, 0, this.getWidth(), this.getHeight());
		c_panel.setBackground(Color.WHITE);
		c_panel.setLayout(null);
		
		BufferedImage p1, p2;
		try {
			p1 = ImageIO.read(new File("/Users/youyoungkim/Downloads/player_1.png"));
			p2 = ImageIO.read(new File("/Users/youyoungkim/Downloads/player_2.png"));
			
			ImageIcon pp1 = new ImageIcon(p1);
			JButton btn = new JButton(pp1);
			btn.setBounds(200, 230, 200, 470);
			btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					com = "JA";
					getContentPane().removeAll();
					getContentPane().add(panel);
					getContentPane().add(panel_1);
					getContentPane().add(panel_2);
					getContentPane().add(panel_3);
					revalidate();
					repaint();
				}
				
			});
			
			ImageIcon pp2 = new ImageIcon(p2);
			JButton btn2 = new JButton(pp2);
			btn2.setBounds(700, 230, 200, 470);
			btn2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					com = "CH";
					getContentPane().removeAll();
					getContentPane().add(panel);
					getContentPane().add(panel_1);
					getContentPane().add(panel_2);
					getContentPane().add(panel_3);
					revalidate();
					repaint();
				}
				
			});
			
			c_panel.add(btn);
			c_panel.add(btn2);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return c_panel;
	}
	
	public void restart() {
		yook.clear(); player = 0; start_n = 0;
		cb.setState(false); cb2.setState(false);
		map = new int[19][19];
		black = 0;
		murugi_1 = 0; murugi_2 = 0;
		m_1 = 0; m_2 = 0;
		count = 0; p_count = 0;
		co = 1; re = 1;
		str = ""; str2 = "";
		first = true;
		second = true;
		b_map = new int[19][19];
		b_map[9][9] = -1;
		b_count = 0;
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
				if(brain && (com.equals("CH") && player == 1) || (com.equals("JA") && player == -1))
					callBrain();
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
	
	public void callBrain() {
		// Computer turn
		if((com.equals("CH") && player == 1) || (com.equals("JA") && player == -1)) {
			for(int i = 0; i < 2; i++) {
				if(count == 2) {
					co *= -1;
					count = 0;
					p_count = 0;
				}
								
				if(p_count == 1) {
					player *= -1;
				}
				brain();
				repaint();
				Row();	
				Column();
				Diagonal_Line();
				Diagonal_Line2();
				All();			
			}
		}
		return;
	}
	
	
	
	public void brain() {
		
		int min = 0, min_i = 0, min_j = 0;
		int max = 0, max_i = 0, max_j = 0;
		int final_i = 0, final_j = 0;
		int com_x, com_y;
		if(first || second) {
			for(int i = 0; i < 19; i++) {
				for(int j = 0; j < 19; j++) {
					com_x = (int)(Math.random()*3)+8;
					com_y = (int)(Math.random()*3)+8;
					if(map[com_x][com_y] == 0) {
							map[com_x][com_y] = co;
							
							for(int k = -1; k < 2; k++) {
								if(com_x-1>=0 && com_y+k <19 && com_y+k >=0)
									b_map[com_x-1][com_y+k] += 1;
								
							}for(int k = -1; k < 2; k++) {
								if(k == 0)
									continue;
								if(com_x>=0 && com_y+k <19 && com_y+k >=0)
									b_map[com_x][com_y+k] += 1;
							}
							for(int k = -1; k < 2; k++) { 
								if(com_y+k <19 && com_y+k >=0)
									b_map[com_x+1][com_y+k] += 1;
							}
							
							Yook yo = new Yook(new Ellipse2D.Double(com_x*30+5, com_y*30+5, 20, 20), co, com_x, com_y);
							yook.add(yo);
							count++;
							if(count == 2)
								second = false;
							p_count++;
							first = false;
							return;
					}
				}
			}
		}
		else {
			if(checkZero() == 0) {
				for(int i = 0; i < 19; i++) {
					for(int j = 0; j < 19; j++) {
						if(map[i][j] == co) {
							for(int k = -1; k < 2; k++) {
								if(i-1>=0 && j+k <19 && j+k >=0)
									b_map[i-1][j+k] += 1;
								
							}for(int k = -1; k < 2; k++) {
								if(k == 0)
									continue;
								if(i>=0 && j+k <19 && j+k >=0)
									b_map[i][j+k] += 1;
							}
							for(int k = -1; k < 2; k++) { 
								if(j+k <19 && j+k >=0)
									b_map[i+1][j+k] += 1;
							}
						}
					}
				}
			}
			check_brain();
			for(int i = 0; i < 19; i++) {
				for(int j = 0; j < 19; j++) {
					if(i==0 && j==0) {
						min = b_map[i][j];
						min_i = 0;
						min_j = 0;
						max = b_map[i][j];
						max_i = i;
						max_j = j;
					}
					if(min > b_map[i][j]) {
						if(map[i][j] == 0) {
							min_i = i;
							min_j = j;
							min = b_map[i][j];
						}
					}
					if(max < b_map[i][j]) {
						if(map[i][j] == 0) {
							max_i = i;
							max_j = j;
							max = b_map[i][j];
						}
					}
				}
			}
			if(Math.abs(min) > Math.abs(max)+20) {
				final_i = min_i;
				final_j = min_j;
			}else {
				final_i = max_i;
				final_j = max_j;
			}
			map[final_i][final_j] = co;
			for(int k = -1; k < 2; k++) {
				if(final_i-1>=0 && final_j+k <19 && final_j+k >=0)
					b_map[final_i-1][final_j+k] += 1;
				
			}for(int k = -1; k < 2; k++) {
				if(k == 0)
					continue;
				if(final_i>=0 && final_j+k <19 && final_j+k >=0)
					b_map[final_i][final_j+k] += 1;
			}
			for(int k = -1; k < 2; k++) { 
				if(final_j+k <19 && final_j+k >=0 && final_i+1 < 19 && final_i+1 >= 0)
					b_map[final_i+1][final_j+k] += 1;
			}
			
			Yook yo = new Yook(new Ellipse2D.Double(final_i*30+5, final_j*30+5, 20, 20), co, final_i, final_j);
			yook.add(yo);
			count++;
			p_count++;
			return;
		}
			
	}
	
	public int checkZero() {
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				if(map[i][j] == 0) {
					if(b_map[i][j] != 0) {
						return 1;
					}
				}
			}
		}
		
		return 0;
	}
	
	
	
	public void check_brain() {
		int h_count = 0;
		int ha_count = 0;
		int sam_count = 0;
		
		// ROW
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 19; j++) {
				if(map[i][j] == (co*-1) && map[i+1][j] == (co*-1) && map[i+2][j] == (co*-1) && map[i+3][j] == (co*-1)) {
					if(i > 0 && i+4 < 19) {
						if(b_map[i-1][j] < -50) {
							b_map[i-1][j] -= 5;
						}else {
							b_map[i-1][j] = -50;
						}
						
						if(b_map[i+4][j] < -50) {
							b_map[i+4][j] -= 5;
						}else {
							b_map[i+4][j] = -50;
						}
						
						if(map[i][j] == (co*-1) && map[i+1][j] == (co*-1) && map[i+2][j] == (co*-1) && map[i+3][j] == (co*-1) && map[i+4][j] == (co*-1)) {
							if(b_map[i-1][j] < -70) {
								b_map[i-1][j] -= 10;
							}else {
								b_map[i-1][j] = -70;
							}
							if(i+5 < 19) {	
								if(b_map[i+5][j] < -70) {
									b_map[i+5][j] -= 10;
								}else {
									b_map[i+5][j] = -70;
								}
							}
						}
						
					}
				}
				
				// JUWON
				if(map[i][j] == (co*-1) && map[i+1][j] == (co*-1)) {
					if(i-1 >= 0) {
						if(map[i-1][j] == 0) {
							if(i+5 < 19) {
								if(map[i+2][j] == 0) {
									if(map[i+3][j] == (co*-1) && map[i+4][j] == (co*-1)) {
										if(map[i+5][j] == 0) {
											for(int k = -1; k < 6; k+=3) {
												if(k+i >= 0 && k+i < 19) {
													if(map[k+i][j] == 0) {
														if(b_map[k+i][j] < -50)
															b_map[k+i][j] -= 5;
														else
															b_map[k+i][j] = -50;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				
				// JUWON2
				if(map[i][j] == (co*-1) && map[i+1][j] == (co*-1)) {
					if(i+5 < 19) {
						if(map[i+2][j] == 0 && map[i+3][j] == 0) {
							if(map[i+4][j] == (co*-1) && map[i+5][j] == (co*-1)) {
								if(b_map[i+2][j] < -50)
									b_map[i+2][j] -= 5;
								else
									b_map[i+2][j] = -50;
							}
						}
					}
				}
				
				// HARD (h_count)
				if(map[i][j] == (co*-1) && map[i+1][j] == (co*-1) && map[i+2][j] == (co*-1)) {
					sam_count++;
					if(i-1 >= 0 && i+3 < 19) {
						if(map[i-1][j] == co && map[i+3][j] == 0) {
							h_count++;
							if(h_count == 1 && ha_count == 1) {
								if(b_map[i+3][j] < -70)
									b_map[i+3][j] -= 10;
								else
									b_map[i+3][j] = -70;
							}
						}
					}
					if(i+3 < 19 && i-1 >= 0) {
						if(map[i+3][j] == co && map[i-1][j] == 0) {
							h_count++;
							if(h_count == 1 && ha_count == 1) {
								if(b_map[i-1][j] < -70)
									b_map[i-1][j] -= 10;
								else
									b_map[i-1][j] = -70;
							}
						}
					}
				}
				
				// HARD (ha_count)
				if(map[i][j] == (co*-1) && map[i+1][j] == (co*-1) && map[i+2][j] == (co*-1)) {
					if(i+3 < 19 && i-1 >= 0) {
						if(map[i+3][j] == 0 && map[i-1][j] == 0) {
							ha_count++;
							if(h_count == 1 && ha_count == 1) {
								if(b_map[i+3][j] < -70)
									b_map[i+3][j] -= 10;
								else
									b_map[i+3][j] = -70;
								
								if(b_map[i-1][j] < -70)
									b_map[i-1][j] -= 10;
								else
									b_map[i-1][j] = -70;
							}
						}
					}
				}
				
				if(map[i][j] == (co*-1) && map[i+1][j] == (co*-1) && map[i+2][j] == (co*-1)) {
					if(i+4 < 19) {
						if(map[i+4][j] == (co*-1)) {
							if(b_map[i+3][j] < -50) {
								b_map[i+3][j] -= 5;
							}else {
								b_map[i+3][j] = -50;
							}
						}
					}
					if(i+5 < 19) {
						if(map[i+5][j] == (co*-1)) {
							if(b_map[i+3][j] < -50) {
								b_map[i+3][j] -= 5;
							}else {
								b_map[i+3][j] = -50;
							}
						}
					}
					
					if(i-2 >= 0) {
						if(map[i-2][j] == (co*-1)) {
							if(b_map[i-1][j] < -50) {
								b_map[i-1][j] -= 5;
							}else {
								b_map[i-1][j] = -50;
							}
						}
					}
					if(i-3 >= 0) {
						if(map[i-3][j] == (co*-1)) {
							if(b_map[i-1][j] < -50) {
								b_map[i-1][j] -= 5;
							}else {
								b_map[i-1][j] = -50;
							}
						}
					}
				}
				
				if(map[i][j] == co && map[i+1][j] == co && map[i+2][j] == co && map[i+3][j] == co){
					if(i > 0) {
						if(b_map[i-1][j] > 50) {
							b_map[i-1][j] += 5;
						}else {
							b_map[i-1][j] = 50;
						}
						if(i+4 < 19) {
							if(b_map[i+4][j] > 50) {
								b_map[i+4][j] += 5;
							}else {
								b_map[i+4][j] = 50;
							}
						}
						
						if(i+4 < 19) {
							if(map[i][j] == co && map[i+1][j] == co && map[i+2][j] == co && map[i+3][j] == co && map[i+4][j] == co) {
								int value = 70;
								if(count == 0) {
									value = 100;
								}
								if(i-1 >= 0) {
									if(b_map[i-1][j] > value) {
										b_map[i-1][j] += 10;
									}else {
										b_map[i-1][j] = value;
									}
								}
								if(i+5 < 19) {
									if(b_map[i+5][j] > value) {
										b_map[i+5][j] += 10;
									}else {
										b_map[i+5][j] = value;
									}
								}
							}
						}
					}
				}
			}
		}
		
		// COLUMN
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 19; j++) {
				if(i+3 < 19) {
					if(map[j][i] == (co*-1) && map[j][i+1] == (co*-1) && map[j][i+2] == (co*-1) && map[j][i+3] == (co*-1)) {
						if(i > 0) {
							if(i-1 >= 0) {
								if(b_map[j][i-1] < -50) {
									b_map[j][i-1] -= 5;
								}else {
									b_map[j][i-1] = -50;
								}
							}
							if(i+4 < 19) {
								if(b_map[j][i+4] < -50) {
									b_map[j][i+4] -= 5;
								}else {
									b_map[j][i+4] = -50;
								}
							}
							
							if(i+4 < 19) {
								if(map[j][i] == (co*-1) && map[j][i+1] == (co*-1) && map[j][i+2] == (co*-1) && map[j][i+3] == (co*-1) && map[j][i+4] == (co*-1)) {
									
									if(i-1 >= 0) {
										if(b_map[j][i-1] < -70) {
											b_map[j][i-1] -= 10;
										}else {
											b_map[j][i-1] = -70;
										}
									}
									
									if(i+5 < 19) {
										if(b_map[j][i+5] < -70) {
											b_map[j][i+5] -= 10;
										}else {
											b_map[j][i+5] = -70;
										}
									}
								}
							}
						}
					}
				}
				
				// JUWON
				if(i+1 < 19) {
					if(map[j][i] == (co*-1) && map[j][i+1] == (co*-1)) {
						if(i-1 >= 0) {
							if(map[j][i-1] == 0) {
								if(i+5 < 19) {
									if(map[j][i+2] == 0) {
										if(map[j][i+3] == (co*-1) && map[j][i+4] == (co*-1)) {
											if(map[j][i+5] == 0) {
												for(int k = -1; k < 6; k+=3) {
													if(map[j][k+i] == 0) {
														if(k+i < 19 && k+i >= 0) {
															if(b_map[j][k+i] < -50)
																b_map[j][k+i] -= 5;
															else
																b_map[j][k+i] = -50;
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				
				// JUWON2
				if(i+1 < 19) {
					if(map[j][i] == (co*-1) && map[j][i+1] == (co*-1)) {
						if(i+5 < 19) {
							if(map[j][i+2] == 0 && map[i+3][j] == 0) {
								if(map[j][i+4] == (co*-1) && map[j][i+5] == (co*-1)) {
									if(b_map[j][i+2] < -50)
										b_map[j][i+2] -= 5;
									else
										b_map[j][i+2] = -50;
								}
							}
						}
					}
				}
				
				// HARD (h_count)
				if(i+2 < 19) {
					if(map[j][i] == (co*-1) && map[j][i+1] == (co*-1) && map[j][i+2] == (co*-1)) {
						if(i-1 >= 0 && i+3 < 19) {
							if(map[j][i-1] == co && map[j][i+3] == 0) {
								h_count++;
								if(h_count == 1 && ha_count == 1) {
									if(b_map[j][i+3] < -70)
										b_map[j][i+3] -= 10;
									else
										b_map[j][i+3] = -70;
								}
							}
						}
						if(i+3 < 19 && i-1 >= 0) {
							if(map[j][i+3] == co && map[j][i-1] == 0) {
								h_count++;
								if(h_count == 1 && ha_count == 1) {
									if(b_map[j][i-1] < -70)
										b_map[j][i-1] -= 10;
									else
										b_map[j][i-1] = -70;
								}
							}
						}
					}
				}
				
				// HARD (ha_count)
				if(i+2 < 19) {
					if(map[j][i] == (co*-1) && map[j][i+1] == (co*-1) && map[j][i+2] == (co*-1)) {
						if(i+3 < 19 && i-1 >= 0) {
							if(map[j][i+3] == 0 && map[j][i-1] == 0) {
								ha_count++;
								if(h_count == 1 && ha_count == 1) {
									if(b_map[j][i+3] < -70)
										b_map[j][i+3] -= 10;
									else
										b_map[j][i+3] = -70;
									
									if(b_map[j][i-1] < -70)
										b_map[j][i-1] -= 10;
									else
										b_map[j][i-1] = -70;
								}
							}
						}
					}
				}
				
				if(i+2 < 19) {
					if(map[j][i] == (co*-1) && map[j][i+1] == (co*-1) && map[j][i+2] == (co*-1)) {
						if(i+4 < 19) {
							if(map[j][i+4] == (co*-1)) {
								if(b_map[j][i+3] < -50) {
									b_map[j][i+3] -= 5;
								}else {
									b_map[j][i+3] = -50;
								}
							}
						}
						if(i+5 < 19) {
							if(map[j][i+5] == (co*-1)) {
								if(b_map[j][i+3] < -50) {
									b_map[j][i+3] -= 5;
								}else {
									b_map[j][i+3] = -50;
								}
							}
						}
						
						if(i-2 >= 0) {
							if(map[j][i-2] == (co*-1)) {
								if(b_map[j][i-1] < -50) {
									b_map[j][i-1] -= 5;
								}else {
									b_map[j][i-1] = -50;
								}
							}
						}
						if(i-3 >= 0) {
							if(map[j][i-3] == (co*-1)) {
								if(b_map[j][i-1] < -50) {
									b_map[j][i-1] -= 5;
								}else {
									b_map[j][i-1] = -50;
								}
							}
						}
					}
				}
				
				if(i+3 < 19) {
					if(map[j][i] == co && map[j][i+1] == co && map[j][i+2] == co && map[j][i+3] == co){
						if(i > 0) {
							if(i-1 >= 0) {
								if(b_map[j][i-1] > 50) {
									b_map[j][i-1] += 5;
								}else {
									b_map[j][i-1] = 50;
								}
							}
							
							if(i+4 < 19) {
								if(b_map[j][i+4] > 50) {
									b_map[j][i+4] += 5;
								}else {
									b_map[j][i+4] = 50;
								}
							}
							
							if(i+4 < 19) {
								if(map[j][i] == co && map[j][i+1] == co && map[j][i+2] == co && map[j][i+3] == co && map[j][i+4] == co) {
									int value = 70;
									if(count == 0) {
										value = 100;
									}
									if(i-1 >= 0) {
										if(b_map[j][i-1] > value) {
											b_map[j][i-1] += 10;
										}else {
											b_map[j][i-1] = value;
										}
									}
									
									if(i+5 < 19) {
										if(b_map[j][i+5] > value) {
											b_map[j][i+5] += 10;
										}else {
											b_map[j][i+5] = value;
										}
									}
								}
							}
							
						}
					}
				}
			}
		}
		
		// Dialog_Line
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				if(map[i][j] == (co*-1) && map[i+1][j+1] == (co*-1) && map[i+2][j+2] == (co*-1) && map[i+3][j+3] == (co*-1)) {
					if(i > 0 && j > 0) {
						if(i-1 >= 0 && j-1 >= 0) {
							if(b_map[i-1][j-1] < -50) {
								b_map[i-1][j-1] -= 5;
							}else {
								b_map[i-1][j-1] = -50;
							}
						}
						
						if(i+4 < 19 && j+4 < 19) {
							if(b_map[i+4][j+4] < -50) {
								b_map[i+4][j+4] -= 5;
							}else {
								b_map[i+4][j+4] = -50;
							}
						}
						
						if(i+4 < 19) {
							if(map[i][j] == co && map[i+1][j+1] == (co*-1) && map[i+2][j+2] == (co*-1) && map[i+3][j+3] == (co*-1) && map[i+4][j+4] == (co*-1)) {
								if(i-1 >= 0 && j-1 >= 0) {
									if(b_map[i-1][j-1] < -70) {
										b_map[i-1][j-1] -= 10;
									}else {
										b_map[i-1][j-1] = -70;
									}
								}
								
								if(i+5 < 19 && j+5 < 19) {
									if(b_map[i+5][j+5] < -70) {
										b_map[i+5][j+5] -= 10;
									}else {
										b_map[i+5][j+5] = -70;
									}
								}
							}
						}
					}
				}
				
				// JUWON
				if(map[i][j] == co && map[i+1][j+1] == (co*-1)) {
					if(i-1 >= 0 && j-1 >= 0) {
						if(map[i-1][j-1] == 0) {
							if(map[i+2][j+2] == 0) {
								if(i+5 < 19 && j+5 < 19) {
									if(map[i+3][j+3] == (co*-1) && map[i+4][j+4] == (co*-1)) {
										if(map[i+5][j+5] == 0) {
											for(int k = -1; k < 6; k+=3) {
												if(map[k+i][k+j] == 0) {
													if(k+i < 19 && k+i >= 0 && k+j < 19 && k+j >= 0) {
														if(b_map[k+i][j+k] < -50)
															b_map[k+i][j+k] -= 5;
														else
															b_map[k+i][j+k] = -50;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				
				// JUWON2
				if(map[i][j] == (co*-1) && map[i+1][j+1] == (co*-1)) {
					if(i+5 < 19 && j+5 < 19) {
						if(map[i+2][j+2] == 0 && map[i+3][j+3] == 0) {
							if(map[i+4][j+4] == (co*-1) && map[i+5][j+5] == (co*-1)) {
								if(b_map[i+2][j+2] < -50)
									b_map[i+2][j+2] -= 5;
								else
									b_map[i+2][j+2] = -50;
							}
						}
					}
				}
				
				// HARD (h_count)
				if(map[i][j] == co && map[i+1][j+1] == (co*-1) && map[i+2][j+2] == (co*-1)) {
					if(i-1 >= 0 && j-1 >= 0 && i+3 < 19 && j+3 < 19) {
						if(map[i-1][j-1] == co && map[i+3][j+3] == 0) {
							h_count++;
							if(h_count == 1 && ha_count == 1) {
								if(b_map[i+3][j+3] < -70)
									b_map[i+3][j+3] -= 10;
								else
									b_map[i+3][j+3] = -70;
							}
						}
					}
					if(i+3 < 19 && j+3 < 19 && i-1 >= 0 && j-1 >= 0) {
						if(map[i+3][j+3] == co && map[i-1][j-1] == 0) {
							h_count++;
							if(h_count == 1 && ha_count == 1) {
								if(b_map[i-1][j-1] < -70)
									b_map[i-1][j-1] -= 10;
								else
									b_map[i-1][j-1] = -70;
							}
						}
					}
				}
				
				// HARD (ha_count)
				if(map[i][j] == co && map[i+1][j+1] == (co*-1) && map[i+2][j+2] == (co*-1)) {
					if(i+3 < 19 && j+3 < 19 && i-1 >= 0 && j-1 >= 0) {
						if(map[i+3][j+3] == 0 && map[i-1][j-1] == 0) {
							ha_count++;
							if(h_count == 1 && ha_count == 1) {
								if(b_map[i+3][j+3] < -70)
									b_map[i+3][j+3] -= 10;
								else
									b_map[i+3][j+3] = -70;
								
								if(b_map[i-1][j-1] < -70)
									b_map[i-1][j-1] -= 10;
								else
									b_map[i-1][j-1] = -70;
							}
						}
					}
				}
				
				if(map[i][j] == (co*-1) && map[i+1][j+1] == (co*-1) && map[i+2][j+2] == (co*-1)) {
					if(i+4 < 19 && j+4 < 19) {
						if(map[i+4][j+4] == (co*-1)) {
							if(b_map[i+3][j+3] < -50) {
								b_map[i+3][j+3] -= 5;
							}else {
								b_map[i+3][j+3] = -50;
							}
						}
					}
					if(i+5 < 19 && j+5 < 19) {
						if(map[i+5][j+5] == (co*-1)) {
							if(b_map[i+3][j+3] < -50) {
								b_map[i+3][j+3] -= 5;
							}else {
								b_map[i+3][j+3] = -50;
							}
						}
					}
					
					if(i-2 >= 0 && j-2 >= 0) {
						if(map[i-2][j-2] == (co*-1)) {
							if(b_map[i-1][j-1] < -50) {
								b_map[i-1][j-1] -= 5;
							}else {
								b_map[i-1][j-1] = -50;
							}
						}
					}
					if(i-3 >= 0 && j-3 >= 0) {
						if(map[i-3][j-3] == (co*-1)) {
							if(b_map[i-1][j-1] < -50) {
								b_map[i-1][j-1] -= 5;
							}else {
								b_map[i-1][j-1] = -50;
							}
						}
					}
				}
				if(map[i][j] == co && map[i+1][j+1] == co && map[i+2][j+2] == co && map[i+3][j+3] == co){
					if(i > 0 && j > 0) {
						if(b_map[i-1][j-1] > 50) {
							b_map[i-1][j-1] += 5;
						}else {
							b_map[i-1][j-1] = 50;
						}
						
						if(b_map[i+4][j+4] > 50) {
							b_map[i+4][j+4] += 5;
						}else {
							b_map[i+4][j+4] = 50;
						}
						
						if(map[i][j] == co && map[i+1][j+1] == co && map[i+2][j+2] == co && map[i+3][j+3] == co && map[i+4][j+4] == co) {
							int value = 70;
							if(count == 0) {
								value = 100;
							}
							if(b_map[i-1][j-1] > value) {
								b_map[i-1][j-1] += 10;
							}else {
								b_map[i-1][j-1] = value;
							}
							
							if(b_map[i+5][j+5] > value) {
								b_map[i+5][j+5] += 10;
							}else {
								b_map[i+5][j+5] = value;
							}
						}
					}
				}
			}
		}
		
		// Dialog_Line2
		for(int i = 0; i < 16; i++) {
			for(int j = 18; j > 6; j--) {
				if(map[i][j] == (co*-1) && map[i+1][j-1] == (co*-1) && map[i+2][j-2] == (co*-1) && map[i+3][j-3] == (co*-1)) {
					if(i > 0 && j+1 < 18) {
						if(b_map[i-1][j+1] < -50) {
							b_map[i-1][j+1] -= 5;
						}else {
							b_map[i-1][j+1] = -50;
						}
						
						if(b_map[i+4][j-4] < -50) {
							b_map[i+4][j-4] -= 5;
						}else {
							b_map[i+4][j-4] = -50;
						}
						
						
						if(map[i][j] == (co*-1) && map[i+1][j-1] == (co*-1) && map[i+2][j-2] == (co*-1) && map[i+3][j-3] == (co*-1) && map[i+4][j-4] == (co*-1)) {
							if(b_map[i-1][j+1] < -70) {
								b_map[i-1][j+1] -= 10;
							}else {
								b_map[i-1][j+1] = -70;
							}
							
							if(b_map[i+5][j-5] < -70) {
								b_map[i+5][j-5] -= 10;
							}else {
								b_map[i+5][j-5] = -70;
							}
						}
					}
				}
				
				// JUWON
				if(map[i][j] == co && map[i+1][j-1] == (co*-1)) {
					if(i-1 >= 0 && j+1 < 19) {
						if(map[i-1][j+1] == 0) {
							if(map[i+2][j-2] == 0) {
								if(i+5 < 19 && j-5 >= 0) {
									if(map[i+3][j-3] == (co*-1) && map[i+4][j-4] == (co*-1)) {
										if(map[i+5][j-5] == 0) {
											for(int k = -1; k < 6; k+=3) {
												if(map[k+i][j-k] == 0) {
													if(b_map[k+i][j-k] < -50)
														b_map[k+i][j-k] -= 5;
													else
														b_map[k+i][j-k] = -50;
												}
											}
										}
									}
								}
							}
						}
					}
				}
				
				// JUWON2
				if(map[i][j] == (co*-1) && map[i+1][j-1] == (co*-1)) {
					if(i+5 < 19 && j-5 >= 0) {
						if(map[i+2][j-2] == 0 && map[i+3][j-3] == 0) {
							if(map[i+4][j-4] == (co*-1) && map[i+5][j-5] == (co*-1)) {
								if(b_map[i+2][j-2] < -50)
									b_map[i+2][j-2] -= 5;
								else
									b_map[i+2][j-2] = -50;
							}
						}
					}
				}
				
				// HARD (h_count)
				if(map[i][j] == (co*-1) && map[i+1][j-1] == (co*-1) && map[i+2][j-2] == (co*-1)) {
					if(i-1 >= 0 && j+1 < 19 && i+3 < 19 && j-3 >= 0) {
						if(map[i-1][j+1] == co && map[i+3][j-3] == 0) {
							h_count++;
							if(h_count == 1 && ha_count == 2) {
								if(b_map[i+3][j-3] < -70)
									b_map[i+3][j-3] -= 10;
								else
									b_map[i+3][j-3] = -70;
							}
						}
					}
					if(i+3 < 19 && j-3 >= 0 && i-1 >= 0 && j+1 < 19) {
						if(map[i+3][j-3] == co && map[i-1][j+1] == 0) {
							h_count++;
							if(h_count == 1 && ha_count == 1) {
								if(b_map[i-1][j+1] < -70)
									b_map[i-1][j+1] -= 10;
								else
									b_map[i-1][j+1] = -70;
							}
						}
					}
				}
				
				// HARD (ha_count)
				if(map[i][j] == (co*-1) && map[i+1][j-1] == (co*-1) && map[i+2][j-2] == (co*-1)) {
					if(i-1 >= 0 && j+1 < 19 && i+3 < 19 && j-3 >= 0) {
						if(map[i-1][j+1] == 0 && map[i+3][j-3] == 0) {
							ha_count++;
							if(h_count == 1 && ha_count == 1) {
								if(b_map[i+3][j-3] < -70)
									b_map[i+3][j-3] -= 10;
								else
									b_map[i+3][j-3] = -70;
								
								if(b_map[i-1][j+1] < -70)
									b_map[i-1][j+1] -= 10;
								else
									b_map[i-1][j+1] = -70;
							}
						}
					}
				}
				
				if(map[i][j] == (co*-1) && map[i+1][j-1] == (co*-1) && map[i+2][j-2] == (co*-1)) {
					if(i+4 < 19 && j-4 >= 0) {
						if(map[i+4][j-4] == (co*-1)) {
							if(b_map[i+3][j-3] < -50) {
								b_map[i+3][j-3] -= 5;
							}else {
								b_map[i+3][j-3] = -50;
							}
						}
					}
					if(i+5 < 19 && j-5 >= 0) {
						if(map[i+5][j-5] == (co*-1)) {
							if(b_map[i+3][j-3] < -50) {
								b_map[i+3][j-3] -= 5;
							}else {
								b_map[i+3][j-3] = -50;
							}
						}
					}
					
					if(i-2 >= 0 && j+2 < 19) {
						if(map[i-2][j+2] == (co*-1)) {
							if(b_map[i-1][j+1] < -50) {
								b_map[i-1][j+1] -= 5;
							}else {
								b_map[i-1][j+1] = -50;
							}
						}
					}
					if(i-3 >= 0 && j+3 < 19) {
						if(map[i-3][j+3] == (co*-1)) {
							if(b_map[i-1][j+1] < -50) {
								b_map[i-1][j+1] -= 5;
							}else {
								b_map[i-1][j+1] = -50;
							}
						}
					}
				}
				if(map[i][j] == co && map[i+1][j-1] == co && map[i+2][j-2] == co && map[i+3][j-3] == co){
					if(i > 0 && j+1 < 18) {
						if(b_map[i-1][j+1] > 50) {
							b_map[i-1][j+1] += 5;
						}else {
							b_map[i-1][j+1] = 50;
						}
							
						if(b_map[i+4][j-4] > 50) {
							b_map[i+4][j-4] += 5;
						}else {
							b_map[i+4][j-4] = 50;
						}
						
						if(map[i][j] == co && map[i+1][j-1] == co && map[i+2][j-2] == co && map[i+3][j-3] == co && map[i+4][j-4] == co) {
							int value = 70;
							if(count == 0) {
								value = 100;
							}
							if(b_map[i-1][j+1] > value) {
								b_map[i-1][j+1] += 10;
							}else {
								b_map[i-1][j+1] = value;
							}
							
							if(b_map[i+5][j-5] > value) {
								b_map[i+5][j-5] += 10;
							}else {
								b_map[i+5][j-5] = value;
							}
						}
					}
				}
			}
		}
	}
	
	public void check() {
		if(yook != null) {
			if(murugi_1 == 1 && player == 1) {
				if(m_1 == 2) {
					JOptionPane.showMessageDialog(null, "무르기 2번을 모두 사용하셨습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}else if(count == 0) {
					JOptionPane.showMessageDialog(null, "이전 돌은 무르기가 불가능합니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int result = JOptionPane.showConfirmDialog(null, "CHEOLSU님의 무르기 신청을 수락하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) {
							if(black == 1 && m_1 < 2) {
								int i;
								for(i = yook.size()-1; i >= 0; i--) {
									if(yook.get(i).color == -1) {
										map[yook.get(i).x][yook.get(i).y] = 0;
										yook.remove(i);
										m_1++;
										if(count > 0) {
											count--;
											p_count--;
										}
										break;
									}
								}
								if(i < 0) {
									JOptionPane.showMessageDialog(null, "더 이상 무를 수 있는 돌이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
								}
						
						}else {
							int i;
							for(i = yook.size()-1; i >= 0; i--) {
								if(yook.get(i).color == 1) {
									if(yook.get(i).x != 9 && yook.get(i).y != 9) {
										map[yook.get(i).x][yook.get(i).y] = 0;
										yook.remove(i);
										m_1++;
										if(count > 0) {
											count--;
											p_count--;
										}
										break;
									}
								}
							}
							if(i < 0) {
								JOptionPane.showMessageDialog(null, "더 이상 무를 수 있는 돌이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
							}
						}
					}
				}
			}
			
			else if(murugi_2 == 1 && player == -1) {
				if(m_2 == 2) {
					JOptionPane.showMessageDialog(null, "무르기 2번을 모두 사용하셨습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}else if(count == 2) {
					JOptionPane.showMessageDialog(null, "이전 돌은 무르기가 불가능합니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int result = JOptionPane.showConfirmDialog(null, "JANGGU님의 무르기 신청을 수락하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						if(black == 1 && m_2 < 2) {
								int i;
								for(i = yook.size()-1; i >= 0; i--) {
									if(yook.get(i).color == 1) {
										if(yook.get(i).x != 9 && yook.get(i).y != 9) {
											map[yook.get(i).x][yook.get(i).y] = 0;
											yook.remove(i);
											m_2++;
											if(count > 0) {
												count--;
												p_count--;
											}
											break;
										}
									}
								}
								if(i < 0)
									JOptionPane.showMessageDialog(null, "더 이상 무를 수 있는 돌이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
						}else {
							int i;
							for(i = yook.size()-1; i >= 0; i--) {
								if(yook.get(i).color == -1) {
									map[yook.get(i).x][yook.get(i).y] = 0;
									yook.remove(i);
									m_2++;
									if(count > 0) {
										count--;
										p_count--;
									}
									break;
								}
							}
							if(i < 0)
								JOptionPane.showMessageDialog(null, "더 이상 무를 수 있는 돌이 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
						}
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
				JOptionPane.showMessageDialog(null, "시작버튼을 눌러주십시오.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if(re == 1 && start_n == 0) {
				JOptionPane.showMessageDialog(null, "시작버튼을 눌러주십시오.", "경고", JOptionPane.WARNING_MESSAGE);
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
					map[yo_x][yo_y] = co;
					
					for(int k = -1; k < 2; k++) {
						if(yo_x-1>=0 && yo_y+k <19 && yo_y+k>=0)
							b_map[yo_x-1][yo_y+k] -= 1;
						
					}for(int k = -1; k < 2; k++) {
						if(k == 0)
							continue;
						if(yo_x>=0 && yo_y+k <19 && yo_y+k>=0)
							b_map[yo_x][yo_y+k] -= 1;
					}
					for(int k = -1; k < 2; k++) { 
						if(yo_y+k <19 && yo_y+k>=0)
							b_map[yo_x+1][yo_y+k] -= 1;
					}
					
					Yook yo = new Yook(new Ellipse2D.Double(yo_x*30+5, yo_y*30+5, 20, 20), co, yo_x, yo_y);
					yook.add(yo);
					count++;
					p_count++;
				}else {
					JOptionPane.showMessageDialog(null, "같은 곳에 둘 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
					int p = p_count - 1;
					if(p == 0) {
						player *= -1;
					}
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
		
		public void mouseReleased(MouseEvent e) {
			if(brain) {
				b_count++;
				if(b_count == 2) {
					callBrain();
					b_count = 0;
				}
			}
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
		for(int i = 0; i < 14; i++) {
			for(int j = 0; j < 19; j++) {
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
