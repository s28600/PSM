package C05_MoonCycle;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class Logic {
    static final String sep = String.valueOf(new DecimalFormatSymbols().getDecimalSeparator()).equals(",")?";":",";
    public static List<String> calculate(){
        List<String> output = new ArrayList<>();

        double t = 0,
                x = 0,
                y = Data.Rzk,
                Vx = Math.sqrt(Data.G*Data.Mz/Data.Rzk),
                Vy = 0;

        output.add(t+sep+x+sep+y+sep+Vx+sep+Vy);

        double[] midpoint;
        while (t < 5381500){
            t += Data.dt;

            double[] Ak = getAxisAcceleration(x, y);
            double Akx = Ak[0], Aky = Ak[1];

            midpoint = solveMidpoint(x, Vx, Akx);
            x = midpoint[0];
            Vx = midpoint[1];

            midpoint = solveMidpoint(y, Vy, Aky);
            y = midpoint[0];
            Vy = midpoint[1];

            output.add(t+sep+x+sep+y+sep+Vx+sep+Vy);
        }

        return output;
    }

    public static double[] solveMidpoint(double position, double speed, double acceleration){
        double[] out = new double[2];

        double[] k = derivatives(position, speed, acceleration);
        double position_tmp = position + k[0] * Data.dt/2;
        double speed_tmp = speed + k[1] * Data.dt/2;

        k = derivatives(position_tmp, speed_tmp, acceleration);
        out[0] = position + k[0] * Data.dt;
        out[1] = speed + k[1] * Data.dt;

        return out;
    }

    public static  double[] derivatives(double position, double speed, double acceleration){
        double[] out = new double[2];
        out[0] = speed;
        out[1] = acceleration;
        return out;
    }

    public static double[] getAxisAcceleration(double x, double y){
        double[] output = new double[2];

        double Wx = 0-x,
                Wy = 0-y,
                Wd = Math.sqrt(Math.pow(Wx, 2)+Math.pow(Wy, 2)),
                Ux = Wx/Wd,
                Uy = Wy/Wd,
                Ak = Data.G*Data.Mz/Math.pow(Wd, 2),
                Akx = Ak * Ux,
                Aky = Ak * Uy;

        output[0] = Akx;
        output[1] = Aky;

        return output;
    }
}
