package C05_MoonCycle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Logic EarthAroundSun = new Logic(SolarObjectsPerspective.EarthAroundSun);
        List<String> EarthAroundSunData = EarthAroundSun.calculate();
        Logic MoonAroundEarth = new Logic(SolarObjectsPerspective.MoonAroundEarth);
        List<String> MoonAroundEarthData = MoonAroundEarth.calculate();

        List<String> data = new ArrayList<>();
        for (int i = 0; i < EarthAroundSunData.size(); i++) {
            data.add(EarthAroundSunData.get(i)+Logic.sep+" "+Logic.sep+MoonAroundEarthData.get(i));
        }

        MyFileWriter.writeDataToFile(data, "data.csv");
    }
}
