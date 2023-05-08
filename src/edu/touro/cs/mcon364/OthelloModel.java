package edu.touro.cs.mcon364;

public class OthelloModel {
    public static final int EMPTY = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;
    private boolean gameOver;

    private int[][] board;
    private int currentPlayer;

    public OthelloModel() {
        board = new int[8][8];
        currentPlayer = BLACK;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = EMPTY;
            }
        }
        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;
    }

    public boolean isValidMove(int[][] board, int row, int col, int player) {
        if (board[row][col] != EMPTY) {
            return false;
        }
        int opponent = 3 - player;
        int[] dRow = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dCol = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int direction = 0; direction < 8; direction++) {
            int r = row + dRow[direction];
            int c = col + dCol[direction];
            boolean foundOpponent = false;

            while (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == opponent) {
                r += dRow[direction];
                c += dCol[direction];
                foundOpponent = true;
            }

            if (foundOpponent && r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == player) {
                return true;
            }
        }
        return false;
    }

    public int[][] getValidMoves(int player) {
        int[][] validMoves = new int[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (isValidMove(board, row, col, player)) {
                    validMoves[row][col] = 1;
                } else {
                    validMoves[row][col] = 0;
                }
            }
        }
        return validMoves;
    }

    public void makeMove(int row, int col) {
        board[row][col] = currentPlayer;
        flipDiscs(row, col);

        if (hasValidMoves(oppositePlayer(currentPlayer))) {
            currentPlayer = oppositePlayer(currentPlayer);

            if (currentPlayer == WHITE) { // Assuming WHITE is the computer player
                int[] bestMove = getBestMove(currentPlayer);
                makeMove(bestMove[0], bestMove[1]);
            }
        } else if (!hasValidMoves(currentPlayer)) {
            gameOver = true;
        }
    }
    private int oppositePlayer(int player) {
        return player == BLACK ? WHITE : BLACK;
    }

    private void flipDiscs(int row, int col) {
        int opponent = 3 - currentPlayer;
        int[] dRow = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dCol = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int direction = 0; direction < 8; direction++) {
            int r = row + dRow[direction];
            int c = col + dCol[direction];
            boolean foundOpponent = false;

            while (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == opponent) {
                r += dRow[direction];
                c += dCol[direction];
                foundOpponent = true;
            }

            if (foundOpponent && r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == currentPlayer) {
                r -= dRow[direction];
                c -= dCol[direction];
                while (r != row || c != col) {
                    board[r][c] = currentPlayer;
                    r -= dRow[direction];
                    c -= dCol[direction];
                }
            }
        }
    }

    public boolean hasValidMoves(int player) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (isValidMove(board, row, col, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isGameOver() {
        return !hasValidMoves(BLACK) && !hasValidMoves(WHITE);
    }

    public int getScore(int player) {
        int score = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == player) {
                    score++;
                }
            }
        }
        return score;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int[] getBestMove(int player) {
        int[][] validMoves = getValidMoves(player);
        int bestScore = -1;
        int[] bestMove = new int[2];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (validMoves[row][col] == 1) {
                    int[][] tempBoard = copyBoard(board);
                    makeMoveOnBoard(tempBoard, row, col, player);
                    int score = getScore(player);
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }
        return bestMove;
    }

    private int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                newBoard[row][col] = board[row][col];
            }
        }
        return newBoard;
    }

    private void makeMoveOnBoard(int[][] board, int row, int col, int player) {
        board[row][col] = player;
        flipDiscsOnBoard(board, row, col, player);
    }

    private void flipDiscsOnBoard(int[][] board, int row, int col, int player) {
        // Copy the flipDiscs method here and replace all references to 'board' with the parameter 'board'
        int opponent = 3 - player;
        int[] dRow = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dCol = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int direction = 0; direction < 8; direction++) {
            int r = row + dRow[direction];
            int c = col + dCol[direction];
            boolean foundOpponent = false;

            while (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == opponent) {
                r += dRow[direction];
                c += dCol[direction];
                foundOpponent = true;
            }

            if (foundOpponent && r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == player) {
                r -= dRow[direction];
                c -= dCol[direction];
                while (r != row || c != col) {
                    board[r][c] = player;
                    r -= dRow[direction];
                    c -= dCol[direction];
                }
            }
        }
    }
    public void setBoard(int[][] newBoard) { // for testing only
        if (newBoard.length != 8 || newBoard[0].length != 8) {
            throw new IllegalArgumentException("The provided board must have dimensions 8x8.");
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = newBoard[row][col];
            }
        }
    }

}




