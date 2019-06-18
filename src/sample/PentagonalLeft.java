package sample;

public class PentagonalLeft extends Pentagonal {

    Cell up, down, left, leftUp, leftDown;

    public PentagonalLeft(int i, int j, Grid grid) {
        super(3);
        setPentagonalLeft(i,j, grid);
    }
    private void setPentagonalLeft(int i, int j, Grid grid) {

        int xLeft = i - 1;
        int yUp = j - 1;
        int yDown = j + 1;


        if (xLeft < 0) {
            xLeft = grid.size - 1;
        }

        if (yUp < 0) {
            yUp = grid.steps - 1;
        } else if (yDown == grid.steps) {
            yDown = 0;
        }

        this.up = grid.cells[i][yUp];
        valuesOfNeighborhood[0] = this.up.value;
        this.down = grid.cells[i][yDown];
        valuesOfNeighborhood[1]=this.down.value;
        this.left = grid.cells[xLeft][j];
        valuesOfNeighborhood[2]=this.left.value;
        this.leftDown = grid.cells[xLeft][yDown];
        valuesOfNeighborhood[3] = this.leftDown.value;
        this.leftUp = grid.cells[xLeft][yUp];
        valuesOfNeighborhood[4] = this.leftUp.value;
    }

    @Override
    public int[] getArray() {
        return valuesOfNeighborhood;
    }
}
