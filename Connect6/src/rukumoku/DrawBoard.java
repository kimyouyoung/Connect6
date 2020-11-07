package rukumoku;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

// read board information and draw
public class DrawBoard {
	
	Board board = new Board();
	
	private static final int MAX = 99999;
	private static final int MIN = -99999;
	private static final int EMPTY = 0;
	private static final int WHITE = 1;
	private static final int BLACK = 2;
	private static final int RED = 3;
	
	boolean start_check = false;
	int our_player = 0;
	int turn = BLACK;
	int count = 0;
	boolean first_black = false;
	JFrame frame;
	
	public DrawBoard() {
		
		frame = new JFrame();
		frame.setSize(1100, 1100);
		frame.setTitle("CONNECT6");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.WHITE);
		
		JPanel panel = new JPanel();
		panel.setBounds(80, 30, 920, 70);
		panel.setBackground(Color.WHITE);
				
		Checkbox black = new Checkbox("BLACK");
		Checkbox white = new Checkbox("WHITE");
		black.setForeground(Color.BLACK);
		white.setForeground(Color.BLACK);
		
		black.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				white.setState(false);
				our_player = BLACK;
				board.ourPlayer(our_player);
			}
		});
				
				
		white.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				black.setState(false);
				our_player = WHITE;
				board.ourPlayer(our_player);
			}
		});
				
		panel.add(black);
		panel.add(white);
		
		JButton start = new JButton("START");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!black.getState() && !white.getState())
					JOptionPane.showMessageDialog(null, "플레이어를 고르시오.");
				else {
					start_check = true;
					frame.repaint();
				}
			}
		});
		
		panel.add(start);
		
		JPanel connect6_board = new Connect6Board();
		connect6_board.setBounds(270, 100, 570, 570);
		connect6_board.setBackground(new Color(244, 203, 134));
		
		frame.add(panel);
		frame.add(connect6_board);
		frame.setVisible(true);
		
	}
	
	class Connect6Board extends JPanel{
		
		private static final long serialVersionUID = 1L;

		public Connect6Board() {
			MyMouseListener ml = new MyMouseListener();
			this.addMouseListener(ml);
			this.addMouseMotionListener(ml);
		}
		
		public void paintComponent(Graphics g) {
			
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g2);
			
			// draw Line
			int x = 15, y = 15, x2 = 15, y2 =15;
			for(int i = 0; i < 19; i++) {	
				g2.drawLine(x, y, x, 554);
				g2.drawLine(x2, y2, 554, y2);
				x += 30;
				y2 += 30;
			}
			// draw Oval
			int e_x = 105, e_y = 105;
			for(int k = 0; k < 3; k++) 
				for(int l = 0; l < 3; l++) 
					g2.fillOval((e_x+180*k)-4, (e_y+180*l)-4, 8, 8);
			
			int[][] move_board = board.getBoard();
			
			if(!first_black && our_player == BLACK) {
				first_black = true;
				if(move_board[9][9] == EMPTY) {
					board.move(9, 9, BLACK);
					board.ourMoves.add(new Coordinates(9, 9)) ;
					board.ourCoordinates = board.updateBoard(board.ourMoves);
					turn = WHITE;
				}
			}
			
			// move
			for(int i = 0; i < move_board.length; i++) {
				for(int j = 0; j < move_board[i].length; j++) {
					if(move_board[i][j] == EMPTY)
						continue;
					
					Shape shape = new Ellipse2D.Double(i*30+5, j*30+5, 20, 20);
					if(move_board[i][j] == BLACK) 
						g2.setColor(Color.black);
					else if(move_board[i][j] == WHITE)
						g2.setColor(Color.white);
					else
						g2.setColor(Color.red);
					g2.fill(shape);
				}
			}
		}
		
	}
	
	class MyMouseListener extends MouseAdapter implements MouseListener{
		
		public void mousePressed(MouseEvent e) {
			
			int x = e.getX() / 30;
			int y = e.getY() / 30;
			int[][] board_check = board.getBoard();
			
			if(e.getX() < 556 && e.getY() < 556) {
				
				if(board_check[x][y] == 0) {
					if(!start_check) {
						board.move(x, y, RED);
					}else {
						if(turn == 3 - our_player) {
							board.move(x, y, 3 - our_player);
							if(board.oppMoves.size() > 2)
								board.oppMoves.remove(0);
							board.oppMoves.add(new Coordinates(x, y));
							board.oppCoordinates = board.updateBoard(board.oppMoves);
							board.ourCoordinates = board.deleteElement(x, y, board.ourCoordinates);
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "같은 곳에 둘 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
			}
			frame.repaint();
			
		}
		
		public void mouseReleased(MouseEvent e) {
			
			// our_player = WHITE;
			if(!first_black && start_check) {
				turn = our_player;
				first_black = true;
				count = 1;
			}
			if(start_check){
				count++;
				if(count == 2) {
					count = 0;
					turn = our_player;
					
					AlphaBeta.alphabeta(board, 0, true, turn, MIN, MAX, 0);
					Coordinates firstMove = AlphaBeta.bestMove;
					if(board.ourMoves.size() > 2)
						board.ourMoves.remove(0);
					board.ourMoves.add(firstMove);
					board.ourCoordinates = board.updateBoard(board.ourMoves);
					board.oppCoordinates = board.deleteElement(firstMove.x, firstMove.y, board.oppCoordinates);
					board.move(firstMove.x, firstMove.y, turn);

					AlphaBeta.alphabeta(board, 0, true, turn, MIN, MAX, 0); 
					Coordinates secondMove = AlphaBeta.bestMove;
					if(board.ourMoves.size() > 2)
						board.ourMoves.remove(0);
					board.ourMoves.add(secondMove);
					board.ourCoordinates = board.updateBoard(board.ourMoves);
					board.oppCoordinates = board.deleteElement(secondMove.x, secondMove.y, board.oppCoordinates);
					board.move(secondMove.x, secondMove.y, turn);
					
					turn = 3 - our_player;	
					
					
				}
			}
			frame.repaint();
			
			if(board.isOver() == WHITE) {
				JOptionPane.showMessageDialog(null, "백돌 승!", "승리", JOptionPane.WARNING_MESSAGE);
				return;
			}else if(board.isOver() == BLACK) {
				JOptionPane.showMessageDialog(null, "흑돌 승!", "승리", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
	}

}
