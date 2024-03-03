package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Minesweeper extends JFrame {
//    Array of buttons for the game board
    private JButton[][] buttons;
    private boolean[][] isMine;
    private int[][] adjMines;
    private int uncoveredCells;


    public Minesweeper(){
        setTitle("Minesweeper");

//        TODO Customise size through user input
        setLayout(new GridLayout(9,9));

        buttons = new JButton[9][9];
        isMine = new boolean[9][9];
        adjMines = new int[9][9];

        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(new onClickListener(i,j));
                add(buttons[i][j]);
            }
        }
        placeMines();
        countAdjMines();
        pack();
        setVisible(true);

    }

    private void placeMines(){
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced<10){
            int i = random.nextInt(9);
            int j = random.nextInt(9);
//            If cell at (i,j) is not a mine
            if(!isMine[i][j]) {
//                Make it a mine
                isMine[i][j] = true;
                minesPlaced++;
            }
        }
    }

    private void countAdjMines(){
//        Loop through the cells on the board and count the number of mines around it
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
//                If current cell is not a mine
                if(!isMine[i][j]){
                    int count = 0;
//                    The row above
                    if(i>0 && j>0 && isMine[i-1][j-1]) count++;
                    if(i>0 && isMine[i-1][j]) count++;
                    if(i>0 && j<8 && isMine[i-1][j+1]) count++;

//                    The row below
                    if(i<8 && j>0 && isMine[i+1][j-1]) count++;
                    if(i<8 && isMine[i+1][j]) count++;
                    if(i<8 && j<8 && isMine[i+1][j+1]) count++;

//                    The same row
                    if(j>0 && isMine[i][j-1]) count++;
                    if(j<8 && isMine[i][j+1]) count++;

                    adjMines[i][j] = count;
                }
            }
        }
    }

    private void winGame(){
        JOptionPane.showMessageDialog(this, "You won.");
    }

    private void loseGame(){
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if(isMine[i][j]){
//                    TODO figure out how to add image
                    buttons[i][j].setText("*");
                }
            }
        }
        JOptionPane.showMessageDialog(this, "You lost.");
    }

    private void showCell(int i, int j){
        if(isMine[i][j]){
            loseGame();
        } else{
            buttons[i][j].setText(Integer.toString(adjMines[i][j]));
            buttons[i][j].setEnabled(false);
            uncoveredCells++;
            if (uncoveredCells == 71) {
                winGame();
            }
            if (adjMines[i][j] == 0) {
                showSurroundingCells(i, j);
            }
        }
        }
    private void showSurroundingCells(int i, int j) {
        if (i > 0 && buttons[i - 1][j].isEnabled()) showCell(i - 1, j);
        if (i < 8 && buttons[i + 1][j].isEnabled()) showCell(i + 1, j);
        if (j > 0 && buttons[i][j - 1].isEnabled()) showCell(i, j - 1);
        if (j < 8 && buttons[i][j + 1].isEnabled()) showCell(i, j + 1);
        if (i > 0 && j > 0 && buttons[i - 1][j - 1].isEnabled()) showCell(i - 1,j - 1);
        if (i < 8 && j < 8 && buttons[i + 1][j + 1].isEnabled()) showCell(i + 1,j + 1);
        if (i > 0 && j < 8 && buttons[i - 1][j + 1].isEnabled()) showCell(i - 1,j + 1);
        if (i < 8 && j > 0 && buttons[i + 1][j - 1].isEnabled()) showCell(i + 1,j - 1);
    }

    private class onClickListener implements ActionListener {
        private int i;
        private int j;

        public onClickListener(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public void actionPerformed(ActionEvent e) {
            showCell(i, j);
        }
    }
}
