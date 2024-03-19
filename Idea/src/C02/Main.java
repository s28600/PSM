package C02;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        double Sx = 0, Sy = 0;
        double Vx = 7, Vy = 7;
        double t = 0, dt = 0.05;
        double gx = 0, gy = -9.81;
        double m = 1;
        double Cx = 0.47;

        System.out.print("Would you like to provide initial data yourself?" +
                "\nOtherwise standard configuration will be used." +
                "\nPlease enter (y/n): ");
        if (scanner.next().equals("y")){
            System.out.print("Enter starting position x: ");
            Sx = scanner.nextDouble();
            System.out.print("Enter starting position y: ");
            Sy = scanner.nextDouble();
            System.out.print("Enter starting speed Vx[m/s]: ");
            Vx = scanner.nextDouble();
            System.out.print("Enter starting speed Vy[m/s]: ");
            Vy = scanner.nextDouble();
            System.out.print("Enter sampling time dt[s]: ");
            dt = scanner.nextDouble();
            System.out.print("Enter mass m[kg]: ");
            m = scanner.nextDouble();
            System.out.print("Enter coefficient of friction Cx: ");
            Cx = scanner.nextDouble();
            System.out.println("Data accepted.");
        } else System.out.println("Standard configuration will be used.");

        double ax = (m*gx-Cx*Vx)/m; //x-axis acceleration
        double ay = (m*gy-Cx*Vy)/m; //y-axis acceleration
        double dSx = Vx*dt; //Change of speed in x-axis
        double dSy = Vy*dt; //Change of speed in y-axis
        double dVx = ax*dt; //Change of x-axis acceleration
        double dVy = ay*dt; //Change of y-axis acceleration
        List<Double> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        List<Double> time = new ArrayList<>();
        xValues.add(Sx);
        yValues.add(Sy);
        time.add(t);

        //Standard Euler

        while (Sy >= 0) {
            t += dt;
            Sx += dSx;
            Sy += dSy;
            Vx += dVx;
            Vy += dVy;
            dSx = Vx*dt;
            dSy = Vy*dt;
            ax = (m*gx-Cx*Vx)/m;
            ay = (m*gy-Cx*Vy)/m;
            dVx = ax*dt;
            dVy = ay*dt;

            time.add(t);
            xValues.add(Sx);
            yValues.add(Sy);
        }

        Path outputFilePath = Paths.get("data.csv");
        Files.deleteIfExists(outputFilePath);
        Files.createFile(outputFilePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv", true));

        writer.append("Standard Euler,,\nt,x,y\n");
        for (int i = 0; i < xValues.size(); i++) {
            writer.append(time.get(i) + "," + xValues.get(i) + "," + yValues.get(i) + "\n");
        }
        writer.close();
        
        System.out.print("Trajectory data was written to file, path: " + outputFilePath.toAbsolutePath());
    }
}
