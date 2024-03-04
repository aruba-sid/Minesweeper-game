package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public static void main(String[] args) {
        int rows = 0, cols =0, mines=0;
        Scanner input = new Scanner(System.in);

        System.out.println("""
                Please choose difficulty level:\s
                1.Easy\s
                2.Medium\s
                3.Hard""");
        int i = input.nextInt();

        switch (i) {
            case 1: rows =9; cols=9; mines=10;
                break;
            case 2: rows = 16; cols=16; mines=40;
                break;
            case 3: rows = 30; cols=16; mines=99;
                break;
            default:
                System.out.println("Invalid input. Please enter either 1, 2, 0r 3.");
        }

         new Minesweeper(rows, cols, mines);

    }

}