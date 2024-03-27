package C03;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Point point = new Point();

        System.out.print("""
                Would you like to provide initial data yourself?
                Otherwise standard configuration will be used.
                Please enter (y/n):\s""");
        if (scanner.next().equals("y")){
            System.out.print("Enter starting degree: ");
            point.a = Math.toRadians(Double.parseDouble(scanner.next()));
            System.out.print("Enter length of pendulum in meters: ");
            point.l = Double.parseDouble(scanner.next());
            System.out.print("Enter probing time in seconds: ");
            point.dt = Double.parseDouble(scanner.next());
            System.out.println("Data accepted.");
        } else System.out.println("Standard configuration will be used.");

        List<String> euler = euler(point);
        writeDataToFile(euler);
        System.out.println(point);
        //List<String> midpoint = midpoint(a, w, t, dt, l, g);

        //Adding both to compare graphs
        /*List<String> compare = new ArrayList<>();
        String str;
        for (int i = 0; i < euler.size(); i++) {
            str = euler.get(i)+",,"+midpoint.get(i);
            compare.add(str);
        }

        writeDataToFile(compare);*/
    }

    public static List<String> euler(Point point){
        List<String> data = new ArrayList<>();
        Double t = (double) 0, a = point.a, w = point.w;
        data.add(t+","+a+","+w+","+Ec(point.l, w, a));

        while(t<10) {
            t += point.dt;
            double e = point.g / point.l * Math.sin(a);
            a += w * point.dt;
            w += e * point.dt;

            data.add(t+","+a+","+w+","+Ec(point.l, w, a));
        }

        return data;
    }

    public static List<String> midpoint(
            Double a,
            Double w,
            Double t,
            Double dt,
            Double l,
            Double g
    ){
        List<String> data = new ArrayList<>();
        data.add(t+","+a+","+w+","+Ec(l,w,a));

        //First cycle
        t += dt;
        double e = g/l*Math.sin(a);
        w += e*dt/2;
        a += w*dt/2;

        data.add(t+","+a+","+w+","+Ec(l,w,a));

        //Continuation
        while(t<10) {
            t += dt;
            e = g/l*Math.sin(a);
            w += e*dt;
            a += w*dt;

            data.add(t+","+a+","+w+","+Ec(l,w,a));
        }

        return data;
    }

    public static List<String> RK4(Point p){
        List<String> data = new ArrayList<>();

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
        System.out.print("Trajectory data was written to file, path: " + outputFilePath.toAbsolutePath());
    }

    public static double[] derivatives(double a, double w, Point p){
        double[] out = new double[2];
        out[0] = w;
        out[1] = p.g/p.l*Math.sin(a);
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
