package edu.touro.cs.mcon364;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OthelloModelTest {

    private OthelloModel model;
    private static final int EMPTY = 0;
    private static final int BLACK = 1;
    private static final int WHITE = 2;

    int[][] customBoard = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 2, 0, 0, 0},
            {0, 0, 0, 2, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };

    @BeforeEach
    void setUp() {
        model = new OthelloModel();
    }

    @Test
    void testMakeMove() {

        assertEquals(OthelloModel.BLACK, model.getCurrentPlayer());

        // Make a valid move for BLACK
        model.makeMove(2, 3);
        assertEquals(OthelloModel.BLACK, model.getBoard()[2][3]);

        System.out.println("Board state after BLACK's move:");
        printBoard(model.getBoard());

        // Make a valid move for WHITE (computer player)
        int[] bestMove = model.getBestMove(OthelloModel.WHITE);
        System.out.println("Best move for WHITE: row=" + bestMove[0] + ", col=" + bestMove[1]);
        model.makeMove(bestMove[0], bestMove[1]);
        assertEquals(OthelloModel.WHITE, model.getBoard()[bestMove[0]][bestMove[1]]);
        assertEquals(OthelloModel.BLACK, model.getCurrentPlayer());

        System.out.println("Board state after WHITE's move:");
        printBoard(model.getBoard());
    }

    private void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    @Test
    void testIsValidMove() {
        int[][] board = model.getBoard();
        int player = OthelloModel.BLACK;
        int opponent = OthelloModel.WHITE;

        // Test invalid move: occupied cell
        assertFalse(model.isValidMove(board, 3, 3, player));

        // Test valid moves for BLACK
        assertTrue(model.isValidMove(board, 2, 3, player));
        assertTrue(model.isValidMove(board, 3, 2, player));
        assertTrue(model.isValidMove(board, 4, 5, player));
        assertTrue(model.isValidMove(board, 5, 4, player));

        // Test invalid moves for BLACK
        assertFalse(model.isValidMove(board, 0, 0, player));
        assertFalse(model.isValidMove(board, 7, 7, player));

        // Test valid moves for WHITE
        assertTrue(model.isValidMove(board, 2, 4, opponent));
        assertTrue(model.isValidMove(board, 4, 2, opponent));
        assertTrue(model.isValidMove(board, 3, 5, opponent));
        assertTrue(model.isValidMove(board, 5, 3, opponent));

        // Test invalid moves for WHITE
        assertFalse(model.isValidMove(board, 0, 0, opponent));
        assertFalse(model.isValidMove(board, 7, 7, opponent));
    }
    @Test
    void testHasValidMoves() {
        assertTrue(model.hasValidMoves(BLACK));
        assertTrue(model.hasValidMoves(WHITE));
    }

    @Test
    void testIsGameOver() {
        assertFalse(model.isGameOver());
    }
    @Test
    void testIsGameOverTrue() {
        int[][] boardState = {
                {1, 2, 2, 2, 2, 2, 2, 1},
                {2, 1, 1, 1, 1, 1, 1, 2},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {2, 1, 1, 1, 1, 1, 1, 2},
                {2, 1, 1, 1, 1, 1, 1, 2},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {2, 1, 1, 1, 1, 1, 1, 2},
                {1, 2, 2, 2, 2, 2, 2, 1},
        };

        model.setBoard(boardState);
        assertTrue(model.isGameOver());
    }

    @Test
    void testGetScore() {
        assertEquals(2, model.getScore(BLACK));
        assertEquals(2, model.getScore(WHITE));
    }
    @Test
    void testGetBestMove() {
        model.setBoard(customBoard);
        int[] bestMove = model.getBestMove(OthelloModel.BLACK);

        int[] expectedBestMove = new int[]{2, 4};
        assertArrayEquals(expectedBestMove, bestMove);
    }
    @Test
    void testGetValidMoves() {
        model.setBoard(customBoard);
        int[][] validMoves = model.getValidMoves(OthelloModel.BLACK);

        int[][] expectedValidMoves = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    assertEquals(expectedValidMoves[row][col], validMoves[row][col]);
                }
            }
        }
}



