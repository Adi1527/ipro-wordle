void main() {
//    WordleModel model = new WordleModel();
//    WordleController controller = new WordleController(model);
//    WordleGUI gui = new WordleGUI(controller, 60);
//    gui.proceedUntilClosed();


    String[] words = GetArray();
    String toguess = GetWord(words);                                    //zu errratendes Wort
    IO.print("Try to guess the 5 letter Word in maximum of 6 tries: (use lowercase letters)\n>>>");
    String guess = Input(words);                                        //der Rateversuch
    if (FirstCheck(toguess,guess)){                                     // erster check ob es beim ersten versuch schon funktioniert.
        IO.println(Backgroundcolor.ANSI_GREEN+guess+Backgroundcolor.ANSI_RESET);
        IO.println("Richtig!!");
    }
    GuessString[] zuraten = GuessToArray(toguess);
    GuessString[] zuratennew = FrequencyCount(zuraten);
    FiveQuestions(toguess,guess, words, zuratennew);
}

/**
 * Diese Methode sorgt dafür das solange nicht 6 versuche durchgeführt wurden und das Wort noch nicht erraten
 * wurde, dass noch weitergefragt wird.
 * @param toguess Der zu erratende String
 * @param guess Der erratene String des Users
 * @param list Ein Array welches alle Wörter enthält welche möglich sind als Eingabe oder als Rateziel.
 * @param zuraten Ein Array Welches das zu erratende Wort in Char's abgespeichert hat und zudem noch die Info,
 *                wie Oft ein Char vorkommt im Wort
 */
void FiveQuestions (String toguess, String guess, String[] list, GuessString[] zuraten){
    int count = 0;
    while (!toguess.equals(guess) && count < 5){
        CustomString[] geraten = TheGuessToArray(guess);
        CheckLetterAndColor(geraten,zuraten);
        guess = Input(list);
        count++;
    }
    if (toguess.equals(guess)){
        IO.println("Richtig gerraten!");
    }else     IO.println("richtiges Wort: "+ toguess);

}

/**
 *Nimmt den Input in die Konsole und Konsole und Kontrolliert ob dieser in der Wort Liste vorkommt.
 * @param list Ein Array welches alle Wörter enthält welche möglich sind als Eingabe oder als Rateziel.
 * @return Gibt das erratene Wort zurück solange es korrekt ist.
 */
String Input(String[] list){
    String guess = IO.readln().toLowerCase();
    while (!Arrays.asList(list).contains(guess)) {
        IO.println("Dieses Wort ist mir nicht bekannt, oder nicht 5 Buchstaben lang.");
        guess = IO.readln();
        IO.println();
    }
    return guess;
}

/**
 * Überprüft ob das erattene mit dem zu erratenden übereinstimmt.
 * @param right zu erratendes Wort
 * @param guess geratenes Wort
 * @return true oder false Rückgabe.
 */
boolean FirstCheck(String right, String guess){
    return (right.equals(guess));
}

/**
 * Wandelt das zu erratene Wort in einen Array um.
 * @param guess Das vom User erratene Wort
 * @return gibt das erratene Wort als Array zurück gibt in CustomString form.
 */
CustomString[] TheGuessToArray(String guess) {
    CustomString[] first = new CustomString[5];
    for (int i = 0; i < 5; i++) {
        first[i] = new CustomString(guess.charAt(i), 0);
    }
    return first;
}

/**
 *Nimmt aus dem Array mit allen Wörtern ein Random Wort das erraten werden muss.
 * @param list Array mit allen Wörtern.
 * @return Das Wort welches erraten werden muss.
 */
String GetWord(String[] list){
    Random rng = new Random();
    int a = rng.nextInt(5353);
    return list[a];
}

/**
 * Mach aus der Wortliste ein array
 * @return Ein Array mit allen Wörtern welche als Ziel möglich sind und als Eingabe.
 */
String[] GetArray(){
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

/**
 * Printed gemäss den Farben welche im CustomString Array sind die Farben und die Char's.
 * @param first Das Wort welches zuletzt geraten wurde.
 */
void PrintColor(CustomString[] first){
    for (int i = 0; i < 5; i++){
        if (first[i].color == 1){
            IO.print(Backgroundcolor.ANSI_GREEN + first[i].c + Backgroundcolor.ANSI_RESET);
        } else if (first[i].color == -1) {
            IO.print(Backgroundcolor.ANSI_YELLOW + first[i].c + Backgroundcolor.ANSI_RESET);
        }else  IO.print(first[i].c);
    }
}

/**
 * Verwandelt das zu erratende Wort in ein Array
 * @param guess Das Wort welches erraten werden muss.
 * @return Das zu erratende Wort als Array.
 */
GuessString[] GuessToArray(String guess){
    GuessString[] guessstring = new GuessString[5];
    for (int i = 0 ; i < 5; i++){
        guessstring[i] = new GuessString(guess.charAt(i),0);
    }
    return guessstring;
}

/**
 * Zählt wie oft ein Buchstabe im richtigen Wort vorkommt.
 * @param guess Das richtige Wort.
 * @return Das richtige Wort mit der frequency der Buchstaben.
 */
GuessString[] FrequencyCount(GuessString[] guess){
    for (int i = 0; i < 5; i++){
        for (int j = 0; j < 5; j++){
            if (guess[i].c == guess[j].c && i != j){
                guess[i].frequency++;
            }
        }
    }
    return guess;
}

/**
 * Kontrolliert die Buchstaben und weist jenen Grün zu welche am richtigen Ort sind, jene welche am falschen Ort sind,
 * aber im zu erratenden Wort sind werden Gelb.
 * @param guess der gerratene String.
 * @param right der zu erratende String.
 */
void CheckLetterAndColor(CustomString[] guess, GuessString[] right) {
    // Temporäres Array für Häufigkeiten erstellen
    int[] tempFreq = new int[5];
    for (int i = 0; i < 5; i++) {
        tempFreq[i] = right[i].frequency + 1; // +1 weil das Original auch mitgezählt werden muss
    }
    // Erst alle grünen markieren
    for (int i = 0; i < 5; i++) {
        if (guess[i].c == right[i].c) {
            guess[i].color = 1; // grün
            tempFreq[i]--; // Häufigkeit reduzieren
        }
    }
    // Dann gelbe markieren
    for (int i = 0; i < 5; i++) {
        if (guess[i].color == 0) {
            int j = 0;
            while (j < 5 && !(guess[i].c == right[j].c && tempFreq[j] > 0)) {
                j++;
            }
            if (j < 5) {
                guess[i].color = -1;
                tempFreq[j]--;
            }
        }
    }
    PrintColor(guess);
    IO.println();
}