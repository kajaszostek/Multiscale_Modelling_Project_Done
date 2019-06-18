package sample;

public class Rule {

    int values[] = new int[8];

    Rule(int ruleNumber, Cell[][] array, int size, int steps) {
        for (int i = 0; i < 8; i++) {
            values[i] = 0;
        }
        convertToBinary(ruleNumber);
        this.setArray(array, size, steps);
    }

    void convertToBinary(int intNumber) {

        int i = 0;

        while (intNumber > 0) {
            this.values[i] = intNumber % 2;
            i++;
            intNumber = intNumber / 2;
        }

        for (int j = 7; j >= 0; j--) {
            System.out.print(this.values[j]);
        }

    }

    void setArray(Cell[][] array, int size, int steps) {
        int l, m, r, tmpSteps = 0;

        do {
            for (int y = 0; y < steps - 1; y++) {
                for (int x = 0; x < size; x++) {

                    if (x == 0) {
                        l = array[size - 1][y].value;
                        m = array[x][y].value;
                        r = array[x + 1][y].value;
                    } else if (x == size - 1) {
                        l = array[x - 1][y].value;
                        m = array[x][y].value;
                        r = array[0][y].value;
                    } else {
                        l = array[x - 1][y].value;
                        m = array[x][y].value;
                        r = array[x + 1][y].value;
                    }

                    setValue(array, x, y, l, m, r);
                }
            }

            tmpSteps++;
        }
        while (tmpSteps != steps);
    }

    void setValue(Cell[][] array, int x, int y, int l, int m, int r) {

        if (l == 1 & m == 1 & r == 1) {
            array[x][y + 1].value = values[7];
        } else if (l == 1 & m == 1 & r == 0) {
            array[x][y + 1].value = values[6];
        } else if (l == 1 & m == 0 & r == 1) {
            array[x][y + 1].value = values[5];
        } else if (l == 1 & m == 0 & r == 0) {
            array[x][y + 1].value = values[4];
        } else if (l == 0 & m == 1 & r == 1) {
            array[x][y + 1].value = values[3];
        } else if (l == 0 & m == 1 & r == 0) {
            array[x][y + 1].value = values[2];
        } else if (l == 0 & m == 0 & r == 1) {
            array[x][y + 1].value = values[1];
        } else if (l == 0 & m == 0 & r == 0) {
            array[x][y + 1].value = values[0];
        }

    }

    void showArray(Cell[][] array, int size, int steps) {
        for (int y = 0; y < steps; y++) {
            System.out.println();
            System.out.print("[");
            for (int x = 0; x < size; x++) {
                System.out.print(array[x][y].value);
            }
            System.out.print("]");
        }
    }
}
