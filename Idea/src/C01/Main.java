package C01;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter x: ");
        String input = scanner.next();
        double x = 0, degrees = 0;
        System.out.println("----------------------------------------");

        if (input.charAt(input.length()-1) == '°') {
            degrees = Double.parseDouble(input.substring(0,input.length()-1));
            System.out.println("Input in degrees: " + degrees);
            x = Math.toRadians(degrees);
            System.out.println("Conversion to radians: " + x);
        } else {
            x = Double.parseDouble(input);
            System.out.println("Input in radians: " + x);
            degrees = x*180/Math.PI;
            System.out.println("Conversion to degrees: " + degrees);
        }
        double defaultSin = Math.sin(x);
        System.out.println("----------------------------------------");

        //Correction of full rotation
        if (degrees >= 360){
            System.out.println("Degrees >360° - correction");
            degrees = degrees%360;
            System.out.println("Corrected: " + degrees);
            System.out.println("----------------------------------------");
        }

        //Correction to first quarter
        if (degrees > 90 && degrees <= 180){
            degrees = 180 - degrees;
        } else if (degrees > 180 && degrees <= 270){
            degrees = (degrees - 180)*(-1);
        } else if (degrees > 270 && degrees < 360){
            degrees = (360 - degrees)*(-1);
        }

        x = Math.toRadians(degrees);
        System.out.println("0) Default sin(x) from java.math: " + defaultSin);

        double taylor = 0;
        double diff;
        for (int i = 0; i < 10; i++) {
            taylor += (Math.pow(-1,i)*Math.pow(x, 2*i+1))/factorial(2*i+1);
            if (i == 0){
                System.out.println((i+1) + ") Taylors sin(x) with " + (i+1) + " expression: " + taylor);
            } else {
                System.out.println((i+1) + ") Taylors sin(x) with " + (i+1) + " expressions: " + taylor);
            }
            diff = Math.abs(defaultSin - taylor);
            System.out.println("Difference: " + diff);
        }
    }

    static int factorial(int num){
        int fact = 1;
        for (int i = 1; i <= num; i++) {
            fact = fact*i;
        }
        return fact;
    }
}
