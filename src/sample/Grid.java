package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class Grid {

    int[][] array;
    Cell[][] cells;
    int size;
    int steps;
    double dxdy;
    double xk = 0;
    double yk = 0;
    int value = 2;
    double A, B, temp;

    ArrayList<CellType> cellTypeList = new ArrayList<>();
    HashMap<Integer, Color> cellTypes = new HashMap<>();

    Grid(int size, int steps, Canvas canvas) {

        cells = new Cell[size][steps];
        array = new int[size][steps];

        dxdy = Math.min(canvas.getWidth() / size, canvas.getHeight() / steps);

        int counter = 1;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {

                cells[i][j] = new Cell(counter);
                cells[i][j].x = (i * dxdy); // i == x
                cells[i][j].y = (j * dxdy); // j == y columns
                cells[i][j].value = 0;

                counter++;
            }
        }
        cells[size / 2][0].value = 1;
        this.size = size;
        this.steps = steps;

        showXYandValues();
    }

    public void draw(GraphicsContext graphicsContext) {

        cellTypes.put(-1, Color.WHITE);
        cellTypes.put(0, Color.SANDYBROWN);
        cellTypes.put(1, Color.BLACK);

        cellTypeList.add(new CellType(-1));
        cellTypeList.get(cellTypeList.size() - 1).setColor(Color.WHITE);

        cellTypeList.add(new CellType(0));
        cellTypeList.get(cellTypeList.size() - 1).setColor(Color.SANDYBROWN);

        cellTypeList.add(new CellType(1));
        cellTypeList.get(cellTypeList.size() - 1).setColor(Color.BLACK);

        graphicsContext.clearRect(0, 0, 600, 600);

        double w = dxdy * size;
        double h = dxdy * steps;

        double x, y;

        graphicsContext.setFill(Color.SANDYBROWN);

        graphicsContext.fillRect(0, 0, w, h);

        for (int i = 0; i < size; i++) {
            x = i * dxdy;

            for (int j = 0; j < steps; j++) {
                y = j * dxdy;
                {
                    if (cells[i][j].value != 0) {

                        int key = cells[i][j].value;

                        Color fxColor = cellTypes.get(key);

                        graphicsContext.setFill(fxColor);
                        graphicsContext.fillRect(x, y, dxdy, dxdy);
                    }

                }

            }
        }
        setGridLines(graphicsContext);

    }

    public void drawEnergyGrid(Grid tmpGrid, GraphicsContext graphicsContext) {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                this.cells[i][j].value = tmpGrid.cells[i][j].energy;
            }
        }

        Color color = Color.rgb(204,229,255);
        cellTypes.put(0,color);

        color = Color.rgb(153, 204, 255);
        cellTypes.put(1,color);

        color = Color.rgb(102,178,255);
        cellTypes.put(2,color);

        color = Color.rgb(51,153,255);
        cellTypes.put(3,color);

        color = Color.rgb(0,128,255);
        cellTypes.put(4,color);

        color = Color.rgb(0,102,204);
        cellTypes.put(5,color);

        color = Color.rgb(0,76,153);
        cellTypes.put(6,color);

        color = Color.rgb(0,51,102);
        cellTypes.put(7,color);

        color = Color.rgb(0,25,51);
        cellTypes.put(8,color);

        graphicsContext.clearRect(0, 0, 600, 600);

        double w = dxdy * size;
        double h = dxdy * steps;

        double x, y;

        graphicsContext.setFill(Color.SANDYBROWN);

        graphicsContext.fillRect(0, 0, w, h);

        for (int i = 0; i < size; i++) {
            x = i * dxdy;

            for (int j = 0; j < steps; j++) {
                y = j * dxdy;
                {
                        int key = cells[i][j].value;

                        Color fxColor = cellTypes.get(key);

                        graphicsContext.setFill(fxColor);
                        graphicsContext.fillRect(x, y, dxdy, dxdy);

                }

            }
        }
        setGridLines(graphicsContext);



    }

    public void findAndChange(double x, double y, GraphicsContext graphics) {

        double xp = 400, yp = 158;
        int val = 0;

        for (int i = 0; i < size; i++) {
            if (x < xp + dxdy && x >= xp) {
                {
                    xk = i * dxdy;
                }

                for (int j = 0; j < steps; j++) {
                    if (y < yp + dxdy && y >= yp) {
                        yk = j * dxdy;
                        break;
                    }
                    if (yp == steps * dxdy) {
                        yp = 0;
                    } else {
                        yp += dxdy;
                    }
                }
            }
            if (xp == size * dxdy)
                xp = 0;
            else {
                xp += dxdy;
            }
        }


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                if (cells[i][j].x == xk && cells[i][j].y == yk) {
                    if (cells[i][j].value == 0) {
                        cells[i][j].value = 1;
                        val = 1;
                    } else if (cells[i][j].value == 1) {
                        cells[i][j].value = 0;
                        val = 0;
                    }
                }
            }
        }

        if (val == 0) {
            graphics.setFill(Color.SANDYBROWN);
            graphics.fillRect(xk, yk, dxdy, dxdy);
            setGridLines(graphics);
        } else if (val == 1) {
            graphics.setFill(Color.BLACK);
            graphics.fillRect(xk, yk, dxdy, dxdy);
            setGridLines(graphics);
        }

    }

    public void setGridLines(GraphicsContext graphics) {
        double x, y;
        graphics.setStroke(Color.WHITE);
        graphics.setLineWidth(1);
        x = 0;
        y = 0;
        double xend = size * dxdy;
        double yend = steps * dxdy;
        for (int i = 0; i <= size; i++) {
            graphics.strokeLine(x, 0, x, yend);
            x += dxdy;
        }

        for (int i = 0; i <= steps; i++) {
            graphics.strokeLine(0, y, xend, y);
            y += dxdy;
        }
    }

    public void showXYandValues() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                System.out.println("Cell " + cells[i][j].id + " x=" + cells[i][j].x + " y=" + cells[i][j].y + " value = " + cells[i][j].value);
            }
        }
    }

    public void findAndChangeManyColors(LinkedList<Color> colorsList, double x, double y, GraphicsContext graphics) {

//        cellTypeList.add(new CellType(value));
//        cellTypeList.get(cellTypeList.size()-1).setColor(colorsList);
//        Color color = colorsList.get(colorsList.size()-1);
//        cellTypes.put(value, color);
//        value++;
//        Color fxColor = color;

        double xp = 400, yp = 158;
        int val = 0;

        for (int i = 0; i < size; i++) {
            if (x < xp + dxdy && x >= xp) {
                {
                    xk = i * dxdy;
                }

                for (int j = 0; j < steps; j++) {
                    if (y < yp + dxdy && y >= yp) {
                        yk = j * dxdy;
                        break;
                    }
                    if (yp == steps * dxdy) {
                        yp = 0;
                    } else {
                        yp += dxdy;
                    }
                }
            }
            if (xp == size * dxdy)
                xp = 0;
            else {
                xp += dxdy;
            }
        }

        Color fxColor = null;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                if (cells[i][j].x == xk && cells[i][j].y == yk) {
                    cells[i][j].value = value;
                    cells[i][j].cellType = new CellType(value);
                    cells[i][j].cellType.setColor(colorsList);
                    cellTypes.put(value, cells[i][j].cellType.color);
                    value++;
                    fxColor = cells[i][j].cellType.color;
                    graphics.setFill(fxColor);
                }
            }
        }
        //graphics.setFill(fxColor);
        graphics.fillRect(xk, yk, dxdy, dxdy);
        setGridLines(graphics);
    }

}

