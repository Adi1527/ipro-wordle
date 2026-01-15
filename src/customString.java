import java.util.Objects;

public class customString {
    char c;                //speichert den Char welcher wiedergegeben werden muss.
    int color = 0;              //speichert die Info zur Farbe 1=normal, 2=gelb, 3=grün
    customString(char c, int color){
        this.c = c;
        this.color = color;
    }
}
