package C03;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double a = Math.toRadians(45);
        double w = 0, t = 0, dt = 0.004, l = 1, g = -9.81;

        System.out.print("""
                Would you like to provide initial data yourself?
                Otherwise standard configuration will be used.
                Please enter (y/n):\s""");
        if (scanner.next().equals("y")){
            System.out.print("Enter starting degree: ");
            a = Math.toRadians(Double.parseDouble(scanner.next()));
            System.out.print("Enter length of pendulum in meters: ");
            l = Double.parseDouble(scanner.next());
            System.out.println("Data accepted.");
        } else System.out.println("Standard configuration will be used.");

        double e = g/l*Math.sin(a);
        double da = derivativeA(w, dt);
        double dw = derivativeW(e, dt);

        while(t<20){
            t+=dt;
            a+=da;
            w+=dw;
            e = g/l*Math.sin(a);
            da = derivativeA(w, dt);
            dw = derivativeW(e, dt);
        }
    }

    public static double derivativeA(double w, double dt){
        return w*dt;
    }

    public static double derivativeW(double e, double dt){
        return e*dt;
    }
}
