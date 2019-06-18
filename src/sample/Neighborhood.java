package sample;

public abstract class Neighborhood {

    String name;
    int id;
    Cell left, right;

    Neighborhood(int i) {
        id = i;

        switch (id)
        {
            case 0: name = "1D"; break;
            case 1: name = "Moore"; break;
            case 2: name = "Von Neumann"; break;
            case 3: name = "Hexagonal Right"; break;
            case 4: name = "Hexagonal Left"; break;
            case 5: name = "Pentagonal Up"; break;
            case 6: name = "Pentagonal Down"; break;
            case 7: name = "Pentagonal Left"; break;
            case 8: name = "Pentagonal Right"; break;

            default: name = "Error";
        }

    }

    protected Neighborhood() {
    }

    public abstract int[] getArray();

    //public abstract double [] getArrayDensity();
}
