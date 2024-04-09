package C02_TRAJECTORY_MOTION;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class Graph extends JPanel {
    private int padding = 20;
    private int labelPadding = 20;
    private Color line1Color = Color.ORANGE;
    private Color line2Color = Color.GREEN;
    private Color pointColor = Color.BLACK;
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions;
    private List<Double> sEyValues;
    private List<Double> eEyValues;
    private List<Double> sExValues;
    private List<Double> eExValues;

    public Graph(List<Double> sEyValues, List<Double> eEyValues, List<Double> sExValues, List<Double> eExValues) {
        this.sEyValues = sEyValues;
        this.eEyValues = eEyValues;
        this.sExValues = sExValues;
        this.eExValues = eExValues;

        numberYDivisions = Math.max(sEyValues.size(), eEyValues.size())/2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (sEyValues.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / getYMax();

        List<Point> graphPoints1 = new ArrayList<>();
        for (int i = 0; i < sEyValues.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getYMax() - sEyValues.get(i)) * yScale + padding);
            graphPoints1.add(new Point(x1, y1));
        }

        List<Point> graphPoints2 = new ArrayList<>();
        for (int i = 0; i < eEyValues.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getYMax() - eEyValues.get(i)) * yScale + padding);
            graphPoints2.add(new Point(x1, y1));
        }

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (sEyValues.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getYMax() * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < sExValues.size(); i++) {
            if (sExValues.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (sExValues.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((sExValues.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = (double)((int)(sExValues.get(i)*100))/100 + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(line1Color);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints1.size() - 1; i++) {
            int x1 = graphPoints1.get(i).x;
            int y1 = graphPoints1.get(i).y;
            int x2 = graphPoints1.get(i + 1).x;
            int y2 = graphPoints1.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints1.size(); i++) {
            int x = graphPoints1.get(i).x - pointWidth / 2;
            int y = graphPoints1.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }

        Stroke oldStroke2 = g2.getStroke();
        g2.setColor(line2Color);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints2.size() - 1; i++) {
            int x1 = graphPoints2.get(i).x;
            int y1 = graphPoints2.get(i).y;
            int x2 = graphPoints2.get(i + 1).x;
            int y2 = graphPoints2.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke2);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints2.size(); i++) {
            int x = graphPoints2.get(i).x - pointWidth / 2;
            int y = graphPoints2.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

    private double getYMax() {
        double maxScore = 0;
        for (Double score : sEyValues) {
            if (score > maxScore) maxScore = score;
        }
        for (Double score : eEyValues) {
            if (score > maxScore) maxScore = score;
        }
        return maxScore;
    }
}
