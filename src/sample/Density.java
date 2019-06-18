package sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Density {

    double A = 86710969050178.5;
    double B = 9.41268203527779;
    double temp = 900;
    double timeStep = 0.001;
    double time = 0;
    double timeMax = 0.2;
    double sigma0 = 0;
    double alpha = 1.9;
    double tmpDensity = 0;
    double dDensity = 0;
    double ni = 0.257 * Math.pow(10, -9);
    double b = 8 * Math.pow(10, 10);
    double criticalDensity = 4.21584014232342E12;
    double criticalDensityForCurrentGrid;
    FileWriter writer;

    Density() throws IOException {

        writer = new FileWriter("C:\\Users\\kaja\\IdeaProjects\\MultiscaleModelling\\src\\sample\\data.txt");

    }

    void countDensity(Grid grid, double percent) throws IOException {

        double density, sigma;

        //criticalDensityForCurrentGrid = criticalDensity/(grid.steps*grid.size);
        criticalDensityForCurrentGrid = 46842668.248;

        for(time=0;time<=timeMax;time+=timeStep){

            writeToData(tmpDensity);
            density = (A / B) + (1 - (A / B)) * Math.exp((-1) * B * time);
            dDensity = density - tmpDensity;

            double dDensityForCell = (dDensity / (grid.size * grid.steps)) * percent;

            for (int i = 0; i < grid.size; i++) {
                for (int j = 0; j < grid.steps; j++) {
                    grid.cells[i][j].density = dDensityForCell;
                    dDensity -= dDensityForCell;
                }
            }

            setDensity(grid);

            //System.out.println(time + " " + density);

            //time += timeStep;
            tmpDensity = density;

        }

        writer.close();

    }

    double countSigma(double density) {
        double sigma;
        sigma = sigma0 + alpha * ni * b * Math.pow(density, 0.5);

        return sigma;
    }

    void setDensity(Grid grid) {

        while (dDensity > 0) {

            Random random = new Random();
            int i = random.nextInt(grid.size);
            Random randomY = new Random();
            int j = randomY.nextInt(grid.steps);

            if (grid.cells[i][j].energy == 0) {

                double probability = random.nextDouble();
                double randomPackage = random.nextDouble() * dDensity;

                if (randomPackage <= dDensity && probability <= 0.2) {
                    grid.cells[i][j].density += randomPackage;
                    dDensity -= randomPackage;
                }
                if (dDensity < 0.00001) {
                    dDensity = 0;
                }
            } else {

                double probability = random.nextDouble();
                double randomPackage = dDensity * random.nextDouble();

                if (randomPackage <= dDensity && probability > 0.2) {
                    grid.cells[i][j].density += randomPackage;
                    dDensity -= randomPackage;
                }
                if (dDensity < 0.00001) {
                    dDensity = 0;
                }
            }


        }

    }

    void writeToData(double density) throws IOException {

        String content = String.valueOf(density);

        writer.write(content);
        writer.write("\n");

    }

}

