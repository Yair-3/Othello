package edu.touro.cs.mcon364;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OthelloGUI {

    private OthelloModel model;
    private JFrame frame;
    private JPanel boardPanel;
    private JButton[][] buttons;
    private JLabel scoreLabel;
    private JLabel turnLabel;

    public OthelloGUI() {
        model = new OthelloModel();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Othello");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        buttons = new JButton[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                button.setOpaque(true);
                button.setBorderPainted(true);
                button.addActionListener(new ButtonClickListener(row, col));
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);

        // Create a bottom panel with a score label
        JPanel bottomPanel = new JPanel(new FlowLayout());
        scoreLabel = new JLabel();
        turnLabel = new JLabel();

        JButton restartButton = new JButton("New Game");
        restartButton.addActionListener(e -> {
            model = new OthelloModel();
            updateBoard();
        });

        bottomPanel.add(scoreLabel);
        bottomPanel.add(turnLabel);
        bottomPanel.add(restartButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        updateBoard();
    }

    private void updateBoard() {
        int[][] board = model.getBoard();
        Color green = new Color(0, 128, 0);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == OthelloModel.BLACK) {
                    buttons[row][col].setBackground(Color.BLACK);
                } else if (board[row][col] == OthelloModel.WHITE) {
                    buttons[row][col].setBackground(Color.WHITE);
                } else {
                    buttons[row][col].setBackground(new Color(0, 128, 0));
                }
                buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            }
        }
        updateScore();
    }

    private void updateScore() {
        int whiteScore = model.getScore(OthelloModel.WHITE);
        int blackScore = model.getScore(OthelloModel.BLACK);
        scoreLabel.setText("White: " + whiteScore + "  Black: " + blackScore);
        turnLabel.setText("Turn: " + (model.getCurrentPlayer() == OthelloModel.WHITE ? "White" : "Black"));
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.isValidMove(model.getBoard(), row, col, model.getCurrentPlayer())) {
                model.makeMove(row, col);
                updateBoard();
                if (model.isGameOver()) {
                    int whiteScore = model.getScore(OthelloModel.WHITE);
                    int blackScore = model.getScore(OthelloModel.BLACK);
                    String winner = whiteScore > blackScore ? "White" : "Black";
                    int result = JOptionPane.showOptionDialog(frame, "Game Over! " + winner + " wins! Would you like to play a new game?", "Game Over",
                            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                    if (result == JOptionPane.YES_OPTION) {
                        model = new OthelloModel();
                        updateBoard();
                    } else { System.exit(0); }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OthelloGUI::new);
    }
}


