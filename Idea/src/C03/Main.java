package C03;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    final static double g = -9.81;
    static double dt = 0.004;
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        final Point point = new Point();

        /*System.out.print("""
                Would you like to provide initial data yourself?
                Otherwise, standard configuration will be used.
                Please enter (y/n):\s""");
        if (scanner.next().equals("y")){
            System.out.print("Enter starting degree: ");
            point.a = Math.toRadians(Double.parseDouble(scanner.next()));
            System.out.print("Enter length of pendulum in meters: ");
            point.l = Double.parseDouble(scanner.next());
            System.out.print("Enter probing time in seconds: ");
            point.dt = Double.parseDouble(scanner.next());
            System.out.println("Data accepted.");
        } else System.out.println("Standard configuration will be used.");*/

        List<String> euler = solveEuler(point);
        List<String> midpoint = solveMidpoint(point);
        List<String> rk4 = solveRK4(point);

        //Adding all to data file
        List<String> compare = new ArrayList<>();
        String str;
        for (int i = 0; i < euler.size(); i++) {
            str = euler.get(i)+",,"+midpoint.get(i)+",,"+rk4.get(i);
            compare.add(str);
        }

        writeDataToFile(compare);
    }

    public static List<String> solveEuler(Point point){
        List<String> data = new ArrayList<>();
        Double t = (double) 0, a = point.a, w = point.w;
        data.add(t+","+a+","+w+","+Ec(point.l, w, a));

        double[] k;
        while(t<10) {
            t += dt;
            k = derivatives(a, w, point);
            a += k[0] * dt;
            w += k[1] * dt;

            data.add(t+","+a+","+w+","+Ec(point.l, w, a));
        }

        return data;
    }

    public static List<String> solveMidpoint(Point point){
        List<String> data = new ArrayList<>();
        Double t = (double) 0, a = point.a, w = point.w;
        data.add(t+","+a+","+w+","+Ec(point.l, w, a));

        //First cycle
        double[] k = derivatives(a, w, point);
        a += k[0] * dt/2;
        w += k[1] * dt/2;

        //Continuation
        while(t<10) {
            t += dt;
            k = derivatives(a, w, point);
            a += k[0] * dt;
            w += k[1] * dt;
            data.add(t+","+a+","+w+","+Ec(point.l, w, a));

            k = derivatives(a, w, point);
            a += k[0] * dt/2;
            w += k[1] * dt/2;
        }

        return data;
    }

    public static List<String> solveRK4(Point point){
        List<String> data = new ArrayList<>();
        Double t = (double) 0, a = point.a, w = point.w;
        data.add(t+","+a+","+w+","+Ec(point.l, w, a));

        while(t<10) {
            t += dt;

            double[] k1 = derivatives(a, w, point);
            a += k1[0] * dt/2;
            w += k1[1] * dt/2;

            double[] k2 = derivatives(a, w, point);
            a += k2[0] * dt/2;
            w += k2[1] * dt/2;

            double[] k3 = derivatives(a, w, point);
            a += k3[0] * dt;
            w += k3[1] * dt;

            double[] k4 = derivatives(a, w, point);

            double ksa = (k1[0] + 2*k2[0] + 2*k3[0] + k4[0])/6;
            double ksw = (k1[1] + 2*k2[1] + 2*k3[1] + k4[1])/6;

            a += ksa * dt;
            w += ksw * dt;

            data.add(t+","+a+","+w+","+Ec(point.l, w, a));
        }

        return data;
    }

    public static void writeDataToFile(List<String> data) throws IOException {
        Path outputFilePath = Paths.get("data.csv");
        Files.deleteIfExists(outputFilePath);
        Files.createFile(outputFilePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv", true));

        for (String str : data) {
            writer.append(str).append("\n");
        }

        writer.close();
        System.out.println("Trajectory data was written to file, path: " + outputFilePath.toAbsolutePath());
    }

    public static double[] derivatives(double a, double w, Point p){
        double[] out = new double[2];
        out[0] = w;
        out[1] = g / p.l * Math.sin(a);
        return out;
    }

    public static double Ec(double l, double w, double a){
        double y = -l*Math.sin(Math.PI/2-a);
        double v = w*l;
        double h = y+l;
        double Ep = Math.abs(-9.81*h);
        double Ek = Math.pow(v, 2)/2;
        double Ec = Ep+Ek;

        return Ec;
    }
}

class Point {
    double a, w = 0, l;

    public Point() {
        a = Math.toRadians(45);
        l = 1;
    }

    @Override
    public String toString() {
        return "Point{" +
                ", a=" + a +
                ", w=" + w +
                ", l=" + l +
                '}';
    }
}
