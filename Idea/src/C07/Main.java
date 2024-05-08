package C07;

import java.io.IOException;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class Main {
    static final double dt = 0.01;
    static final double A = 10;
    static final double B = 25;
    static final double C = (double) 8/3;
    static double x = 1;
    static double y = 1;
    static double z = 1;
    static final String decimalSep = String.valueOf(new DecimalFormatSymbols().getDecimalSeparator());
    static final String sep = decimalSep.equals(",")?";":",";
    public static void main(String[] args) throws IOException {
        ArrayList<String> euler = solveEuler(x, y, z);
        ArrayList<String> midpoint = solveMidpoint(z, y, z);
        ArrayList<String> rk4 = solveRK4(z, y, z);

        ArrayList<String> combined = new ArrayList<>();
        for (int i = 0; i < euler.size(); i++) {
            combined.add(euler.get(i) + sep + sep + midpoint.get(i) + sep + sep + rk4.get(i));
        }

        MyFileWriter.writeDataToFile(combined, "data.csv");
    }

    public static ArrayList<String> solve (Methods method, double x, double y, double z){
        ArrayList<String> results = new ArrayList<>();
        switch (method){
            case Euler -> results.add("Euler" +sep+sep+sep);
            case Midpoint -> results.add("Midpoint" +sep+sep+sep);
            case RK4 -> results.add("RK4" +sep+sep+sep);
        }
        results.add("t"+sep+"x"+sep+"y"+sep+"z");
        double t = 0;
        results.add((t+sep+x+sep+y+sep+z).replaceAll("\\.", decimalSep));
        System.out.println(t + " " + x + " " + y + " " + z + " ");

        while (t < 40){
            t += dt;

            switch (method){
                case Euler -> {
                    double[] k = derivatives(x, y, z);
                    x += k[0]*dt;
                    y += k[1]*dt;
                    z += k[2]*dt;
                }
                case Midpoint -> {
                    double[] k = derivatives(x, y, z);
                    double x_tmp = x + k[0]*dt/2;
                    double y_tmp = y + k[1]*dt/2;
                    double z_tmp = z + k[2]*dt/2;
                    k = derivatives(x_tmp, y_tmp, z_tmp);
                    x += k[0]*dt;
                    y += k[1]*dt;
                    z += k[2]*dt;
                }
                case RK4 -> {
                    double[] k1 = derivatives(x, y, z);
                    double x_tmp = x + k1[0]*dt/2;
                    double y_tmp = y + k1[1]*dt/2;
                    double z_tmp = z + k1[2]*dt/2;

                    double[] k2 = derivatives(x_tmp, y_tmp, z_tmp);
                    x_tmp = x + k2[0]*dt/2;
                    y_tmp = y + k2[1]*dt/2;
                    z_tmp = z + k2[2]*dt/2;

                    double[] k3 = derivatives(x_tmp, y_tmp, z_tmp);
                    x_tmp = x + k2[0]*dt;
                    y_tmp = y + k2[1]*dt;
                    z_tmp = z + k2[2]*dt;

                    double[] k4 = derivatives(x_tmp, y_tmp, z_tmp);

                    double ksx = (k1[0] + 2*k2[0] + 2*k3[0] + k4[0])/6;
                    double ksy = (k1[1] + 2*k2[1] + 2*k3[1] + k4[1])/6;
                    double ksz = (k1[2] + 2*k2[2] + 2*k3[2] + k4[2])/6;

                    x += ksx * dt;
                    y += ksy * dt;
                    z += ksz * dt;
                }
            }

            results.add((t+sep+x+sep+y+sep+z).replaceAll("\\.", decimalSep));
            System.out.println(t + " " + x + " " + y + " " + z + " ");
        }
        return results;
    }

    public static ArrayList<String> solveEuler(double x, double y, double z){
        ArrayList<String> results = new ArrayList<>();
        double t = 0;
        results.add("Euler" +sep+sep+sep);
        results.add("t"+sep+"x"+sep+"y"+sep+"z");
        String line = t + sep + x + sep + y + sep + z;
        results.add(line.replaceAll("\\.", decimalSep));
        System.out.println(t + " " + x + " " + y + " " + z + " ");
        while (t < 40){
            t += dt;
            double[] k = derivatives(x, y, z);
            x += k[0]*dt;
            y += k[1]*dt;
            z += k[2]*dt;

            line = t + sep + x + sep + y + sep + z;
            results.add(line.replaceAll("\\.", decimalSep));
            System.out.println(t + " " + x + " " + y + " " + z + " ");
        }
        return results;
    }

    public static ArrayList<String> solveMidpoint(double x, double y, double z){
        ArrayList<String> results = new ArrayList<>();
        double t = 0;
        results.add("Midpoint" +sep+sep+sep);
        results.add("t"+sep+"x"+sep+"y"+sep+"z");
        System.out.println(t + " " + x + " " + y + " " + z + " ");
        String line = t + sep + x + sep + y + sep + z;
        results.add(line.replaceAll("\\.", decimalSep));

        while (t < 40){
            t += dt;
            double[] k = derivatives(x, y, z);
            double x_tmp = x + k[0]*dt/2;
            double y_tmp = y + k[1]*dt/2;
            double z_tmp = z + k[2]*dt/2;
            k = derivatives(x_tmp, y_tmp, z_tmp);
            x += k[0]*dt;
            y += k[1]*dt;
            z += k[2]*dt;

            line = t + sep + x + sep + y + sep + z;
            results.add(line.replaceAll("\\.", decimalSep));
            System.out.println(t + " " + x + " " + y + " " + z + " ");
        }
        return results;
    }

    public static ArrayList<String> solveRK4(double x, double y, double z){
        ArrayList<String> results = new ArrayList<>();
        double t = 0;
        results.add("RK4" +sep+sep+sep);
        results.add("t"+sep+"x"+sep+"y"+sep+"z");
        System.out.println(t + " " + x + " " + y + " " + z + " ");
        String line = t + sep + x + sep + y + sep + z;
        results.add(line.replaceAll("\\.", decimalSep));

        while (t < 40){
            t += dt;

            double[] k1 = derivatives(x, y, z);
            double x_tmp = x + k1[0]*dt/2;
            double y_tmp = y + k1[1]*dt/2;
            double z_tmp = z + k1[2]*dt/2;

            double[] k2 = derivatives(x_tmp, y_tmp, z_tmp);
            x_tmp = x + k2[0]*dt/2;
            y_tmp = y + k2[1]*dt/2;
            z_tmp = z + k2[2]*dt/2;

            double[] k3 = derivatives(x_tmp, y_tmp, z_tmp);
            x_tmp = x + k2[0]*dt;
            y_tmp = y + k2[1]*dt;
            z_tmp = z + k2[2]*dt;

            double[] k4 = derivatives(x_tmp, y_tmp, z_tmp);

            double ksx = (k1[0] + 2*k2[0] + 2*k3[0] + k4[0])/6;
            double ksy = (k1[1] + 2*k2[1] + 2*k3[1] + k4[1])/6;
            double ksz = (k1[2] + 2*k2[2] + 2*k3[2] + k4[2])/6;

            x += ksx * dt;
            y += ksy * dt;
            z += ksz * dt;

            line = t + sep + x + sep + y + sep + z;
            results.add(line.replaceAll("\\.", decimalSep));
            System.out.println(t + " " + x + " " + y + " " + z + " ");
        }
        return results;
    }

    public static double[] derivatives(double x, double y, double z){
        double[] out = new double[3];
        double dx = A*y - A*x;
        double dy = -x*z + B*x - y;
        double dz = x*y - C*z;
        out[0] = dx;
        out[1] = dy;
        out[2] = dz;
        return out;
    }
}

enum Methods {
    Euler, Midpoint, RK4
}
