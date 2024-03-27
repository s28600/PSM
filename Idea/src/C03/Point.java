package C03;

public class Point {
    final double g = -9.81;
    double a, w = 0, dt, l;

    public Point() {
        a = Math.toRadians(45);
        dt = 0.004;
        l = 1;
    }

    @Override
    public String toString() {
        return "Point{" +
                "g=" + g +
                ", a=" + a +
                ", w=" + w +
                ", dt=" + dt +
                ", l=" + l +
                '}';
    }
}
