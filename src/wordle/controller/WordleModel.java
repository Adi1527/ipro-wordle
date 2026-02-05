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

    /**
     * Gibt den Tastatur-Status eines Buchstabens zurück.
     * @param letter gibt den Buchstaben mit (a-z)
     * @return  gibt den Status der gewollten Position zurück
     */
    public int getKeyStatus(char letter) {
        int index = Character.toLowerCase(letter) - 'a';
        if (index < 0 || index >= 26) {
            return 0;
        }
        return keyboardStatus[index];
    }

    /**
     * gibt dem buchstaben die richtige Farbe für die Tastatur in der GUI
     * @param guess gibt die chars und farben des erratene Wortes mit
     */
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

    /**
     * holt die Farbe aus dem Grid
     * @param row welche Reihe
     * @param col Welche Spalte
     * @return gibt die Farbe zurück
     */
    public int GetColor(int row, int col){
        return Colors[row][col];
    }

    /**
     * gibt die Lösung zurück für das Anzeigen wenn man verliert oder gewinnt.
     * @return gibt das Lösungswort zurück
     */
    public String getSolution() {
        return Solution;
    }

    /**
     * gibt den Status zurück ob der Input falsch ist.
     * @return gibt den boolean zurück
     */
    public boolean isWrongInput() {
        return wrongInput;
    }

    /**
     * Ändert den Status des booleans
     * @return gibt den boolean danach zurück.
     */
    public boolean setWrongInput(){
        return wrongInput = true;
    }

    /**
     * holt den Status ob das Spiel gewonnen wurde.
     * @return gibt den boolean zurück
     */
    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * holt den Status ob das Spiel verloren wurde.
     * @return gibt den boolean zurück
     */
    public boolean isGameLost() {
        return gameLost;
    }

    /**
     * Ändert den Status des boolean ob das Spiel gewonnen wurde.
     * @param won gibt diesen boolean wieder zurück.
     */
    public void setGameWon(boolean won) {
        this.gameWon = won;
    }

    /**
     * Ändert den Status des boolean ob das Spiel verloren wurde.
     * @param lost gibt diesen boolean wieder zurück.
     */
    public void setGameLost(boolean lost) {
        this.gameLost = lost;
    }

    /**
     * macht aus allen möglichen Lösungswortern einen Array, damit aus diesem ein Lösungswort ausgesucht werden kann.
     * @return gibt diesen Array zurück.
     */
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

    /**
     * Erstellt ein Array aus allen möglichen Eingabe für den Check.
     * @return gibt dieses Array zurück
     */
    public String[] GetArray2(){
        String[] list = new String[12972];
        InputStream is = getClass().getResourceAsStream("/Data/words_en-guessable.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        for (int i = 0; i < 12972; i++){
            try {
                list[i] = reader.readLine();
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    /**
     * holt aus dem Array mit allen möglichen Lösungen ein random Wort.
     * @param list gibt das Array aus GetArray() mit.
     * @return gibt ein Random Wort zurück als String.
     */
    String GetRandomWord(String[] list){
        Random rng = new Random();
        int a = rng.nextInt(2315);
        return list[a];
    }

    /**
     * gibt den Buchstaben mit zum ihn in das Grid zu zeichnen an der korrekten Stelle. (für handleKeyInput)
     * @param letter der Buchstabe der eingegeben werden soll in das Grid.
     */
    public void addLetter(char letter){
        if (currentcol < 5 && currentrow < 6){
            grid[currentrow][currentcol] = String.valueOf(letter).toUpperCase();
            currentcol++;
        }
    }

    /**
     * nimmt den Buchstaben wieder aus der Position raus. (für handleKeyInput)
     */
    public void removeLetter(){
        if (wrongInput == true) {
            wrongInput = false;
        }
        if (currentcol > 0){
            currentcol--;
            grid[currentrow][currentcol] = "";
        }
    }

    /**
     * Überprüft ob der Input möglich ist wenn die Zeile ausgefüllt ist und berechnet und speichert danach die Farben,
     * welche im Nachhinein noch ins Colors-Array gespeichert werden. Falls der Input falsch ist Löst es den WrongInput
     * aus.
     * @return gibt den WrongInput zurück um die Anzeige anzuzeigen oder nicht.
     */
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

    /**
     * Holt den Buchstaben in der Aktuellen Position
     * @param row Reihe welche aktuell ist
     * @param col aktuelle Spalte
     * @return gibt den Buchstaben als String zurück
     */
    public String getLetter(int row, int col) {
        if (grid[row][col] == null) {
            return "";
        } else {
            return grid[row][col];
        }
    }

    /**
     * Holt das aktuelle Wort aus dem Grid in der aktuellen reihe
     * @return gibt dieses als String zurück
     */
    public String getCurrentWord() {
        String CurrentWord = "";
        for (int i = 0; i < 5; i++){
            if (grid[currentrow][i] != null) {
                CurrentWord += grid[currentrow][i];
            }
        }
        return CurrentWord.toLowerCase();
    }

    /**
     * gibt die aktuelle Reihe an
     * @return gibt die aktuelle reihe zurück
     */
    public int getCurrentRowIndex() {
        return currentrow;
    }

    /**
     * vergleicht das Lösungswort mit dem geratenen Wort und weist die richtigen Farben den Buchstaben zu gemäss,
     * Wordle Logik
     * @param guess der erratene String, ohne zugewiesene Farben (ist ein Customstring array mit char und int auf einer
     *              position.
     * @param right der Lösungsstring mit info zu wie oft ein char im wort vorkommt.
     * @return der erratene String, mit zugewiesenen Farben.
     */
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
                    guess[i].color = -1;
                    tempFreq[guessChar - 'a']--;
                }
            }
        }

        return guess;
    }

    /**
     * verwandelt das Lösungswort in ein Array und wie oft die buchstaben dabei vorkommen wird auch gezählt.
     * (ist sehr unglücklich benannt...)
     * @param guess das Lösungswort wird mitgegeben.
     * @return gibt das Array zurück
     */
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

    /**
     * Das erratene Wort wird in ein Array umgewandelt des Typs Customstring
     * @param guess Das erratene Wort wird mitgegeben
     * @return der Array wird zurückgegeben ohne zugewiesene Farben
     */
    CustomString[] TheGuessToArray(String guess) {
        CustomString[] first = new CustomString[5];
        for (int i = 0; i < 5; i++) {
            first[i] = new CustomString(guess.charAt(i), 0);
        }
        return first;
    }

    /**
     * Klasse welche es ermöglicht Chars mit Info zur Farbe zu speichern, wobei:
     * 1 = grün
     * 0 = grau
     * -1= gelb
     */
    public class CustomString {
        char c;
        int color = 0;
        CustomString(char c, int color){
            this.c = c;
            this.color = color;
        }
    }

    /**
     * Klasse welche es ermöglicht einen String in chars zu ändern mit dem gedanken die häufigkeit eines buchstaben im
     * Wort zu speichern.
     */
    public class GuessString {
        char c;
        int frequency = 0;
        GuessString(char c, int frequency){
            this.c = c;
            this.frequency = frequency;
        }
    }

    /**
     * Setzt alle variabeln und methoden wieder zurück um das Spiel nach Win oder Loss neuzustarten.
     */
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