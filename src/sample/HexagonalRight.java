package sample;

public class HexagonalRight extends Hexagonal {

    Cell leftUp, rightDown;

    public HexagonalRight(int i, int j, Grid grid) {
        super(3);
        setHexagonalRight(i,j,grid);
    }

    private void setHexagonalRight(int i, int j, Grid grid) {

        int xLeft = i - 1;
        int xRight = i + 1;
        int yUp = j - 1;
        int yDown = j + 1;


        if (xLeft < 0) {
            xLeft = grid.size - 1;
        } else if (xRight == grid.size) {
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
        valuesOfNeighborhood[1]=this.down.value;
        this.left = grid.cells[xLeft][j];
        valuesOfNeighborhood[2]=this.left.value;
        this.right = grid.cells[xRight][j];
        valuesOfNeighborhood[3]=this.right.value;
        this.leftUp = grid.cells[xLeft][yUp];
        valuesOfNeighborhood[4] = this.leftUp.value;
        this.rightDown = grid.cells[xRight][yDown];
        valuesOfNeighborhood[5] = this.rightDown.value;
    }

    @Override
    public int[] getArray() {
        return valuesOfNeighborhood;
    }
}
