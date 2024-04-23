package C06_StringMotion;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> data = Util.calculate();
        MyFileWriter.writeDataToFile(data, "data.csv");
    }
}
