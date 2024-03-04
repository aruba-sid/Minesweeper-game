import org.example.Minesweeper;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class MinesweeperTest {
    int row=5;
    int col=5;
    int mines=10;

//    @Test
//    public void testPlaceMines(){
//        Minesweeper minesweeper = new Minesweeper(row, col, mines);
//        minesweeper.placeMines(row,col,mines);
//        int count=0;
//        for (int i=0;i<row;i++){
//            for (int j=0;j<col;j++){
//                if(minesweeper.isMine[i][j]){
//                    count++;
//                }
//            }
//        }
//        assertEquals(mines, count, "You have more mines than that");
//    }



    @Test
    public void isInitialised(){
        Minesweeper minesweeper = new Minesweeper(row, col, mines);
        assertEquals(row,minesweeper.buttons.length, "Not the correct number of rows");
        assertEquals(col,minesweeper.buttons[0].length, "Not the correct number of columns");
    }

}
