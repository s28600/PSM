package C04_ROLLING;

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

    public static void main(String[] args) {
        System.out.println("Using standard configuration:");
        System.out.println(ball);
        System.out.println(sphere);


    }

    public void calculateTrajectory(SphericalObject object){
        double t = 0, Sx = 0, Sy = object.r, Vx = 0;

    }

    public static double[] derivatives(double Sx, double dVx, SphericalObject object){
        double[] out = new double[2];
        out[0] = dVx;
        out[1] = g*Math.sin(alpha)/(1+object.I/m*Math.pow(r, 2));
        return out;
    }
}
