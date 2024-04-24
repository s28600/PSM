package C06_StringMotion;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class Util {
    static double dt = 0.2;
    static StringData stringData = new StringData();
    static final String decimalSep = String.valueOf(new DecimalFormatSymbols().getDecimalSeparator());
    static final String sep = decimalSep.equals(",")?";":",";

    public static List<String> calculate(){
        List<String> out = new ArrayList<>();

        double t = 0;
        double[] pointsPosition = new double[stringData.pointsStartingPosition.length];
        double[] pointsSpeed = new double[stringData.pointsStartingPosition.length];
        double[] pointsAcceleration = new double[stringData.pointsStartingPosition.length];
        for (int i = 1; i < stringData.pointsStartingPosition.length-1; i++) {
            pointsPosition[i] = Math.sin(stringData.pointsStartingPosition[i])/10000;
        }

        out.add(pointsToString(t, "xi", stringData.pointsStartingPosition));
        out.add(pointsToString(t, "u", pointsPosition));

        while (t < 2){
            t += dt;

            for (int i = 1; i < pointsAcceleration.length-1; i++) {
                pointsAcceleration[i] = (pointsPosition[i-1]-2*pointsPosition[i]+pointsPosition[i+1])/Math.pow(stringData.dx, 2);
            }
            for (int i = 1; i < pointsPosition.length-1; i++) {
                pointsPosition[i] += pointsSpeed[i]*dt;
            }
            for (int i = 1; i < pointsSpeed.length-1; i++) {
                pointsSpeed[i] += pointsAcceleration[i]*dt;
            }

            out.add(pointsToString(t, "u", pointsPosition));
        }

        return out;
    }

    public static String pointsToString(double t, String type, double[] points){
        StringBuilder out = new StringBuilder();
        out.append("t").append(t).append(sep);
        out.append(type).append(sep);
        for (double point : points){
            out.append(String.valueOf(point).replaceAll("\\.", decimalSep)).append(sep);
        }
        return out.toString();
    }
}
