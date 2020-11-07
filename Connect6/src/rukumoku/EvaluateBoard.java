package rukumoku;

public class EvaluateBoard {
	
    static int maxInARow(int[][] board, int turn){
        int horizontalCount = horizontalScore(board, turn).maxInARow;
        if(horizontalCount == 6){ return 6;}
        int verticalCount = verticalScore(board, turn).maxInARow;
        if(verticalCount == 6){ return 6;}
        return Math.max(diagonalScore(board, turn).maxInARow, Math.max(horizontalCount, verticalCount));
    }

    
    static Score horizontalScore(int[][] board, int turn){
        Score score = new Score(0, 0);

        for(int i=0; i<board.length; i++){
            int[] row = board[i];
            int stonesInARow = 0;
            int blankSpaces = 0;

            for(int tile: row){

                if(tile == 0){
                    blankSpaces++;
                } else if(tile == turn){
                    stonesInARow++;
                } else {
                    stonesInARow = 0;
                    blankSpaces = 0;
                }
                if(stonesInARow == 6){
                    return new Score(6, 0);
                }
                if (stonesInARow >= score.maxInARow){
                    score.maxInARow = stonesInARow;
                    score.surroundingBlanks = blankSpaces;
                }
            }
        }
        
        
        return score;
    }

    
    static Score verticalScore(int[][] board, int turn){
        Score score = new Score(0, 0);

        for(int i=0; i<board.length; i++){
            int stonesInARow = 0;
            int blankSpaces = 0;

            for(int j=0; j<board.length; j++){

                if(board[j][i] == 0){
                    blankSpaces++;
                } else if(board[j][i]== turn){
                    stonesInARow++;
                } else {
                    stonesInARow = 0;
                    blankSpaces = 0;
                }
                if(stonesInARow == 6){
                    return new Score(6, 0);
                }
                if (stonesInARow >= score.maxInARow){
                    score.maxInARow = stonesInARow;
                    score.surroundingBlanks = blankSpaces;
                }
            }
        }
        return score;
    }

    static Score diagonalScore(int[][] board, int turn) {

        // Left to right diagonals
        Score leftToRight = diagonalCount(board, turn);

        // Only check the other diagonals if necessary
        if(leftToRight.maxInARow < 6){
            // Flip the board and repeat for right to left
            int [][] reverseBoard = Board.getBoardReversed(board);
            Score rightToLeft = diagonalCount(reverseBoard, turn);
            if(rightToLeft.maxInARow > leftToRight.maxInARow){
                return rightToLeft;
            }
        }

        return leftToRight;
    }

    private static Score diagonalCount(int[][] board, int turn) {

        Score score = new Score(0, 0);
        int stonesInARow = 0;
        int blankSpaces = 0;

        // Top half diagonals starting from top left and working right
        for(int i=0; i< board.length * 2; i++){
            for(int j=0; j<=i; j++){
                int index = i -j;

                if(index < board.length && j < board.length){
                    int tile = board[index][j];
                    if(tile == 0){
                        blankSpaces++;
                    }else if(tile == turn){
                        stonesInARow++;
                    } else {
                        stonesInARow = 0;
                        blankSpaces = 0;
                    }
                    if(stonesInARow == 6){
                        return new Score(6, 0);
                    }
                }
                if (stonesInARow >= score.maxInARow){
                    score.maxInARow = stonesInARow;
                    score.surroundingBlanks = blankSpaces;
                }
            }

            stonesInARow = 0;
            blankSpaces = 0;
        }
        return score;
    }
}

class Score{

    int maxInARow;
    int surroundingBlanks;

    Score(int maxInARow, int surroundingBlanks){
        this.maxInARow = maxInARow;
        this.surroundingBlanks = surroundingBlanks;
    }
}
