package C05_MoonCycle;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class Logic {
    static final String sep = String.valueOf(new DecimalFormatSymbols().getDecimalSeparator()).equals(",")?";":",";
    static final double endTime = 31622400; //Seconds in year
    SolarObjectsPerspective perspective;

    public Logic(SolarObjectsPerspective perspective) {
        this.perspective = perspective;
    }

    public List<String> calculate(){
        List<String> output = new ArrayList<>();

        double t = 0,
                x = 0,
                y = perspective==SolarObjectsPerspective.MoonAroundEarth?Data.Rzk:Data.Rzs,
                Vx = perspective==SolarObjectsPerspective.MoonAroundEarth?(Math.sqrt(Data.G*Data.Mz/Data.Rzk)):(Math.sqrt(Data.G*Data.Ms/Data.Rzs)),
                Vy = 0;

        output.add("t"+sep+"x"+sep+"y");
        output.add(t+sep+x+sep+y);

        double[] midpoint;
        while (t < endTime) {
            t += Data.dt;

            midpoint = solveMidpoint(x, y, Vx, Vy);
            x = midpoint[0];
            y = midpoint[1];
            Vx = midpoint[2];
            Vy = midpoint[3];

            output.add(t+sep+x+sep+y);
        }

        return output;
    }

    public double[] solveMidpoint(double x, double y, double Vx, double Vy){
        double[] out = new double[4];

        double[] Ak = getAxisAcceleration(x, y);
        double Akx = Ak[0], Aky = Ak[1];

        double[] k = derivatives(x, Vx, Akx);
        double x_tmp = x + k[0] * Data.dt/2;
        double Vx_tmp = Vx + k[1] * Data.dt/2;
        k = derivatives(y, Vy, Aky);
        double y_tmp = y + k[0] * Data.dt/2;
        double Vy_tmp = Vy + k[1] * Data.dt/2;

        Ak = getAxisAcceleration(x_tmp, y_tmp);
        Akx = Ak[0]; Aky = Ak[1];

        k = derivatives(x_tmp, Vx_tmp, Akx);
        x += k[0] * Data.dt;
        Vx += k[1] * Data.dt;
        k = derivatives(y_tmp, Vy_tmp, Aky);
        y += k[0] * Data.dt;
        Vy += k[1] * Data.dt;

        out[0] = x;
        out[1] = y;
        out[2] = Vx;
        out[3] = Vy;

        return out;
    }

    public  double[] derivatives(double position, double speed, double acceleration){
        double[] out = new double[2];
        out[0] = speed;
        out[1] = acceleration;
        return out;
    }

    public double[] getAxisAcceleration(double x, double y){
        double[] output = new double[2];

        double Wx = 0-x,
                Wy = 0-y,
                Wd = Math.sqrt(Math.pow(Wx, 2)+Math.pow(Wy, 2)),
                Ux = Wx/Wd,
                Uy = Wy/Wd,
                Ak = Data.G*(perspective==SolarObjectsPerspective.MoonAroundEarth?Data.Mz:Data.Ms)/Math.pow(Wd, 2),
                Akx = Ak * Ux,
                Aky = Ak * Uy;

        output[0] = Akx;
        output[1] = Aky;

        return output;
    }
}
