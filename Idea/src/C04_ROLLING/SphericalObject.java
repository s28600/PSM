package C04_ROLLING;

public class SphericalObject {
    String label;
    double r;
    double m;
    double I;

    private SphericalObject(String label, double r, double m, double i) {
        this.label = label;
        this.r = r;
        this.m = m;
        I = i;
    }

    public static SphericalObject Ball(double r, double m){
        double I = ((double)2/5)*m*Math.pow(r, 2);
        return new SphericalObject("Ball", r, m, I);
    }

    public static SphericalObject Sphere(double r, double m){
        double I = ((double)2/3)*m*Math.pow(r, 2);
        return new SphericalObject("Sphere", r, m, I);
    }

    @Override
    public String toString() {
        return label + ":" +
                "\n\tRadius: " + r + "[m]" +
                "\n\tMass: " + m + "[kg]";
    }
}
