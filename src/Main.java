import wordle.controller.WordleController;
import wordle.controller.WordleModel;
import wordle.view.gui.WordleGUI;

void main() {
    WordleModel model = new WordleModel();
    WordleController controller = new WordleController(model);
    WordleGUI gui = new WordleGUI(controller, 60);
    gui.proceedUntilClosed();





//    String[] words = getarray();
//    String toguess = getword(words);                         //zu errratendes Wort
//    IO.print("Try to guess the 5 letter Word in maximum of 6 tries: (use lowercase letters)\n>>>");
//    String guess = input(words);                             //der Rateversuch
//    if (firstcheck(toguess,guess)){                     // erster check ob es beim ersten versuch schon funktioniert.
//        IO.println(Backgroundcolor.ANSI_GREEN+guess+Backgroundcolor.ANSI_RESET);
//        IO.println("Richtig!!");
//    }
//    guessstring[] zuraten = guesstoarray(toguess);
//    guessstring[] zuratennew = frequencycount(zuraten);
//    fivequestions(toguess,guess, words, zuratennew);
//    IO.println("richtiges Wort: "+ toguess);
}

void fivequestions (String toguess, String guess, String[] list, guessstring[] zuraten){
    int count = 0;
    while (!toguess.equals(guess) && count != 5){       //weitere checks mit aufforderung zum nochmals raten.
        customString[] geraten = theguesstoarray(guess);
        checkletterandcolor(geraten,zuraten);
        guess = input(list);
        count++;
    }
}
String input(String[] list){                                         // nimmt einen 5 buchstaben langen Input.
    String guess = IO.readln().toLowerCase();                         // soll noch kontrollieren ob der Input ein wort ist
    while (!Arrays.asList(list).contains(guess)) {
        IO.println("Dieses Wort ist mir nicht bekannt, oder nicht 5 Buchstaben lang.");
        guess = IO.readln();
        IO.println();
    }
    return guess;
}

boolean firstcheck(String right, String guess){         // erster check
    return (right.equals(guess));
}

customString[] theguesstoarray(String guess) {           //kontrolliert jeden buchstaben und gibt ihn in der entsprechenden Farbe zurück
    customString[] first = new customString[5];
    for (int i = 0; i < 5; i++) {
        first[i] = new customString(guess.charAt(i), 0);
    }
    return first;
}
String getword(String[] list){                                       //nimmt ein random wort aus dem array
    Random rng = new Random();
    int a = rng.nextInt(5353);
    return list[a];
}

/**
 * Mach aus der Wortliste ein array
 * @return das Arr
 */
String[] getarray(){                                                //macht aus der wortliste ein array.
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
void printcolor(customString[] first){                              //printed den guess in den Farben
    for (int i = 0; i < 5; i++){
        if (first[i].color == 1){
            IO.print(Backgroundcolor.ANSI_GREEN + first[i].c + Backgroundcolor.ANSI_RESET);
        } else if (first[i].color == -1) {
            IO.print(Backgroundcolor.ANSI_YELLOW + first[i].c + Backgroundcolor.ANSI_RESET);
        }else  IO.print(first[i].c);
    }
}
guessstring[] guesstoarray(String guess){                           //verwandelt das richtige wort in einen array
    guessstring[] guessstring = new guessstring[5];
    for (int i = 0 ; i < 5; i++){
        guessstring[i] = new guessstring(guess.charAt(i),0);
    }
    return guessstring;
}
guessstring[] frequencycount(guessstring[] guess){                  //zählt wie oft ein buchstabe im richtigen wort vorkommt falls mehrmals
    for (int i = 0; i < 5; i++){
        for (int j = 0; j < 5; j++){
            if (guess[i].c == guess[j].c && i != j){
                guess[i].frequency++;
            }
        }
    }
    return guess;
}
void checkletterandcolor(customString[] guess, guessstring[] right) {
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
    printcolor(guess);
    IO.println();
}