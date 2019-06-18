package sample;

public class PentagonalUp extends Pentagonal {

    Cell up, left, right, leftUp, rightUp;

    PentagonalUp(int i, int j, Grid grid){
        super(1);
        setPentagonalUp(i,j,grid);
    }

    private void setPentagonalUp(int i, int j, Grid grid) {

        int xRight = i + 1;
        int yUp = j - 1;
        int xLeft = i - 1;

        if (xLeft < 0) {
            xLeft = grid.size - 1;
        } else if (xRight == grid.size) {
            xRight = 0;
        }

        if (yUp < 0) {
            yUp = grid.steps - 1;
        }

        this.up = grid.cells[i][yUp];
        valuesOfNeighborhood[0] = this.up.value;

        this.right = grid.cells[xRight][j];
        valuesOfNeighborhood[1] = this.right.value;

        this.left = grid.cells[xLeft][j];
        valuesOfNeighborhood[2] = this.left.value;

        this.rightUp = grid.cells[xRight][yUp];
        valuesOfNeighborhood[3] = this.rightUp.value;

        this.leftUp = grid.cells[xLeft][yUp];
        valuesOfNeighborhood[4] = this.leftUp.value;
    }

    @Override
    public int[] getArray() {
        return valuesOfNeighborhood;
    }

}
