package C03_PENDULUM_RK4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    final static Point point = new Point();
    final static double g = -9.81;
    static double dt = 0.004;
    static String sep;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        String dsep = String.valueOf(dfs.getDecimalSeparator());
        if (dsep.equals(",")) sep = ";";
        else sep = ",";

        System.out.print("""
                Would you like to provide initial data yourself?
                Otherwise, standard configuration will be used.
                Please enter (y/n):\s""");
        if (scanner.next().equals("y")){
            System.out.print("Enter starting degree: ");
            point.a = Math.toRadians(Double.parseDouble(scanner.next()));
            System.out.print("Enter length of pendulum in meters: ");
            point.l = Double.parseDouble(scanner.next());
            System.out.print("Enter probing time in seconds: ");
            dt = Double.parseDouble(scanner.next());
            System.out.println("Data accepted.");
        } else System.out.println("Standard configuration will be used.");

        List<String> euler = solveEuler(point);
        List<String> midpoint = solveMidpoint(point);
        List<String> rk4 = solveRK4(point);

        //Adding all to data file
        List<String> compare = new ArrayList<>();
        String str = "Euler+Midpoint+RK4";
        str = str.replace("+", sep+sep+sep+sep+sep+sep+sep+sep+sep);
        compare.add(str);
        str = "t"+sep+"a"+sep+"w"+sep+"x"+sep+"y"+sep+"Ep"+sep+"Ek"+sep+"Ec";
        compare.add(str+sep+sep+str+sep+sep+str);
        for (int i = 0; i < euler.size(); i++) {
            str = euler.get(i)+sep+sep+midpoint.get(i)+sep+sep+rk4.get(i);
            str = str.replace(".", dsep);
            compare.add(str);
        }

        writeDataToFile(compare);
    }

    public static List<String> solveEuler(Point point){
        List<String> data = new ArrayList<>();
        Double t = (double) 0, a = point.a, w = point.w;
        data.add(t+sep+a+sep+w+sep+positionEnergy(point.l, w, a));

        double[] k;
        while(t<10) {
            t += dt;
            k = derivatives(a, w, point);
            a += k[0] * dt;
            w += k[1] * dt;

            data.add(t+sep+a+sep+w+sep+positionEnergy(point.l, w, a));
        }

        return data;
    }

    public static List<String> solveMidpoint(Point point){
        List<String> data = new ArrayList<>();
        Double t = (double) 0, a = point.a, w = point.w;
        data.add(t+sep+a+sep+w+sep+positionEnergy(point.l, w, a));

        double[] k;
        while(t<10) {
            t += dt;

            k = derivatives(a, w, point);
            double a_tmp = a + k[0] * dt/2;
            double w_tmp = w + k[1] * dt/2;

            k = derivatives(a_tmp, w_tmp, point);
            a += k[0] * dt;
            w += k[1] * dt;

            data.add(t+sep+a+sep+w+sep+positionEnergy(point.l, w, a));
        }

        return data;
    }

    public static List<String> solveRK4(Point point){
        List<String> data = new ArrayList<>();
        Double t = (double) 0, a = point.a, w = point.w;
        data.add(t+sep+a+sep+w+sep+positionEnergy(point.l, w, a));

        while(t<10) {
            t += dt;

            double[] k1 = derivatives(a, w, point);
            double a_tmp = a + k1[0] * dt/2;
            double w_tmp = w + k1[1] * dt/2;

            double[] k2 = derivatives(a_tmp, w_tmp, point);
            a_tmp = a + k2[0] * dt/2;
            w_tmp = w + k1[1] * dt/2;

            double[] k3 = derivatives(a_tmp, w_tmp, point);
            a_tmp = a + k2[0] * dt;
            w_tmp = w + k1[1] * dt;

            double[] k4 = derivatives(a_tmp, w_tmp, point);

            double ksa = (k1[0] + 2*k2[0] + 2*k3[0] + k4[0])/6;
            double ksw = (k1[1] + 2*k2[1] + 2*k3[1] + k4[1])/6;

            a += ksa * dt;
            w += ksw * dt;

            data.add(t+sep+a+sep+w+sep+positionEnergy(point.l, w, a));
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

    public static String positionEnergy(double l, double w, double a){
        double x = l*Math.cos(Math.PI/2-a);
        double y = -l*Math.sin(Math.PI/2-a);
        double Ep = Math.abs(-9.81*(y+l));
        double Ek = Math.pow(w*l, 2)/2;
        double Ec = Ep+Ek;

        return x+sep+y+sep+Ep+sep+Ek+sep+Ec;
    }
}
