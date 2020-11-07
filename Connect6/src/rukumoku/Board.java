package rukumoku;

import java.util.*;

public class Board {
	
	private static final int EMPTY = 0;
	private static final int WHITE = 1;
	private static final int BLACK = 2;
	private static final int RED = 3;
	
	int[][] board;
	int our_player;
	
	ArrayList<Coordinates> ourCoordinates;
	ArrayList<Coordinates> oppCoordinates;
	ArrayList<Coordinates> ourMoves;
	ArrayList<Coordinates> oppMoves;
	
	//New board
	Board(){
		this.board = new int[19][19];
		this.ourCoordinates = new ArrayList<>();
		this.oppCoordinates = new ArrayList<>();
		this.ourMoves = new ArrayList<>();
		this.oppMoves = new ArrayList<>();
	}
	
	Board(int[][] board, ArrayList<Coordinates> ourCoordinates, ArrayList<Coordinates> oppCoordinates, ArrayList<Coordinates> ourMoves, ArrayList<Coordinates> oppMoves){
		this.board = board;
		this.ourCoordinates = ourCoordinates;
		this.oppCoordinates = oppCoordinates;
		this.ourMoves = ourMoves;
		this.oppMoves = oppMoves;
	}
	
	public void ourPlayer(int our_player) {
		this.our_player = our_player;
	}
	
	static boolean IsInOfBounds(int x, int y) {
		if(0 <= x && x < 19 && 0 <= y && y < 19)
			return true;
		
		return false;
	}
	
	public void move(int x, int y, int color) {
		
		board[x][y] = color;
		
	}
	
	public ArrayList<Coordinates> deleteElement(int x, int y, ArrayList<Coordinates> coordnateList){
		ArrayList<Coordinates> lastCoordinates = new ArrayList<>(coordnateList);
		
		for(Coordinates coord : lastCoordinates) {
			if(coord.x == x && coord.y == y) {
				lastCoordinates.remove(coord);
				break;
			}
		}
		
		return lastCoordinates;
	}
	
	// 이 전 수의 두 개에 대한 이웃찾기 
	public ArrayList<Coordinates> updateBoard(ArrayList<Coordinates> coordnateList){
		ArrayList<Coordinates> lastCoordinates = new ArrayList<>();
		
		for(Coordinates c : coordnateList) {
			if(c.x == -1)
				break;
			ArrayList<Coordinates> neighbors = getNeighborCoordinates(c.x, c.y);
			boolean same = false;
			for(Coordinates n : neighbors) {
				same = false;
				if(board[n.x][n.y] == 0) {
					for(Coordinates a : lastCoordinates) {
						if(a.x == n.x && a.y == n.y) {
							same = true;
							break;
						}
					}
					if(!same)
						lastCoordinates.add(n);
				}
			}
		}
		
		return lastCoordinates;
	}
	
//	public ArrayList<Coordinates> updateBoard(int x, int y, int color, ArrayList<Coordinates> coordnateList){
//		
//		ArrayList<Coordinates> lastCoordinates = new ArrayList<>(coordnateList);
//		
//		for(Coordinates coord : lastCoordinates) {
//			if(coord.x == x && coord.y == y) {
//				lastCoordinates.remove(coord);
//				break;
//			}
//		}
//		
//		ArrayList<Coordinates> neighbors = getNeighborCoordinates(x, y);
//		boolean same = false;
//		for(Coordinates n : neighbors) {
//			same = false;
//			if(board[n.x][n.y] == 0) {
//				for(Coordinates a : lastCoordinates) {
//					if(a.x == n.x && a.y == n.y) {
//						same = true;
//						break;
//					}
//				}
//				if(!same)
//					lastCoordinates.add(n);
//			}
//		}
//		
//		return lastCoordinates;
//	}
	
	private ArrayList<Coordinates> getNeighborCoordinates(int x, int y){

        ArrayList<Coordinates> neighborCoordinates = new ArrayList<>();
        int[][] neighbors = new int[][]{{x-1, y-1}, {x, y-1}, {x+1, y-1}, {x-1, y}, {x+1, y}, {x-1, y+1},
                {x, y+1}, {x+1, y+1}};

        for(int[] neighbor: neighbors){
            int xCoord = neighbor[0];
            int yCoord = neighbor[1];

            if(IsInOfBounds(xCoord, yCoord) && board[xCoord][yCoord] == 0){
                neighborCoordinates.add(new Coordinates(xCoord, yCoord));
            }
        }

        return neighborCoordinates;
    }
	
	static int[][] copyBoard(int[][] board){
		int[][] copied = new int[19][19];
		
		for(int i = 0 ; i < 19; i++) {
			System.arraycopy(board[i], 0, copied[i], 0, 19);
		}
		
		return copied;
	}
	
	public boolean isValidMove(int x, int y) {
		if(x < 0 || x > 18 || y < 0 || y > 18)
			return false;
		else if(board[x][y] != EMPTY)
			return false;
		else 
			return true;
	}
	
	public int isOver() {
		
		int dx[] = {1, 1, 0, 1};
		int dy[] = {1, 0, 1, -1};
		int count;
		int curx, cury;
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				if(IsInOfBounds(i, j) && board[i][j] == WHITE) {
					for(int k = 0; k < 4; k++) {
						count = 1;
						curx = i + dx[k];
						cury = j + dy[k];
						while(IsInOfBounds(curx, cury) && board[curx][cury] == WHITE) {
							count++;
							curx += dx[k];
							cury += dy[k];
						}
						curx = i - dx[k];
						cury = j - dy[k];
						while(IsInOfBounds(curx, cury) && board[curx][cury] == WHITE) {
							count++;
							curx = dx[k];
							cury -= dy[k];
						}
						if(count == 6)
							return WHITE;
					}
				}
				if(IsInOfBounds(i, j) && board[i][j] == BLACK) {
					for(int k = 0; k < 4; k++) {
						count = 1;
						curx = i + dx[k];
						cury = j + dy[k];
						while(IsInOfBounds(curx, cury) && board[curx][cury] == BLACK) {
							count++;
							curx += dx[k];
							cury += dy[k];
						}
						curx = i - dx[k];
						cury = j - dy[k];
						while(IsInOfBounds(curx, cury) && board[curx][cury] == BLACK) {
							count++;
							curx = dx[k];
							cury -= dy[k];
						}
						if(count == 6)
							return BLACK;
					}
				}
			}
		}
		
		for(int i = 0 ; i <= 18 ; i++) {
			for(int j = 0 ; j <= 18 ; j++) {
				//not over
				if(board[i][j] == EMPTY)
					return -1;
			}
		}
		
		//Full
		return RED;
		
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	static int[][] getBoardReversed(int[][] board){
		int [][] reversedBoard = new int[board.length][board.length];

        for(int i=0; i<board.length; i++){
        	int[] row = board[i];
            Collections.reverse(Arrays.asList(row));
            reversedBoard[i] = row;
        }
        return reversedBoard;
    }

}

class Coordinates{
	
	int x;
	int y;
	
	Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
}