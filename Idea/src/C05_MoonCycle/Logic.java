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
                Vy = 0,
                Wx = 0-x,
                Wy = 0-y,
                Wd = Math.sqrt(Math.pow(Wx, 2)+Math.pow(Wy, 2)),
                Ux = Wx/Wd,
                Uy = Wy/Wd,
                Ak = Data.G*Data.Mz/Math.pow(Wd, 2),
                Akx = Ak * Ux,
                Aky = Ak * Uy,
                Dx = Vx * Data.dt,
                Dy = Vy * Data.dt,
                DVx = Akx * Data.dt,
                DVy = Aky * Data.dt;

        output.add(t+sep+x+sep+y+sep+Vx+sep+Vy+sep+Wx+sep+Wy+sep+Wd+sep+Ux+sep+Uy+sep+Ak+sep+Akx+sep+Aky+sep+Dx+sep+Dy+sep+DVx+sep+DVy+sep);

        while (t < 5381500){
            t += Data.dt;
            x += Dx;
            y += Dy;
            Vx += DVx;
            Vy += DVy;

            Wx = 0-x;
            Wy = 0-y;
            Wd = Math.sqrt(Math.pow(Wx, 2)+Math.pow(Wy, 2));
            Ux = Wx/Wd;
            Uy = Wy/Wd;
            Ak = Data.G*Data.Mz/Math.pow(Wd, 2);
            Akx = Ak * Ux;
            Aky = Ak * Uy;
            Dx = Vx * Data.dt;
            Dy = Vy * Data.dt;
            DVx = Akx * Data.dt;
            DVy = Aky * Data.dt;

            output.add(t+sep+x+sep+y+sep+Vx+sep+Vy+sep+Wx+sep+Wy+sep+Wd+sep+Ux+sep+Uy+sep+Ak+sep+Akx+sep+Aky+sep+Dx+sep+Dy+sep+DVx+sep+DVy+sep);
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

    public double getAxisAcceleration(){
        return 0;
    }
}
