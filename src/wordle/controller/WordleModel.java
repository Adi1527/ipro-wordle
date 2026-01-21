package wordle.controller;

public class WordleModel {

    String[][] grid = new String[6][5];
    int currentrow = 0;
    int currentcol = 0;

    public void addLetter(char letter){
        if (currentcol < 5){
            grid[currentrow][currentcol] = String.valueOf(letter).toUpperCase();
            currentcol++;
        }
    }

    public void removeLetter(){
        if (currentcol > 0){
            grid[currentrow][currentcol] = "";
            currentcol--;
        }
    }

    public void submitword(){
        if (currentcol == 5 && currentrow < 6){
            currentrow++;
            currentcol = 0;
        }
    }

    public String getLetter(int row, int col) {
        return grid[row][col] == null ? "" : grid[row][col];
    }

    public int getCurrentRow() {
        return currentrow;
    }

    public int getCurrentCol() {
        return currentcol;
    }


}
