void main() {
    int count = 0;
    String toguess = getword();                         //zu errratendes Wort
    IO.print("Try to guess the 5 letter Word in maximum of 6 tries:\n>");
    String guess = input();                             //der Rateversuch
    if (firstcheck(toguess,guess)){                     // erster check ob es beim ersten versuch schon funktioniert.
        IO.println(Backgroundcolor.ANSI_GREEN+guess+Backgroundcolor.ANSI_RESET);
        IO.println("Richtig!!");
    }

    while (!toguess.equals(guess) && count != 5){       //weitere checks mit aufforderung zum nochmals raten.
        lettercheck(toguess,guess);
        guess = input();
        count++;
    }
    IO.println("richtiges Wort: "+ toguess);
}
String input(){                                         // nimmt einen 5 buchstaben langen Input.
    String guess = IO.readln();                         // soll noch kontrollieren ob der Input ein wort ist
    while (guess.length() != 5) {
        IO.println("FIVE Letters please");
        guess = IO.readln();
        IO.println();
    }
    return guess;
}

boolean firstcheck(String right, String guess){         // erster check
    return (right.equals(guess));
}

void lettercheck(String right, String guess) {           //kontrolliert jeden buchstaben und gibt ihn in der entsprechenden Farbe zurück
    for (int i = 0; i < 5; i++) {
        String a = String.valueOf(guess.charAt(i));
        if (right.contains(a)){
            if(guess.charAt(i) == right.charAt(i)){
                IO.print(Backgroundcolor.ANSI_GREEN + guess.charAt(i) + Backgroundcolor.ANSI_RESET);
            }else IO.print(Backgroundcolor.ANSI_YELLOW + guess.charAt(i) + Backgroundcolor.ANSI_RESET);
        }else IO.print(guess.charAt(i));
    }
    IO.println();
}
String getword(){//Wort aus der Liste nehmen, damit dieses erraten werden kann.
    String[] list = new String[264];
    InputStream is = getClass().getResourceAsStream("/Data/words_de.txt");
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    for (int i = 0; i < 264; i++){
        try {
            list[i] = reader.readLine();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    Random rng = new Random();
    int a = rng.nextInt(264);
    String word = list[a];
    return word;
}