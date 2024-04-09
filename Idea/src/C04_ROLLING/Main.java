package C04_ROLLING;

import java.io.IOException;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static double r = 3;
    static double h = 20;
    static double alpha = Math.toRadians(25);
    static double d = h/Math.sin(alpha);
    static double dt = 0.1;
    static double m = 1;
    static final double g = 9.81;
    static SphericalObject ball = SphericalObject.Ball(r, m);
    static SphericalObject sphere = SphericalObject.Sphere(r, m);
    static String sep;

    public static void main(String[] args) throws IOException {
        final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        String dsep = String.valueOf(dfs.getDecimalSeparator());
        if (dsep.equals(",")) sep = ";";
        else sep = ",";

        System.out.println("Using standard configuration:");
        System.out.println(ball);
        System.out.println(sphere);

        List<String> ballData = calculateTrajectory(ball);
        DataWriter.writeDataToFile(ballData, "data.csv");
    }

    public static List<String> calculateTrajectory(SphericalObject object){
        List<String> data = new ArrayList<>();
        double t = 0, Sx = 0, Sy = object.r, Vx = 0,
                a = g*Math.sin(alpha)/(1+object.I/(m*Math.pow(r, 2))),
                Sx_o = Sx*Math.cos(-alpha)-Sy*Math.sin(-alpha),
                Sy_o = Sx*Math.sin(-alpha)+Sy*Math.cos(-alpha)+h;
        data.add(t+sep+Sx+sep+Sy+sep+Vx+sep+Sx_o+sep+Sy_o);

        double[] k;
        while (Sx < d){
            t += dt;

            k = derivatives(Sx, Vx, a);
            Sx += k[0] * dt;
            Vx += k[1] * dt;

            Sx_o = Sx*Math.cos(-alpha)-Sy*Math.sin(-alpha);
            Sy_o = Sx*Math.sin(-alpha)+Sy*Math.cos(-alpha)+h;

            data.add(t+sep+Sx+sep+Sy+sep+Vx+sep+Sx_o+sep+Sy_o);
        }

        return data;
    }

    public static double[] derivatives(double Sx, double Vx, double a){
        double[] out = new double[2];
        out[0] = Vx;
        out[1] = a;
        return out;
    }

    public static double[] solveMidpoint(double Sx, double Vx, double a){
        double[] out = new double[2];

        double[] k = derivatives(Sx, Vx, a);
        Sx += k[0] * dt/2;
        Vx += k[1] * dt/2;

        k = derivatives(Sx, Vx, a);
        out[0] = Sx + k[0] * dt;
        out[1] = Vx + k[1] * dt;

        return out;
    }
}
