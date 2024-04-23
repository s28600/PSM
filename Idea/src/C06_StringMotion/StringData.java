package C06_StringMotion;

public class StringData {
    double L = Math.PI; //Length
    int N = 10; //Points count
    double dx = L/N; //Distance between points
    double[] string;

    public StringData() {
        string = new double[N+1];
        for (int i = 0; i < string.length; i++) {
            string[i] = i*dx;
        }
    }
}
