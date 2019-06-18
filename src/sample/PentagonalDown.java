package sample;

public class PentagonalDown extends Pentagonal {

    Cell left, right, down, leftDown, rightDown;

    public PentagonalDown(int i, int j, Grid grid) {
        super(2);
        setPentagonalDown(i, j, grid);
    }

    private void setPentagonalDown(int i, int j, Grid grid) {

        int xRight = i + 1;
        int yDown = j + 1;
        int xLeft = i - 1;

        if (xLeft < 0) {
            xLeft = grid.size - 1;
        } else if (xRight == grid.size) {
            xRight = 0;
        }

        if (yDown == grid.steps) {
            yDown = 0;
        }

        this.down = grid.cells[i][yDown];
        valuesOfNeighborhood[0] = this.down.value;

        this.right = grid.cells[xRight][j];
        valuesOfNeighborhood[1] = this.right.value;

        this.left = grid.cells[xLeft][j];
        valuesOfNeighborhood[2] = this.left.value;

        this.rightDown = grid.cells[xRight][yDown];
        valuesOfNeighborhood[3] = this.rightDown.value;

        this.leftDown = grid.cells[xLeft][yDown];
        valuesOfNeighborhood[4] = this.leftDown.value;
    }

    @Override
    public int[] getArray() {
        return valuesOfNeighborhood;
    }
}
