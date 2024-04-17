package C05_MoonCycle;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Logic logic = new Logic(SolarObjectsPerspective.MoonAroundEarth);
        MyFileWriter.writeDataToFile(logic.calculate(), "data.csv");
    }
}
