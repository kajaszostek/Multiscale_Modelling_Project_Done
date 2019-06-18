package sample;

public class oneLine extends Neighborhood {

    oneLine() {
        super(0);
    }

    @Override
    public int[] getArray() {
        return new int[0];
    }

    void setOneLineNeighborhood(Grid grid, int i, int j)
    {
        left = grid.cells[i-1][j];
        right = grid.cells[i+1][j];
    }

}
