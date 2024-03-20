package C02;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static JFrame testFrame;
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        double Sx = 0, Sy = 0;
        double Vx = 7, Vy = 7;
        double t = 0, dt = 0.05;
        double gx = 0, gy = -9.81;
        double m = 1;
        double Cx = 0.47;

        System.out.print("""
                Would you like to provide initial data yourself?
                Otherwise standard configuration will be used.
                Please enter (y/n):\s""");
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

        List<List<Double>> standardEuler = standardEuler(Sx, Sy, Vx, Vy, t, dt, gx, gy, m, Cx);
        List<Double> sEtime = standardEuler.get(0);
        List<Double> sExValues = standardEuler.get(1);
        List<Double> sEyValues = standardEuler.get(2);

        List<List<Double>> enhancedEuler = enhancedEuler(Sx, Sy, Vx, Vy, t, dt, gx, gy, m, Cx);
        List<Double> eEtime = enhancedEuler.get(0);
        List<Double> eExValues = enhancedEuler.get(1);
        List<Double> eEyValues = enhancedEuler.get(2);

        //==========================================================================================
        //Testing graph creating
        testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel p = new Panel();
        p.setLayout(new BorderLayout());
        JLabel label = new JLabel("Standard Euler - orange, Enhanced Euler - green");
        p.add(label, BorderLayout.NORTH);
        Graph graph = new Graph(sEyValues, eEyValues, sExValues, eExValues);
        p.add(graph, BorderLayout.CENTER);
        testFrame.add(p);
        testFrame.setBounds(100, 100, 764, 470);
        testFrame.setVisible(true);
        //==========================================================================================

        Path outputFilePath = Paths.get("data.csv");
        Files.deleteIfExists(outputFilePath);
        Files.createFile(outputFilePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv", true));

        writer.append("Standard Euler,,,Enhanced Euler,\nt,x,y,t,x,y\n");
        int leftoff = 0;
        String str;
        for (int i = 0; i < eEtime.size(); i++) {
            str = sEtime.get(i) + "," + sExValues.get(i) + "," + sEyValues.get(i) + "," +
                    eEtime.get(i) + "," + eExValues.get(i) + "," + eEyValues.get(i) + "\n";
            writer.append(str);
            if (i == eEtime.size()-1) leftoff = i+1;
        }
        for (int i = leftoff; i < sEtime.size(); i++) {
            str = sEtime.get(i) + "," + sExValues.get(i) + "," + sEyValues.get(i) + "\n";
            writer.append(str);
        }
        writer.close();

        System.out.print("Trajectory data was written to file, path: " + outputFilePath.toAbsolutePath());
    }

    public static List<List<Double>> standardEuler(
            double Sx,
            double Sy,
            double Vx,
            double Vy,
            double t,
            double dt,
            double gx,
            double gy,
            double m,
            double Cx
    ){
        List<Double> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        List<Double> time = new ArrayList<>();
        xValues.add(Sx);
        yValues.add(Sy);
        time.add(t);

        double ax = (m*gx-Cx*Vx)/m; //x-axis acceleration in t=0
        double ay = (m*gy-Cx*Vy)/m; //y-axis acceleration in t=0
        double dVx = ax*dt; //Change of speed in x-axis
        double dVy = ay*dt; //Change of speed in y-axis

        while (Sy >= 0) {
            t += dt;
            Sx += Vx*dt;
            Sy += Vy*dt;
            Vx += dVx;
            Vy += dVy;
            ax = (m*gx-Cx*Vx)/m;
            ay = (m*gy-Cx*Vy)/m;
            dVx = ax*dt;
            dVy = ay*dt;

            time.add(t);
            xValues.add(Sx);
            yValues.add(Sy);
        }

        List<List<Double>> out = new ArrayList<>();
        out.add(time);
        out.add(xValues);
        out.add(yValues);
        return out;
    }

    public static List<List<Double>> enhancedEuler(
            double Sx,
            double Sy,
            double Vx,
            double Vy,
            double t,
            double dt,
            double gx,
            double gy,
            double m,
            double Cx
    ){
        List<Double> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        List<Double> time = new ArrayList<>();
        xValues.add(Sx);
        yValues.add(Sy);
        time.add(t);

        double ax = (m*gx-Cx*Vx)/m; //x-axis acceleration in t=0
        double ay = (m*gy-Cx*Vy)/m; //y-axis acceleration in t=0
        double dVx = ax*dt/2; //Change of speed in x-axis
        double dVy = ay*dt/2; //Change of speed in y-axis

        while (Sy >= 0) {
            t += dt;
            Vx += dVx;
            Vy += dVy;
            Sx += Vx*dt;
            Sy += Vy*dt;
            ax = (m*gx-Cx*Vx)/m;
            ay = (m*gy-Cx*Vy)/m;
            dVx = ax*dt;
            dVy = ay*dt;

            time.add(t);
            xValues.add(Sx);
            yValues.add(Sy);
        }

        List<List<Double>> out = new ArrayList<>();
        out.add(time);
        out.add(xValues);
        out.add(yValues);
        return out;
    }
}
