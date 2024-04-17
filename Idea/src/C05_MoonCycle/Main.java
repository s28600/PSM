package C05_MoonCycle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        double scale = 20;
        Logic EarthAroundSun = new Logic(SolarObjectsPerspective.EarthAroundSun);
        List<double[]> EarthAroundSunData = EarthAroundSun.calculate();
        Logic MoonAroundEarth = new Logic(SolarObjectsPerspective.MoonAroundEarth);
        List<double[]> MoonAroundEarthData = MoonAroundEarth.calculate();

        List<String> data = new ArrayList<>();
        data.add("Earth_x"+Logic.sep+"Earth_y"+Logic.sep+"Moon_x"+Logic.sep+"Moon_y");
        StringBuilder sb;
        for (int i = 0; i < EarthAroundSunData.size(); i++) {
            sb = new StringBuilder();
            sb.append(EarthAroundSunData.get(i)[1]/scale).append(Logic.sep);
            sb.append(EarthAroundSunData.get(i)[2]/scale).append(Logic.sep);
            sb.append(EarthAroundSunData.get(i)[1]/scale+MoonAroundEarthData.get(i)[1]).append(Logic.sep);
            sb.append(EarthAroundSunData.get(i)[2]/scale+MoonAroundEarthData.get(i)[2]);
            data.add(sb.toString());
        }

        MyFileWriter.writeDataToFile(data, "data.csv");
    }
}
