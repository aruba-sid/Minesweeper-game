package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Minesweeper extends JFrame {
//    Array of buttons for the game board
    public final JButton[][] buttons;
    public final boolean[][] isMine;
    private final int[][] adjMines;
    private int uncoveredCells;
    private final int nonMineCells;
    private final ImageIcon flaggedIcon;
    private final ImageIcon mineIcon;

    public Minesweeper(int rows, int cols, int mines){
        setTitle("Minesweeper");
        nonMineCells = (rows*cols) - mines;
        int tileSize = 50;
        int width = cols*tileSize;
        int height = rows*tileSize;
        pack();

        setLayout(new GridLayout(rows,cols));
        setSize(width, height);

        buttons = new JButton[rows][cols];
        isMine = new boolean[rows][cols];
        adjMines = new int[rows][cols];

//      Icons from Icons8.com
        flaggedIcon = new ImageIcon("icons8-flag-16.png");
        mineIcon = new ImageIcon("icons8-bomb-30.png");

        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                buttons[i][j] = new JButton();
                buttons[i][j].addMouseListener(new onClickListener(i, j));
                add(buttons[i][j]);
            }
        }
        placeMines(rows, cols, mines);
        countAdjMines(rows, cols);
        setVisible(true);

    }

    public void placeMines(int rows, int cols, int mines){
        Random random = new Random();

        int minesPlaced = 0;
        while (minesPlaced<mines){
            int i = random.nextInt(rows);
            int j = random.nextInt(cols);
//            If cell at (i,j) is not a mine
            if(!isMine[i][j]){
//                Make it a mine
                isMine[i][j] = true;
                minesPlaced++;
            }
        }
    }

    private void countAdjMines(int rows,int cols){
//        Loop through the cells on the board and count the number of mines around it
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
//                If current cell is not a mine
                if(!isMine[i][j]){
                    int count = 0;
                    for (int k = Math.max(0, i - 1); k <= Math.min(rows - 1, i + 1); k++) {
                        for (int l = Math.max(0, j - 1); l <= Math.min(cols - 1, j + 1); l++) {
                            if (isMine[k][l]) {
                                count++;
                            }
                        }
                    }
                    adjMines[i][j] = count;
                }
            }
        }
    }

    private void winGame(){
        JOptionPane.showMessageDialog(this, "You won.");
    }

    private void loseGame(){
        for (int i=0; i<buttons.length - 1; i++){
            for (int j=0; j<buttons[0].length - 1; j++){
                if(isMine[i][j]){
//                    buttons[i][j].setText("X");
                    buttons[i][j].setIcon(mineIcon);
                }
                buttons[i][j].setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(this, "Game over.");
//        System.exit(0);
    }

    public void showCell(int i, int j){
        if(isMine[i][j]){
            loseGame();
        } else{
            if (adjMines[i][j]>0) {
                buttons[i][j].setText(Integer.toString(adjMines[i][j]));
            }
            else {
                buttons[i][j].setText("");
            }
            buttons[i][j].setEnabled(false);
            uncoveredCells++;
            if (uncoveredCells == nonMineCells) {
                winGame();
            }
            if (adjMines[i][j] == 0) {
                buttons[i][j].setEnabled(false);
                showSurroundingCells(i, j);
            }
        }
        }
    private void showSurroundingCells(int row, int col) {
        for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, buttons.length - 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(col + 1, buttons[0].length - 1); j++) {
                if (buttons[i][j].isEnabled() && !(i == row && j == col)) {
                    showCell(i, j);
                }
            }
        }
    }

    private  class onClickListener extends MouseAdapter {
        private final int row;
        private final int col;

        public onClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                JButton button = (JButton) e.getSource();
                button.setIcon(flaggedIcon);
//                button.setText("F");
            }
            else if (SwingUtilities.isLeftMouseButton(e)) {
               showCell(row, col);
            }
        }
    }
}
