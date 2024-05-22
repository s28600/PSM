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
        for (int i = 1; i < stringData.pointsStartingPosition.length-1; i++) {
            pointsPosition[i] = Math.sin(stringData.pointsStartingPosition[i])/10000;
        }
        double[] energy = solveEnergy(pointsPosition, pointsSpeed);

        out.add("t"+sep+"i"+sep+0+sep+1+sep+2+sep+3+sep+4+sep+5+sep+6+sep+7+sep+8+sep+9+sep+10);
        out.add(pointsToString(t, "xi", stringData.pointsStartingPosition)+sep+"t"+sep+"Ek"+sep+"Ep"+sep+"Ec");
        out.add(pointsToString(t, "u", pointsPosition)+sep+energyToString(t, energy));

        while (t < 3){
            t += dt;

            double[][] midpoint = solveMidpoint(pointsPosition, pointsSpeed);
            pointsPosition = midpoint[0];
            pointsSpeed = midpoint[1];
            energy = solveEnergy(pointsPosition, pointsSpeed);

            out.add(pointsToString(t, "u", pointsPosition)+sep+energyToString(t, energy));
        }

        return out;
    }

    public static double[] solveEnergy(double[] pointsPosition, double[] pointsSpeed){
        double Ek = 0, Ep = 0, Ec = 0;
        for (int i = 1; i < pointsSpeed.length-1; i++) {
            Ek += Math.pow(pointsSpeed[i], 2);
        }
        Ek = stringData.dx/2*Ek;
        for (int i = 1; i < pointsPosition.length; i++) {
            Ep += Math.pow((pointsPosition[i-1]-pointsPosition[i]), 2);
        }
        Ep = 1/(2* stringData.dx)*Ep;
        Ec = Ek + Ep;
        return new double[]{Ek, Ep, Ec};
    }

    public static double[][] solveMidpoint(double[] pointsPosition, double[] pointsSpeed){
        double[] pointsAcceleration = new double[stringData.pointsStartingPosition.length];
        for (int i = 1; i < pointsAcceleration.length-1; i++) {
            pointsAcceleration[i] = (pointsPosition[i-1]-2*pointsPosition[i]+pointsPosition[i+1])/Math.pow(stringData.dx, 2);
        }
        double[] pointsPosition_tmp = new double[pointsPosition.length];
        for (int i = 1; i < pointsPosition_tmp.length-1; i++) {
            pointsPosition_tmp[i] = pointsPosition[i] + pointsSpeed[i]*dt/2;
        }
        double[] pointsSpeed_tmp = new double[pointsSpeed.length];
        for (int i = 1; i < pointsSpeed_tmp.length-1; i++) {
            pointsSpeed_tmp[i] = pointsSpeed[i] + pointsAcceleration[i]*dt/2;
        }
        for (int i = 1; i < pointsAcceleration.length-1; i++) {
            pointsAcceleration[i] = (pointsPosition_tmp[i-1]-2*pointsPosition_tmp[i]+pointsPosition_tmp[i+1])/Math.pow(stringData.dx, 2);
        }
        for (int i = 1; i < pointsPosition.length-1; i++) {
            pointsPosition[i] += pointsSpeed_tmp[i]*dt;
        }
        for (int i = 1; i < pointsSpeed.length-1; i++) {
            pointsSpeed[i] += pointsAcceleration[i]*dt;
        }
        return new double[][]{pointsPosition, pointsSpeed};
    }

    public static String energyToString(double t, double[] energy){
        StringBuilder out = new StringBuilder();
        out.append(String.valueOf(t).replaceAll("\\.", decimalSep)).append(sep);
        for (double value : energy){
            out.append(String.valueOf(value).replaceAll("\\.", decimalSep)).append(sep);
        }
        return out.toString();
    }

    public static String pointsToString(double t, String type, double[] points){
        StringBuilder out = new StringBuilder();
        out.append(t).append(sep);
        out.append(type).append(sep);
        for (double point : points){
            out.append(String.valueOf(point).replaceAll("\\.", decimalSep)).append(sep);
        }
        return out.toString();
    }
}
