package C05_MoonCycle;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Data.dt = 7200;
        MyFileWriter.writeDataToFile(Logic.calculate(SolarObjectsPerspective.EarthAroundSun), "data.csv");
    }
}
