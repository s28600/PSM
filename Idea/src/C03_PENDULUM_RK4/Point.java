package C03_PENDULUM_RK4;

public class Point {
    double a, w = 0, l;

    public Point() {
        a = Math.toRadians(45);
        l = 1;
    }

    @Override
    public String toString() {
        return "Point{" +
                ", a=" + a +
                ", w=" + w +
                ", l=" + l +
                '}';
    }
}
