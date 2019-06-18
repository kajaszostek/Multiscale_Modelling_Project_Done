package sample;

public class Moore extends Neighborhood {

    Cell leftUp, leftDown, rightUp, rightDown, up, down;
    int[] valuesOfNeighborhood = new int[8];
    double[] valuesOfDensity = new double[8];

    Moore(int i, int j, Grid grid) {
        super(1);

        Cell lu, ld, l, ru, rd, r, d, u;

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

        lu = grid.cells[xLeft][yUp];
        u = grid.cells[i][yUp];
        ru = grid.cells[xRight][yUp];
        l = grid.cells[xLeft][j];
        r = grid.cells[xRight][j];
        ld = grid.cells[xLeft][yDown];
        d = grid.cells[i][yDown];
        rd = grid.cells[xRight][yDown];

        setMoore(lu, ld, l, r, ru, rd, u, d);
        setMooreIsRecrystallized(lu, ld, l, r, ru, rd, u, d);
    }

    Moore(int i, int j, Grid grid, String str){
        Cell lu, ld, l, ru, rd, r, d, u;

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

        lu = grid.cells[xLeft][yUp];
        u = grid.cells[i][yUp];
        ru = grid.cells[xRight][yUp];
        l = grid.cells[xLeft][j];
        r = grid.cells[xRight][j];
        ld = grid.cells[xLeft][yDown];
        d = grid.cells[i][yDown];
        rd = grid.cells[xRight][yDown];

        setMooreIsRecrystallized(lu, ld, l, r, ru, rd, u, d);
    }

    void setMoore(Cell lu, Cell ld, Cell l, Cell r, Cell ru, Cell rd, Cell u, Cell d) {

        leftDown = ld;
        valuesOfNeighborhood[0] = leftDown.value;
        leftUp = lu;
        valuesOfNeighborhood[1] = leftUp.value;
        left = l;
        valuesOfNeighborhood[2] = left.value;
        rightDown = rd;
        valuesOfNeighborhood[3] = rightDown.value;
        rightUp = ru;
        valuesOfNeighborhood[4] = rightUp.value;
        right = r;
        valuesOfNeighborhood[5] = right.value;
        up = u;
        valuesOfNeighborhood[6] = up.value;
        down = d;
        valuesOfNeighborhood[7] = down.value;

    }

    void setMooreIsRecrystallized(Cell lu, Cell ld, Cell l, Cell r, Cell ru, Cell rd, Cell u, Cell d){
        leftDown = ld;
        valuesOfDensity[0] = leftDown.density;
        leftUp = lu;
        valuesOfDensity[1] = leftUp.density;
        left = l;
        valuesOfDensity[2] = left.density;
        rightDown = rd;
        valuesOfDensity[3] = rightDown.density;
        rightUp = ru;
        valuesOfDensity[4] = rightUp.density;
        right = r;
        valuesOfDensity[5] = right.density;
        up = u;
        valuesOfDensity[6] = up.density;
        down = d;
        valuesOfDensity[7] = down.density;
    }

    @Override
    public int[] getArray() {
        return valuesOfNeighborhood;
    }

//    @Override
//    public double[] getArrayDensity() {
//        return valuesOfDensity;
//    }
}
