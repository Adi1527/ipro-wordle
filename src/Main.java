void main() {
    int count = 0;
    String toguess = "waage";                         //zu errratendes Wort
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

//void lettercheck(String right, String guess) {           //kontrolliert jeden buchstaben und gibt ihn in der entsprechenden Farbe zurück
//    for (int i = 0; i < 5; i++) {
//        String a = String.valueOf(guess.charAt(i));
//        if (right.contains(a)){
//            if(guess.charAt(i) == right.charAt(i)){
//                IO.print(Backgroundcolor.ANSI_GREEN + guess.charAt(i) + Backgroundcolor.ANSI_RESET);
//            }else IO.print(Backgroundcolor.ANSI_YELLOW + guess.charAt(i) + Backgroundcolor.ANSI_RESET);
//        }else IO.print(guess.charAt(i));
//    }
//    IO.println();
//}

void lettercheck(String right, String guess) {           //kontrolliert jeden buchstaben und gibt ihn in der entsprechenden Farbe zurück
    customString[] first = new customString[5];
    for (int i = 0; i < 5; i++) {
        String a = String.valueOf(guess.charAt(i));
        if (right.contains(a)){
            if(guess.charAt(i) == right.charAt(i)){
                first[i] = new customString(guess.charAt(i), 3);
            }else first[i] = new customString(guess.charAt(i), 2);
        }else first[i] = new customString(guess.charAt(i), 1);
    }
    printcolor(checkcolor(right,first));
    IO.println();
}
customString[] checkcolor(String right, customString[] first){
//    int countright = 0;
//    for (int i = 0; i < 5; i++){
//       for (int j = 0; j < 5; j++){
//           if (right.charAt(i) == right.charAt(j) && i != j){
//               countright++;
//           }
//       }
//    }
//    if (countright == 0){
//        for (int k = 1; k < 5; k++){
//            for (int f = k; f > 0; f--){
//                if (first[k].c == first[f].c && k != f){
//                    first[k].color = 1;
//                }
//            }
//        }
//    } else {
//        for (int k = 1; k < 5; k++){
//            for (int f = k; f > 0; f--){
//                if (first[k].c == first[f].c){
//                    first[k] = new customString(first[k].getchar(), 1);
//                }
//            }
//        }
//    }
    for (int k = 1; k < 5; k++){                                // in zusammenarbeit mit Kevin von Gunten haben wir den obigen Code verkürzt.
        for (int f = k - 1; f >= 0; f--){
            if (first[f].color == 2 && first[k].c == first[f].c && first[k].color == first[f].color){
                first[k].color = 1;
            }
        }
    }
    return first;
}

String getword(){                                       //Wort aus der Liste nehmen, damit dieses erraten werden kann.
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
void printcolor(customString[] first){
    for (int i = 0; i < 5; i++){
        if (first[i].getint() == 3){
            IO.print(Backgroundcolor.ANSI_GREEN + first[i].getchar() + Backgroundcolor.ANSI_RESET);
        } else if (first[i].getint() == 2) {
            IO.print(Backgroundcolor.ANSI_YELLOW + first[i].getchar() + Backgroundcolor.ANSI_RESET);
        }else  IO.print(first[i].getchar());
    }
}