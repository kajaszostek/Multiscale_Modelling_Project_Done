package sample;

public class Cell {

    int id;
    double x, y;
    public int value;
    int[] moore = new int[8];
    double[] mooreDensity = new double[8];

    int[] neumann = new int[4];
    int[] hexagonalRight = new int[6];
    int[] hexagonalLeft = new int[6];
    int[] pentagonalUp = new int[5];
    int[] pentagonalDown = new int[5];
    int[] pentagonalRight = new int[5];
    int[] pentagonalLeft = new int[5];
    boolean hasRecNeighborhood = false;
    boolean isChecked = false;
    int energy;
    double density;
    double sigma;
    boolean isRecrystallized;



    CellType cellType;

    Neighborhood neighborhood;

    Cell(int id) {
        this.id = id;
    }

    void setNeighborhood(int i, int j, Grid grid, int id) {
        switch (id) {
            case 0: {
                neighborhood = new oneLine();
                break;
            }
            case 1: {
                neighborhood = new Moore(i, j, grid);
                moore = this.neighborhood.getArray();
                break;
            }
            case 2: {
                neighborhood = new VonNeumann(i, j, grid);
                neumann = this.neighborhood.getArray();
                break;
            }
            case 3: {
                neighborhood = new HexagonalRight(i, j, grid);
                hexagonalRight = this.neighborhood.getArray();
            }
            case 4: {
                neighborhood = new HexagonalLeft(i, j, grid);
                hexagonalLeft = this.neighborhood.getArray();
            }
            case 5: {
                neighborhood = new PentagonalUp(i, j, grid);
                pentagonalUp = this.neighborhood.getArray();
            }
            case 6: {
                neighborhood = new PentagonalDown(i, j, grid);
                pentagonalDown = this.neighborhood.getArray();
            }
            case 7: {
                neighborhood = new PentagonalLeft(i, j, grid);
                pentagonalLeft = this.neighborhood.getArray();
            }
            case 8: {
                neighborhood = new PentagonalRight(i, j, grid);
                pentagonalRight = this.neighborhood.getArray();
            }
            default: {
                System.out.println("Error - setNeighborhood");
                break;
            }

        }
    }



}
