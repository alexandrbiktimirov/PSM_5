package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {
    public static void main(String[] args) {
        double day = 86400.0;
        double year = 365.0 * day;
        double Tm = 27.3 * day;
        double dt = 6 * 3600.0;
        double wE = 2.0 * Math.PI / year;
        double wM = 2.0 * Math.PI / Tm;
        double Rzs = 1.5e11;
        double Rzk = 3.844e8;
        double moonRatio = 20.0;

        XYSeries sun = new XYSeries("Sun");
        sun.add(0, 0);

        XYSeries earth = new XYSeries("Earth");
        XYSeries moon = new XYSeries("Moon");

        for (double t = 0; t <= year; t += dt) {
            double xE = Rzs * Math.cos(wE * t);
            double yE = Rzs * Math.sin(wE * t);
            earth.add(xE, yE);

            double xM = xE + moonRatio * Rzk * Math.cos(wM * t);
            double yM = yE + moonRatio * Rzk * Math.sin(wM * t);
            moon.add(xM, yM);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(sun);
        dataset.addSeries(earth);
        dataset.addSeries(moon);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Moon Orbit Simulation",
                "X (m)",
                "Y (m)",
                dataset
        );
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.BLACK);
        plot.getDomainAxis().setRange(-2.0e11, 2.0e11);
        plot.getRangeAxis().setRange(-2.0e11, 2.0e11);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new Ellipse2D.Double(-5, -5, 10, 10));
        renderer.setSeriesPaint(0, Color.YELLOW);
        renderer.setSeriesShapesFilled(0, true);

        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShape(1, new Ellipse2D.Double(-2, -2, 4, 4));
        renderer.setSeriesPaint(1, Color.GREEN);

        renderer.setSeriesLinesVisible(2, false);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesShape(2, new Ellipse2D.Double(-1, -1, 2, 2));
        renderer.setSeriesPaint(2, Color.WHITE);
        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 600));

        JFrame frame = new JFrame("Moon Orbit Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}