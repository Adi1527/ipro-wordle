//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    IO.println(input());

}

String input(){
    IO.print("Try to guess the 5 letter Word:\n\n     ->");
    String guess = IO.readln();
    while (guess.length() != 5) {
        IO.println("I said FIVE Letters...");
        guess = IO.readln("\n");
    }
    return guess;
}