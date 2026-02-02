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
    private boolean gameWon = false;
    private boolean gameLost = false;
    private boolean wrongInput = false;
    private String[] WordList;
    private String[] GuessList;
    private String Solution;
    private int[][] Colors = new int[6][5];  // 0=grau, 1=grün, -1=gelb
    GuessString[] SolutionArray;
    private int[] keyboardStatus = new int[26];



    public WordleModel() {
        this.WordList = GetArray();
        this.Solution = GetRandomWord(WordList);
        this.SolutionArray = GuessToArray(Solution);
        this.GuessList = GetArray2();
    }

    public int getKeyStatus(char letter) {
        int index = Character.toLowerCase(letter) - 'a';
        if (index < 0 || index >= 26) {
            return 0;
        }
        return keyboardStatus[index];
    }
    private void updateKeyboardStatus(CustomString[] guess) {
        for (int i = 0; i < 5; i++) {
            char letter = Character.toLowerCase(guess[i].c);
            int index = letter - 'a';

            if (index < 0 || index >= 26) {
                continue;
            }

            // Grün überschreibt alles
            if (guess[i].color == 1) {
                keyboardStatus[index] = 1;
            }
            // Gelb überschreibt nur "noch nicht benutzt" und "dunkelgrau"
            else if (guess[i].color == -1 && keyboardStatus[index] != 1) {
                keyboardStatus[index] = -1;
            }
            // Dunkelgrau nur wenn noch nie benutzt
            else if (guess[i].color == 0 && keyboardStatus[index] == 0) {
                keyboardStatus[index] = -2;
            }
        }
    }

    // Getter für Farben
    public int GetColor(int row, int col){
        return Colors[row][col];
    }

    // NEU: Getter für Solution (für Debugging/Anzeige bei Verlieren)
    public String getSolution() {
        return Solution;
    }

    public boolean isWrongInput() {
        return wrongInput;
    }

    public boolean setWrongInput(){
        return wrongInput = true;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public void setGameWon(boolean won) {
        this.gameWon = won;
    }

    public void setGameLost(boolean lost) {
        this.gameLost = lost;
    }



    public String[] GetArray(){
        String[] list = new String[2315];
        InputStream is = getClass().getResourceAsStream("/Data/words_en-toguess.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        for (int i = 0; i < 2315; i++){
            try {
                list[i] = reader.readLine();
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public String[] GetArray2(){
        String[] list = new String[10657];
        InputStream is = getClass().getResourceAsStream("/Data/words_en-guessable.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        for (int i = 0; i < 10657; i++){
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
        int a = rng.nextInt(2315);
        return list[a];
    }

    public void addLetter(char letter){
        if (currentcol < 5 && currentrow < 6){
            grid[currentrow][currentcol] = String.valueOf(letter).toUpperCase();
            currentcol++;
        }
    }

    public void removeLetter(){
        if (wrongInput == true) {
            wrongInput = false;
        }
        if (currentcol > 0){
            currentcol--;
            grid[currentrow][currentcol] = "";
        }
    }

    public boolean submitword(){
        wrongInput = false;
        if (currentcol == 5 && currentrow < 6 && Arrays.asList(GuessList).contains(getCurrentWord())){
            // Farben berechnen und speichern
            CustomString[] Guess = CheckLetterAndColor(TheGuessToArray(getCurrentWord()), SolutionArray);
            // Farben ins Colors-Array übertragen
            for (int i = 0; i < 5; i++) {
                Colors[currentrow][i] = Guess[i].color;
            }
            updateKeyboardStatus(Guess);
            currentrow++;
            currentcol = 0;
            return wrongInput;
        } else return setWrongInput();
    }

    public String getLetter(int row, int col) {
        return grid[row][col] == null ? "" : grid[row][col];
    }

    // Umbenennen für Klarheit: holt das AKTUELLE Wort, nicht die Zeile
    public String getCurrentWord() {
        String CurrentWord = "";
        for (int i = 0; i < 5; i++){
            if (grid[currentrow][i] != null) {
                CurrentWord += grid[currentrow][i];
            }
        }
        return CurrentWord.toLowerCase();
    }

    public int getCurrentRowIndex() {
        return currentrow;
    }


    CustomString[] CheckLetterAndColor(CustomString[] guess, GuessString[] right) {
        int[] tempFreq = new int[26];
        for (int i = 0; i < 5; i++) {
            char c = Character.toLowerCase(right[i].c);
            tempFreq[c - 'a']++;
        }
        for (int i = 0; i < 5; i++) {
            if (Character.toLowerCase(guess[i].c) == Character.toLowerCase(right[i].c)) {
                guess[i].color = 1;
                char c = Character.toLowerCase(guess[i].c);
                tempFreq[c - 'a']--;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (guess[i].color == 0) {
                char guessChar = Character.toLowerCase(guess[i].c);

                if (tempFreq[guessChar - 'a'] > 0) {
                    guess[i].color = -1;  // gelb
                    tempFreq[guessChar - 'a']--;
                }
            }
        }

        return guess;
    }

    GuessString[] GuessToArray(String guess){
        GuessString[] guessstring = new GuessString[5];
        for (int i = 0 ; i < 5; i++){
            guessstring[i] = new GuessString(guess.charAt(i),0);
        }
        for (int k = 0; k < 5; k++){
            for (int j = 0; j < 5; j++){
                if (guessstring[k].c == guessstring[j].c && k != j){
                    guessstring[k].frequency++;
                }
            }
        }
        return guessstring;
    }

    CustomString[] TheGuessToArray(String guess) {
        CustomString[] first = new CustomString[5];
        for (int i = 0; i < 5; i++) {
            first[i] = new CustomString(guess.charAt(i), 0);
        }
        return first;
    }

    public class CustomString {
        char c;
        int color = 0;
        CustomString(char c, int color){
            this.c = c;
            this.color = color;
        }
    }

    public class GuessString {
        char c;
        int frequency = 0;
        GuessString(char c, int frequency){
            this.c = c;
            this.frequency = frequency;
        }
    }

    public void Reset(){
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 5; j++){
                grid[i][j] = "";
            }
        }
        for (int i = 0; i < 26; i++) {
            keyboardStatus[i] = 0;
        }
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 5; j++){
                Colors[i][j] = 0;
            }
        }
        currentrow = 0;
        currentcol = 0;
        gameWon = false;
        gameLost = false;
        Solution = GetRandomWord(WordList);
        SolutionArray = GuessToArray(Solution);
    }


}