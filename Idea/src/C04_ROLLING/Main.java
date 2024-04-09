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
    static String sep;

    public static void main(String[] args) throws IOException {
        final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        String dsep = String.valueOf(dfs.getDecimalSeparator());
        if (dsep.equals(",")) sep = ";";
        else sep = ",";

        System.out.println("Using standard configuration:");
        System.out.println(SphericalObject.Ball(r, m));
        System.out.println(SphericalObject.Sphere(r, m));

        List<String> ballData = calculateData(SphericalObject.Ball(r, m));
        List<String> sphereData = calculateData(SphericalObject.Sphere(r, m));
        List<String> combinedData = new ArrayList<>();
        String filler = "";
        for (int i = 0; i < 15; i++) {
            filler += sep;
        }
        for (int i = 0; i < sphereData.size(); i++) {
            try{
                combinedData.add(ballData.get(i)+sep+sep+sphereData.get(i));
            } catch (IndexOutOfBoundsException e){
                combinedData.add(filler+sphereData.get(i));
            }
        }
        DataWriter.writeDataToFile(combinedData, "data.csv");
    }

    public static List<String> calculateData(SphericalObject object){
        List<String> data = new ArrayList<>();
        double t = 0, Sx = 0, Sy = object.r, Vx = 0,
                a = g*Math.sin(alpha)/(1+object.I/(m*Math.pow(r, 2))),
                Sx_o = Sx*Math.cos(-alpha)-Sy*Math.sin(-alpha),
                Sy_o = Sx*Math.sin(-alpha)+Sy*Math.cos(-alpha)+h,
                beta = Math.PI/2, w = 0, eps = -a/r,
                x = r*Math.cos(beta)+Sx_o,
                y = r*Math.sin(beta)+Sy_o,
                Ep = m*g*Sy_o,
                Ek = m*Math.pow(Vx, 2)/2 + object.I*Math.pow(w, 2)/2,
                Ec = Ep + Ek;

        String title = object.label;
        for (int i = 0; i < 13; i++) {
            title += sep;
        }
        data.add(title);
        data.add("t"+sep+"Sx"+sep+"Sy"+sep+"Vx"+sep+"Sx_o"+sep+"Sy_o"+sep+"beta"+sep+"w"+sep+"eps"+sep+"x"+sep+"y"+sep+"Ep"+sep+"Ek"+sep+"Ec");
        data.add(t+sep+Sx+sep+Sy+sep+Vx+sep+Sx_o+sep+Sy_o+sep+beta+sep+w+sep+eps+sep+x+sep+y+sep+Ep+sep+Ek+sep+Ec);

        double[] midpoint;
        while (Sx < d){
            t += dt;

            midpoint = solveMidpoint(Sx, Vx, a);
            Sx = midpoint[0];
            Vx = midpoint[1];

            Sx_o = Sx*Math.cos(-alpha)-Sy*Math.sin(-alpha);
            Sy_o = Sx*Math.sin(-alpha)+Sy*Math.cos(-alpha)+h;

            midpoint = solveMidpoint(beta, w, eps);
            beta = midpoint[0];
            w = midpoint[1];

            x = r*Math.cos(beta)+Sx_o;
            y = r*Math.sin(beta)+Sy_o;

            Ep = m*g*Sy_o;
            Ek = m*Math.pow(Vx, 2)/2 + object.I*Math.pow(w, 2)/2;
            Ec = Ep + Ek;

            data.add(t+sep+Sx+sep+Sy+sep+Vx+sep+Sx_o+sep+Sy_o+sep+beta+sep+w+sep+eps+sep+x+sep+y+sep+Ep+sep+Ek+sep+Ec);
        }

        return data;
    }

    public static double[] solveMidpoint(double Sx, double Vx, double a){
        double[] out = new double[2];

        double[] k = derivatives(Sx, Vx, a);
        double Sx_tmp = Sx + k[0] * dt/2;
        double Vx_tmp = Vx + k[1] * dt/2;

        k = derivatives(Sx_tmp, Vx_tmp, a);
        out[0] = Sx + k[0] * dt;
        out[1] = Vx + k[1] * dt;

        return out;
    }

    public static double[] derivatives(double Sx, double Vx, double a){
        double[] out = new double[2];
        out[0] = Vx;
        out[1] = a;
        return out;
    }
}
