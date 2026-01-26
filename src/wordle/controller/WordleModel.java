package wordle.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class WordleModel {

    String[][] grid = new String[6][5];
    int currentrow = 0;
    int currentcol = 0;

    private String[] WordList;
    private String Solution;
    private int[][] Colors = new int[6][5];  // 0=grau, 1=grün, -1=gelb

    public WordleModel() {
        this.WordList = GetArray();
        this.Solution = GetRandomWord(WordList);
    }

    public int GetColor(int row, int col){
        return Colors[row][col];
    }

    public void SetColor(int row, int col, int color){
        this.Colors[row][col] = color;
    }

    public String[] GetArray(){
        String[] list = new String[5353];
        InputStream is = getClass().getResourceAsStream("/Data/words_de.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        for (int i = 0; i < 5353; i++){
            try {
                list[i] = reader.readLine();
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    String GetRandomWord(String[] list){
        Random rng = new Random();
        int a = rng.nextInt(5353);
        return list[a];
    }

    public void addLetter(char letter){
        if (currentcol < 5 && currentrow < 6){
            grid[currentrow][currentcol] = String.valueOf(letter).toUpperCase();
            currentcol++;
        }
    }

    public void removeLetter(){
        if (currentcol > 0){
            currentcol--;
            grid[currentrow][currentcol] = "";
        }
    }

    public void submitword(){

        if (currentcol == 5 && currentrow < 6 && Arrays.asList(WordList).contains(getCurrentRow())){
            currentrow++;
            currentcol = 0;
        }
    }

    public String getLetter(int row, int col) {
        return grid[row][col] == null ? "" : grid[row][col];
    }

    public String getCurrentRow() {
        String CurrentWord = "";
        for (int i = 0; i < 5; i++){
            CurrentWord += grid[currentrow][i];
        }
        return CurrentWord.toLowerCase();
    }

    public int getCurrentCol() {
        return currentcol;
    }

}
