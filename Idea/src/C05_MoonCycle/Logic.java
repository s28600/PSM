package C05_MoonCycle;

public class Logic {
    Data data;

    public Logic(Data data) {
        this.data = data;
    }

    public double[] solveMidpoint(double Sx, double Vx, double a){
        double[] out = new double[2];

        double[] k = derivatives(Sx, Vx, a);
        double Sx_tmp = Sx + k[0] * data.dt/2;
        double Vx_tmp = Vx + k[1] * data.dt/2;

        k = derivatives(Sx_tmp, Vx_tmp, a);
        out[0] = Sx + k[0] * data.dt;
        out[1] = Vx + k[1] * data.dt;

        return out;
    }

    public double[] derivatives(double Sx, double Vx, double a){
        double[] out = new double[2];
        out[0] = Vx;
        out[1] = a;
        return out;
    }
}
