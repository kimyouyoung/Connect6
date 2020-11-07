package rukumoku;


public class AlphaBeta {
	
	private static final int MAX_DEPTH = 3;
	
	static Coordinates bestMove = new Coordinates(0, 0);
	
	static int alphabeta(Board board, int depth, boolean maxPlayer, int color, int alpha, int beta, int index) {
		for(Coordinates c : board.ourCoordinates)
			System.out.println(c.x + " " + c.y);
		System.out.println("=========================");
		if(board.isOver() != -1 || depth == MAX_DEPTH) {
			return CalculateWeight.evaluate(board, maxPlayer, color);
//			return utilityScore(board, color, maxPlayer);
		}
		
		if(maxPlayer) {
			return getMax(board, depth, color, alpha, beta, index);
		} else {
			return getMin(board, depth, color, alpha, beta, index);
		}
	}
	
	static int getMax(Board board, int depth, int color, int alpha, int beta, int index) {
		
		int maxWeight = Integer.MIN_VALUE;
		boolean maxPlayer = false;
		
		for(Coordinates coords: board.ourCoordinates) {
			
			if(index % 2 == 0) {
				maxPlayer = true;
				color = turn(color);
			}
			
			int[][] currentBoard = Board.copyBoard(board.board);
			currentBoard[coords.x][coords.y]= color; 
			Board possibleBoard = new Board(currentBoard, board.ourCoordinates, board.oppCoordinates, board.ourMoves, board.oppMoves);
			if(possibleBoard.ourMoves.size() > 2)
				possibleBoard.ourMoves.remove(0);
			possibleBoard.ourMoves.add(new Coordinates(coords.x, coords.y));
			possibleBoard.ourCoordinates = possibleBoard.updateBoard(possibleBoard.ourMoves);
			possibleBoard.oppCoordinates = possibleBoard.deleteElement(coords.x, coords.y, possibleBoard.oppCoordinates);
			
			int weight = alphabeta(possibleBoard, depth+1, maxPlayer, color, alpha, beta, index++);
			
			if(weight > maxWeight) {
				maxWeight = weight;
				
				bestMove = new Coordinates(coords.x, coords.y);
			}
			alpha = Math.max(alpha, maxWeight);
			
			if(beta <= alpha)
				break;
		}
		
		return maxWeight;
	}
	
	static int getMin(Board board, int depth, int color, int alpha, int beta, int index) {
		
		int minWeight = Integer.MAX_VALUE;
		boolean maxPlayer = true;
		
		for(Coordinates coords: board.oppCoordinates) {
			
			if(index % 2 == 0) {
				maxPlayer = false;
				color = turn(color);
			}
			
			int[][] currentBoard = Board.copyBoard(board.board);
			currentBoard[coords.x][coords.y]= color;
			Board possibleBoard = new Board(currentBoard, board.ourCoordinates, board.oppCoordinates, board.ourMoves, board.oppMoves);
			if(possibleBoard.oppMoves.size() > 2)
				possibleBoard.oppMoves.remove(0);
			possibleBoard.oppMoves.add(new Coordinates(coords.x, coords.y));
			possibleBoard.oppCoordinates = possibleBoard.updateBoard(possibleBoard.oppMoves);
			possibleBoard.ourCoordinates = possibleBoard.deleteElement(coords.x, coords.y, possibleBoard.ourCoordinates);
			
			
			int weight = alphabeta(possibleBoard, depth+1, maxPlayer, color, alpha, beta, index++);
			
			if(weight < minWeight) {
				minWeight = weight;
				bestMove = new Coordinates(coords.x, coords.y);
			}
			beta = Math.min(beta, minWeight);
			
			if(beta <= alpha)
				break;
		}
		
		return minWeight;
	}
	
	static int turn(int color) {
		
		if(color == 1)
			return 2;
		else 
			return 1;
		
	}
	
	
	private static int utilityScore(Board gameBoard, int turn, boolean maxPlayer){

        int utility = 0;
        Score score = maxScore(gameBoard.board, turn);
        boolean canWin = (score.maxInARow + score.surroundingBlanks) >= 6;

        // May have to adjust these scores later
        if(canWin){
            switch (score.maxInARow){
                case 6:
                    utility = 10000;
                    break;
                case 5:
                    utility = 500;
                    break;
                case 4:
                    utility = 400;
                    break;
                case 3:
                    utility = 100;
                    break;
                case 2:
                    utility = 30;
                    break;
                case  1:
                    utility = 10;
                    break;
            }
        }

        // Return the opposite score for the opponent's turn
        if(!maxPlayer){
            utility *= -1;
        }

        return utility;
    }

    private static Score maxScore(int [][] board, int turn){
        Score horizontalScore = EvaluateBoard.horizontalScore(board, turn);
        Score verticalScore = EvaluateBoard.verticalScore(board, turn);
        Score diagonalScore = EvaluateBoard.diagonalScore(board, turn);

        if(horizontalScore.maxInARow > verticalScore.maxInARow){
            if(horizontalScore.maxInARow > diagonalScore.maxInARow){
                return horizontalScore;
            }
        } else {
            if(verticalScore.maxInARow > diagonalScore.maxInARow){
                return verticalScore;
            }
        }

        return diagonalScore;
    }

}