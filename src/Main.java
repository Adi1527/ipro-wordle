void main() {
    String[] words = getarray();
    String toguess = "statt";                         //zu errratendes Wort
    IO.print("Try to guess the 5 letter Word in maximum of 6 tries:\n>");
    String guess = input(words);                             //der Rateversuch
    if (firstcheck(toguess,guess)){                     // erster check ob es beim ersten versuch schon funktioniert.
        IO.println(Backgroundcolor.ANSI_GREEN+guess+Backgroundcolor.ANSI_RESET);
        IO.println("Richtig!!");
    }
    fivequestions(toguess,guess, words);
    IO.println("richtiges Wort: "+ toguess);
}


void fivequestions (String toguess, String guess, String[] list){
    int count = 0;
    while (!toguess.equals(guess) && count != 5){       //weitere checks mit aufforderung zum nochmals raten.
        lettercheck(toguess,guess);
        guess = input(list);
        count++;
    }
}
String input(String[] list){                                         // nimmt einen 5 buchstaben langen Input.
    String guess = IO.readln();                         // soll noch kontrollieren ob der Input ein wort ist
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

void lettercheck(String right, String guess) {           //kontrolliert jeden buchstaben und gibt ihn in der entsprechenden Farbe zurück
    customString[] first = new customString[5];
    for (int i = 0; i < 5; i++) {
        String a = String.valueOf(guess.charAt(i));
        if (right.contains(a)){
            if(guess.charAt(i) == right.charAt(i)){
                first[i] = new customString(guess.charAt(i), 1);
            }else first[i] = new customString(guess.charAt(i), -1);
        }else first[i] = new customString(guess.charAt(i), 0);
    }
    guessstring[] rightword = guesstoarray(right);
    rightword = frequencycount(rightword);
    printcolor(colorchecker(first,rightword));
    IO.println();
}

String getword(String[] list){                                       //Wort aus der Liste nehmen, damit dieses erraten werden kann.
    Random rng = new Random();
    int a = rng.nextInt(5359);
    return list[a];
}
String[] getarray(){
    String[] list = new String[5359];
    InputStream is = getClass().getResourceAsStream("/Data/words_de.txt");
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    for (int i = 0; i < 5359; i++){
        try {
            list[i] = reader.readLine();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    return list;
}
void printcolor(customString[] first){
    for (int i = 0; i < 5; i++){
        if (first[i].color == 1){
            IO.print(Backgroundcolor.ANSI_GREEN + first[i].c + Backgroundcolor.ANSI_RESET);
        } else if (first[i].color == -1) {
            IO.print(Backgroundcolor.ANSI_YELLOW + first[i].c + Backgroundcolor.ANSI_RESET);
        }else  IO.print(first[i].c);
    }
}
guessstring[] guesstoarray(String guess){
    guessstring[] guessstring = new guessstring[5];
    for (int i = 0 ; i < 5; i++){
        guessstring[i] = new guessstring(guess.charAt(i),0);
    }
    return guessstring;
}
guessstring[] frequencycount(guessstring[] guess){
    for (int i = 0; i < 5; i++){
        for (int j = 0; j < 5; j++){
            if (guess[i].c == guess[j].c && i != j){
                guess[i].frequency++;
            }
        }
    }
    return guess;
}
customString[] colorchecker(customString[] first, guessstring[] right){
    for (int i = 0; i < 5; i++){
        for (int j = 0; j < 5; j++){
            if (right[i].c == first[j].c && first[j].color == -1 && right[i].frequency > 0){
                for (int k = 0; k < 5; k++){
                    if (right[k].c == first[j].c){
                        right[k].frequency = right[k].frequency - 2;
                    }else first[j].color = 0;
                }
            }
        }
    }
    return first;
}