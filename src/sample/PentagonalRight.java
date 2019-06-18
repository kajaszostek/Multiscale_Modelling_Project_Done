package sample;

public class PentagonalRight extends Pentagonal {

    Cell up, down, right, rightUp, rightDown;

    public PentagonalRight(int i, int j, Grid grid) {
        super(4);
        setPentagonalRight(i, j, grid);
    }

    private void setPentagonalRight(int i, int j, Grid grid) {

        int xRight = i + 1;
        int yUp = j - 1;
        int yDown = j + 1;

        if (xRight == grid.size) {
            xRight = 0;
        }

        if (yUp < 0) {
            yUp = grid.steps - 1;
        } else if (yDown == grid.steps) {
            yDown = 0;
        }

        this.up = grid.cells[i][yUp];
        valuesOfNeighborhood[0] = this.up.value;

        this.down = grid.cells[i][yDown];
        valuesOfNeighborhood[1] = this.down.value;

        this.right = grid.cells[xRight][j];
        valuesOfNeighborhood[2] = this.right.value;

        this.rightUp = grid.cells[xRight][yUp];
        valuesOfNeighborhood[3] = this.rightUp.value;

        this.rightDown = grid.cells[xRight][yDown];
        valuesOfNeighborhood[4] = this.rightDown.value;

    }

    @Override
    public int[] getArray() {
        return valuesOfNeighborhood;
    }
}
