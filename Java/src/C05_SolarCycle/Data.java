package C05_SolarCycle;

public class Data {
    public static final double
            G = 6.6743*Math.pow(10, -11), //Stała grawitacyjna [Nm^2/kg^2]
            Ms = 1.989*Math.pow(10, 30), //Masa Słońca [kg]
            Mz = 5.972*Math.pow(10, 24), //Masa Ziemi [kg]
            Rzs = 1.5*Math.pow(10, 8)*1000, //Odległość Ziemia-Słońce [m]
            Rzk = 3.844*Math.pow(10, 5)*1000; //Odległość Ziemia-Księżyc [m]

    public static double dt = 7200;
}
