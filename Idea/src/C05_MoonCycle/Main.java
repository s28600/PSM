package C05_MoonCycle;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MyFileWriter.writeDataToFile(Logic.calculate(), "data.csv");
    }
}
