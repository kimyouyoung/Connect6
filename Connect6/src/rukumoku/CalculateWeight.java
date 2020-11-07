package rukumoku;

public class CalculateWeight {
	
	int[][] weights = new int[19][19];
	int our_player;
	int opponent;
	
	public void setOurPlayer(int our_player) {
		this.our_player = our_player;
		this.opponent = 3 - this.our_player;
	}
	
	static boolean IsInOfBounds(int x, int y) {
		if(0 <= x && x < 19 && 0 <= y && y < 19)
			return true;
		
		return false;
	}
	
	//board와 필요한 얘들 받아서 계산 후 리턴 
	static int evaluate(Board board, boolean maxPlayer, int color) {
		
		int b[][] = board.board;
		int weight = 0;
		int curx, cury;
		
		// for ROW
		int count_row;
		for(int y = 0; y < 19; y++) {
			for(int x = 0; x < 19; x++) {
				if(IsInOfBounds(x, y) && b[x][y] == color) {
					count_row = 1;
					curx = x + 1;
					cury = y;
					while(IsInOfBounds(curx, cury) && b[curx][cury] == color) {
						count_row++;
						curx++;
					}
					if(count_row == 6) {
						weight += 1000;
						x += 6;
					}else if(count_row == 5) {
						weight += 500;
						x += 5;
					}else if(count_row == 4) {
						weight += 400;
						x += 4;
					}else if(count_row == 3) {
						weight += 300;
						x += 3;
					}else if(count_row == 2) {
						weight += 100;
						x += 2;
					}else if(count_row == 1) {
						weight += 50;
						x += 1;
					}
				}
			}
		}
		
		// for COLUMN
		int count_col;
		for(int x = 0; x < 19; x++) {
			for(int y = 0; y < 19; y++) {
				if(IsInOfBounds(x, y) && b[x][y] == color) {
					count_col = 1;
					curx = x;
					cury = y + 1;
					while(IsInOfBounds(curx, cury) && b[curx][cury] == color) {
						count_col++;
						cury++;
					}
					if(count_col == 6) {
						weight += 1000;
						y += 6;
					}else if(count_col == 5) {
						weight += 500;
						y += 5;
					}else if(count_col == 4) {
						weight += 400;
						y += 4;
					}else if(count_col == 3) {
						weight += 300;
						y += 3;
					}else if(count_col == 2) {
						weight += 100;
						y += 2;
					}else if(count_col == 1) {
						weight += 50;
						y += 1;
					}
				}
			}
		}
		
		// for Down RIGHT DIAGONAL(LT -> RB)
		int count_rdia;
		int rx;
		for(int x = 0; x < 19; x++) {
			rx = x;
			for(int y = 18; y >= 0; y--) {
				if(IsInOfBounds(rx, y) && b[rx][y] == color) {
					count_rdia = 1;
					curx = rx - 1;
					cury = y - 1;
					while(IsInOfBounds(curx, cury) && b[curx][cury] == color) {
						count_rdia++;
						curx--;
						cury--;
					}
					if(count_rdia == 6) {
						weight += 1000;
						rx -= 7;
						y -= 6;
					}else if(count_rdia == 5) {
						weight += 500;
						rx -= 6;
						y -= 5;
					}else if(count_rdia == 4) {
						weight += 400;
						rx -= 5;
						y -= 4;
					}else if(count_rdia == 3) {
						weight += 300;
						rx -= 4;
						y -= 3;
					}else if(count_rdia == 2) {
						weight += 100;
						rx -= 3;
						y -= 2;
					}else if(count_rdia == 1) {
						weight += 50;
						rx -= 2;
						y -= 1;
					}
				}
			}
		}
		
		// for Up RIGHT DIAGONAL(LT -> RB)
		int count_rdia_up;
		int rx_u;
		for(int x = 17; x >= 0; x--) {
			rx_u = x;
			for(int y = 0; y < 18; y++) {
				if(IsInOfBounds(rx_u, y) && b[rx_u][y] == color) {
					count_rdia_up = 1;
					curx = rx_u + 1;
					cury = y + 1;
					while(IsInOfBounds(curx, cury) && b[curx][cury] == color) {
						count_rdia_up++;
						curx++;
						cury++;
					}
					if(count_rdia_up == 6) {
						weight += 1000;
						rx_u += 7;
						y += 6;
					}else if(count_rdia_up == 5) {
						weight += 500;
						rx_u += 6;
						y += 5;
					}else if(count_rdia_up == 4) {
						weight += 400;
						rx_u += 5;
						y += 4;
					}else if(count_rdia_up == 3) {
						weight += 300;
						rx_u += 4;
						y += 3;
					}else if(count_rdia_up == 2) {
						weight += 100;
						rx_u += 3;
						y += 2;
					}else if(count_rdia_up == 1) {
						weight += 50;
						rx_u += 2;
						y += 1;
					}
				}
			}
		}
		
		
		
		// for Down LEFT DIAGONOL(LB -> RT)
		int count_ldia;
		int lx;
		for(int x = 18; x >= 0; x--) {
			lx = x;
			for(int y = 18; y >= 0; y--) {
				if(IsInOfBounds(lx, y) && b[lx][y] == color) {
					count_ldia = 1;
					curx = lx + 1;
					cury = y - 1;
					while(IsInOfBounds(curx, cury) && b[curx][cury] == color) {
						count_ldia++;
						curx++;
						cury--;
					}
					if(count_ldia == 6) {
						weight += 1000;
						lx += 7;
						y -= 6;
					}else if(count_ldia == 5) {
						weight += 500;
						lx += 6;
						y -= 5;
					}else if(count_ldia == 4) {
						weight += 400;
						lx += 5;
						y -= 4;
					}else if(count_ldia == 3) {
						weight += 300;
						lx += 4;
						y -= 3;
					}else if(count_ldia == 2) {
						weight += 100;
						lx += 3;
						y -= 2;
					}else if(count_ldia == 1) {
						weight += 50;
						lx += 2;
						y -= 1;
					}
				
				}
			}
		}
		
		// for Up LEFT DIAGONOL(LB -> RT)
		int count_ldia_up;
		int lx_u;
		for(int x = 0; x < 18; x++) {
			lx_u = x;
			for(int y = 0; y < 18; y++) {
				if(IsInOfBounds(lx_u, y) && b[lx_u][y] == color) {
					count_ldia_up = 1;
					curx = lx_u - 1;
					cury = y + 1;
					while(IsInOfBounds(curx, cury) && b[curx][cury] == color) {
						count_ldia_up++;
						curx--;
						cury++;
					}
					if(count_ldia_up == 6) {
						weight += 1000;
						lx_u -= 7;
						y += 6;
					}else if(count_ldia_up == 5) {
						weight += 500;
						lx_u -= 6;
						y += 5;
					}else if(count_ldia_up == 4) {
						weight += 400;
						lx_u -= 5;
						y += 4;
					}else if(count_ldia_up == 3) {
						weight += 300;
						lx_u -= 4;
						y += 3;
					}else if(count_ldia_up == 2) {
						weight += 100;
						lx_u -= 3;
						y += 2;
					}else if(count_ldia_up == 1) {
						weight += 50;
						lx_u -= 2;
						y += 1;
					}
				
				}
			}
		}
		
		if(!maxPlayer) {
			weight *= -1;
		}
		
		return weight;
	}

}